
 
 
package org.oslo.ocl20.syntax.ast.contexts;

public interface PropertyContextDeclAS
  extends contextsVisitable,
           uk.ac.kent.cs.kmf.patterns.Destroyable
,
      ContextDeclarationAS

{

  java.lang.String getName();
  void setName(java.lang.String name);

  java.util.List getPathName();
  void setPathName(java.util.List pathName);


  
  org.oslo.ocl20.syntax.ast.types.TypeAS getType();
  void setType(org.oslo.ocl20.syntax.ast.types.TypeAS type);
  
  



  //--- java.lang.Object ---

   public java.lang.String toString();

  public boolean equals(java.lang.Object o);
  public int hashCode();
  public java.lang.Object clone();
}
