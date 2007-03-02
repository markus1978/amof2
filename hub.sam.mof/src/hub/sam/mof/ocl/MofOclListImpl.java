package hub.sam.mof.ocl;

import hub.sam.mof.util.ListImpl;

import java.util.List;

import org.oslo.ocl20.standard.lib.OclAny;

public class MofOclListImpl extends ListImpl {

	private OclAny property;

	@SuppressWarnings("unchecked")
	public MofOclListImpl(List values, OclAny property) {
		super(values);
		this.property = property;
	}
	
	public OclAny getOclCollection() {
		return property;
	}
}
