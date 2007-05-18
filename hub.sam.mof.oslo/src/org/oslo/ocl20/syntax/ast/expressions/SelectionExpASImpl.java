
package org.oslo.ocl20.syntax.ast.expressions;

import uk.ac.kent.cs.kmf.patterns.Visitor;

public class SelectionExpASImpl

  extends
    
      OclExpressionASImpl
    
  
  implements SelectionExpAS,
             expressionsVisitable
{
  //--- Constructors ---
  public SelectionExpASImpl() {

    _id=_nextId++;

  }




  java.lang.String _name = null;
  public java.lang.String getName()
  { return _name; }
  public void setName(java.lang.String name)
  { _name = name; }




    
  //uni1to1
  protected OclExpressionAS source = null;
  public OclExpressionAS getSource() {
   return source;
  }
  public void setSource(OclExpressionAS source) {
    this.source = source;
  }





  //--- Visitable ---
  public java.lang.Object accept(Visitor visitor, java.lang.Object data) {
    if (visitor instanceof expressionsVisitor)
      return ((expressionsVisitor)visitor).visit(this,data);
    else
      return visitor.visit(this,data);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Factory factory) {
  	factory.destroy("SelectionExpAS",this);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Repository rep) {
  	rep.removeElement("uk.ac.kent.cs.ocl20.syntax.ast.expressions.SelectionExpAS",this);
  }


  //--- java.lang.Object ---

  static int _nextId=0;
   int _id=0;


  public java.lang.String toString() {
      return "SelectionExpAS {"+

  _id+
"}";
  }

  public boolean equals(java.lang.Object o) {
    if (o instanceof SelectionExpAS) {
      try {
        SelectionExpAS obj = (SelectionExpAS)o;
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
    SelectionExpAS obj = new SelectionExpASImpl();

  obj.setName(_name);

    return obj;
  }
}



