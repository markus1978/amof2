
 
 
package org.oslo.ocl20.syntax.ast.contexts;

public interface ContextDeclarationAS
  extends contextsVisitable,
           uk.ac.kent.cs.kmf.patterns.Destroyable
,
      org.oslo.ocl20.syntax.ast.SyntaxElement

{


  
  PackageDeclarationAS getPackageDeclarationAS();
  void setPackageDeclarationAS(PackageDeclarationAS packageDeclarationAS);
  
  

  final ConstraintAS constraints_elementType=null;
  java.util.List getConstraints();
  void setConstraints(java.util.List constraints);
  boolean addConstraints(ConstraintAS constraints);
  boolean removeConstraints(ConstraintAS constraints);



  //--- java.lang.Object ---

   public java.lang.String toString();

  public boolean equals(java.lang.Object o);
  public int hashCode();
  public java.lang.Object clone();
}
