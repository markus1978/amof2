
 
 
package org.oslo.ocl20.syntax.ast.expressions;

public interface SelectionExpAS
  extends expressionsVisitable,
           uk.ac.kent.cs.kmf.patterns.Destroyable
,
      OclExpressionAS

{

  java.lang.String getName();
  void setName(java.lang.String name);


  
  OclExpressionAS getSource();
  void setSource(OclExpressionAS source);
  
  



  //--- java.lang.Object ---

   public java.lang.String toString();

  public boolean equals(java.lang.Object o);
  public int hashCode();
  public java.lang.Object clone();
}
