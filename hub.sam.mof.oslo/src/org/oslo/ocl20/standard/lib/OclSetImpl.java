package org.oslo.ocl20.standard.lib;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import org.oslo.ocl20.semantics.bridge.Classifier;


public class OclSetImpl extends OclCollectionImpl implements OclSet {
	protected OclSetImpl(Classifier eT, Object[] array, StdLibAdapter adapter) {
		super(eT,adapter);
		_implementation = new LinkedHashSet();
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null) {
				if (!((Boolean)includes(array[i]).asJavaObject()).booleanValue()) 
					_implementation.add(array[i]);
			}
		}
	}

	public OclType oclType() {
		Classifier type = adapter.getProcessor().getTypeFactory().buildSetType(super.getElementType());
		return adapter.Type(type);
	}

	java.util.Set _implementation;
	protected java.util.Collection implementation() {
		return _implementation;
	}
	protected java.util.Set set_impl() {
		return _implementation;
	}

	public OclBoolean equalTo(OclSet set2) {
		if (!this.size().equals( set2.size() ) )
			return adapter.Boolean(false);
		Collection b = implementation();
		Iterator it = b.iterator();
		while (it.hasNext()) {
			OclAny ob = (OclAny) it.next();
			if (!((Boolean)count(ob).equalTo(set2.count(ob)).asJavaObject()).booleanValue())
				return adapter.Boolean(false);
		}
		return adapter.Boolean(true);
	}
	
	public OclBoolean notEqualTo(OclSet set2) {
		return equalTo(set2).not();
	}

	//--- IOclSet ---
	public OclSet union(OclSet set2) {
		OclSet s = this.union(set2.asBag()).asSet();
		return s;
	}

	public OclBag union(OclBag bag) {
		OclBagImpl b = (OclBagImpl) adapter.Bag(this.getElementType(), set_impl());
		b.implementation().addAll(((OclBagImpl) bag).implementation());
		return b;
	}

	public OclSet union(OclOrderedSet set2) {
		OclSet s = this.union(set2.asBag()).asSet();
		return s;
	}

	public OclSet intersection(OclSet set2) {
		OclSet s = adapter.Set(this.getElementType());
		Set s1 = (Set)this.getImplementation();
		Set s2 = (Set)set2.getImplementation();
		Iterator i1 = s1.iterator();
		while (i1.hasNext()) {
			OclAny o = (OclAny) i1.next();
			if (((Boolean)set2.includes(o).asJavaObject()).booleanValue())
				s=s.including(o);
		}
		Iterator i2 = s2.iterator();
		while (i2.hasNext()) {
		    OclAny o = (OclAny) i2.next();
			if (((Boolean)this.includes(o).asJavaObject()).booleanValue())
				s = s.including(o);
		}
		return s;
	}

	public OclSet intersection(OclBag bag) {
		return this.intersection(bag.asSet());
	}

	public OclSet intersection(OclOrderedSet set2) {
		return this.intersection(set2.asSet());
	}

	public OclSet subtract(OclSet set2) {
		OclSet s = adapter.Set(this.getElementType());
		Set s1 = (Set)this.getImplementation();
		Set s2 = (Set)set2.getImplementation();
		Iterator i1 = s1.iterator();
		while (i1.hasNext()) {
			OclAny o = (OclAny) i1.next();
			if (!s2.contains(o))
				s = s.including(o);
		}
		return s;
	}

	public OclSet including(OclAny object) {
		OclSetImpl s = (OclSetImpl) adapter.Set(this.getElementType(), set_impl());
		if (object instanceof OclUndefined) return s;
		if (!((Boolean)includes(object).asJavaObject()).booleanValue()) 
			s.set_impl().add(object);
		return s;
	}

	public OclSet excluding(OclAny object) {
		OclSetImpl s = (OclSetImpl) adapter.Set(this.getElementType(), set_impl());
		s.set_impl().remove(object);
		return s;
	}

	public OclSet symmetricDifference(OclSet set2) {
		OclSet s = adapter.Set(this.getElementType());
		Set s1 = (Set)this.getImplementation();
		Set s2 = (Set)set2.getImplementation();
		Iterator i1 = s1.iterator();
		while (i1.hasNext()) {
			OclAny o = (OclAny) i1.next();
			if (!s2.contains(o))
				s = s.including(o);
		}
		Iterator i2 = s2.iterator();
		while (i2.hasNext()) {
		    OclAny o = (OclAny) i2.next();
			if (!s1.contains(o))
				s = s.including(o);
		}
		return s;
	}

	public OclInteger count(OclAny object) {
		return super.count(object);
	}

	public OclSet flatten() {
		OclSet flat = (OclSetImpl) adapter.Set(this.getElementType());
		java.util.Iterator i = _implementation.iterator();
		while (i.hasNext()) {
			OclAny elem = (OclAny) i.next();
			if (elem instanceof OclBag) {
				flat = flat.union(((OclBag) elem)).asSet();
			} else if (elem instanceof OclSet) {
				flat = flat.union(((OclSet) elem)).asSet();
			} else if (elem instanceof OclSequence) {
				flat = flat.union(((OclSequence) elem).asSet()).asSet();
			} else {
				flat = flat.including(elem);
			}
		}
		return flat;
	}

	//--- Object ---
	public String toString() {
		String str = "Set { ";
		java.util.Iterator i = _implementation.iterator();
		while (i.hasNext()) {
			str += i.next();
			if (i.hasNext())
				str += ", ";
		}
		return str + " }";
	}

	public Object clone() {
		return adapter.Set(this.getElementType(), set_impl());
	}

	public boolean equals(Object o) {
		return (o instanceof OclSet) ? ((Boolean)equalTo((OclSet)o).asJavaObject()).booleanValue() : false;
	}

	//--- IOclVisitable ---
	public Object accept(OclVisitor v, Object obj) {
		return v.visit(this, obj);
	}
	public Object asJavaObject() {
		Set jObj = new LinkedHashSet();
		Iterator i = _implementation.iterator();
		while (i.hasNext()) {
			Object o = i.next();
			if (o instanceof OclAny)
				o = ((OclAny)o).asJavaObject();
			jObj.add( o );
		}
		return java.util.Collections.unmodifiableSet(jObj);
	}
}
