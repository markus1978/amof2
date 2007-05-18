
package org.oslo.ocl20.syntax.ast.contexts;

public interface VariableDeclarationAS
        extends
            contextsVisitable,
            uk.ac.kent.cs.kmf.patterns.Destroyable

{

    java.lang.String getName();
    void setName(java.lang.String name);

    org.oslo.ocl20.syntax.ast.expressions.OclExpressionAS getInitExp();
    void setInitExp(
            org.oslo.ocl20.syntax.ast.expressions.OclExpressionAS initExp);

    org.oslo.ocl20.syntax.ast.types.TypeAS getType();
    void setType(org.oslo.ocl20.syntax.ast.types.TypeAS type);

    //--- java.lang.Object ---

    public java.lang.String toString();

    public boolean equals(java.lang.Object o);
    public int hashCode();
    public java.lang.Object clone();
}