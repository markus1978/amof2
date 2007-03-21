package hub.sam.mof.ocl.oslobridge;

import hub.sam.mof.util.AssertionException;

import org.oslo.ocl20.semantics.SemanticsVisitor;
import org.oslo.ocl20.semantics.bridge.Classifier;
import org.oslo.ocl20.semantics.bridge.Property;

public class MofAdditionalPropertyImpl implements Property {

	private final Classifier type;
	private final String name;
	
	public MofAdditionalPropertyImpl(String name, Classifier type) {
		this.type = type;
		this.name = name;
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
