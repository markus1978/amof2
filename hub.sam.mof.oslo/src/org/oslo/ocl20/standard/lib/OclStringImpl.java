package org.oslo.ocl20.standard.lib;

import org.oslo.ocl20.semantics.bridge.Classifier;

public class OclStringImpl extends OclAnyImpl implements OclString {
	public OclStringImpl(java.lang.String s, StdLibAdapter adapter) {
		super(adapter);
		_implementation = s;
	}

	public OclType oclType() {
		Classifier type = adapter.getProcessor().getTypeFactory().buildStringType();
		return adapter.Type(type);
	}

	String _implementation;
	public String implementation() {
		return _implementation;
	}

	public Object asJavaObject() {
		return implementation();
	}
	//--- IOclString ---
	public OclBoolean equalTo(OclString string2) {
		return adapter.Boolean(implementation().equals(((OclStringImpl) string2).implementation()));
	}

	public OclInteger size() {
		return adapter.Integer(implementation().length());
	}

	public OclString concat(OclString string2) {
		return adapter.String(implementation() + ((OclStringImpl) string2).implementation());
	}

	public OclString substring(OclInteger lower, OclInteger upper) {
		return adapter.String(implementation().substring(((Integer)lower.asJavaObject()).intValue()-1, ((Integer)upper.asJavaObject()).intValue()-1 + 1));
	}
	
	public OclInteger toInteger() {
		return adapter.Integer( Integer.parseInt(implementation()) );
	}

	public OclReal toReal() {
		return adapter.Real( Double.parseDouble(implementation()) );
	}

	//--- IOclVisitable ---
	public Object accept(OclVisitor v, Object obj) {
		return v.visit(this, obj);
	}
	//--- Object ---
	public String toString() {
		return "'" + _implementation + "'";
	}

	public boolean equals(Object o) {
		return (o instanceof OclString) ? ((Boolean)equalTo((OclString)o).asJavaObject()).booleanValue() : false;
	}

	public int hashCode() {
		return implementation().hashCode();
	}

	public Object clone() {
		return adapter.String(_implementation);
	}
}
