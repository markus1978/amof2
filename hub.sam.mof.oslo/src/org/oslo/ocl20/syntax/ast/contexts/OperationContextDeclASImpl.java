
package org.oslo.ocl20.syntax.ast.contexts;

import uk.ac.kent.cs.kmf.patterns.Visitor;

public class OperationContextDeclASImpl

  extends
    
      ContextDeclarationASImpl
    
  
  implements OperationContextDeclAS,
             contextsVisitable
{
  //--- Constructors ---
  public OperationContextDeclASImpl() {

    _id=_nextId++;

  }







    
  //uni1to1
  protected OperationAS operation = null;
  public OperationAS getOperation() {
   return operation;
  }
  public void setOperation(OperationAS operation) {
    this.operation = operation;
  }





  //--- Visitable ---
  public java.lang.Object accept(Visitor visitor, java.lang.Object data) {
    if (visitor instanceof contextsVisitor)
      return ((contextsVisitor)visitor).visit(this,data);
    else
      return visitor.visit(this,data);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Factory factory) {
  	factory.destroy("OperationContextDeclAS",this);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Repository rep) {
  	rep.removeElement("uk.ac.kent.cs.ocl20.syntax.ast.contexts.OperationContextDeclAS",this);
  }


  //--- java.lang.Object ---

  static int _nextId=0;
   int _id=0;


  public java.lang.String toString() {
      return "OperationContextDeclAS {"+

  _id+
"}";
  }

  public boolean equals(java.lang.Object o) {
    if (o instanceof OperationContextDeclAS) {
      try {
        OperationContextDeclAS obj = (OperationContextDeclAS)o;
        return (this == o);
      } catch (NullPointerException e) {
        return this == o;
      }
    }
    return false;
  }

  public int hashCode() {
      return 0;
  }

  public java.lang.Object clone() {
    OperationContextDeclAS obj = new OperationContextDeclASImpl();

    return obj;
  }
}



