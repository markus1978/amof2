package org.oslo.ocl20.syntax.ast.types;

import org.oslo.ocl20.syntax.ast.contexts.VariableDeclarationAS;

import uk.ac.kent.cs.kmf.patterns.Visitor;

public class TupleTypeASImpl

extends

 TypeASImpl

implements TupleTypeAS, typesVisitable {
    //--- Constructors ---
    public TupleTypeASImpl() {

        _id = _nextId++;

    }

    //uniNto1
    protected java.util.List variableDeclarationList = new java.util.Vector();
    public java.util.List getVariableDeclarationList() {
        return variableDeclarationList;
    }
    public void setVariableDeclarationList(
            java.util.List variableDeclarationList) {
        this.variableDeclarationList = variableDeclarationList;
    }
    public boolean addVariableDeclarationList(VariableDeclarationAS p) {
        boolean b = this.variableDeclarationList.add(p);
        return b;
    }
    public boolean removeVariableDeclarationList(VariableDeclarationAS p) {
        boolean b = this.variableDeclarationList.remove(p);
        return b;
    }

    //--- Visitable ---
    public java.lang.Object accept(Visitor visitor, java.lang.Object data) {
        if (visitor instanceof typesVisitor)
            return ((typesVisitor) visitor).visit(this, data);
        else
            return visitor.visit(this, data);
    }

    public void destroy(uk.ac.kent.cs.kmf.patterns.Factory factory) {
        factory.destroy("TupleTypeAS", this);
    }

    public void destroy(uk.ac.kent.cs.kmf.patterns.Repository rep) {
        rep.removeElement("uk.ac.kent.cs.ocl20.syntax.ast.types.TupleTypeAS",
                this);
    }

    //--- java.lang.Object ---

    static int _nextId = 0;
    int _id = 0;

    public java.lang.String toString() {
        return "TupleTypeAS {" +

        _id + "}";
    }

    public boolean equals(java.lang.Object o) {
        if (o instanceof TupleTypeAS) {
            try {
                TupleTypeAS obj = (TupleTypeAS) o;
                return (this == o);
            } catch (NullPointerException e) {
                return this == o;
            }
        }
        return false;
    }

    public int hashCode() {
        return 0;
    }

    public java.lang.Object clone() {
        TupleTypeAS obj = new TupleTypeASImpl();

        return obj;
    }
}

