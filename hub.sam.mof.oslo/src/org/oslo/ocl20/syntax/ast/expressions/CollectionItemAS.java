
 
 
package org.oslo.ocl20.syntax.ast.expressions;

public interface CollectionItemAS
  extends expressionsVisitable,
           uk.ac.kent.cs.kmf.patterns.Destroyable
,
      CollectionLiteralPartAS

{


  
  OclExpressionAS getItem();
  void setItem(OclExpressionAS item);
  
  



  //--- java.lang.Object ---

   public java.lang.String toString();

  public boolean equals(java.lang.Object o);
  public int hashCode();
  public java.lang.Object clone();
}
