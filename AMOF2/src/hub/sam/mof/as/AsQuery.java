package hub.sam.mof.as;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import cmof.Constraint;
import cmof.Feature;
import cmof.MultiplicityElement;
import cmof.NamedElement;
import cmof.OpaqueExpression;
import cmof.Operation;
import cmof.Parameter;
import cmof.Property;
import cmof.Type;
import cmof.TypedElement;
import cmof.cmofFactory;
import cmof.common.ReflectiveSequence;

public class AsQuery extends AsBehavior {

	private final Collection<String> contextQualifiedName;
	private final String expression;
	private Feature context = null;
	private Constraint constraint = null;	
	
	public AsQuery(Collection<String> context, String expr) {
		this.contextQualifiedName = context;
		this.expression = getOclExpressionForExpression(expr);
	}
	
	public AsQuery(Constraint constraint) {
		this.constraint = constraint;
		context = (Feature)constraint.getOwner();
		contextQualifiedName = null;
		expression = ((OpaqueExpression)constraint.getSpecification()).getBody();
	}
	
	private Constraint createConstraint(cmofFactory factory) {
		constraint = factory.createConstraint();
		constraint.setName("query");
		OpaqueExpression value = factory.createOpaqueExpression();
		value.setBody(expression);
		value.setLanguage("OCL");
		constraint.setSpecification(value);
		return constraint;
	}
	
	@Override
	public void staticSemantics(AsAnalysisEnvironment environment) throws AsSemanticException {		
		AsSemanticException exceptions = new AsSemanticException("Errors in bevavior declarations for name " + contextQualifiedName);	
		NamedElement contextNamedElement = null;
		try {
			contextNamedElement = resolveQualifiedName(contextQualifiedName, environment.getTopLevelPackages());
		} catch (AsSemanticException e) {
			exceptions.addException(e);
		}
		
		if (contextNamedElement == null) {
			exceptions.addException(new AsSemanticException("The name " + contextQualifiedName + " does not reference an element."));
		}
		
				
		if (contextNamedElement instanceof Operation) {
			Operation contextOperation = (Operation)contextNamedElement;
			contextOperation.setIsQuery(true);		
			contextOperation.setBodyCondition(createConstraint(environment.getFactory()));
			context = contextOperation;		
			if (contextOperation.getFormalParameter().size() > 1) {
				exceptions.addException(new AsSemanticException("Operation support with more than one parameter not yet implemented.")); 
			} else if (contextOperation.getFormalParameter().size() == 1) {
				Parameter parameter = contextOperation.getFormalParameter().get(0);
				environment.addAdditionalContextAttribute(parameter.getName(), null, parameter.getType(), contextOperation.getUmlClass());
			}
		} else if (contextNamedElement instanceof Property) {
			Property contextProperty = (Property)contextNamedElement;
			if (!contextProperty.isDerived() && !contextProperty.isDerivedUnion()) {
				exceptions.addException(new AsSemanticException("The query behavior does not reference a derived property."));
			}
			contextProperty.setOwnedBehavior(createConstraint(environment.getFactory()));
			context = contextProperty;		
		} else {
			exceptions.addException(new AsSemanticException("Name " + contextNamedElement + " does not reference an operation or property."));
		}
				
		try {
			Type requiredType = null;								
			if (context instanceof TypedElement) {
				requiredType = ((TypedElement)context).getType();
				environment.checkOclConstraint(expression, contextNamedElement.getNamespace(), requiredType,
						(context instanceof MultiplicityElement && ((MultiplicityElement)context).getUpper() != 1),
						((MultiplicityElement)context).isUnique(),
						((MultiplicityElement)context).isOrdered());				
			} else {
				requiredType = ((Operation)context).getType();
				environment.checkOclConstraint(expression, contextNamedElement.getNamespace(), requiredType,
						false, false, false);
			}			
			environment.removeAdditionalAttribute();
		} catch (AsSemanticException e) {
			exceptions.addException(e);
		} finally {
			environment.removeAdditionalAttribute();
		}
		
		if (exceptions.getExceptions().size() > 0) {
			throw exceptions;
		}		
	}

	@Override
	public Object invoke(Object object, Object[] args, AsExecutionEnvironment environment) {
		//System.out.println("### Behavior for " + context.getQualifiedName() + " invoked.");
		
		if (context instanceof Operation) {
			ReflectiveSequence<? extends Parameter> parameters = ((Operation)context).getFormalParameter();
			if (parameters.size() > 0) {
				Parameter parameter = parameters.get(0);
				environment.addAdditionalContextAttribute(parameter.getName(), args[0], parameter.getType(), ((Operation)context).getUmlClass());	
			}	
		}
		Object result = environment.evaluateInvariant(expression, (NamedElement)context.getNamespace(), object);
		environment.removeAdditionalAttribute();
		return result;
	}
}
