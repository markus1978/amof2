
package org.oslo.ocl20.syntax.ast.expressions;

import org.oslo.ocl20.syntax.ast.contexts.VariableDeclarationAS;

public interface LetExpAS
        extends
            expressionsVisitable,
            uk.ac.kent.cs.kmf.patterns.Destroyable,
            OclExpressionAS

{

    final org.oslo.ocl20.syntax.ast.contexts.VariableDeclarationAS variables_elementType = null;
    java.util.List getVariables();
    void setVariables(java.util.List variables);
    boolean addVariables(VariableDeclarationAS variables);
    boolean removeVariables(VariableDeclarationAS variables);

    OclExpressionAS getIn();
    void setIn(OclExpressionAS in);

    //--- java.lang.Object ---

    public java.lang.String toString();

    public boolean equals(java.lang.Object o);
    public int hashCode();
    public java.lang.Object clone();
}