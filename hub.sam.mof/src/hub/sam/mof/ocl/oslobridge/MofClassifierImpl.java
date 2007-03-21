package hub.sam.mof.ocl.oslobridge;

import org.oslo.ocl20.OclProcessor;
import org.oslo.ocl20.semantics.bridge.ClassifierImpl;

public class MofClassifierImpl extends ClassifierImpl {

	public MofClassifierImpl(OclProcessor proc) {
		super(proc);
	}

	@Override
	public String toString() {
		if (getNamespace() != null) {
			return getNamespace().toString() + "::" + getName();
		} else {
			return getName();
		}
	}
}
