
package org.oslo.ocl20.syntax.ast.expressions;

import uk.ac.kent.cs.kmf.patterns.Visitor;

public class OclMessageArgASImpl

  
  implements OclMessageArgAS,
             expressionsVisitable
{
  //--- Constructors ---
  public OclMessageArgASImpl() {

    _id=_nextId++;

  }







    
  //uni1to1
  protected OclExpressionAS expression = null;
  public OclExpressionAS getExpression() {
   return expression;
  }
  public void setExpression(OclExpressionAS expression) {
    this.expression = expression;
  }


    
  //uni1to1
  protected org.oslo.ocl20.syntax.ast.types.TypeAS type = null;
  public org.oslo.ocl20.syntax.ast.types.TypeAS getType() {
   return type;
  }
  public void setType(org.oslo.ocl20.syntax.ast.types.TypeAS type) {
    this.type = type;
  }





  //--- Visitable ---
  public java.lang.Object accept(Visitor visitor, java.lang.Object data) {
    if (visitor instanceof expressionsVisitor)
      return ((expressionsVisitor)visitor).visit(this,data);
    else
      return visitor.visit(this,data);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Factory factory) {
  	factory.destroy("OclMessageArgAS",this);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Repository rep) {
  	rep.removeElement("uk.ac.kent.cs.ocl20.syntax.ast.expressions.OclMessageArgAS",this);
  }


  //--- java.lang.Object ---

  static int _nextId=0;
   int _id=0;


  public java.lang.String toString() {
      return "OclMessageArgAS {"+

  _id+
"}";
  }

  public boolean equals(java.lang.Object o) {
    if (o instanceof OclMessageArgAS) {
      try {
        OclMessageArgAS obj = (OclMessageArgAS)o;
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
    OclMessageArgAS obj = new OclMessageArgASImpl();

    return obj;
  }
}



