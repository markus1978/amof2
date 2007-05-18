package org.oslo.ocl20.generation.lib;

import org.oslo.ocl20.semantics.bridge.Classifier;
import org.oslo.ocl20.standard.lib.OclBoolean;
import org.oslo.ocl20.standard.lib.OclInteger;
import org.oslo.ocl20.standard.lib.OclReal;
import org.oslo.ocl20.standard.lib.OclType;
import org.oslo.ocl20.standard.lib.OclVisitor;


public class OclRealImpl
 extends OclAnyImpl
  implements OclReal {
	protected OclRealImpl(String r, StdLibGenerationAdapterImpl adapter,boolean newVariable) {
		super(adapter);
		if (newVariable) {
			_implementation = StdLibGenerationAdapterImpl.newTempVar("real");
			_init = "java.lang.Double "+_implementation+"="+r+";\n";
		} else {
		  _implementation = r;
		}
	}
	String _implementation;
	public String implementation() {
		return _implementation;
	}

	public OclType oclType() {
		Classifier type = adapter.getProcessor().getTypeFactory().buildRealType();
		OclTypeImpl ot = (OclTypeImpl)adapter.Type(type);
		ot.setInitialisation( this.getInitialisation() + ot.getInitialisation());
		return ot;
	}
	public OclBoolean equalTo(OclReal r2) {
		OclBooleanImpl res = (OclBooleanImpl)adapter.Boolean("new java.lang.Boolean("+this.implementation() + " == "+ r2+")",true);
		res.setInitialisation( this.getInitialisation() + ((OclRealImpl)r2).getInitialisation() +res._init);
		return res;
	}

	public OclBoolean notEqualTo(OclReal r2) {
		OclBooleanImpl res = (OclBooleanImpl)adapter.Boolean("new java.lang.Boolean("+this.implementation() + " != " + r2+")",true);
		res.setInitialisation( this.getInitialisation() + ((OclRealImpl)r2).getInitialisation() +res._init);
		return res;
	}
	
	public Object asJavaObject() {
		return "new Double("+implementation()+")";
	}

	//--- IOclReal ---
	public OclReal add(OclReal r2) {
		OclRealImpl res = (OclRealImpl)adapter.Real("("+this.implementation() + " + " + r2+")");
		res.setInitialisation( this.getInitialisation() + ((OclRealImpl)r2).getInitialisation() );
		return res;
	}

	public OclReal subtract(OclReal r2) {
		OclRealImpl res = (OclRealImpl)adapter.Real("("+this.implementation() + " - " + r2+")");
		res.setInitialisation( this.getInitialisation() + ((OclRealImpl)r2).getInitialisation() );
		return res;
	}

	public OclReal multiply(OclReal r2) {
		OclRealImpl res = (OclRealImpl)adapter.Real("("+this.implementation() + " * " + r2+")");
		res.setInitialisation( this.getInitialisation() + ((OclRealImpl)r2).getInitialisation() );
		return res;
	}

	public OclReal negate() {
		OclRealImpl res = (OclRealImpl)adapter.Real("("+"-"+this.implementation()+")");
		res.setInitialisation( this.getInitialisation() );
		return res;
	}

	public OclReal divide(OclReal r2) {
		//if (adapter.impl(r2).doubleValue() == 0) return adapter.Undefined();
		OclRealImpl res = (OclRealImpl)adapter.Real("("+this.implementation() + " / " + r2+")");
		res.setInitialisation( this.getInitialisation() + ((OclRealImpl)r2).getInitialisation() );
		return res;
	}

	public OclReal abs() {
		OclRealImpl res = (OclRealImpl)adapter.Real( "Math.abs("+this.implementation()+")" );
		res.setInitialisation( this.getInitialisation() );
		return res;
	}

	public OclInteger floor() {
		OclIntegerImpl res = (OclIntegerImpl)adapter.Integer("((int) Math.floor("+this.implementation()+"))");
		res.setInitialisation( this.getInitialisation() );
		return res;
	}

	public OclInteger round() {
		OclIntegerImpl res = (OclIntegerImpl)adapter.Integer("((int) Math.round("+this.implementation()+"))");
		res.setInitialisation( this.getInitialisation() );
		return res;
	}

	public OclReal max(OclReal r2) {
		OclRealImpl res = (OclRealImpl)adapter.Real("Math.max("+this.implementation()+", "+r2+")");
		res.setInitialisation( this.getInitialisation() + ((OclRealImpl)r2).getInitialisation() );
		return res;
	}

	public OclReal min(OclReal r2) {
		OclRealImpl res = (OclRealImpl) adapter.Real("Math.min("+this.implementation()+", "+r2+")");
		res.setInitialisation( this.getInitialisation() + ((OclRealImpl)r2).getInitialisation() );
		return res;
	}

	public OclBoolean lessThan(OclReal r2) {
		OclBooleanImpl res = (OclBooleanImpl) adapter.Boolean("new java.lang.Boolean("+this.implementation()+" < "+r2+")",true);
		res.setInitialisation( this.getInitialisation() + ((OclRealImpl)r2).getInitialisation() +res._init);
		return res;
	}

	public OclBoolean greaterThan(OclReal r2) {
		OclBooleanImpl res = (OclBooleanImpl) adapter.Boolean("new java.lang.Boolean("+this.implementation()+" > "+r2+")",true);
		res.setInitialisation( this.getInitialisation() + ((OclRealImpl)r2).getInitialisation()  +res._init);
		return res;
	}

	public OclBoolean lessThanOrEqualTo(OclReal r2) {
		OclBooleanImpl res = (OclBooleanImpl)adapter.Boolean("new java.lang.Boolean("+this.implementation()+" <= "+r2+")",true);
		res.setInitialisation( this.getInitialisation() + ((OclRealImpl)r2).getInitialisation()  +res._init);
		return res;
	}

	public OclBoolean greaterThanOrEqualTo(OclReal r2) {
		OclBooleanImpl res = (OclBooleanImpl) adapter.Boolean("new java.lang.Boolean("+this.implementation()+" >= "+r2+")",true);
		res.setInitialisation( this.getInitialisation() + ((OclRealImpl)r2).getInitialisation()  +res._init);
		return res;
	}

	//--- IOclVisitable ---
	public Object accept(OclVisitor v, Object obj) {
		return v.visit(this, obj);
	}

	//---Object---
	public String toString() {
		return implementation();
	}
	public boolean equals(Object r) {
	    return this==r;
	}

	public int hashCode() {
		return implementation().hashCode();
	}

	public Object clone() {
		return new OclRealImpl(implementation(), super.adapter,false);
	}
}
