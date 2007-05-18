package org.oslo.ocl20.generation.lib;

import org.oslo.ocl20.semantics.bridge.Classifier;
import org.oslo.ocl20.standard.lib.OclBoolean;
import org.oslo.ocl20.standard.lib.OclInteger;
import org.oslo.ocl20.standard.lib.OclReal;
import org.oslo.ocl20.standard.lib.OclString;
import org.oslo.ocl20.standard.lib.OclType;
import org.oslo.ocl20.standard.lib.OclVisitor;


public class OclStringImpl extends OclAnyImpl implements OclString {
	public OclStringImpl(java.lang.String s, StdLibGenerationAdapterImpl adapter,boolean newVariable) {
		super(adapter);
		if (newVariable) {
			_implementation = StdLibGenerationAdapterImpl.newTempVar("str");
			_init = "java.lang.String "+_implementation+"="+s+";\n";
		} else {
		  _implementation = s;
		}
	}

	String _implementation;
	public String implementation() {
		return _implementation;
	}

	public Object asJavaObject() {
		String x = "\\\\";
		String y = "\\\\\\\\";
		return implementation().replaceAll(x,y);
	}
	//--- IOclString ---
	public OclType oclType() {
		Classifier type = adapter.getProcessor().getTypeFactory().buildStringType();
		OclTypeImpl ot = (OclTypeImpl)adapter.Type(type);
		ot.setInitialisation( this.getInitialisation() + ot.getInitialisation());
		return ot;
	}
	public OclBoolean equalTo(OclString string2) {
		OclBooleanImpl res = (OclBooleanImpl)adapter.Boolean("new java.lang.Boolean("+this+".equals("+string2+"))",true);
		res._init = this._init + ((OclStringImpl)string2)._init + res._init;
		return res;
	}

	public OclInteger size() {
		return adapter.Integer(this+".length()");
	}

	public OclString concat(OclString string2) {
		return adapter.String(this + " + " + string2);
	}

	public OclString substring(OclInteger lower, OclInteger upper) {
		return adapter.String(this + ".substring("+lower+"-1, "+upper+")");
	}
	
	public OclInteger toInteger() {
		return adapter.Integer( "Integer.parseInt("+this+")" );
	}

	public OclReal toReal() {
		return adapter.Real( "Double.parseDouble("+this+")" );
	}

	//--- IOclVisitable ---
	public Object accept(OclVisitor v, Object obj) {
		return v.visit(this, obj);
	}
	//--- Object ---
	public String toString() {
		return _implementation;
	}

	public boolean equals(Object o) {
	    return this==o;
	}

	public int hashCode() {
		return implementation().hashCode();
	}

	public Object clone() {
		return adapter.String(_implementation);
	}
}
