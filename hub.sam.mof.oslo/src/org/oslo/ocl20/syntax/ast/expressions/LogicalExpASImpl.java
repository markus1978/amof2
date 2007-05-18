
package org.oslo.ocl20.syntax.ast.expressions;

import uk.ac.kent.cs.kmf.patterns.Visitor;

public class LogicalExpASImpl

  extends
    
      OclExpressionASImpl
    
  
  implements LogicalExpAS,
             expressionsVisitable
{
  //--- Constructors ---
  public LogicalExpASImpl() {

    _id=_nextId++;

  }







    
  //uni1to1
  protected OclExpressionAS leftOperand = null;
  public OclExpressionAS getLeftOperand() {
   return leftOperand;
  }
  public void setLeftOperand(OclExpressionAS leftOperand) {
    this.leftOperand = leftOperand;
  }


    
  //uni1to1
  protected OclExpressionAS rightOperand = null;
  public OclExpressionAS getRightOperand() {
   return rightOperand;
  }
  public void setRightOperand(OclExpressionAS rightOperand) {
    this.rightOperand = rightOperand;
  }





  //--- Visitable ---
  public java.lang.Object accept(Visitor visitor, java.lang.Object data) {
    if (visitor instanceof expressionsVisitor)
      return ((expressionsVisitor)visitor).visit(this,data);
    else
      return visitor.visit(this,data);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Factory factory) {
  	factory.destroy("LogicalExpAS",this);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Repository rep) {
  	rep.removeElement("uk.ac.kent.cs.ocl20.syntax.ast.expressions.LogicalExpAS",this);
  }


  //--- java.lang.Object ---

  static int _nextId=0;
   int _id=0;


  public java.lang.String toString() {
      return "LogicalExpAS {"+

  _id+
"}";
  }

  public boolean equals(java.lang.Object o) {
    if (o instanceof LogicalExpAS) {
      try {
        LogicalExpAS obj = (LogicalExpAS)o;
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
    LogicalExpAS obj = new LogicalExpASImpl();

    return obj;
  }
}



