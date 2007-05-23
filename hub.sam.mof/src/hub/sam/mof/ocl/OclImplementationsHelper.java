package hub.sam.mof.ocl;

import cmof.Constraint;
import cmof.OpaqueExpression;
import cmof.Operation;
import cmof.Property;
import cmof.cmofFactory;

public class OclImplementationsHelper {

	public static String getOclImplementation(Property property) {		
		for (Object content: property.getUmlClass().getMember()) {
			if (content instanceof Constraint) {
				Constraint constraint = (Constraint)content;
				for (Object constraintElement: constraint.getConstrainedElement()) {					
					if (constraintElement.equals(property)) {
						return OclImplementationsHelper.getOclImplementation(constraint);
					}
				}
			}
		}
		return null;
	}
	
	public static void setOclImplementation(Property property, cmofFactory factory, String oclImplementationExpression) {
		boolean hasImplementation = false;
		for (Object content: property.getUmlClass().getMember()) {
			if (content instanceof Constraint) {
				Constraint constraint = (Constraint)content;
				for (Object constraintElement: constraint.getConstrainedElement()) {					
					if (constraintElement.equals(property)) {
						setOclImplementastion(constraint, factory, oclImplementationExpression);
						hasImplementation = true;
					}
				}
			}
		}
		if (!hasImplementation) {
			Constraint constraint = factory.createConstraint();
    		constraint.setName("implementation of "  + property.getName());    		
    		property.getUmlClass().getOwnedRule().add(constraint);
    		constraint.getConstrainedElement().add(property);
    		setOclImplementastion(constraint, factory, oclImplementationExpression);
		}		
	}
	
	public static String getOclImplementation(Operation operation) {
		return getOclImplementation(operation.getBodyCondition());
	}
	
	public static void setOclImplementation(Operation operation, cmofFactory factory, String oclImplementationExpression) {
		Constraint bodyCondition = operation.getBodyCondition();
    	if (bodyCondition != null) {    		    		
    	} else {
    		bodyCondition = factory.createConstraint();
    		bodyCondition.setName("implementation");    		
    		operation.setBodyCondition(bodyCondition);    		
    	}   
    	setOclImplementastion(bodyCondition, factory, oclImplementationExpression);
	}
	
	public static void setOclImplementastion(Constraint constraint, cmofFactory factory, String oclImplementationExpression) {
		OpaqueExpression oclSpecification = (OpaqueExpression)constraint.getSpecification();    		
		if (oclSpecification != null && oclSpecification.getLanguage().startsWith("OCL")) {
			oclSpecification.setBody(oclImplementationExpression);
		} else {
			if (oclSpecification != null) {
				oclSpecification.delete();
			}
			OpaqueExpression newOclSpecification = factory.createOpaqueExpression();
			newOclSpecification.setLanguage("OCL");
			newOclSpecification.setBody(oclImplementationExpression);
			constraint.setSpecification(newOclSpecification);
		}
	}
	
	public static String getOclImplementation(Constraint constraint) {
		if (constraint == null) {
			return null;
		}
		Object specificationAsObject = constraint.getSpecification();
		if (specificationAsObject instanceof OpaqueExpression) {
			OpaqueExpression specification = (OpaqueExpression)specificationAsObject;			
			String language = specification.getLanguage();
			if (language != null && language.startsWith("OCL")) {
				return specification.getBody();
			}
		} 
		return null;
	}
}
