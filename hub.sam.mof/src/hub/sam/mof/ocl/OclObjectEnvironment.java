package hub.sam.mof.ocl;

import hub.sam.mof.ocl.OclImplementations.Implementation;
import hub.sam.mof.ocl.oslobridge.MofOclModelElementTypeImpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.oslo.ocl20.synthesis.RuntimeEnvironment;

import cmof.Constraint;
import cmof.OpaqueExpression;
import cmof.Property;
import cmof.Type;
import cmof.UmlClass;
import cmof.ValueSpecification;

public class OclObjectEnvironment {

	private final OclEnvironment fEnvironment;
	private final RuntimeEnvironment fRuntimeEnvironment;
	private final cmof.reflection.Object fSelf;
	
	private OclObjectEnvironment(final OclEnvironment environment, 
			final cmof.reflection.Object object) {
		super();
		fSelf = object;
		fEnvironment = environment;
		fRuntimeEnvironment = OclProcessor.createRuntimeEnvironment(object);
	}

	public static OclObjectEnvironment createObjectEnvironment(cmof.reflection.Object object, 
			OclEnvironment environment) {
		return new OclObjectEnvironment(environment, object);
	}
	
	public void analyse(String expression) throws OclException {
		fEnvironment.analyseOclExpression(expression, fSelf.getMetaClass());
	}
	
	public void analyse(String expression, Type requiredTyp, boolean collection, boolean unique, boolean ordered) 
			throws OclException {
		fEnvironment.analyseOclExpression(expression, fSelf.getMetaClass(), requiredTyp, collection, unique, ordered);
	}
	
	public Object execute(String expression) throws OclException {
		try {
            UmlClass metaClass = (fSelf == null ? null : fSelf.getMetaClass()); 
			return OclProcessor.evaluateExpression(fEnvironment.analyseOclExpression(expression, metaClass), 
					expression, metaClass, fRuntimeEnvironment);
		} catch (Exception ex) {
			throw new OclException("Exception during evaluation of ocl.", ex);
		}
	}
	
	public boolean checkAllInvariants() throws OclException {
		UmlClass metaClass = fSelf.getMetaClass();
		boolean result = true;
		for (Object content: metaClass.getMember()) {
			if (content instanceof Constraint) {
				Constraint constraint = (Constraint)content;
				for (Object constraintElement: constraint.getConstrainedElement()) {					
					if (constraintElement == metaClass) {
						ValueSpecification specification = constraint.getSpecification();						
						if (specification instanceof OpaqueExpression) {
							OpaqueExpression opaqueSpecification = (OpaqueExpression)specification;			
							String language = opaqueSpecification.getLanguage();
							if (language != null && language.startsWith("OCL")) {
								String expression = opaqueSpecification.getBody();
								try {
									result &= (Boolean)execute(expression);
								} catch (ClassCastException ex) {
									throw new OclException("Invariant " + expression + " is not of type boolean");
								}
							} 
						}						
					}
				}
			}
		}
		return result;
	}
	
	private Map<String, Type> additionalContextAttributes = new HashMap<String, Type>();
	
	public void addAdditionalContextAttribute(String name, Object value, Type attributeType, Type contextType) {	
		List<String> contextName = Arrays.asList(contextType.getQualifiedName().split("\\."));		
		MofOclModelElementTypeImpl contextOclModelElementType = (MofOclModelElementTypeImpl)fEnvironment.getEnvironment().lookupPathName(contextName);
		if (contextOclModelElementType == null) {
			throw new OclException("Cannot resolve context (" + contextName + ") of action " + this.toString());
		}
		additionalContextAttributes.put(name, contextType);	
		contextOclModelElementType.addAdditionalProperty(name, value, attributeType);		
	}
	
	public void removeAdditionalAttributes() {
		for (String name: additionalContextAttributes.keySet()) {
			List<String> contextName = Arrays.asList(additionalContextAttributes.get(name).getQualifiedName().split("\\."));		
			MofOclModelElementTypeImpl contextOclModelElementType = (MofOclModelElementTypeImpl)fEnvironment.getEnvironment().lookupPathName(contextName);
			if (contextOclModelElementType == null) {
				throw new OclException("Cannot resolve context of action " + this.toString());
			}			
			contextOclModelElementType.removeAdditionalProperty(name);			
		}
		additionalContextAttributes.clear();
	}
}
