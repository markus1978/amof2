
 
 
package org.oslo.ocl20.syntax.ast.expressions;

public interface LogicalExpAS
  extends expressionsVisitable,
           uk.ac.kent.cs.kmf.patterns.Destroyable
,
      OclExpressionAS

{


  
  OclExpressionAS getLeftOperand();
  void setLeftOperand(OclExpressionAS leftOperand);
  
  

  
  OclExpressionAS getRightOperand();
  void setRightOperand(OclExpressionAS rightOperand);
  
  



  //--- java.lang.Object ---

   public java.lang.String toString();

  public boolean equals(java.lang.Object o);
  public int hashCode();
  public java.lang.Object clone();
}
