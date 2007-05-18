
 
 
package org.oslo.ocl20.syntax.ast.contexts;

public interface ConstraintAS
  extends contextsVisitable,
           uk.ac.kent.cs.kmf.patterns.Destroyable
,
      org.oslo.ocl20.syntax.ast.SyntaxElement

{

  ConstraintKindAS getKind();
  void setKind(ConstraintKindAS kind);

  java.lang.String getName();
  void setName(java.lang.String name);


  
  OperationAS getDefOperation();
  void setDefOperation(OperationAS defOperation);
  
  

  
  VariableDeclarationAS getDefVariable();
  void setDefVariable(VariableDeclarationAS defVariable);
  
  

  
  org.oslo.ocl20.syntax.ast.expressions.OclExpressionAS getBodyExpression();
  void setBodyExpression(org.oslo.ocl20.syntax.ast.expressions.OclExpressionAS bodyExpression);
  
  

  
  ContextDeclarationAS getContextDeclarationAS();
  void setContextDeclarationAS(ContextDeclarationAS contextDeclarationAS);
  
  



  //--- java.lang.Object ---

   public java.lang.String toString();

  public boolean equals(java.lang.Object o);
  public int hashCode();
  public java.lang.Object clone();
}
