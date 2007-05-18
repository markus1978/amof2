package org.oslo.ocl20.generation.lib;

import org.oslo.ocl20.semantics.bridge.Classifier;
import org.oslo.ocl20.standard.lib.OclAny;
import org.oslo.ocl20.standard.lib.OclBag;
import org.oslo.ocl20.standard.lib.OclBoolean;
import org.oslo.ocl20.standard.lib.OclCollection;
import org.oslo.ocl20.standard.lib.OclInteger;
import org.oslo.ocl20.standard.lib.OclOrderedSet;
import org.oslo.ocl20.standard.lib.OclReal;
import org.oslo.ocl20.standard.lib.OclSequence;
import org.oslo.ocl20.standard.lib.OclSet;
import org.oslo.ocl20.standard.lib.OclString;
import org.oslo.ocl20.standard.lib.OclType;
import org.oslo.ocl20.standard.lib.OclUndefined;
import org.oslo.ocl20.standard.lib.OclVisitor;
import org.oslo.ocl20.standard.lib.StdLibAdapter;


public class OclUndefinedImpl
extends OclAnyImpl
implements OclUndefined
{
	static OclUndefinedImpl UNDEFINED;

	StdLibAdapter adapter;
	
	String _error;
	public String getError() {return _error;}

	Throwable _exception;
	public Throwable getException() {return _exception;}

	protected OclUndefinedImpl(String s, Throwable e, StdLibGenerationAdapterImpl adapter) {
		super(adapter);
		this.adapter = adapter;
		_error = s;
		_exception = e;
	}

	public OclAny impl() {
		return null;
	}

	//--- IOclAny ---
	public OclSet allInstances() {
		OclSet s = adapter.Set(null);
		return s.including(this);
	}
	
	public Object asJavaObject() {
		return null;
	}

	
	public OclType oclType() {
		Classifier type = adapter.getProcessor().getTypeFactory().buildVoidType();
		OclTypeImpl ot = (OclTypeImpl)adapter.Type(type);
		ot.setInitialisation( this.getInitialisation() + ot.getInitialisation());
		return ot;
	}

	public OclBoolean equalTo(OclAny object2) {
		return this;
	}
	public OclBoolean notEqualTo(OclAny object2) {
		return this;
	}
	public OclBoolean oclIsNew() {
		return this;
	}
	public OclAny oclAsType(OclType type) {
		return this;
	}
	public OclBoolean oclIsInState(Object s) {
		return this;
	}
	public OclBoolean oclIsKindOf(OclType type) {
		return this;
	}
	public OclBoolean oclIsTypeOf(OclType type) {
		return this;
	}
	//type oclAsType(IOclType type) {return UNDEFINED;}
	//OclAny oclAsType(Object type);
	//IOclBoolean oclInState(IOclState state) {return UNDEFINED;}
	//IOclBoolean oclIsNew() {return UNDEFINED;}
	//OclType oclType();

	public OclBoolean oclIsUndefined() {
		return this.adapter.Boolean(true);
	}

	public OclBoolean oclIsEmpty() {
		return this.adapter.Boolean(true);
	}

	public OclAny property(OclString property_name) {
		return property(property_name, new Object[] {
		});
	}
	public OclAny property(OclString property_name, OclSequence params) {
		return property(property_name, new Object[] {
		});
	}
	public OclAny property(OclString property_name, Object[] pobjs) {
		if (property_name.equals(adapter.String("oclIsEmpty")))
			return this.oclIsEmpty();
		if (property_name.equals(adapter.String("oclIsUndefined")))
			return this.oclIsUndefined();
		if (property_name.equals(adapter.String("impl")))
			return this.impl();
		if (property_name.equals(adapter.String("or")))
			if (pobjs.length > 0)
				if (pobjs[0] instanceof OclBoolean)
					return this.or((OclBoolean) pobjs[0]);
		if (property_name.equals(adapter.String("and")))
			if (pobjs.length > 0)
				if (pobjs[0] instanceof OclBoolean)
					return this.or((OclBoolean) pobjs[0]);
		if (property_name.equals(adapter.String("implies")))
			if (pobjs.length > 0)
				if (pobjs[0] instanceof OclBoolean)
					return this.or((OclBoolean) pobjs[0]);
		return this;
	}

	//--- Boolean ---
	public OclBoolean equalTo(OclBoolean b2) {
		return this;
	}
	public OclBoolean notEqualTo(OclBoolean b2) {
		return this;
	}
	public OclBoolean or(OclBoolean b2) {
		if (b2 == this.adapter.Boolean(true))
			return this.adapter.Boolean(true);
		return this;
	}

	public OclBoolean xor(OclBoolean b2) {
		return this;
	}

	public OclBoolean and(OclBoolean b2) {
		if (b2 == this.adapter.Boolean(false))
			return this.adapter.Boolean(false);
		return this;
	}

	public OclBoolean not() {
		return this;
	}
	public OclBoolean implies(OclBoolean b2) {
		if (b2 == this.adapter.Boolean(true))
			return this.adapter.Boolean(true);
		return this;
	}

	//--- Integer ---
	public OclBoolean equalTo(OclInteger i2) {
		return this;
	}
	public OclBoolean notEqualTo(OclInteger i2) {
		return this;
	}
	public OclInteger inegate() {
		return this;
	}
	public OclInteger add(OclInteger i2) {
		return this;
	}
	public OclInteger subtract(OclInteger i2) {
		return this;
	}
	public OclInteger multiply(OclInteger i2) {
		return this;
	}
	public OclReal divide(OclInteger i2) {
		return this;
	}
	public OclInteger iabs() {
		return this;
	}
	public OclInteger div(OclInteger i2) {
		return this;
	}
	public OclInteger mod(OclInteger i2) {
		return this;
	}
	public OclInteger max(OclInteger i2) {
		return this;
	}
	public OclInteger min(OclInteger i2) {
		return this;
	}

	//--- IOclReal ---
	public OclBoolean equalTo(OclReal r2) {
		return this;
	}
	public OclBoolean notEqualTo(OclReal r2) {
		return this;
	}
	public OclReal add(OclReal r2) {
		return this;
	}
	public OclReal subtract(OclReal r2) {
		return this;
	}
	public OclReal multiply(OclReal r2) {
		return this;
	}
	public OclReal negate() {
		return this;
	}
	public OclReal divide(OclReal r2) {
		return this;
	}
	public OclReal abs() {
		return this;
	}
	public OclInteger floor() {
		return this;
	}
	public OclInteger round() {
		return this;
	}
	public OclReal max(OclReal r2) {
		return this;
	}
	public OclReal min(OclReal r2) {
		return this;
	}
	public OclBoolean lessThan(OclReal r2) {
		return this;
	}
	public OclBoolean greaterThan(OclReal r2) {
		return this;
	}
	public OclBoolean lessThanOrEqualTo(OclReal r2) {
		return this;
	}
	public OclBoolean greaterThanOrEqualTo(OclReal r2) {
		return this;
	}

	//--- IOclString ---
	public OclBoolean equalTo(OclString string2) {
		return this;
	}

	public OclInteger size() {
		return this;
	}

	public OclString concat(OclString string2) {
		return this;
	}

	public OclString substring(OclInteger lower, OclInteger upper) {
		return this;
	}

	public OclInteger toInteger() {
		return this;
	}

	public OclReal toReal() {
		return this;
	}

	//--- IOclVisitable ---
	public Object accept(OclVisitor v, Object obj) {
		return v.visit((OclBoolean) this, obj);
	}

	// Object
	public boolean equals(Object o) {
		return o instanceof OclUndefinedImpl;
	}
	public Object clone() {
		return this;
	}
	public int hashCode() {
		return 0;
	}
	public String toString() {
		//return "Undefined<" + _error + "|" + _exception + ">";
		return "null";
	}

	//---Impl---
	public Object getImplementation() {
	    return null;
	}
	
	//--- OclCollection ---
	public OclBoolean includes(Object object) {
		return this;
	}

	public OclBoolean excludes(Object object) {
		return this;
	}

	public OclInteger count(OclAny object) {
		return this;
	}

	public OclBoolean includesAll(OclCollection col) {
		return this;
	}

	public OclBoolean excludesAll(OclCollection col) {
		return this;
	}

	public OclBoolean isEmpty() {
		return this;
	}

	public OclBoolean notEmpty() {
		return this;
	}

	public Object sum() {
		return this;
	}

	public OclSet product(OclCollection c2) {
		return adapter.Set(null);
	}
	public OclBoolean equalTo(OclCollection set2) {
		return this;
	}

	public OclBoolean notEqualTo(OclCollection set2) {
		return this;
	}

	public OclCollection union(OclCollection set2) {
		return this;
	}

	public OclCollection intersection(OclCollection set2) {
		return this;
	}

	public OclCollection subtract(OclSet set2) {
		return this;
	}

	public OclCollection including(OclAny object) {
		return this;
	}

	public OclCollection excluding(OclAny object) {
		return this;
	}

	public OclCollection symmetricDifference(OclSet set2) {
		return this;
	}

	public OclCollection flatten() {
		return this;
	}

   public OclBag asBag() {
		return null;
	}

	public OclSet asSet() {
		return null;
	}


	public OclSequence asSequence() {
		return null;
	}


	public OclOrderedSet asOrderedSet() {
		return null;
	}

	public OclCollection union(OclSequence sequence2) {
		return this;
	}


	public OclCollection append(OclAny object) {
		return this;
	}


	public OclCollection prepend(OclAny object) {
		return this;
	}

	public OclCollection insertAt(OclInteger index, OclAny object) {
		return this;
	}
 

	public OclCollection subSequence(OclInteger lower, OclInteger upper) {
		return this;
   }

	public Object at(OclInteger i) {
		return this;
	}

	public OclInteger indexOf(OclAny object) {
		return this;
   }

	public OclAny first() {
		return this;
	}

   public OclAny last() {
		return this;
	}

	public OclCollection subOrderedSet(OclInteger lower, OclInteger upper) {
		return this;
	}

}
