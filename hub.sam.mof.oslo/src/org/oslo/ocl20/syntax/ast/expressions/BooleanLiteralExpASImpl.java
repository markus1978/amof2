
package org.oslo.ocl20.syntax.ast.expressions;

import uk.ac.kent.cs.kmf.patterns.Visitor;

public class BooleanLiteralExpASImpl

  extends
    
      PrimitiveLiteralExpASImpl
    
  
  implements BooleanLiteralExpAS,
             expressionsVisitable
{
  //--- Constructors ---
  public BooleanLiteralExpASImpl() {

    _id=_nextId++;

  }




  java.lang.Boolean _value = null;
  public java.lang.Boolean getValue()
  { return _value; }
  public void setValue(java.lang.Boolean value)
  { _value = value; }







  //--- Visitable ---
  public java.lang.Object accept(Visitor visitor, java.lang.Object data) {
    if (visitor instanceof expressionsVisitor)
      return ((expressionsVisitor)visitor).visit(this,data);
    else
      return visitor.visit(this,data);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Factory factory) {
  	factory.destroy("BooleanLiteralExpAS",this);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Repository rep) {
  	rep.removeElement("uk.ac.kent.cs.ocl20.syntax.ast.expressions.BooleanLiteralExpAS",this);
  }


  //--- java.lang.Object ---

  static int _nextId=0;
   int _id=0;


  public java.lang.String toString() {
      return "BooleanLiteralExpAS {"+

  _id+
"}";
  }

  public boolean equals(java.lang.Object o) {
    if (o instanceof BooleanLiteralExpAS) {
      try {
        BooleanLiteralExpAS obj = (BooleanLiteralExpAS)o;
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
    BooleanLiteralExpAS obj = new BooleanLiteralExpASImpl();

  obj.setValue(_value);

    return obj;
  }
}



