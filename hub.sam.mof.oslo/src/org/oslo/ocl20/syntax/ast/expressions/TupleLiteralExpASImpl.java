
package org.oslo.ocl20.syntax.ast.expressions;

import org.oslo.ocl20.syntax.ast.contexts.VariableDeclarationAS;

import uk.ac.kent.cs.kmf.patterns.Visitor;

public class TupleLiteralExpASImpl

  extends
    
      LiteralExpASImpl
    
  
  implements TupleLiteralExpAS,
             expressionsVisitable
{
  //--- Constructors ---
  public TupleLiteralExpASImpl() {

    _id=_nextId++;

  }







    
  //uniNto1
  protected java.util.List tupleParts = new java.util.Vector();
  public java.util.List getTupleParts() {
   return tupleParts;
  }
  public void setTupleParts(java.util.List tupleParts) {
    this.tupleParts = tupleParts;
  }
  public boolean addTupleParts(VariableDeclarationAS p) {
	boolean b = this.tupleParts.add(p);
	return b;
  }
  public boolean removeTupleParts(VariableDeclarationAS p) {
	boolean b = this.tupleParts.remove(p);
	return b;
  }





  //--- Visitable ---
  public java.lang.Object accept(Visitor visitor, java.lang.Object data) {
    if (visitor instanceof expressionsVisitor)
      return ((expressionsVisitor)visitor).visit(this,data);
    else
      return visitor.visit(this,data);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Factory factory) {
  	factory.destroy("TupleLiteralExpAS",this);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Repository rep) {
  	rep.removeElement("uk.ac.kent.cs.ocl20.syntax.ast.expressions.TupleLiteralExpAS",this);
  }


  //--- java.lang.Object ---

  static int _nextId=0;
   int _id=0;


  public java.lang.String toString() {
      return "TupleLiteralExpAS {"+

  _id+
"}";
  }

  public boolean equals(java.lang.Object o) {
    if (o instanceof TupleLiteralExpAS) {
      try {
        TupleLiteralExpAS obj = (TupleLiteralExpAS)o;
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
    TupleLiteralExpAS obj = new TupleLiteralExpASImpl();

    return obj;
  }
}



