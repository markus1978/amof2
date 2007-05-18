
 
 
package org.oslo.ocl20.syntax.ast.types;

import org.oslo.ocl20.syntax.ast.contexts.VariableDeclarationAS;

public interface TupleTypeAS
  extends typesVisitable,
           uk.ac.kent.cs.kmf.patterns.Destroyable
,
      TypeAS

{


  final org.oslo.ocl20.syntax.ast.contexts.VariableDeclarationAS variableDeclarationList_elementType=null;
  java.util.List getVariableDeclarationList();
  void setVariableDeclarationList(java.util.List variableDeclarationList);
  boolean addVariableDeclarationList(VariableDeclarationAS variableDeclarationList);
  boolean removeVariableDeclarationList(VariableDeclarationAS variableDeclarationList);



  //--- java.lang.Object ---

   public java.lang.String toString();

  public boolean equals(java.lang.Object o);
  public int hashCode();
  public java.lang.Object clone();
}
