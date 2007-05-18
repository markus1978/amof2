
 
 
package org.oslo.ocl20.syntax.ast.contexts;

public interface PackageDeclarationAS
  extends contextsVisitable,
           uk.ac.kent.cs.kmf.patterns.Destroyable
,
      org.oslo.ocl20.syntax.ast.SyntaxElement

{

  java.util.List getPathName();
  void setPathName(java.util.List pathName);


  final ContextDeclarationAS contextDecls_elementType=null;
  java.util.List getContextDecls();
  void setContextDecls(java.util.List contextDecls);
  boolean addContextDecls(ContextDeclarationAS contextDecls);
  boolean removeContextDecls(ContextDeclarationAS contextDecls);



  //--- java.lang.Object ---

   public java.lang.String toString();

  public boolean equals(java.lang.Object o);
  public int hashCode();
  public java.lang.Object clone();
}
