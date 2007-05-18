
 
 
package org.oslo.ocl20.syntax.ast.expressions;

public interface OclExpressionAS
  extends expressionsVisitable,
           uk.ac.kent.cs.kmf.patterns.Destroyable
,
      org.oslo.ocl20.syntax.ast.SyntaxElement

{

  java.lang.Boolean getIsMarkedPre();
  void setIsMarkedPre(java.lang.Boolean isMarkedPre);


  
  IfExpAS getIfExpAS();
  void setIfExpAS(IfExpAS ifExpAS);
  
  

  
  OclExpressionAS getParent();
  void setParent(OclExpressionAS parent);
  
  



  //--- java.lang.Object ---

   public java.lang.String toString();

  public boolean equals(java.lang.Object o);
  public int hashCode();
  public java.lang.Object clone();
}
