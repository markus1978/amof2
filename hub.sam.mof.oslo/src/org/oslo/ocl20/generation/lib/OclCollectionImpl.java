package org.oslo.ocl20.generation.lib;

import org.oslo.ocl20.semantics.bridge.Classifier;
import org.oslo.ocl20.semantics.model.types.IntegerType;
import org.oslo.ocl20.semantics.model.types.RealType;
import org.oslo.ocl20.semantics.model.types.StringType;
import org.oslo.ocl20.semantics.model.types.TupleType;
import org.oslo.ocl20.standard.lib.OclAny;
import org.oslo.ocl20.standard.lib.OclBag;
import org.oslo.ocl20.standard.lib.OclBoolean;
import org.oslo.ocl20.standard.lib.OclCollection;
import org.oslo.ocl20.standard.lib.OclInteger;
import org.oslo.ocl20.standard.lib.OclOrderedSet;
import org.oslo.ocl20.standard.lib.OclSequence;
import org.oslo.ocl20.standard.lib.OclSet;
import org.oslo.ocl20.synthesis.OclCodeGeneratorVisitorImpl;


abstract public class OclCollectionImpl extends OclAnyImpl implements OclCollection {
	//StdLibAdapter adapter;
	protected OclCollectionImpl(Classifier eT, StdLibGenerationAdapterImpl adapter) {
		super(adapter);
		this.adapter = adapter;
		this._elementType = eT;
	}
	
	protected String _currentVariableName;

	Classifier _elementType;
	public Classifier getElementType() {return this._elementType;}

	abstract String implementation();
	
	//---Impl---
	public Object getImplementation() {return implementation();}
	
	public void addParts(OclAny[] array, Classifier eT) {
		//OrderedSet done differently
		for (int i = 0; i < array.length; i++) {
			OclAny x = ((OclAnyImpl)array[i]).wrappedWithExceptionHandler(eT);
			_init +=  ((Impl)x).getInitialisation();
			_init += _currentVariableName + ".add(" + x.asJavaObject() + ");\n";
		}
	}

	protected OclCollection flatten(OclCollectionImpl result, OclCollectionImpl col) {
		String itVar1 = StdLibGenerationAdapterImpl.newTempVar("iter");
		String flat = StdLibGenerationAdapterImpl.newTempVar("flat");
		String ex = StdLibGenerationAdapterImpl.newTempVar("ex");
		String obj = StdLibGenerationAdapterImpl.newTempVar("obj");
		result._init = this.getInitialisation() + "// flatten\n" + result.getInitialisation();
		result._init += "try { "+result+" = "+this+"; } catch (Exception "+ex+") {}";
		result._init += "boolean "+flat+"=false;";
		result._init += "while (!"+flat+") {";
		result._init += "  "+flat+"=true;";
		result._init += "java.util.Iterator " + itVar1 + " = "+result+".iterator();";
		result._init += col.getInitialisation();
		result._init += "while (" + itVar1 + ".hasNext()) {\n";
		result._init += "  Object "+obj+" = " + itVar1 + ".next();\n";
		result._init += "  if ( "+obj+" instanceof java.util.Collection ) {\n";
		result._init += "    " + col + ".addAll((java.util.Collection)"+obj+");\n";
		result._init += "    "+flat+" = false;\n";
		result._init += "  } else\n";
		result._init += "    " + col + ".add("+obj+");\n";
		result._init += "}\n";
		result._init += result +"="+col+";";
		result._init += "}\n";
		return result;
	}

	//--- ICollection ---
	public OclInteger size() {
		OclIntegerImpl res = (OclIntegerImpl)adapter.Integer(this +".size()");
		res.setInitialisation(this.getInitialisation());
		return res;
	}
	public OclBoolean includes(Object object) {
		if (object instanceof OclAnyImpl) {
			OclAnyImpl obj = (OclAnyImpl) object;
			OclBooleanImpl resultVar = (OclBooleanImpl) adapter.Boolean("java.lang.Boolean.FALSE",true);
			String itVar = StdLibGenerationAdapterImpl.newTempVar("i");
			String str = "// includes\n";
			str+=resultVar._init;
			str += "java.util.Iterator " + itVar + " = " + this +".iterator();\n";
			str += "while (" + itVar + ".hasNext()) if ( (" + obj.asJavaObject() + ").equals(" + itVar + ".next()) ) {" + resultVar + " = "+OclBooleanImpl.TRUE+"; break; }\n";
			resultVar._init = _init + obj._init + str;
			return resultVar;
		}
		return null;
	}

	public OclBoolean excludes(Object object) {
		if (object instanceof OclAnyImpl) {
			OclAnyImpl obj = (OclAnyImpl) object;
			OclBooleanImpl resultVar = (OclBooleanImpl) adapter.Boolean("java.lang.Boolean.TRUE",true);
			String itVar = StdLibGenerationAdapterImpl.newTempVar("iterator");
			String str = "// excludes\n";
			str+=resultVar._init;
			str += "java.util.Iterator " + itVar + " = " + this +".iterator();\n";
			str += "while (" + itVar + ".hasNext()) if ( (" + obj.asJavaObject() + ").equals(" + itVar + ".next()) ) {" + resultVar + " = java.lang.Boolean.FALSE; break; }\n";
			resultVar._init = _init + obj._init + str;
			return resultVar;
		}
		return null;
	}

	/* post: result = collection->iterate( elem; acc : Integer = 0 |
	                                        if elem = object then acc + 1 else acc endif )
	*/
	public OclInteger count(OclAny object) {
		if (object instanceof OclAnyImpl) {
			OclAnyImpl obj = (OclAnyImpl) object;
			String resultVar = OclCodeGeneratorVisitorImpl.newTempVar();
			String itVar = OclCodeGeneratorVisitorImpl.newTempVar();
			String str = "// count\n";
			str += "int " + resultVar + " =0;\n";
			str += "java.util.Iterator " + itVar + " = " + this +".iterator();\n";
			str += "while (" + itVar + ".hasNext()) if ( (" + obj.asJavaObject() + ").equals(" + itVar + ".next()) ) {" + resultVar + "++; }\n";
			OclIntegerImpl i = (OclIntegerImpl) adapter.Integer(resultVar);
			i._init = _init + obj._init + str;
			return i;
		}
		return null;
	}

	public OclBoolean includesAll(OclCollection collection) {
		if (collection instanceof OclCollectionImpl) {
			OclCollectionImpl col = (OclCollectionImpl) collection;
			OclBooleanImpl resultVar = (OclBooleanImpl) adapter.Boolean("java.lang.Boolean.TRUE",true);
			String itVar = OclCodeGeneratorVisitorImpl.newTempVar();
			String str = "// includesAll\n";
			str+=resultVar._init;
			str += "java.util.Iterator " + itVar + " = " + col +".iterator();\n";
			str += "while (" + itVar + ".hasNext()) if ( ! (" + this + ").contains(" + itVar + ".next()) ) {" + resultVar + "=java.lang.Boolean.FALSE; break; }\n";
			resultVar._init = _init + col._init + str;
			return resultVar;
		}
		return null;
	}

	public OclBoolean excludesAll(OclCollection collection) {
		if (collection instanceof OclCollectionImpl) {
			OclCollectionImpl col = (OclCollectionImpl) collection;
			OclBooleanImpl resultVar = (OclBooleanImpl) adapter.Boolean("java.lang.Boolean.TRUE",true);
			String itVar = OclCodeGeneratorVisitorImpl.newTempVar();
			String str = "// excludesAll\n";
			str+=resultVar._init;
			str += "java.util.Iterator " + itVar + " = " + col +".iterator();\n";
			str += "while (" + itVar + ".hasNext()) if ( (" + this + ").contains(" + itVar + ".next()) ) {" + resultVar + "=java.lang.Boolean.FALSE; break; }\n";
			resultVar._init = _init + col._init + str;
			return resultVar;
		}
		return null;
	}

	public OclBoolean isEmpty() {
		OclBooleanImpl b = (OclBooleanImpl) adapter.Boolean("new java.lang.Boolean("+this+".isEmpty())",true);
		b._init = _init+b._init;
		return b;
	}

	public OclBoolean notEmpty() {
		OclBooleanImpl b = (OclBooleanImpl) adapter.Boolean("new java.lang.Boolean(!"+this+".isEmpty())",true);
		b._init = _init+b._init;
		return b;
	}

	/* post: result = collection->iterate( elem; acc : T = 0 | acc + elem ) */
	/* elem must have an operation 'add(T)' defined. */
	//T sum();
	public Object sum() {
		OclAnyImpl result = (OclAnyImpl)((StdLibGenerationAdapterImpl)adapter).OclAny(this.getElementType(),StdLibGenerationAdapterImpl.newTempVar());
		result._init += this.getInitialisation();
		
		String resType = OclCodeGeneratorVisitorImpl.unBoxedTypeName(this.getElementType());
		String itVar = StdLibGenerationAdapterImpl.newTempVar();
		result._init += resType+" "+result+";\n";
		result._init += "if (!"+this+".isEmpty()) {\n";
		result._init += "  java.util.Iterator "+itVar+" = "+this+".iterator();\n";
		String unboxed = OclCodeGeneratorVisitorImpl.unBox(this.getElementType(),itVar+".next()" );
		result._init += "  "+result+" = ("+resType+")"+unboxed+";\n";
		result._init += "  while (" + itVar + ".hasNext()) \n";
		String addStr = getAddStr(this.getElementType());
		result._init += "    "+result+" = ("+result+")"+addStr+"(("+resType+")"+unboxed+");\n";
		result._init += "}\n";
		
		return result;
	}



	private String getAddStr(Classifier c) {
		if (  c instanceof IntegerType
		   || c instanceof RealType
	   	   || c instanceof StringType
		   )
			return "+";
		else
			return ".add";
	}

	public OclSet product(OclCollection col2) {
		Classifier[] types = new Classifier[]{this.getElementType(), this.getElementType()};
		String[] keys = new String[]{"first", "second"};
		TupleType tt = adapter.getProcessor().getTypeFactory().buildTupleType(keys, types);
		String tupleVar = OclCodeGeneratorVisitorImpl.newTempVar();
		OclTupleImpl tuple = (OclTupleImpl)((StdLibGenerationAdapterImpl)adapter).Tuple(tt, tupleVar, false);
		OclSetImpl result = (OclSetImpl)adapter.Set(tt);
		
		String colType = this.getElementType().getName();
		String itVar1 = OclCodeGeneratorVisitorImpl.newTempVar();
		String itVar2 = OclCodeGeneratorVisitorImpl.newTempVar();
		OclAnyImpl o1 = (OclAnyImpl)adapter.OclAny(this.getElementType());
		OclAnyImpl o2 = (OclAnyImpl)adapter.OclAny(this.getElementType());
		
		result._init += "java.util.Set "+result+" = new java.util.HashSet();\n";
		result._init += "java.util.Iterator "+itVar1+" = "+this+".iterator();\n";
		result._init += "while (" + itVar1 + ".hasNext()) {\n";
		result._init += "  Object "+o1+" = "+itVar1+".next();\n";
		result._init += "  java.util.Iterator "+itVar2+" = "+this+".iterator();\n";
		result._init += "  while (" + itVar2 + ".hasNext()) {\n";
		result._init += "    Object "+o2+" = "+itVar2+".next();\n";
		result._init += "    java.util.Map "+tuple+" = new java.util.HashSet();\n";
		result._init += "    "+tuple+".put(\"first\","+o1+");\n";
		result._init += "    "+tuple+".put(\"second\","+o2+");\n";
		result._init += "    "+result+" = "+result+".add("+tuple+");\n";
		result._init += "  }\n";
		result._init += "}\n";
		
		return result;
	}

    public OclSequence asSequence() {
        OclSequenceImpl result = (OclSequenceImpl) adapter.Sequence(this.getElementType());
        result._init = this._init;
        result._init += "java.util.List " + result + " = new java.util.Vector(" + this +");\n";
        return result;
    }

    public OclBag asBag() {
        OclBagImpl result = (OclBagImpl) adapter.Bag(this.getElementType());
        result._init = this._init;
        result._init += "java.util.Collection " + result + " = new java.util.Vector(" + this +");\n";
        return result;
    }

    public OclSet asSet() {
        OclSetImpl result = (OclSetImpl) adapter.Set(this.getElementType());
        result._init = this._init;
        result._init += "java.util.Set " + result + " = new java.util.HashSet(" + this +");\n";
        return result;
    }

    public OclOrderedSet asOrderedSet() {
        OclOrderedSetImpl result = (OclOrderedSetImpl) adapter.OrderedSet(this.getElementType());
        result._init = this._init;
        result._init += "java.util.List " + result + " = new java.util.Vector(" + this +");\n";
        return result;
    }
	
	//--- Object ---
	abstract public Object clone();

}
