
 
 
package org.oslo.ocl20.syntax.ast.contexts;

public interface OperationContextDeclAS
  extends contextsVisitable,
           uk.ac.kent.cs.kmf.patterns.Destroyable
,
      ContextDeclarationAS

{


  
  OperationAS getOperation();
  void setOperation(OperationAS operation);
  
  



  //--- java.lang.Object ---

   public java.lang.String toString();

  public boolean equals(java.lang.Object o);
  public int hashCode();
  public java.lang.Object clone();
}
