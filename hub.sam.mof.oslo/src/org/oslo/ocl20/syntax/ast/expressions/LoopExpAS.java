package org.oslo.ocl20.syntax.ast.expressions;

import java.util.List;

public interface LoopExpAS
        extends
            expressionsVisitable,
            uk.ac.kent.cs.kmf.patterns.Destroyable,
            OclExpressionAS

{

    java.lang.String getName();
    void setName(java.lang.String name);

    OclExpressionAS getBody();
    void setBody(OclExpressionAS body);

    //ExpressionLiteralAS getExpressionLiteral();
    //void setExpressionLiteral(ExpressionLiteralAS exp);
    
    List getIterator();
    void setIterator(List iterator);

    org.oslo.ocl20.syntax.ast.contexts.VariableDeclarationAS getResult();
    void setResult(
            org.oslo.ocl20.syntax.ast.contexts.VariableDeclarationAS result);

    OclExpressionAS getSource();
    void setSource(OclExpressionAS source);

    //--- java.lang.Object ---

    public java.lang.String toString();

    public boolean equals(java.lang.Object o);
    public int hashCode();
    public java.lang.Object clone();

}