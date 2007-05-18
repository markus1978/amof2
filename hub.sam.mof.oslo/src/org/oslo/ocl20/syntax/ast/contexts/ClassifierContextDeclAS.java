
 
 
package org.oslo.ocl20.syntax.ast.contexts;

import org.oslo.ocl20.syntax.ast.types.TypeAS;

public interface ClassifierContextDeclAS
  extends contextsVisitable,
           uk.ac.kent.cs.kmf.patterns.Destroyable
,
      ContextDeclarationAS

{

  java.util.List getPathName();
  void setPathName(java.util.List pathName);

  void setType(TypeAS t);
  TypeAS getType();


  //--- java.lang.Object ---

   public java.lang.String toString();

  public boolean equals(java.lang.Object o);
  public int hashCode();
  public java.lang.Object clone();

}
