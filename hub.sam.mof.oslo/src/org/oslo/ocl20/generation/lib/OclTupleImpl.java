package org.oslo.ocl20.generation.lib;

import java.util.HashMap;

import org.oslo.ocl20.semantics.bridge.Classifier;
import org.oslo.ocl20.semantics.model.types.TupleType;
import org.oslo.ocl20.standard.lib.OclAny;
import org.oslo.ocl20.standard.lib.OclString;
import org.oslo.ocl20.standard.lib.OclTuple;
import org.oslo.ocl20.standard.lib.OclType;
import org.oslo.ocl20.standard.lib.OclVisitor;
import org.oslo.ocl20.synthesis.OclCodeGeneratorVisitorImpl;


public class OclTupleImpl extends OclAnyImpl implements OclTuple {

	protected OclTupleImpl(TupleType tT, java.lang.String m, StdLibGenerationAdapterImpl adapter, boolean newVariable) {
		super(adapter);
		type = tT;
		_types = (Classifier[])tT.getTypes().toArray(new Classifier[0]);
		_keys = (String[])tT.getNames().toArray(new String[0]);
		for (int i = 0; i < _keys.length; i++) {
			_elementTypes.put(_keys[i], _types[i]);
		}

		if (newVariable) {
			_implementation = StdLibGenerationAdapterImpl.newTempVar("tuple");
			_init = "java.util.Map "+_implementation+"="+m+";\n";
		} else {
		  _implementation = m;
		}

	}

	TupleType type;
	String[] _keys;
	Classifier[] _types;

	protected OclTupleImpl(TupleType tT, OclAny[] arr, StdLibGenerationAdapterImpl adapter) {
		super(adapter);
		type = tT;
		_types = (Classifier[])tT.getTypes().toArray(new Classifier[0]);
		_keys = (String[])tT.getNames().toArray(new String[0]);
		_implementation = StdLibGenerationAdapterImpl.newTempVar();
		_init = "java.util.Map "+_implementation + " = new java.util.HashMap();\n";
		for (int i = 0; i < arr.length; i++) {
			_elementTypes.put(_keys[i], _types[i]);
			_init +=((OclAnyImpl)arr[i])._init;
			_init +=_implementation+".put(\""+_keys[i]+"\", "+arr[i].asJavaObject()+");\n";
		}
	}

	private java.lang.String _implementation;
	java.util.Map _elementTypes = new HashMap();

	protected java.lang.String implementation() {
		return _implementation;
	}

	public Object asJavaObject() {
		return implementation();
	}

	public OclType oclType() {
		Classifier type = adapter.getProcessor().getTypeFactory().buildTupleType(_keys, _types);
		OclTypeImpl ot = (OclTypeImpl)adapter.Type(type);
		ot.setInitialisation( this.getInitialisation() + ot.getInitialisation());
		return ot;
	}

	//--- IOclTuple ---
	public OclAny property(OclString s) {
		String key = (String)s.asJavaObject();
		Classifier eT = (Classifier)_elementTypes.get(key);
		String part = OclCodeGeneratorVisitorImpl.unBox(eT,this+".get(\""+key+"\")");
		OclAnyImpl result = (OclAnyImpl)((StdLibGenerationAdapterImpl)adapter).OclAny(eT, part );
		result._init = this._init + result._init;
		return result;
	}

	public OclAny property(OclString s, Object[] pobjs) {
		return adapter.Undefined(); //property(s);
	}

	public void setProperty(OclString name, Object value) {
		//_implementation.put(name, value);
	}

	//--- Object ---
	public String toString() {
		return _implementation;
	}

	public boolean equals(Object o) {
		if (o instanceof OclTupleImpl) {
			OclTupleImpl tuple = (OclTupleImpl)o;
			return _implementation.equals(tuple._implementation);
		} else {
			return false;
		}
	}

	public Object clone() {
		return new OclTupleImpl(
		        type,
		this._implementation,
			super.adapter, false
			);
	}

	//--- IOclVisitable ---
	public Object accept(OclVisitor v, Object obj) {
		return v.visit(this, obj);
	}
}
