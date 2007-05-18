
 
 
package org.oslo.ocl20.syntax.ast.contexts;

public interface OperationAS
  extends contextsVisitable,
           uk.ac.kent.cs.kmf.patterns.Destroyable

{

  java.lang.String getName();
  void setName(java.lang.String name);

  java.util.List getPathName();
  void setPathName(java.util.List pathName);


  final VariableDeclarationAS parameters_elementType=null;
  java.util.List getParameters();
  void setParameters(java.util.List parameters);
  boolean addParameters(VariableDeclarationAS parameters);
  boolean removeParameters(VariableDeclarationAS parameters);

  
  org.oslo.ocl20.syntax.ast.types.TypeAS getType();
  void setType(org.oslo.ocl20.syntax.ast.types.TypeAS type);
  
  



  //--- java.lang.Object ---

   public java.lang.String toString();

  public boolean equals(java.lang.Object o);
  public int hashCode();
  public java.lang.Object clone();
}
