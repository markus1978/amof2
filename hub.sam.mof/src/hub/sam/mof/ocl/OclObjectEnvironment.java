package hub.sam.mof.ocl;

import hub.sam.mof.ocl.oslobridge.MofOclModelElementTypeImpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.oslo.ocl20.synthesis.RuntimeEnvironment;

import cmof.Type;
import cmof.UmlClass;

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
