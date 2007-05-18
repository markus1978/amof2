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


public class OclBagImpl extends OclCollectionImpl implements OclBag {
    protected OclBagImpl(Classifier eT, OclAny[] array, StdLibGenerationAdapterImpl adapter) {
        super(eT, adapter);
        _currentVariableName = StdLibGenerationAdapterImpl.newTempVar();
        _init = "java.util.Collection " + _currentVariableName + " = new java.util.Vector();\n";
        super.addParts(array, eT);
    }

    protected OclBagImpl(Classifier eT, String bag, StdLibGenerationAdapterImpl adapter, boolean newVariable) {
        super(eT, adapter);
        if (newVariable) {
            _currentVariableName = StdLibGenerationAdapterImpl.newTempVar("bag");
            _init = "java.util.Collection " + _currentVariableName + "=new java.util.Vector(" + bag + ");\n";
        } else {
            super._currentVariableName = bag;
        }
    }

    protected String implementation() {
        return _currentVariableName;
    }
    protected String bag_impl() {
        return _currentVariableName;
    }

    public OclType oclType() {
        Classifier type = adapter.getProcessor().getTypeFactory().buildBagType(super.getElementType());
        OclTypeImpl ot = (OclTypeImpl) adapter.Type(type);
        ot.setInitialisation(this.getInitialisation() + ot.getInitialisation());
        return ot;
    }

    public OclBoolean equalTo(OclBag bag2) {
        OclBooleanImpl result = (OclBooleanImpl) adapter.Boolean(false);
        if (bag2 instanceof OclBagImpl) {
            OclBagImpl s2 = (OclBagImpl) bag2;
            //_init += s2._init;
            result = (OclBooleanImpl) adapter.Boolean("new java.lang.Boolean(" +this+".size()=="+bag2+".size() && "+ this +".containsAll(" + bag2 + ") && "+bag2+".containsAll("+this+"))", true);
            result._init = this._init + s2._init + result._init;
        }
        return result;
    }

    public OclBoolean notEqualTo(OclBag bag2) {
        return equalTo(bag2).not();
    }

    public OclBag union(OclBag bag2) {
        if (bag2 instanceof OclBagImpl) {
            OclBagImpl bag = (OclBagImpl) bag2;
            OclBagImpl result = (OclBagImpl) adapter.Bag(this.getElementType());
            result._init = this._init + bag._init + result._init;
            result._init += result + ".addAll(" + this +");\n";
            result._init += result + ".addAll(" + bag + ");\n";
            return result;
        }
        return null;
    }

    public OclBag union(OclSet s) {
        if (s instanceof OclSetImpl) {
            OclSetImpl set = (OclSetImpl) s;
            OclBagImpl result = (OclBagImpl) adapter.Bag(this.getElementType());
            result._init = this._init + set._init + result._init;
            result._init += result + ".addAll(" + this +");\n";
            result._init += result + ".addAll(" + set + ");\n";
            return result;
        }
        return null;
    }

    public OclBag union(OclOrderedSet s) {
        if (s instanceof OclOrderedSetImpl) {
            OclOrderedSetImpl set = (OclOrderedSetImpl) s;
            OclBagImpl result = (OclBagImpl) adapter.Bag(this.getElementType());
            result._init = this._init + set._init + result._init;
            result._init += result + ".addAll(" + this +");\n";
            result._init += result + ".addAll(" + set + ");\n";
            return result;
        }
        return null;
    }

    public OclBag intersection(OclBag bag2) {
        OclBagImpl result = (OclBagImpl) adapter.Bag(this.getElementType());
        result._init = this._init + ((OclBagImpl) bag2).getInitialisation() + "// intersection\n" + result._init;
        String it1 = StdLibGenerationAdapterImpl.newTempVar();
        result._init += "java.util.Iterator " + it1 + " = " + this +".iterator();\n";
        result._init += "while (" + it1 + ".hasNext()) {\n";
        result._init += "  Object o = " + it1 + ".next();\n";
        result._init += "  if (" + bag2 + ".contains(o)) " + result + ".add(o);\n";
        result._init += "}\n";
        result._init += it1 + " = " + bag2 + ".iterator();\n";
        result._init += "while (" + it1 + ".hasNext()) {\n";
        result._init += "  Object o = " + it1 + ".next();\n";
        result._init += "  if (" + this +".contains(o)) " + result + ".add(o);\n";
        result._init += "}\n";

        return result;
    }

    public OclSet intersection(OclSet set) {
        return this.intersection(set.asBag()).asSet();
    }

    public OclOrderedSet intersection(OclOrderedSet set) {
        return this.intersection(set.asOrderedSet());
    }

    public OclBag including(OclAny object) {
        if (object instanceof OclAnyImpl) {
            OclAnyImpl obj = (OclAnyImpl) object;
            String resultVar = OclCodeGeneratorVisitorImpl.newTempVar();
            String str = "java.util.Collection " + resultVar + " = new java.util.Vector(" + this +");\n";
            String t = StdLibGenerationAdapterImpl.newTempVar("obj");
            str += "Object " + t + "=" + obj.asJavaObject() + "\n;";
            str += "if (" + t + "!=null) " + resultVar + ".add(" + t + ");\n";
            OclBagImpl result = (OclBagImpl) ((StdLibGenerationAdapterImpl) adapter).Bag(this.getElementType(), resultVar);
            result._init = this._init + obj._init + str;
            return result;
        }
        return null;
    }

    public OclBag excluding(OclAny object) {
        if (object instanceof OclAnyImpl) {
            OclAnyImpl obj = (OclAnyImpl) object;
            String resultVar = OclCodeGeneratorVisitorImpl.newTempVar();
            String itVar = OclCodeGeneratorVisitorImpl.newTempVar();
            String str = "java.util.Collection " + resultVar + " = new java.util.Vector(" + this +");\n";
            str += resultVar + ".remove(" + obj.asJavaObject() + ");\n";
            OclBagImpl result = (OclBagImpl) ((StdLibGenerationAdapterImpl) adapter).Bag(this.getElementType(), resultVar);
            result._init = this._init + obj._init + str;
            return result;
        }
        return null;
    }

    public OclInteger count(OclAny object) {
        return super.count(object);
    }

    public OclBag flatten() {
		OclBagImpl result = (OclBagImpl) adapter.Bag(this.getElementType());
		OclBagImpl col = (OclBagImpl) adapter.Bag(this.getElementType());
		OclAnyType oat = adapter.getProcessor().getTypeFactory().buildOclAnyType();
        if (this.getElementType() instanceof CollectionType || this.getElementType().equals(oat)) {
			return (OclBag)super.flatten(result,col);
        } else {
            return this.asBag();
        }
    }

    //--- IOclVisitable ---
    public Object accept(OclVisitor v, Object obj) {
        return v.visit(this, obj);
    }

    //--- Object ---
    public String toString() {
        return _currentVariableName;
    }

    public Object clone() {
        return ((StdLibGenerationAdapterImpl) adapter).Bag(this.getElementType(), implementation());
    }

    public boolean equals(Object o) {
        if (o instanceof OclBag) {
            OclBoolean b = equalTo((OclBag) o);
            return ((Boolean) b.asJavaObject()).booleanValue();
        }
        return false;
    }

    public Object asJavaObject() {
        return this.toString();
    }

}
