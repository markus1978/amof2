package org.oslo.ocl20.generation.lib;

import org.oslo.ocl20.standard.lib.OclAny;
import org.oslo.ocl20.standard.lib.OclBoolean;
import org.oslo.ocl20.standard.lib.OclInteger;
import org.oslo.ocl20.standard.lib.OclReal;
import org.oslo.ocl20.standard.lib.OclVisitor;

public class OclIntegerImpl extends OclRealImpl implements OclInteger {
	public OclIntegerImpl(String i, StdLibGenerationAdapterImpl adapter, boolean newVariable) {
		super(i,adapter,newVariable);
		if (newVariable) {
			_int_impl = StdLibGenerationAdapterImpl.newTempVar("int");
			_init = "java.lang.Integer "+_int_impl+"="+i+";\n";
		} else {
			_int_impl = i;
		}
	}

	String _int_impl;
	public String int_impl() {
		return _int_impl;
	}

	public OclBoolean equalTo(OclInteger i2) {
		return super.equalTo(i2);
	}

	//--- IOclInteger ---
	public OclInteger inegate() {
		OclIntegerImpl res = (OclIntegerImpl)adapter.Integer("(-"+this+")");
		res.setInitialisation( this.getInitialisation() );
		return res;
	}

	public OclInteger add(OclInteger i2) {
		OclIntegerImpl res = (OclIntegerImpl)adapter.Integer("("+this + " + "+ i2+")");
		res.setInitialisation( this.getInitialisation() + ((OclIntegerImpl)i2).getInitialisation() );
		return res;
	}

	public OclInteger subtract(OclInteger i2) {
		OclIntegerImpl res = (OclIntegerImpl) adapter.Integer("("+this + " - "+ i2+")");
		res.setInitialisation( this.getInitialisation() + ((OclIntegerImpl)i2).getInitialisation() );
		return res;
	}

	public OclInteger multiply(OclInteger i2) {
		OclIntegerImpl res = (OclIntegerImpl) adapter.Integer("("+this + " * "+ i2+")");
		res.setInitialisation( this.getInitialisation() + ((OclIntegerImpl)i2).getInitialisation() );
		return res;
	}

	public OclReal divide(OclInteger i2) {
		//if (adapter.impl(i2).intValue() == 0) return adapter.Undefined();
		OclRealImpl res = (OclRealImpl) adapter.Real("("+this + ".0 / "+ i2+")");
		res.setInitialisation( this.getInitialisation() + ((OclIntegerImpl)i2).getInitialisation() );
		return res;
	}

	public OclInteger iabs() {
		OclIntegerImpl res = (OclIntegerImpl) adapter.Integer("Math.abs("+this+")");
		res.setInitialisation( this.getInitialisation()  );
		return res;
	}

	public OclInteger div(OclInteger i2) {
		//if (adapter.impl(i2).intValue() == 0) return adapter.Undefined();
		OclIntegerImpl res = (OclIntegerImpl) adapter.Integer("("+this +" / " + i2+")");
		res.setInitialisation( this.getInitialisation() + ((OclIntegerImpl)i2).getInitialisation() );
		return res;
	}

	public OclInteger mod(OclInteger i2) {
		//if (adapter.impl(i2).intValue() == 0) return adapter.Undefined();
		OclIntegerImpl res = (OclIntegerImpl) adapter.Integer("("+this +" % "+ i2+")");
		res.setInitialisation( this.getInitialisation() + ((OclIntegerImpl)i2).getInitialisation() );
		return res;
	}

	public OclInteger max(OclInteger i2) {
		OclIntegerImpl res = (OclIntegerImpl) adapter.Integer("Math.max("+this+", "+i2+")");
		res.setInitialisation( this.getInitialisation() + ((OclIntegerImpl)i2).getInitialisation() );
		return res;
	}

	public OclInteger min(OclInteger i2) {
		OclIntegerImpl res = (OclIntegerImpl) adapter.Integer("Math.min("+this+", "+i2+")");
		res.setInitialisation( this.getInitialisation() + ((OclIntegerImpl)i2).getInitialisation() );
		return res;
	}

	//--- OclAny ---
	public OclBoolean equalTo(OclAny i2) {
		OclBooleanImpl res = (OclBooleanImpl)super.equalTo( (OclReal)i2);
		if ( ! (i2 instanceof OclReal) )
		   res = (OclBooleanImpl) this.adapter.Boolean(false);
		res.setInitialisation( this.getInitialisation() + ((OclAnyImpl)i2).getInitialisation() );
		return res;
	}
	public Object asJavaObject() {
		return "new Integer("+int_impl()+")";
	}

	//--- IOclVisitable ---
	public Object accept(OclVisitor v, Object obj) {
		return v.visit(this, obj);
	}

	//---Object---
	public String toString() {
		return int_impl();
	}
	
	public boolean equals(Object i) {
		if ( ! (i instanceof OclIntegerImpl)) return false;
		return (int_impl() == ((OclIntegerImpl)i).int_impl());
	}
	
	public int hashCode() {
		return int_impl().hashCode();
	}

	public Object clone() {
		return adapter.Integer(int_impl());
	}
}
