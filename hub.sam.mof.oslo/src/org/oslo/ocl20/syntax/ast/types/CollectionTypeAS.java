
 
 
package org.oslo.ocl20.syntax.ast.types;

public interface CollectionTypeAS
  extends typesVisitable,
           uk.ac.kent.cs.kmf.patterns.Destroyable
,
      TypeAS

{


  
  TypeAS getElementType();
  void setElementType(TypeAS elementType);
  
  



  //--- java.lang.Object ---

   public java.lang.String toString();

  public boolean equals(java.lang.Object o);
  public int hashCode();
  public java.lang.Object clone();
}
