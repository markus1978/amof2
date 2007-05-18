
 
 
package org.oslo.ocl20.syntax.ast.expressions;

public interface IfExpAS
  extends expressionsVisitable,
           uk.ac.kent.cs.kmf.patterns.Destroyable
,
      OclExpressionAS

{


  
  OclExpressionAS getThenExpression();
  void setThenExpression(OclExpressionAS thenExpression);
  
  

  
  OclExpressionAS getElseExpression();
  void setElseExpression(OclExpressionAS elseExpression);
  
  

  
  OclExpressionAS getCondition();
  void setCondition(OclExpressionAS condition);
  
  



  //--- java.lang.Object ---

   public java.lang.String toString();

  public boolean equals(java.lang.Object o);
  public int hashCode();
  public java.lang.Object clone();
}
