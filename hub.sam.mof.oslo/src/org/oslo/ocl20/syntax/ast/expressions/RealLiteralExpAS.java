
 
 
package org.oslo.ocl20.syntax.ast.expressions;

public interface RealLiteralExpAS
  extends expressionsVisitable,
           uk.ac.kent.cs.kmf.patterns.Destroyable
,
      PrimitiveLiteralExpAS

{

  java.lang.Double getValue();
  void setValue(java.lang.Double value);




  //--- java.lang.Object ---

   public java.lang.String toString();

  public boolean equals(java.lang.Object o);
  public int hashCode();
  public java.lang.Object clone();
}
