package org.oslo.ocl20.syntax.ast.contexts;

import uk.ac.kent.cs.kmf.patterns.Visitor;

public class VariableDeclarationASImpl

implements VariableDeclarationAS, contextsVisitable {
    //--- Constructors ---
    public VariableDeclarationASImpl() {

    }

    java.lang.String _name = null;
    public java.lang.String getName() {
        return _name;
    }
    public void setName(java.lang.String name) {
        _name = name;
    }

    //uni1to1
    protected org.oslo.ocl20.syntax.ast.expressions.OclExpressionAS initExp = null;
    public org.oslo.ocl20.syntax.ast.expressions.OclExpressionAS getInitExp() {
        return initExp;
    }
    public void setInitExp(
            org.oslo.ocl20.syntax.ast.expressions.OclExpressionAS initExp) {
        this.initExp = initExp;
    }

    //uni1to1
    protected org.oslo.ocl20.syntax.ast.types.TypeAS type = null;
    public org.oslo.ocl20.syntax.ast.types.TypeAS getType() {
        return type;
    }
    public void setType(org.oslo.ocl20.syntax.ast.types.TypeAS type) {
        this.type = type;
    }

    //--- Visitable ---
    public java.lang.Object accept(Visitor visitor, java.lang.Object data) {
        if (visitor instanceof contextsVisitor)
            return ((contextsVisitor) visitor).visit(this, data);
        else
            return visitor.visit(this, data);
    }

    public void destroy(uk.ac.kent.cs.kmf.patterns.Factory factory) {
        factory.destroy("VariableDeclarationAS", this);
    }

    public void destroy(uk.ac.kent.cs.kmf.patterns.Repository rep) {
        rep
                .removeElement(
                        "uk.ac.kent.cs.ocl20.syntax.ast.contexts.VariableDeclarationAS",
                        this);
    }

    //--- java.lang.Object ---

    public java.lang.String toString() {
        return "VariableDeclarationAS {" +

        "name = " + this.getName() + "}";
    }

    public boolean equals(java.lang.Object o) {
        if (o instanceof VariableDeclarationAS) {
            try {
                VariableDeclarationAS obj = (VariableDeclarationAS) o;
                return (obj.getName().equals(this.getName()));
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
        VariableDeclarationAS obj = new VariableDeclarationASImpl();

        obj.setName(_name);

        return obj;
    }
}

