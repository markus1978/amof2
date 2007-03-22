package hub.sam.mof.ocl;

import org.oslo.ocl20.synthesis.RuntimeEnvironment;

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
	
	public Object execute(String invariant) throws OclException {
		// TODO caching of invariants
		try {
			return OclProcessor.evaluateExpression(invariant, fSelf.getMetaClass(), 
					fEnvironment.getEnvironment(), fRuntimeEnvironment);
		} catch (Exception ex) {
			throw new OclException("Exception during evaluation of ocl.", ex);
		}
	}
}
