package org.oslo.ocl20.syntax.ast;

import uk.ac.kent.cs.kmf.patterns.Visitor;

public class SyntaxElementImpl

  implements SyntaxElement,
             astVisitable
{
  //--- Constructors ---
  public SyntaxElementImpl() {
    _id=_nextId++;
  }

  //--- Delegate for AssociationEnd ---
  uk.ac.kent.cs.kmf.patterns.association.AssociationEnd _assocEnd;
  {_assocEnd = new uk.ac.kent.cs.kmf.patterns.association.AssociationEndImpl(this);}
  public java.lang.Object getOtherEnd(java.lang.String n) { return _assocEnd.getOtherEnd(n); }
  public void setOtherEnd(java.lang.String pn, java.lang.Object o, java.lang.String on) { _assocEnd.setOtherEnd(pn,o,on); }
  public void initOtherEnd(java.lang.String pn, java.lang.Object o, java.lang.String on) { _assocEnd.initOtherEnd(pn,o,on); }

  java.lang.Object _symbol=null;
  public java.lang.Object getSymbol() { return _symbol; }
  public void setSymbol(java.lang.Object symbol) { _symbol = symbol; }

  // Non Navigable AssociationEnds

  //--- Visitable ---
  public java.lang.Object accept(Visitor visitor, java.lang.Object data) {
    if (visitor instanceof astVisitor)
      return ((astVisitor)visitor).visit(this,data);
    else
      return visitor.visit(this,data);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Factory factory) {
  	factory.destroy("SyntaxElement",this);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Repository rep) {
  	rep.removeElement("uk.ac.kent.cs.ocl20.syntax.ast.SyntaxElement",this);
  }


  //--- java.lang.Object ---
  static int _nextId=0;
   int _id=0;

  public java.lang.String toString() {
      return "SyntaxElement {"+_id+"}";
  }	
	
  public boolean equals(java.lang.Object o) {
    if (o instanceof SyntaxElement) {
      try {
        SyntaxElement obj = (SyntaxElement)o;
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
    SyntaxElement obj = new SyntaxElementImpl();
     obj.setSymbol(_symbol);
    return obj;
  }
}
