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
import org.oslo.ocl20.synthesis.OclCodeGeneratorVisitorImpl;


public class OclOrderedSetImpl extends OclCollectionImpl
//extends OclSetImpl
implements OclOrderedSet {
    protected OclOrderedSetImpl(Classifier eT, OclAny[] array, StdLibGenerationAdapterImpl adapter) {
        super(eT, adapter);
        _currentVariableName = StdLibGenerationAdapterImpl.newTempVar();
        _init = "java.util.List " + _currentVariableName + " = new java.util.Vector();\n";
        for (int i = 0; i < array.length; i++) {
            OclAny x = ((OclAnyImpl) array[i]).wrappedWithExceptionHandler(eT);
            _init += ((Impl)x).getInitialisation();
            _init += "Object obj = " + x.asJavaObject() + ";\n";
            _init += "if ( ! " + _currentVariableName + ".contains(obj)) { " + _currentVariableName + ".add(obj); }\n";
        }
    }

    protected OclOrderedSetImpl(Classifier eT, String oset, StdLibGenerationAdapterImpl adapter, boolean newVariable) {
        super(eT, adapter);
        if (newVariable) {
            _currentVariableName = StdLibGenerationAdapterImpl.newTempVar("ordSet");
            _init = "java.util.List " + _currentVariableName + "=new java.util.Vector(" + oset + ");\n";
        } else {
            super._currentVariableName = oset;
        }
    }

    protected String implementation() {
        return _currentVariableName;
    }
    protected String orderedset_impl() {
        return _currentVariableName;
    }

    public OclType oclType() {
        Classifier type = adapter.getProcessor().getTypeFactory().buildOrderedSetType(super.getElementType());
        OclTypeImpl ot = (OclTypeImpl) adapter.Type(type);
        ot.setInitialisation(this.getInitialisation() + ot.getInitialisation());
        return ot;
    }
    public OclBoolean equalTo(OclOrderedSet set2) {
        OclBooleanImpl result = (OclBooleanImpl) adapter.Boolean(false);
        if (set2 instanceof OclOrderedSetImpl) {
            OclOrderedSetImpl s2 = (OclOrderedSetImpl) set2;
            //_init += s2._init;
            result = (OclBooleanImpl) adapter.Boolean("new java.lang.Boolean(" + this +".equals(" + set2 + "))", true);
            result._init = this._init + s2._init + result._init;
        }
        return result;
    }

    public OclBoolean notEqualTo(OclOrderedSet set2) {
        return equalTo(set2).not();
    }

    //--- Set ---
    public OclBoolean equalTo(OclSet set2) {
            return this.asSet().equalTo(set2);
    }
    public OclBoolean notEqualTo(OclSet set2) {
        if (set2 instanceof OclOrderedSet)
            return this.notEqualTo((OclOrderedSet) set2);
        else
            return adapter.Boolean(true);
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

    public OclSet intersection(OclSet s2) {
        if (s2 instanceof OclSetImpl) {
            OclSetImpl set2 = (OclSetImpl) s2;
            OclSetImpl result = (OclSetImpl) adapter.Set(this.getElementType());
            String itVar1 = OclCodeGeneratorVisitorImpl.newTempVar();
            OclAnyImpl o1 = (OclAnyImpl) adapter.OclAny(this.getElementType());

            result._init += this.getInitialisation() + set2.getInitialisation();
            result._init += "java.util.Set " + result + " = new java.util.HashSet();\n";
            result._init += "Iterator " + itVar1 + " = " + this +".iterator();\n";
            result._init += "while (" + itVar1 + ".hasNext()) {\n";
            result._init += "  Object " + o1 + " = " + itVar1 + ".next();\n";
            result._init += "  if ( " + set2 + ".contains(" + o1 + ") ) " + result + " = " + result + ".add(" + o1 + ");\n";
            result._init += "}\n";
            result._init += "" + itVar1 + " = " + set2 + ".iterator();\n";
            result._init += "while (" + itVar1 + ".hasNext()) {\n";
            result._init += "  Object " + o1 + " = " + itVar1 + ".next();\n";
            result._init += "  if ( " + this +".contains(" + o1 + ") ) " + result + " = " + result + ".add(" + o1 + ");\n";
            result._init += "}\n";

            return result;
        }
        return null;
    }

    public OclSet intersection(OclBag bag) {
        return this.intersection(bag.asSet());
    }

    public OclSet intersection(OclOrderedSet set2) {
        return this.intersection(set2.asSet()).asOrderedSet();
    }

    public OclSet subtract(OclSet s2) {
        if (s2 instanceof OclSetImpl) {
            OclSetImpl set2 = (OclSetImpl) s2;
            OclSetImpl result = (OclSetImpl) adapter.Set(this.getElementType());
            String itVar1 = OclCodeGeneratorVisitorImpl.newTempVar();
            OclAnyImpl o1 = (OclAnyImpl) adapter.OclAny(this.getElementType());

            result._init += this.getInitialisation() + set2.getInitialisation();
            result._init += "java.util.Set " + result + " = new java.util.HashSet();\n";
            result._init += "Iterator " + itVar1 + " = " + this +".iterator();\n";
            result._init += "while (" + itVar1 + ".hasNext()) {\n";
            result._init += "  Object " + o1 + " = " + itVar1 + ".next();\n";
            result._init += "  if ( !" + set2 + ".contains(" + o1 + ") ) " + result + " = " + result + ".add(" + o1 + ");\n";
            result._init += "}\n";

            return result;
        }
        return null;
    }

    public OclSet symmetricDifference(OclSet s2) {
        if (s2 instanceof OclSetImpl) {
            OclSetImpl set2 = (OclSetImpl) s2;
            OclSetImpl result = (OclSetImpl) adapter.Set(this.getElementType());
            String itVar1 = OclCodeGeneratorVisitorImpl.newTempVar();
            OclAnyImpl o1 = (OclAnyImpl) adapter.OclAny(this.getElementType());

            result._init += this.getInitialisation() + set2.getInitialisation();
            result._init += "java.util.Set " + result + " = new java.util.HashSet();\n";
            result._init += "Iterator " + itVar1 + " = " + this +".iterator();\n";
            result._init += "while (" + itVar1 + ".hasNext()) {\n";
            result._init += "  Object " + o1 + " = " + itVar1 + ".next();\n";
            result._init += "  if ( ! " + set2 + ".contains(" + o1 + ") ) " + result + " = " + result + ".add(" + o1 + ");\n";
            result._init += "}\n";
            result._init += "" + itVar1 + " = " + set2 + ".iterator();\n";
            result._init += "while (" + itVar1 + ".hasNext()) {\n";
            result._init += "  Object " + o1 + " = " + itVar1 + ".next();\n";
            result._init += "  if ( ! " + this +".contains(" + o1 + ") ) " + result + " = " + result + ".add(" + o1 + ");\n";
            result._init += "}\n";

            return result;
        }
        return null;
    }

    //--- OrderedSet ---
    public OclOrderedSet append(OclAny obj) {
        OclOrderedSetImpl result = (OclOrderedSetImpl) adapter.OrderedSet(this.getElementType(),this.toString(),true);
        result._init = ((Impl)obj).getInitialisation() + this._init + result._init;
        String t = StdLibGenerationAdapterImpl.newTempVar("obj");
        String str = "Object " + t + "=" + obj.asJavaObject() + "\n;";
        str += "if (" + t + "!=null && !" + result + ".contains(" + t + ")) " + result + ".add(" + t + ");\n";
        result._init += str;
        return result;
    }

    public OclOrderedSet prepend(OclAny obj) {
        OclOrderedSetImpl result = (OclOrderedSetImpl) adapter.OrderedSet(this.getElementType());
        result._init = ((Impl)obj).getInitialisation() + this._init + result._init;
        String t = StdLibGenerationAdapterImpl.newTempVar("obj");
        String str = "Object " + t + "=" + obj.asJavaObject() + "\n;";
        str += "if (" + t + "!=null && !" + result + ".contains(" + t + ")) " + result + ".add(0," + t + ");\n";
        result._init += str;
        return result;
    }

    public OclSet including(OclAny object) {
        return this.append(object);
    }

    public OclSet excluding(OclAny object) {
        OclOrderedSetImpl result = (OclOrderedSetImpl) adapter.OrderedSet(this.getElementType());
        result._init = ((Impl)object).getInitialisation() + this._init + result._init;
        result._init += result + ".removeAll(" + object + ");\n";
        return result;
    }

    public OclOrderedSet insertAt(OclInteger index, OclAny obj) {
        OclOrderedSetImpl result = (OclOrderedSetImpl) adapter.OrderedSet(this.getElementType());
        result._init = ((Impl)obj).getInitialisation() + ((Impl)index).getInitialisation() + this._init + result._init;
        String t = StdLibGenerationAdapterImpl.newTempVar("obj");
        String str = "Object " + t + "=" + obj.asJavaObject() + "\n;";
        str += "if (" + t + "!=null && !" + result + ".contains(" + t + ")) " + result + ".add(" + index + "," + t + ");\n";
        return result;
    }

    public OclOrderedSet subOrderedSet(OclInteger lower, OclInteger upper) {
        OclOrderedSetImpl result = (OclOrderedSetImpl) adapter.OrderedSet(this.getElementType());
        result._init = ((Impl)lower).getInitialisation() + ((Impl)upper).getInitialisation() + this._init + result._init;
        result._init += result + " = "+this+".subList(" + lower + "-1," + upper + ");\n";
        return result;
    }

    public OclAny at(OclInteger i) {
        OclAnyImpl res = (OclAnyImpl) ((StdLibGenerationAdapterImpl) adapter).OclAny(this.getElementType(), OclCodeGeneratorVisitorImpl.unBox(this.getElementType(), this +".get((" + i + ")-1)"));
        res.setInitialisation(((Impl)i).getInitialisation() + this.getInitialisation() + res.getInitialisation());
        return res;
    }

    public OclInteger indexOf(OclAny o) {
        OclIntegerImpl res = (OclIntegerImpl) adapter.Integer(this +".indexOf(" + o.asJavaObject() + ") + 1");
        res.setInitialisation(((Impl)o).getInitialisation()+this.getInitialisation() + res.getInitialisation());
        return res;
    }

    public OclAny first() {
        Classifier elemType = this.getElementType();
        String s = "((" + this +".isEmpty()) ? null : " + this +".get(0))";
        OclAnyImpl result = (OclAnyImpl) ((StdLibGenerationAdapterImpl) adapter).OclAny(elemType, OclCodeGeneratorVisitorImpl.unBox(this.getElementType(), s));
        result._init += this.getInitialisation();
        return result;
    }

    public OclAny last() {
        Classifier elemType = this.getElementType();
        String s = "((" + this +".isEmpty()) ? null : " + this +".get(" + this +".size()-1))";
        OclAnyImpl result = (OclAnyImpl) ((StdLibGenerationAdapterImpl) adapter).OclAny(elemType, OclCodeGeneratorVisitorImpl.unBox(this.getElementType(), s));
        result._init += this.getInitialisation();
        return result;
    }

    public OclSet flatten() {
		OclOrderedSetImpl result = (OclOrderedSetImpl) adapter.OrderedSet(this.getElementType());
		OclOrderedSetImpl col = (OclOrderedSetImpl) adapter.OrderedSet(this.getElementType());

		OclAnyType oat = adapter.getProcessor().getTypeFactory().buildOclAnyType();
		if (this.getElementType() instanceof CollectionType || this.getElementType().equals(oat)) {
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
			result._init += "    if (!" + col + ".contains("+obj+"))" + col + ".add("+obj+");\n";
			result._init += "}\n";
			result._init += result +"="+col+";";
			result._init += "}\n";
			return result;
        } else {
            return this.asOrderedSet();
        }
    }

    //--- Object ---
    public String toString() {
        return _currentVariableName;
    }

    public boolean equals(Object o) {
	    return this==o;
    }

    public Object clone() {
        return ((StdLibGenerationAdapterImpl) adapter).OrderedSet(this.getElementType(), orderedset_impl());
    }

    public Object asJavaObject() {
        return this.toString();
    }
}
