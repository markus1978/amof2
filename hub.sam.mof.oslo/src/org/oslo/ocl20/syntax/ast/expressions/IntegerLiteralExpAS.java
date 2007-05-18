
 
 
package org.oslo.ocl20.syntax.ast.expressions;

public interface IntegerLiteralExpAS
  extends expressionsVisitable,
           uk.ac.kent.cs.kmf.patterns.Destroyable
,
      PrimitiveLiteralExpAS

{

  java.lang.Integer getValue();
  void setValue(java.lang.Integer value);




  //--- java.lang.Object ---

   public java.lang.String toString();

  public boolean equals(java.lang.Object o);
  public int hashCode();
  public java.lang.Object clone();
}
