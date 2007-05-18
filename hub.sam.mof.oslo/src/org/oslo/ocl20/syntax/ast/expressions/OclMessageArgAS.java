
 
 
package org.oslo.ocl20.syntax.ast.expressions;

public interface OclMessageArgAS
  extends expressionsVisitable,
           uk.ac.kent.cs.kmf.patterns.Destroyable

{


  
  OclExpressionAS getExpression();
  void setExpression(OclExpressionAS expression);
  
  

  
  org.oslo.ocl20.syntax.ast.types.TypeAS getType();
  void setType(org.oslo.ocl20.syntax.ast.types.TypeAS type);
  
  



  //--- java.lang.Object ---

   public java.lang.String toString();

  public boolean equals(java.lang.Object o);
  public int hashCode();
  public java.lang.Object clone();
}
