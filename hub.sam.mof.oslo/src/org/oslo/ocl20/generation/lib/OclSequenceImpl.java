package org.oslo.ocl20.generation.lib;

import org.oslo.ocl20.semantics.bridge.Classifier;
import org.oslo.ocl20.semantics.model.types.CollectionType;
import org.oslo.ocl20.semantics.model.types.OclAnyType;
import org.oslo.ocl20.standard.lib.OclAny;
import org.oslo.ocl20.standard.lib.OclBoolean;
import org.oslo.ocl20.standard.lib.OclInteger;
import org.oslo.ocl20.standard.lib.OclSequence;
import org.oslo.ocl20.standard.lib.OclType;
import org.oslo.ocl20.standard.lib.OclVisitor;
import org.oslo.ocl20.synthesis.OclCodeGeneratorVisitorImpl;


public class OclSequenceImpl extends OclCollectionImpl implements OclSequence {

    protected OclSequenceImpl(Classifier eT, OclAny[] array, StdLibGenerationAdapterImpl adapter) {
        super(eT, adapter);
        _currentVariableName = StdLibGenerationAdapterImpl.newTempVar();
        _init = "java.util.List " + _currentVariableName + " = new java.util.Vector();\n";
        super.addParts(array, eT);
    }

    protected OclSequenceImpl(Classifier eT, String seq, StdLibGenerationAdapterImpl adapter, boolean newVariable) {
        super(eT, adapter);
        if (newVariable) {
            _currentVariableName = StdLibGenerationAdapterImpl.newTempVar("seq");
            _init = "java.util.List " + _currentVariableName + "=new java.util.Vector(" + seq + ");\n";
        } else {
            _currentVariableName = seq;
        }
    }

    protected String implementation() {
        return _currentVariableName;
    }
    protected String list_impl() {
        return _currentVariableName;
    }

    //--- IOclSequence ---
    public OclType oclType() {
        Classifier type = adapter.getProcessor().getTypeFactory().buildSequenceType(super.getElementType());
        OclTypeImpl ot = (OclTypeImpl) adapter.Type(type);
        ot.setInitialisation(this.getInitialisation() + ot.getInitialisation());
        return ot;
    }
    public OclInteger count(OclAny object) {
        return super.count(object);
    }

    public OclBoolean equalTo(OclSequence seq2) {
        OclBooleanImpl result = (OclBooleanImpl) adapter.Boolean(false);
        if (seq2 instanceof OclSequenceImpl) {
            OclSequenceImpl s2 = (OclSequenceImpl) seq2;
            result = (OclBooleanImpl) adapter.Boolean("new java.lang.Boolean(" + this +".equals(" + seq2 + "))", true);
            result._init = this._init + s2._init + result._init;
        }
        return result;
    }

    public OclBoolean notEqualTo(OclSequence sequence2) {
        return equalTo(sequence2).not();
    }

    public OclSequence union(OclSequence s2) {
        if (s2 instanceof OclSequenceImpl) {
            OclSequenceImpl seq2 = (OclSequenceImpl) s2;
            OclSequenceImpl result = (OclSequenceImpl) adapter.Sequence(this.getElementType());
            result._init = this._init + seq2._init + result._init;
            result._init += result + ".addAll(" + this +");\n";
            result._init += result + ".addAll(" + seq2 + ");\n";
            return result;
        }
        return null;
    }

    public OclSequence append(OclAny obj) {
        OclSequenceImpl result = (OclSequenceImpl) adapter.Sequence(this.getElementType(), this.toString(),true);
        result._init = ((OclAnyImpl) obj).getInitialisation() + this._init + result._init;
        String t = StdLibGenerationAdapterImpl.newTempVar("obj");
        String str = "Object " + t + "=" + obj.asJavaObject() + "\n;";
        str += "if (" + t + "!=null) " + result + ".add(" + t + ");\n";
        result._init += str;
        return result;
    }

    public OclSequence prepend(OclAny obj) {
        OclSequenceImpl result = (OclSequenceImpl) adapter.Sequence(this.getElementType());
        result._init = ((OclAnyImpl) obj).getInitialisation() + this._init + result._init;
        String t = StdLibGenerationAdapterImpl.newTempVar("obj");
        String str = "Object " + t + "=" + obj.asJavaObject() + "\n;";
        str += "if (" + t + "!=null) " + result + ".add(0," + t + ");\n";
        result._init += str;
        return result;
    }

    public OclSequence subSequence(OclInteger lower, OclInteger upper) {
        OclSequenceImpl result = (OclSequenceImpl) adapter.Sequence(this.getElementType());
        result._init = ((OclAnyImpl) lower).getInitialisation() + ((OclAnyImpl) upper).getInitialisation() + this._init + result._init;
        result._init += result + " = "+this+".subList(" + lower + "-1," + upper + ");\n";
        return result;
    }

    public Object at(OclInteger i) {
        OclAnyImpl res = (OclAnyImpl) ((StdLibGenerationAdapterImpl) adapter).OclAny(this.getElementType(), OclCodeGeneratorVisitorImpl.unBox(this.getElementType(), this +".get((" + i + ")-1)"));
        res.setInitialisation(((OclAnyImpl) i).getInitialisation() + this.getInitialisation() + res.getInitialisation());
        return res;
    }

    public OclInteger indexOf(OclAny o) {
        OclIntegerImpl res = (OclIntegerImpl) adapter.Integer(this +".indexOf(" + o.asJavaObject() + ") + 1");
        res.setInitialisation(((OclAnyImpl) o).getInitialisation() + this.getInitialisation() + res.getInitialisation());
        return res;
    }

    public OclAny first() {
        Classifier elemType = this.getElementType();
        String s = "((" + this +".isEmpty()) ? null : " + this +".get(0))";
        OclAnyImpl result = (OclAnyImpl) ((StdLibGenerationAdapterImpl) adapter).OclAny(elemType, OclCodeGeneratorVisitorImpl.unBox(this.getElementType(), s));
        result._init += this.getInitialisation();
        return result;
    }

    public OclSequence tail() {
        OclSequenceImpl result = (OclSequenceImpl) ((StdLibGenerationAdapterImpl) adapter).Sequence(this.getElementType(), this +".subList(1," + this +".size())");
        result._init += this.getInitialisation();
        return result;
    }

    public OclSequence insertAt(OclInteger i, OclAny obj) {
        OclSequenceImpl result = (OclSequenceImpl) adapter.Sequence(this.getElementType());
        result._init = ((OclAnyImpl) obj).getInitialisation() + ((OclIntegerImpl) i).getInitialisation() + this._init + result._init;
        String t = StdLibGenerationAdapterImpl.newTempVar("obj");
        String str = "Object " + t + "=" + obj.asJavaObject() + "\n;";
        str += "if (" + t + "!=null) " + result + ".add(" + i + ", " + t + ");\n";
        result._init += str;
        return result;
    }

    public OclAny last() {
        Classifier elemType = this.getElementType();
        String s = "((" + this +".isEmpty()) ? null : " + this +".get(" + this +".size()-1))";
        OclAnyImpl result = (OclAnyImpl) ((StdLibGenerationAdapterImpl) adapter).OclAny(elemType, OclCodeGeneratorVisitorImpl.unBox(this.getElementType(), s));
        result._init += this.getInitialisation();
        return result;
    }

    public OclSequence including(OclAny object) {
        return this.append(object);
    }

    public OclSequence excluding(OclAny object) {
        OclSequenceImpl result = (OclSequenceImpl) adapter.Sequence(this.getElementType());
        result._init = ((OclAnyImpl) object).getInitialisation() + this._init + result._init;
        result._init += result + ".remove(" + object.asJavaObject() + ");\n";
        return result;
    }

    public OclSequence flatten() {
		OclSequenceImpl result = (OclSequenceImpl) adapter.Sequence(this.getElementType());
		OclSequenceImpl col = (OclSequenceImpl) adapter.Sequence(this.getElementType());
		OclAnyType oat = adapter.getProcessor().getTypeFactory().buildOclAnyType();
		if (this.getElementType() instanceof CollectionType || this.getElementType().equals(oat)) {
			return (OclSequence)super.flatten(result,col);
        } else {
            return this.asSequence();
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
        return ((StdLibGenerationAdapterImpl) adapter).Sequence(this.getElementType(), list_impl());
    }

    public boolean equals(Object o) {
	    return this==o;
    }

    public Object asJavaObject() {
        return this.toString();
    }
}
