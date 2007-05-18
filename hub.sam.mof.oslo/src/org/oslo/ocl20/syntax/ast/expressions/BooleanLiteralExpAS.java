
 
 
package org.oslo.ocl20.syntax.ast.expressions;

public interface BooleanLiteralExpAS
  extends expressionsVisitable,
           uk.ac.kent.cs.kmf.patterns.Destroyable
,
      PrimitiveLiteralExpAS

{

  java.lang.Boolean getValue();
  void setValue(java.lang.Boolean value);




  //--- java.lang.Object ---

   public java.lang.String toString();

  public boolean equals(java.lang.Object o);
  public int hashCode();
  public java.lang.Object clone();
}
