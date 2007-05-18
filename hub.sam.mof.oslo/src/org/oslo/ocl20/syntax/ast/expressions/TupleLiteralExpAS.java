
 
 
package org.oslo.ocl20.syntax.ast.expressions;

import org.oslo.ocl20.syntax.ast.contexts.VariableDeclarationAS;

public interface TupleLiteralExpAS
  extends expressionsVisitable,
           uk.ac.kent.cs.kmf.patterns.Destroyable
,
      LiteralExpAS

{


  final org.oslo.ocl20.syntax.ast.contexts.VariableDeclarationAS tupleParts_elementType=null;
  java.util.List getTupleParts();
  void setTupleParts(java.util.List tupleParts);
  boolean addTupleParts(VariableDeclarationAS tupleParts);
  boolean removeTupleParts(VariableDeclarationAS tupleParts);



  //--- java.lang.Object ---

   public java.lang.String toString();

  public boolean equals(java.lang.Object o);
  public int hashCode();
  public java.lang.Object clone();
}
