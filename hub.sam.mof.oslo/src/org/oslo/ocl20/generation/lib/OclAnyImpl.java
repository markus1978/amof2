package org.oslo.ocl20.generation.lib;

import org.oslo.ocl20.semantics.bridge.Classifier;
import org.oslo.ocl20.standard.lib.OclAny;
import org.oslo.ocl20.standard.lib.OclBoolean;
import org.oslo.ocl20.standard.lib.OclSet;
import org.oslo.ocl20.standard.lib.OclType;


abstract
public class OclAnyImpl implements OclAny, Impl {
	protected StdLibGenerationAdapterImpl adapter;
	protected OclAnyImpl(StdLibGenerationAdapterImpl adapter) {
		this.adapter = adapter;
	}
	
	protected String _init = "";
	public String getInitialisation() {return _init;}
	public void setInitialisation(String init) { _init = init; }	

	protected String currentVariable = "";

	public OclAny wrappedWithExceptionHandler(Classifier type) {
		OclAnyImpl result = (OclAnyImpl) adapter.OclAny(type, "null", true);
		//String resType = ((Class)type.getDelegate()).getName();
		String e = StdLibGenerationAdapterImpl.newTempVar("e");
		result._init += "try {\n";
		result._init += this._init;
		result._init += result + " = " + this.asJavaObject()+";\n";
		result._init += "} catch (Exception "+e+") {\n";
		result._init += result + " = null;\n";
		result._init += "};\n";
		return result;
	}

	//--- IOclAny ---
	public OclBoolean equalTo(OclAny obj2) {
		if (obj2 instanceof OclAnyImpl) {
			OclAnyImpl object2 = (OclAnyImpl)obj2;
			OclBooleanImpl result = (OclBooleanImpl)adapter.Boolean("new java.lang.Boolean(("+this.asJavaObject()+").equals("+object2.asJavaObject()+"))",true);
			result._init = this._init + object2._init + result._init;
			return result;
		}
		return null;
	}

	public OclBoolean notEqualTo(OclAny obj2) {
		if (obj2 instanceof OclAnyImpl) {
			OclAnyImpl object2 = (OclAnyImpl)obj2;
			OclBooleanImpl result = (OclBooleanImpl)adapter.Boolean("new java.lang.Boolean(!("+this.asJavaObject()+").equals("+object2.asJavaObject()+"))",true);
			result._init = this._init + object2._init + result._init;
			return result;
		}
		return null;
	}
	
	public OclBoolean oclIsNew() {
		return this.adapter.Boolean(false);
	}

	public OclAny oclAsType(OclType type) {
		Class cls = (Class)type.asJavaObject();
		Classifier clsfr = ((OclTypeImpl)type).cls;
		OclAnyImpl o = (OclAnyImpl)adapter.OclAny(clsfr,"(("+cls.getName()+")"+this+")");
		o._init = this._init + o._init;
		return o;
	}

	public OclBoolean oclIsTypeOf(OclType type) {
		Class cls = (Class) type.asJavaObject();
		return adapter.Boolean( "new java.lang.Boolean("+this+" instanceof "+cls.getName()+")",true );
	}

	public OclBoolean oclIsKindOf(OclType type) {
		return adapter.Boolean("new java.lang.Boolean("+type+".class.isAssignableFrom("+this+".getClass()))",true);
	}

	public OclBoolean oclIsInState(Object state) {
		return OclBooleanImpl.FALSE;
	}
	public OclBoolean oclIsUndefined() {
	    OclBooleanImpl res = (OclBooleanImpl)this.adapter.Boolean("new java.lang.Boolean("+this.asJavaObject()+" == null"+")",true);
	    res.setInitialisation( this.getInitialisation() + res.getInitialisation() );
	    return res;
	}

	public OclSet allInstances() {
		return null;
	}

	String toString(Class[] x) {
		String str = "(";
		for(int i=0; i<x.length; i++) {
			str+=x[i].getName();
			if (i+1<x.length) str+="' ";
		}
		str+=")";
		return str;
	}

	//--- Object ---
	public abstract Object clone();
	public abstract boolean equals(Object o);
	public int hashCode() {
		return 0;
	}
}
