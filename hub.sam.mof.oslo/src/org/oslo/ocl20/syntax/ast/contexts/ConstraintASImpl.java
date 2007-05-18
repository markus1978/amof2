
package org.oslo.ocl20.syntax.ast.contexts;

import uk.ac.kent.cs.kmf.patterns.Visitor;

public class ConstraintASImpl

  extends
    
      org.oslo.ocl20.syntax.ast.SyntaxElementImpl
    
  
  implements ConstraintAS,
             contextsVisitable
{
  //--- Constructors ---
  public ConstraintASImpl() {

  }




  ConstraintKindAS _kind = null;
  public ConstraintKindAS getKind()
  { return _kind; }
  public void setKind(ConstraintKindAS kind)
  { _kind = kind; }

  java.lang.String _name = null;
  public java.lang.String getName()
  { return _name; }
  public void setName(java.lang.String name)
  { _name = name; }




    
  //uni1to1
  protected OperationAS defOperation = null;
  public OperationAS getDefOperation() {
   return defOperation;
  }
  public void setDefOperation(OperationAS defOperation) {
    this.defOperation = defOperation;
  }


    
  //uni1to1
  protected VariableDeclarationAS defVariable = null;
  public VariableDeclarationAS getDefVariable() {
   return defVariable;
  }
  public void setDefVariable(VariableDeclarationAS defVariable) {
    this.defVariable = defVariable;
  }


    
  //uni1to1
  protected org.oslo.ocl20.syntax.ast.expressions.OclExpressionAS bodyExpression = null;
  public org.oslo.ocl20.syntax.ast.expressions.OclExpressionAS getBodyExpression() {
   return bodyExpression;
  }
  public void setBodyExpression(org.oslo.ocl20.syntax.ast.expressions.OclExpressionAS bodyExpression) {
    this.bodyExpression = bodyExpression;
  }


    
 
 

  //bi1toN
  protected ContextDeclarationAS contextDeclarationAS = null;
  public ContextDeclarationAS getContextDeclarationAS() {
   return this.contextDeclarationAS;
  }
  public void setContextDeclarationAS(ContextDeclarationAS contextDeclarationAS) {
	//If aggregate part then this pool contained by one parent diagram only
	if (this.contextDeclarationAS != null)
		this.contextDeclarationAS.removeConstraints(this);
    this.contextDeclarationAS = contextDeclarationAS;
    if (contextDeclarationAS != null)
      if (!contextDeclarationAS.getConstraints().contains(this)) {
        contextDeclarationAS.addConstraints(this);
     }
  }






  //--- Visitable ---
  public java.lang.Object accept(Visitor visitor, java.lang.Object data) {
    if (visitor instanceof contextsVisitor)
      return ((contextsVisitor)visitor).visit(this,data);
    else
      return visitor.visit(this,data);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Factory factory) {
  	factory.destroy("ConstraintAS",this);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Repository rep) {
  	rep.removeElement("uk.ac.kent.cs.ocl20.syntax.ast.contexts.ConstraintAS",this);
  }


  //--- java.lang.Object ---


  public java.lang.String toString() {
      return "ConstraintAS {"+

"name = "+this.getName()+
"}";
  }

  public boolean equals(java.lang.Object o) {
    if (o instanceof ConstraintAS) {
      try {
        ConstraintAS obj = (ConstraintAS)o;
        return (
obj.getName().equals( this.getName() )
);
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
    ConstraintAS obj = new ConstraintASImpl();

  obj.setKind(_kind);

  obj.setName(_name);

    return obj;
  }
}



