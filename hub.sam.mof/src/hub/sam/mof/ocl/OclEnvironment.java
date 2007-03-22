package hub.sam.mof.ocl;

import org.oslo.ocl20.semantics.bridge.Environment;

import cmof.Package;

public class OclEnvironment {
	
	private final Environment fEnvironment;
	
	
	private OclEnvironment(final Environment environment) {
		super();
		fEnvironment = environment;
	}

	public static OclEnvironment createOclEnvironment(Iterable<? extends Package> packages) {
		return new OclEnvironment(OclProcessor.createEnvironment(packages));
	}
	
	protected Environment getEnvironment() {
		return fEnvironment;
	}
}
