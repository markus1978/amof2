
 
 
package org.oslo.ocl20.syntax.ast.expressions;

public interface CollectionRangeAS
  extends expressionsVisitable,
           uk.ac.kent.cs.kmf.patterns.Destroyable
,
      CollectionLiteralPartAS

{


  
  OclExpressionAS getFirst();
  void setFirst(OclExpressionAS first);
  
  

  
  OclExpressionAS getLast();
  void setLast(OclExpressionAS last);
  
  



  //--- java.lang.Object ---

   public java.lang.String toString();

  public boolean equals(java.lang.Object o);
  public int hashCode();
  public java.lang.Object clone();
}
