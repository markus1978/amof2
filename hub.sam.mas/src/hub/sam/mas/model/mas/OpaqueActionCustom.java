package hub.sam.mas.model.mas;

import hub.sam.mas.execution.ExecutionEnvironment;
import hub.sam.mas.execution.SemanticException;
import hub.sam.mas.model.petrinets.Place;
import hub.sam.mas.model.petrinets.Transition;
import hub.sam.mof.mofinstancemodel.MofClassSemantics;
import hub.sam.mof.reflection.ArgumentImpl;
import hub.sam.mof.util.ListImpl;

import java.util.Collection;
import java.util.Vector;

import cmof.Operation;
import cmof.Property;
import cmof.Type;
import cmof.UmlClass;
import cmof.common.ReflectiveSequence;
import cmof.reflection.Argument;

public class OpaqueActionCustom extends OpaqueActionDlg {
    
	@Override
	public void fire(ActivityInstance context) {
		// perform all as semantics actions
		Object contextValue = null;
        MofClassSemantics semantics = null;
		switch(self.getActionKind()) {
			case EXPRESSION:
                DebugInfo.printInfo("eval " + self.getActionBody());
				propagateOutput(evaluateExpression(self, self.getActionBody(), self.getInput(), context), true, context);
				break;
			case CALL:
                DebugInfo.printInfo("call " + self.getActionBody());
				String operationName = getActionBody();				;
				contextValue = context.getOclContext();
				ReflectiveSequence<Argument> arguments = new ListImpl<Argument>();
				for(InputPin pin: self.getInput()) {
					if (pin instanceof ContextPin) {
						contextValue = ((PinInstance)context.getPlaces(pin)).getValue();
					} else {
						arguments.add(new ArgumentImpl(null, ((PinInstance)context.getPlaces(pin)).getValue()));
					}
				}
				semantics = MofClassSemantics.createClassClassifierForUmlClass((UmlClass)
						getTypeForObject(contextValue));
				
				for (InputPin inputPin: self.getInput()) {
					if (!(inputPin instanceof ContextPin)) {
						operationName += "_" + getTypeOfValue(((PinInstance)context.getPlaces(inputPin)).getValue());
					}
				}      
			    Operation operation = semantics.getFinalOperation(operationName);
			    if (operation == null) {
		            throw new SemanticException("The arguments of action " + toString() + "(call " + operationName + ") does not match to an operation in the given context.");
		        }			    
			    Object result = ((cmof.reflection.Object)contextValue).invokeOperation(operation, arguments);
		        propagateOutput(result, false, context);
                DebugInfo.printInfo("end call " + self.getActionBody());
		        break;						
			case PRINT:
				System.out.println(self.getActionBody());
				break;							
			case WRITE_STRUCTURAL_FEATURE:
                DebugInfo.printInfo("set " + self.getActionBody());
				contextValue = context.getOclContext();
				Object featureValue = null;
				for(InputPin pin: self.getInput()) {
					if (pin instanceof ContextPin) {
						contextValue = ((PinInstance)context.getPlaces(pin)).getValue();
					} else {
						if (featureValue != null) {
							throw new SemanticException("To much input pins for a write structural feature action.");
						}
						featureValue = ((PinInstance)context.getPlaces(pin)).getValue();
					}
				}				
				Property feature = resolveFeature(contextValue);				
				((cmof.reflection.Object)contextValue).set(feature, featureValue);
				break;
			case WRITE_STRUCTURAL_FEATURE_VALUE:
                DebugInfo.printInfo("add " + self.getActionBody());
				contextValue = context.getOclContext();
				featureValue = null;
				for(InputPin pin: self.getInput()) {
					if (pin instanceof ContextPin) {
						contextValue = ((PinInstance)context.getPlaces(pin)).getValue();
					} else {
						if (featureValue != null) {
							throw new SemanticException("To much input pins for a write structural feature action.");
						}
						featureValue = ((PinInstance)context.getPlaces(pin)).getValue();
					}
				}				
				feature = resolveFeature(contextValue);				
				((ReflectiveSequence)((cmof.reflection.Object)contextValue).get(feature)).add(featureValue);
				break;
			case REMOVE_STRUCTURAL_FEATURE_VALUE:
                DebugInfo.printInfo("remove " + self.getActionBody());
				contextValue = context.getOclContext();
				featureValue = null;
				for(InputPin pin: self.getInput()) {
					if (pin instanceof ContextPin) {
						contextValue = ((PinInstance)context.getPlaces(pin)).getValue();
					} else {
						if (featureValue != null) {
							throw new SemanticException("To much input pins for a write structural feature action.");
						}
						featureValue = ((PinInstance)context.getPlaces(pin)).getValue();
					}
				}				
				feature = resolveFeature(contextValue);				
				((ReflectiveSequence)((cmof.reflection.Object)contextValue).get(feature)).remove(featureValue);
				break;
			case CREATE_OBJECT:
                DebugInfo.printInfo("create " + self.getActionBody());
                contextValue = context.getOclContext();
                for(InputPin pin: self.getInput()) {
                    if (pin instanceof ContextPin) {
                        contextValue = ((PinInstance)context.getPlaces(pin)).getValue();
                    } else {                     
                        throw new SemanticException("Only a single context pin is allowed for a create action.");                      
                    }
                }
                semantics = MofClassSemantics.createClassClassifierForUmlClass((UmlClass)
                        getTypeForObject(contextValue));
                propagateOutput(semantics, false, context);
                break;
			case PRINT_EXPRESSION:
                System.out.println(evaluateExpression(self, self.getActionBody(), self.getInput(), context));
                break;
			default:
				System.out.println("WARNING: unknown action kind");
		}
		
		// call the actual petri net semantics perfom method
		((Transition)getSuper(Transition.class)).fire(context);
	}	
	
	private Property resolveFeature(Object contextValue) {
		
		Property result = MofClassSemantics.createClassClassifierForUmlClass(
				(UmlClass)getTypeForObject(contextValue)).getProperty(self.getActionBody());
		if (result == null) {
			throw new SemanticException("Feature could not be resolved.");				
		}
		return result;
	}
	
	private void propagateOutput(Object value, boolean forced, ActivityInstance context) {
		if (self.getOutput().size() != 1) {
			if (forced || self.getOutput().size() > 1) { 
				throw new SemanticException("Illegal argument count for expression.");
			}
			return;
		} else {
			OutputPin pin = self.getOutput().get(0);
			for (ActivityEdge edge: pin.getOutgoing()) {
				ActivityNode target = edge.getTarget();
				if (target instanceof ValueNode && "return".equals(((ValueNode)target).getName())) {
					context.setReturn(value);
				}
			}
		}
		for(Place place: self.getOutputPlaces()) {
			if (place instanceof InputPin) {
				((PinInstance)context.getPlaces(place)).setValue(value);
			}
		}		
	}

	protected static Type getTypeForObject(Object object) {
        if (object == null) {
            return null;
        } else if (object instanceof cmof.reflection.Object) {
			return ((cmof.reflection.Object)object).getMetaClass();
		} else {
			throw new SemanticException("non object contexts types not allowed yet.");
		}
	}
	
	protected static Object evaluateExpression(ActivityNode self, String body, Iterable<? extends InputPin> pins,
            ActivityInstance context) {
		ExecutionEnvironment env = context.getEnv();	
		
		Object contextValue = context.getOclContext();
		Collection<ContextExtensionPin> extensions = new Vector<ContextExtensionPin>();
		for (InputPin pin: pins) {
			if (pin instanceof ContextExtensionPin) {
				extensions.add((ContextExtensionPin)pin);
			} else if (pin instanceof ContextPin) {
				contextValue = ((PinInstance)context.getPlaces(pin)).getValue();
			} else {
				throw new SemanticException("no non context input pins for expressions allowed.");
			}
		}
		boolean additionalAttribute = false;
		for (ContextExtensionPin pin: extensions) {
			Object contextExtensionValue = ((PinInstance)context.getPlaces(pin)).getValue();
			env.addAdditionalContextAttribute(pin.getExtensionName(), 
					contextExtensionValue, OpaqueActionCustom.getTypeForObject(contextExtensionValue), 
					OpaqueActionCustom.getTypeForObject(contextValue));
			additionalAttribute = true;
		}
		Object result = env.evaluateInvariant(body, OpaqueActionCustom.getTypeForObject(contextValue), contextValue);
		if (additionalAttribute) {
			env.removeAdditionalAttribute();
		}
		return result;
	}
	
	private String getTypeOfValue(Object value) {
		if (value instanceof cmof.reflection.Object) {
			return ((cmof.reflection.Object)value).getMetaClass().getQualifiedName();
		} else if (value instanceof Integer) {
			return "Core.PrimitiveTypes.Integer";
		} else if (value instanceof Boolean) {
			return "Core.PrimitiveTypes.Boolean";
		} else if (value instanceof String) {
			return "String";
		} else if (value instanceof Long) {
			return "Core.PrimitiveTypes.Long";
		} else {
			return value.getClass().getCanonicalName();
		}
	}
}
