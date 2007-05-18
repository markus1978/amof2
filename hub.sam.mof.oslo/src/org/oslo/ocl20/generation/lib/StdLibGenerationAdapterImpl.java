/*
 * Created on 07-Jun-2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package org.oslo.ocl20.generation.lib;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.oslo.ocl20.OclProcessor;
import org.oslo.ocl20.semantics.bridge.Classifier;
import org.oslo.ocl20.semantics.model.types.BagType;
import org.oslo.ocl20.semantics.model.types.BooleanType;
import org.oslo.ocl20.semantics.model.types.CollectionType;
import org.oslo.ocl20.semantics.model.types.IntegerType;
import org.oslo.ocl20.semantics.model.types.OclAnyType;
import org.oslo.ocl20.semantics.model.types.OrderedSetType;
import org.oslo.ocl20.semantics.model.types.RealType;
import org.oslo.ocl20.semantics.model.types.SequenceType;
import org.oslo.ocl20.semantics.model.types.SetType;
import org.oslo.ocl20.semantics.model.types.StringType;
import org.oslo.ocl20.semantics.model.types.TupleType;
import org.oslo.ocl20.semantics.model.types.VoidType;
import org.oslo.ocl20.standard.lib.OclAny;
import org.oslo.ocl20.standard.lib.OclAnyModelElement;
import org.oslo.ocl20.standard.lib.OclBag;
import org.oslo.ocl20.standard.lib.OclBoolean;
import org.oslo.ocl20.standard.lib.OclCollection;
import org.oslo.ocl20.standard.lib.OclEnumeration;
import org.oslo.ocl20.standard.lib.OclInteger;
import org.oslo.ocl20.standard.lib.OclOrderedSet;
import org.oslo.ocl20.standard.lib.OclReal;
import org.oslo.ocl20.standard.lib.OclSequence;
import org.oslo.ocl20.standard.lib.OclSet;
import org.oslo.ocl20.standard.lib.OclString;
import org.oslo.ocl20.standard.lib.OclTuple;
import org.oslo.ocl20.standard.lib.OclType;
import org.oslo.ocl20.standard.lib.OclUndefined;
import org.oslo.ocl20.standard.lib.StdLibAdapter;



/**
 * @author dha
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class StdLibGenerationAdapterImpl
implements StdLibAdapter
{
	/** Temporary variables */
	protected static Map tempVarCounters = new HashMap();
	public static String newTempVar(String key) {
		Integer i = (Integer)tempVarCounters.get(key);
		int counter = 0;
		if (i != null) {
			counter = i.intValue();
		}
		counter++;
		tempVarCounters.put(key,new Integer(counter));
		return key + counter;
	}
	public static String newTempVar() {
		return newTempVar("t");
	}
	public void resetCounters() {
		tempVarCounters = new HashMap();
	}

	private OclProcessor processor;
	public OclProcessor getProcessor() {return processor;}

	public StdLibGenerationAdapterImpl(OclProcessor proc) {
		this.processor = proc;
		OclUndefinedImpl.UNDEFINED = new OclUndefinedImpl("", null, this);
		OclBooleanImpl.FALSE = Boolean("java.lang.Boolean.FALSE", false);
		OclBooleanImpl.TRUE = Boolean("java.lang.Boolean.TRUE", false);
	}

	public OclAny OclAny(java.lang.Object obj) {
		if (obj == null) return this.Undefined();
		if (obj instanceof OclAny) return (OclAny)obj;
		if (obj instanceof java.lang.Boolean) return this.Boolean((java.lang.Boolean)obj);
		if (obj instanceof java.lang.String) return this.String((java.lang.String)obj);
		if (obj instanceof java.lang.Double) return this.Real((java.lang.Double)obj);
		if (obj instanceof java.lang.Float) return this.Real(new Double(((java.lang.Float)obj).doubleValue()));
		if (obj instanceof java.lang.Integer) return this.Integer((java.lang.Integer)obj);
		return this.Undefined();
	}

	public OclAny OclAny(Classifier type, java.lang.Object obj) {
		return this.OclAny(type, (java.lang.String)obj, false);
	}
	
	public OclAny OclAny(Classifier type, java.lang.String obj) {
		return this.OclAny(type, obj, false);
	}
	
	public OclAny OclAny(Classifier type, java.lang.String obj, boolean newVariable) {
		if (type == null) return this.Undefined();
		if (type instanceof VoidType) return this.Undefined();
		if (type instanceof BooleanType) return this.Boolean(obj,newVariable);
		if (type instanceof StringType) return this.String(obj,newVariable);
		if (type instanceof IntegerType) return this.Integer(obj,newVariable);
		if (type instanceof RealType) return this.Real(obj,newVariable);
		if (type instanceof TupleType) return this.Tuple((TupleType)type, obj,newVariable);
		if (type instanceof CollectionType) return this.OclCollection((CollectionType)type, obj,newVariable);
		return this.OclAnyModelElement(type, obj,newVariable);
	}

	//public Object impl(OclAny obj) {
	//	throw new UnsupportedOperationException();	}

	public OclType Type(Classifier cls) {
		return new OclTypeImpl(cls, this);
	}

	public OclAnyModelElement OclAnyModelElement(Classifier type,java.lang.Object obj) {
		return OclAnyModelElement(type, obj,false);
	}

	public OclAnyModelElement OclAnyModelElement(Classifier type, java.lang.Object obj, boolean newVariable) {
		if (obj instanceof String)
			return new OclAnyModelElementImpl(type, (String)obj,this, newVariable);
		return null;
	}

	public OclEnumeration Enumeration(Classifier type, java.lang.Object obj) {
		if (obj instanceof String)
			return new OclEnumerationImpl(type, (String)obj, this, false);
		return null; 
	}

	//public java.lang.Object impl(OclAnyModelElement obj) {
	//	throw new UnsupportedOperationException();	}

	//--- Undefined ---
	//public Object impl(OclUndefined obj) {
	//	throw new UnsupportedOperationException();
	//}
	
	public OclUndefined Undefined() {
		return OclUndefinedImpl.UNDEFINED;
	}

	//--- Boolean ---
	public OclBoolean Boolean(boolean b) {
		if (b)
			return OclBooleanImpl.TRUE;
		else
			return OclBooleanImpl.FALSE;
	}

	public OclBoolean Boolean(java.lang.Boolean b) {
		if (b.booleanValue())
			return OclBooleanImpl.TRUE;
		else
			return OclBooleanImpl.FALSE;
	}

	public OclBoolean Boolean(java.lang.String b) {
		return new OclBooleanImpl(b, this, false);
	}

	public OclBoolean Boolean(java.lang.String b, boolean newVariable) {
		return new OclBooleanImpl(b, this, newVariable);
	}

	//public java.lang.Boolean impl(OclBoolean b) {
	//	throw new UnsupportedOperationException();
	//}

	//--- Real ---
	public OclReal Real(double r) {
		return Real(String.valueOf(r));
	}

	public OclReal Real(Double r) {
		return Real(r.toString());
	}

	public OclReal Real(java.lang.String r) {
		return Real(r, false);
	}

	public OclReal Real(java.lang.String r, boolean newVariable) {
		return new OclRealImpl(r, this, newVariable);
	}

	public OclReal Real(float r) {
		return Real(String.valueOf(r));
	}

	//public java.lang.Double impl(OclReal r) {
	//	throw new UnsupportedOperationException();
	//}

	//--- Integer ---
	public OclInteger Integer(int i) {
		return Integer(String.valueOf(i));
	}

	public OclInteger Integer(Integer i) {
		return Integer(i.toString());
	}

	public OclInteger Integer(java.lang.String i) {
		return Integer(i, false);
	}
	public OclInteger Integer(java.lang.String i, boolean newVariable) {
		return new OclIntegerImpl(i, this, newVariable);
	}

	//public java.lang.Integer impl(OclInteger i) {
	//	throw new UnsupportedOperationException();
	//}

	//--- String ---
	public OclString String(java.lang.String s) {
		return String(s, false);
	}

	public OclString String(java.lang.String s, boolean newVariable) {
		return new OclStringImpl(s, this, newVariable);
	}

	//public java.lang.String impl(OclString s) {
	//	throw new UnsupportedOperationException();
	//}

	//--- Tuple ---
	public OclTuple Tuple(TupleType tT, java.util.Map m) {
		Vector types = new Vector();
		Vector keys = new Vector();
		Vector values = new Vector();
		Iterator i = m.entrySet().iterator();
		while (i.hasNext()) {
			java.util.Map.Entry me = (java.util.Map.Entry)i.next();
			types.add( me.getValue().getClass() );
			keys.add( me.getKey() );
			values.add( OclAny(me.getValue()) );
		}
		return Tuple( tT, (OclAny[])values.toArray(new OclAny[]{}) );
	}

	public OclTuple Tuple(TupleType tT,  OclAny[] arr) {
		return new OclTupleImpl(tT, arr, this);
	}

	public OclTuple Tuple(TupleType type, String obj) {
		return Tuple(type, obj, false);
	}
	
	public OclTuple Tuple(TupleType tT, java.lang.String tup, boolean newVariable) {
		return new OclTupleImpl(tT, tup, this, newVariable);
	}

	//public java.util.Map impl(OclTuple t) {
	//	throw new UnsupportedOperationException();
	//}

	//--- Collection ---
	public OclCollection Collection(java.util.Collection obj) {
		OclAnyType oat = processor.getTypeFactory().buildOclAnyType();
		if (obj == null) return this.Undefined();
		if (obj instanceof java.util.List) return this.Sequence(oat, (java.util.List)obj);
		if (obj instanceof java.util.Set) return this.Set(oat,(java.util.Set)obj);
		if (obj instanceof java.util.Collection) return this.Bag(oat,(java.util.Collection)obj);
		return this.Undefined();
	}

	public OclCollection Collection(Classifier eT) {
		if (eT instanceof OrderedSetType) return this.OrderedSet(eT);
		if (eT instanceof SetType) return this.Set(eT);
		if (eT instanceof SequenceType) return this.Sequence(eT);
		if (eT instanceof BagType) return this.Bag(eT);
		return this.Undefined();
	}

	public OclCollection OclCollection(CollectionType type, java.lang.String obj) {
		return OclCollection(type, obj,false);
	}
	
	public OclCollection OclCollection(CollectionType type, java.lang.String obj, boolean newVariable) {
		if (obj == null) return this.Undefined();
		if (type instanceof OrderedSetType) return this.OrderedSet(type.getElementType(),obj, newVariable);
		if (type instanceof SetType) return this.Set(type.getElementType(),obj, newVariable);
		if (type instanceof SequenceType) return this.Sequence(type.getElementType(), obj, newVariable);
		if (type instanceof BagType) return this.Bag(type.getElementType(),obj, newVariable);
		return this.Undefined();
	}

	//public java.util.Collection impl(OclCollection col) {
	//	throw new UnsupportedOperationException();
	//}
   
	//--- Set ---
	public OclSet Set(Classifier eT) {
		return Set(eT,new OclAny[0]);
	}

	public OclSet Set(Classifier eT,java.util.Collection impl) {
		if (impl != null) {
			return Set(eT,convert(impl.toArray()));
		} else {
			return Set(eT); 
		}
			
	}

	public OclSet Set(Classifier eT, String expr) {
		return Set(eT, expr,false);
	}

	public OclSet Set(Classifier eT, String expr, boolean newVariable) {
		return new OclSetImpl(eT, expr, this, newVariable);
	}

	public OclSet Set(Classifier eT, Object[] array) {
		return new OclSetImpl(eT, convert(array), this);
	}

	//public java.util.Set impl(OclSet s) {
	//	throw new UnsupportedOperationException();
	//}

	//--- OrderedSet ---
	public OclOrderedSet OrderedSet(Class eT) {
		return OrderedSet(processor.getBridgeFactory().buildClassifier(eT));
	}

	public OclOrderedSet OrderedSet(Classifier eT) {
		return OrderedSet(eT,new OclAny[0]);
	}

	public OclOrderedSet OrderedSet(Classifier eT,java.util.Collection impl) {
		return OrderedSet(eT,convert(impl.toArray()));
	}

	public OclOrderedSet OrderedSet(Classifier eT,java.lang.String os) {
		return OrderedSet(eT,os, false);
	}

	public OclOrderedSet OrderedSet(Classifier eT,java.lang.String os, boolean newVariable) {
		return new OclOrderedSetImpl(eT,os, this, newVariable);
	}

	public OclOrderedSet OrderedSet(Classifier eT, Object[] array) {
		return new OclOrderedSetImpl(eT,convert(array), this);
	}

	//public java.util.List impl(OclOrderedSet s) {
	//	throw new UnsupportedOperationException();
	//}

	//--- Sequence ---
	public OclSequence Sequence(Classifier eT) {
		return Sequence(eT, new OclAny[0]);
	}

	public OclSequence Sequence(Classifier eT, java.util.Collection impl) {
		return Sequence(eT, convert(impl.toArray()));
	}

	public OclSequence Sequence(Classifier eT, java.lang.String seq) {
		return Sequence(eT, seq, false);
	}
	public OclSequence Sequence(Classifier eT, java.lang.String seq, boolean newVariable) {
		return new OclSequenceImpl(eT, seq, this,newVariable);
	}
  	public OclSequence Sequence(Classifier eT, Object[] array) {
		return new OclSequenceImpl(eT, convert(array), this);
	}

	//public java.util.List impl(OclSequence s) {
	//	throw new UnsupportedOperationException();
	//}

	//--- Bag ---
	public OclBag Bag(Classifier eT) {
		return Bag(eT,new OclAny[0]);
	}

	public OclBag Bag(Classifier eT,java.util.Collection impl) {
		return Bag(eT, convert(impl.toArray()));
	}

	public OclBag Bag(Classifier eT,java.lang.String b) {
		return Bag(eT,b, false);
	}

	public OclBag Bag(Classifier eT,java.lang.String b, boolean newVariable) {
		return new OclBagImpl(eT,b, this,newVariable);
	}

	public OclBag Bag(Classifier eT, Object[] array) {
		return new OclBagImpl(eT, convert(array), this);
	}

	//public java.util.Collection impl(OclBag b) {
	//	throw new UnsupportedOperationException();
	//}
	
	protected OclAny[] convert(Object[] array) {
		if (array instanceof OclAny[]) return (OclAny[])array;
		OclAny[] oclArray = new OclAny[array.length];
		for(int i = 0; i<array.length;i++) {
			oclArray[i] = this.OclAny(array[i]);
		}
		return oclArray;
	}

}
