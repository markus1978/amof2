
 
 
package org.oslo.ocl20.syntax.ast.expressions;

public interface EnumLiteralExpAS
  extends expressionsVisitable,
           uk.ac.kent.cs.kmf.patterns.Destroyable
,
      LiteralExpAS

{

  java.util.List getPathName();
  void setPathName(java.util.List pathName);




  //--- java.lang.Object ---

   public java.lang.String toString();

  public boolean equals(java.lang.Object o);
  public int hashCode();
  public java.lang.Object clone();
}
