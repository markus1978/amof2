package hub.sam.mof.ocl;

import hub.sam.mof.instancemodel.ClassifierSemantics;
import hub.sam.mof.reflection.ImplementationsImpl;
import hub.sam.mof.reflection.ObjectDlg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cmof.Constraint;
import cmof.Feature;
import cmof.OpaqueExpression;
import cmof.Operation;
import cmof.Parameter;
import cmof.ParameterDirectionKind;
import cmof.Property;
import cmof.UmlClass;

public class OclImplementations  extends ImplementationsImpl {
	
	private final UmlClass fMetaClass;
	private final Map<Feature, Implementation> fImplementations = new HashMap<Feature, Implementation>();
	
	class Implementation {
		boolean isImplementation;
		String body;
		Implementation(boolean isImplementation, String body) {
			super();
			this.isImplementation = isImplementation;
			this.body = body;
		}		
	}
	
	public OclImplementations(List<ObjectDlg> delegates, UmlClass forMetaClass) {
		super(delegates, new HashMap());
		this.fMetaClass = forMetaClass;
		for (Object content: forMetaClass.getMember()) {
			if (content instanceof Constraint) {
				Constraint constraint = (Constraint)content;
				for (Object constraintElement: constraint.getConstrainedElement()) {					
					if (constraintElement instanceof Property) {
						fImplementations.put((Property)constraintElement, implementationFor(OclImplementationsHelper.getOclImplementation(constraint)));
					}
				}
			}
		}
	}

	@Override
	public boolean hasImplementationFor(Operation operatoin, ClassifierSemantics<Property, Operation, String> semantics) {
		return implementationFor(operatoin).isImplementation;
	}
		
	
	private Implementation implementationFor(Operation operation) {
		Implementation impl = fImplementations.get(operation);
		if (impl == null) {
			String oclImplementationExpression = OclImplementationsHelper.getOclImplementation(operation);
			impl = implementationFor(oclImplementationExpression);
			fImplementations.put(operation, impl);
		}
		return impl;
	}

	private Implementation implementationFor(String oclImplementationExpression) {			
		if (oclImplementationExpression != null) {
			return new Implementation(true, oclImplementationExpression);
		} else {
			return new Implementation(false, null);
		}				
	}

	@Override
	public boolean hasImplementationFor(Property property, ClassifierSemantics<Property, Operation, String> semantics) {
		return implementationFor(property).isImplementation;
	}
	
	private Implementation implementationFor(Property property) {
		Implementation impl = fImplementations.get(property);
		if (impl == null) {
			return new Implementation(false, null);
		} else {
			return impl;
		}
	}

	@Override
	public Object invokeImplementationFor(Operation operation, cmof.reflection.Object object, Object[] args, ClassifierSemantics<Property, Operation, String> semantics) {
		String oclExpression = implementationFor(operation).body;
		OclObjectEnvironment env = object.getAdaptor(OclObjectEnvironment.class);
		int i = 0;
		for (Parameter parameter: operation.getFormalParameter()) {
			if (parameter.getDirection() != ParameterDirectionKind.RETURN) {
				env.addAdditionalContextAttribute(parameter.getName(), args[i], 
						parameter.getType(), fMetaClass);
			}
		}
		Object result = null;
		try {
			result = env.execute(oclExpression); // TODO parameters
		} catch(RuntimeException ex) {
			env.removeAdditionalAttributes();
			throw ex;
		}
		env.removeAdditionalAttributes();
		return result;
	}

	@Override
	public Object invokeImplementationFor(Property property, cmof.reflection.Object object, ClassifierSemantics<Property, Operation, String> semantics) {
		String oclExpression = implementationFor(property).body;
		OclObjectEnvironment env = object.getAdaptor(OclObjectEnvironment.class);
		return env.execute(oclExpression);	
	}

}
