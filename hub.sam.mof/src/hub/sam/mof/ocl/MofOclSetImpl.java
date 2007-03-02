package hub.sam.mof.ocl;

import java.util.Collection;

import org.oslo.ocl20.standard.lib.OclAny;

import hub.sam.mof.util.SetImpl;

public class MofOclSetImpl extends SetImpl {

	private OclAny property;

	@SuppressWarnings("unchecked")
	public MofOclSetImpl(Collection values, OclAny property) {
		super(values);
		this.property = property;
	}
	
	public OclAny getOclCollection() {
		return property;
	}
}
