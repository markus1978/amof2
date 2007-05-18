
 
 
package org.oslo.ocl20.syntax.ast.expressions;

public interface StringLiteralExpAS
  extends expressionsVisitable,
           uk.ac.kent.cs.kmf.patterns.Destroyable
,
      PrimitiveLiteralExpAS

{

  java.lang.String getValue();
  void setValue(java.lang.String value);




  //--- java.lang.Object ---

   public java.lang.String toString();

  public boolean equals(java.lang.Object o);
  public int hashCode();
  public java.lang.Object clone();
}
