package hub.sam.mof.ocl.oslobridge;

import hub.sam.mof.util.AssertionException;

import org.oslo.ocl20.semantics.SemanticsVisitor;
import org.oslo.ocl20.semantics.bridge.Classifier;
import org.oslo.ocl20.semantics.bridge.Property;

public final class MofAdditionalPropertyImpl implements Property {

	private final Classifier type;
	private final String name;
	private final int index;
	
	public MofAdditionalPropertyImpl(String name, Classifier type, int index) {
		this.type = type;
		this.name = name;
		this.index = index;
	}
		
	public int getIndex() {
		return index;
	}

	public Classifier getType() {
		return type;
	}

	public void setType(Classifier type) {
		throw new AssertionException();
	}

	public Object clone() {
		return this;
	}

	public Object accept(SemanticsVisitor v, Object obj) {
		return v.visit(this,obj);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		throw new AssertionException();
	}

	public Object getDelegate() {
		throw new AssertionException();
	}
}
