
package org.oslo.ocl20.syntax.ast.expressions;

import uk.ac.kent.cs.kmf.patterns.Visitor;

public class EnumLiteralExpASImpl

  extends
    
      LiteralExpASImpl
    
  
  implements EnumLiteralExpAS,
             expressionsVisitable
{
  //--- Constructors ---
  public EnumLiteralExpASImpl() {

    _id=_nextId++;

  }




  java.util.List _pathName = null;
  public java.util.List getPathName()
  { return _pathName; }
  public void setPathName(java.util.List pathName)
  { _pathName = pathName; }







  //--- Visitable ---
  public java.lang.Object accept(Visitor visitor, java.lang.Object data) {
    if (visitor instanceof expressionsVisitor)
      return ((expressionsVisitor)visitor).visit(this,data);
    else
      return visitor.visit(this,data);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Factory factory) {
  	factory.destroy("EnumLiteralExpAS",this);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Repository rep) {
  	rep.removeElement("uk.ac.kent.cs.ocl20.syntax.ast.expressions.EnumLiteralExpAS",this);
  }


  //--- java.lang.Object ---

  static int _nextId=0;
   int _id=0;


  public java.lang.String toString() {
      return "EnumLiteralExpAS {"+

  _id+
"}";
  }

  public boolean equals(java.lang.Object o) {
    if (o instanceof EnumLiteralExpAS) {
      try {
        EnumLiteralExpAS obj = (EnumLiteralExpAS)o;
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
    EnumLiteralExpAS obj = new EnumLiteralExpASImpl();

  obj.setPathName(_pathName);

    return obj;
  }
}



