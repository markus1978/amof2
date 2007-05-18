package org.oslo.ocl20.generation.lib;

import org.oslo.ocl20.semantics.bridge.Classifier;
import org.oslo.ocl20.semantics.model.types.CollectionType;
import org.oslo.ocl20.semantics.model.types.OclAnyType;
import org.oslo.ocl20.standard.lib.OclAny;
import org.oslo.ocl20.standard.lib.OclBag;
import org.oslo.ocl20.standard.lib.OclBoolean;
import org.oslo.ocl20.standard.lib.OclInteger;
import org.oslo.ocl20.standard.lib.OclOrderedSet;
import org.oslo.ocl20.standard.lib.OclSet;
import org.oslo.ocl20.standard.lib.OclType;
import org.oslo.ocl20.standard.lib.OclVisitor;
import org.oslo.ocl20.synthesis.OclCodeGeneratorVisitorImpl;


public class OclSetImpl extends OclCollectionImpl implements OclSet {

    public OclSetImpl(Classifier eT, OclAny[] array, StdLibGenerationAdapterImpl adapter) {
        super(eT, adapter);
        _currentVariableName = StdLibGenerationAdapterImpl.newTempVar();
        _init = "java.util.Set " + _currentVariableName + " = new java.util.HashSet();\n";
        super.addParts(array, eT);
    }

    public OclSetImpl(Classifier eT, String set, StdLibGenerationAdapterImpl adapter, boolean newVariable) {
        super(eT, adapter);
        if (newVariable) {
            _currentVariableName = StdLibGenerationAdapterImpl.newTempVar("set");
            _init = "java.util.Set " + _currentVariableName + "=new java.util.HashSet(" + set + ");\n";
        } else {
            _currentVariableName = set;
        }
    }

    public String implementation() {
        return _currentVariableName;
    }
    protected String set_impl() {
        return _currentVariableName;
    }

    public OclType oclType() {
        Classifier type = adapter.getProcessor().getTypeFactory().buildSetType(super.getElementType());
        OclTypeImpl ot = (OclTypeImpl) adapter.Type(type);
        ot.setInitialisation(this.getInitialisation() + ot.getInitialisation());
        return ot;
    }
    public OclBoolean equalTo(OclSet set2) {
        OclBooleanImpl result = (OclBooleanImpl) adapter.Boolean(false);
        if (set2 instanceof OclSetImpl) {
            OclSetImpl s2 = (OclSetImpl) set2;
            //_init += s2._init;
            result = (OclBooleanImpl) adapter.Boolean("new java.lang.Boolean(" + this +".equals(" + set2 + "))", true);
            result._init = this._init + s2._init + result._init;
        }
        return result;
    }

    public OclBoolean notEqualTo(OclSet set2) {
        return equalTo(set2).not();
    }

    //--- IOclSet ---
    public OclSet union(OclSet s2) {
        if (s2 instanceof OclSetImpl) {
            OclSetImpl set2 = (OclSetImpl) s2;
            OclSetImpl result = (OclSetImpl) adapter.Set(this.getElementType());
            result._init = this._init + set2._init + result._init;
            result._init += result + ".addAll(" + this +");\n";
            result._init += result + ".addAll(" + set2 + ");\n";
            return result;
        }
        return null;
    }

    public OclBag union(OclBag b) {
        if (b instanceof OclBagImpl) {
            OclBagImpl bag = (OclBagImpl) b;
            OclBagImpl result = (OclBagImpl) adapter.Bag(this.getElementType());
            result._init = this._init + bag._init + result._init;
            result._init += result + ".addAll(" + this +");\n";
            result._init += result + ".addAll(" + bag + ");\n";
            return result;
        }
        return null;
    }

    public OclSet union(OclOrderedSet s2) {
        if (s2 instanceof OclOrderedSetImpl) {
            OclOrderedSetImpl set2 = (OclOrderedSetImpl) s2;
            OclSetImpl result = (OclSetImpl) adapter.Set(this.getElementType());
            result._init = this._init + set2._init + result._init;
            result._init += result + ".addAll(" + this +");\n";
            result._init += result + ".addAll(" + set2 + ");\n";
            return result;
        }
        return null;
    }

    public OclSet intersection(OclSet s2) {
        if (s2 instanceof OclSetImpl) {
            OclSetImpl set2 = (OclSetImpl) s2;
            OclSetImpl result = (OclSetImpl) adapter.Set(this.getElementType());
            result._init = this._init + set2.getInitialisation() + "// intersection\n" + result._init;
            String it1 = StdLibGenerationAdapterImpl.newTempVar();
            result._init += "java.util.Iterator " + it1 + " = " + this +".iterator();\n";
            result._init += "while (" + it1 + ".hasNext()) {\n";
            result._init += "  Object o = " + it1 + ".next();\n";
            result._init += "  if (" + set2 + ".contains(o)) " + result + ".add(o);\n";
            result._init += "}\n";
            result._init += it1 + " = " + set2 + ".iterator();\n";
            result._init += "while (" + it1 + ".hasNext()) {\n";
            result._init += "  Object o = " + it1 + ".next();\n";
            result._init += "  if (" + this +".contains(o)) " + result + ".add(o);\n";
            result._init += "}\n";

            return result;
        }
        return null;
    }

    public OclSet intersection(OclBag bag) {
        return this.intersection(bag.asSet());
    }

    public OclSet intersection(OclOrderedSet set2) {
        return this.intersection(set2.asSet());
    }

    public OclSet subtract(OclSet s2) {
        if (s2 instanceof OclSetImpl) {
            OclSetImpl set2 = (OclSetImpl) s2;
            OclSetImpl result = (OclSetImpl) adapter.Set(this.getElementType());
            String itVar1 = OclCodeGeneratorVisitorImpl.newTempVar();

            result._init += this.getInitialisation() + set2.getInitialisation();
            //result._init += "java.util.Set " + result + " = new java.util.HashSet();\n";
            result._init += "java.util.Iterator " + itVar1 + " = " + this +".iterator();\n";
            result._init += "while (" + itVar1 + ".hasNext()) {\n";
            result._init += "  Object o = " + itVar1 + ".next();\n";
            result._init += "  if ( !" + set2 + ".contains(o) ) " + result + ".add(o);\n";
            result._init += "}\n";

            return result;
        }
        return null;
    }

    public OclSet including(OclAny object) {
        if (object instanceof OclAnyImpl) {
            OclAnyImpl obj = (OclAnyImpl) object;
            OclSetImpl result = (OclSetImpl) ((StdLibGenerationAdapterImpl) adapter).Set(this.getElementType(),this.toString(),true);
            String str = result._init;
            String t = StdLibGenerationAdapterImpl.newTempVar("obj");
            str += "Object " + t + "=" + obj.asJavaObject() + "\n;";
            str += "if (" + t + "!=null) " + result + ".add(" + t + ");\n";
            result._init = this._init + obj._init + str;
            return result;
        }
        return null;
    }

    public OclSet excluding(OclAny object) {
        if (object instanceof OclAnyImpl) {
            OclAnyImpl obj = (OclAnyImpl) object;
            OclSetImpl result = (OclSetImpl) ((StdLibGenerationAdapterImpl) adapter).Set(this.getElementType());
            String itVar = OclCodeGeneratorVisitorImpl.newTempVar();
            //String str = "java.util.Set " + result + " = new HashSet(" + this +");\n";
            String str = result._init;
            str += result + ".remove(" + obj.asJavaObject() + ");\n";
            result._init = this._init + obj._init + str;
            return result;
        }
        return null;
    }

    public OclSet symmetricDifference(OclSet s2) {
        if (s2 instanceof OclSetImpl) {
            OclSetImpl set2 = (OclSetImpl) s2;
            OclSetImpl result = (OclSetImpl) adapter.Set(this.getElementType());
            String itVar1 = OclCodeGeneratorVisitorImpl.newTempVar();

            result._init += this.getInitialisation() + set2.getInitialisation();
            result._init += "java.util.Iterator " + itVar1 + " = " + this +".iterator();\n";
            result._init += "while (" + itVar1 + ".hasNext()) {\n";
            result._init += "  Object o = " + itVar1 + ".next();\n";
            result._init += "  if ( ! " + set2 + ".contains(o) ) " + result + ".add(o);\n";
            result._init += "}\n";
            result._init += "" + itVar1 + " = " + set2 + ".iterator();\n";
            result._init += "while (" + itVar1 + ".hasNext()) {\n";
            result._init += "  Object o = " + itVar1 + ".next();\n";
            result._init += "  if ( ! " + this +".contains(o) ) " + result + ".add(o);\n";
            result._init += "}\n";

            return result;
        }
        return null;
    }

    public OclInteger count(OclAny object) {
        return super.count(object);
    }

    public OclSet flatten() {
		OclSetImpl result = (OclSetImpl) adapter.Set(this.getElementType());
		OclSetImpl col = (OclSetImpl) adapter.Set(this.getElementType());
		OclAnyType oat = adapter.getProcessor().getTypeFactory().buildOclAnyType();
		if (this.getElementType() instanceof CollectionType || this.getElementType().equals(oat)) {
			return (OclSet)super.flatten(result,col);
        } else {
            return this.asSet();
        }
    }

    //--- Object ---
    public String toString() {
        return _currentVariableName;
    }

    public Object clone() {
        return ((StdLibGenerationAdapterImpl) adapter).Set(this.getElementType(), set_impl());
    }

    public boolean equals(Object o) {
	    return this==o;
    }

    //--- IOclVisitable ---
    public Object accept(OclVisitor v, Object obj) {
        return v.visit(this, obj);
    }
    public Object asJavaObject() {
        return this.toString();
    }
}
