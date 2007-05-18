
package org.oslo.ocl20.syntax.ast.expressions;

import uk.ac.kent.cs.kmf.patterns.Visitor;

public class OclExpressionASImpl

  extends
    
      org.oslo.ocl20.syntax.ast.SyntaxElementImpl
    
  
  implements OclExpressionAS,
             expressionsVisitable
{
  //--- Constructors ---
  public OclExpressionASImpl() {

    _id=_nextId++;

  }




  java.lang.Boolean _isMarkedPre = null;
  public java.lang.Boolean getIsMarkedPre()
  { return _isMarkedPre; }
  public void setIsMarkedPre(java.lang.Boolean isMarkedPre)
  { _isMarkedPre = isMarkedPre; }




    
  //bi1to1
  protected IfExpAS ifExpAS = null;
  public IfExpAS getIfExpAS() {
   return this.ifExpAS;
  }
  public void setIfExpAS(IfExpAS ifExpAS) {
    this.ifExpAS = ifExpAS;
    if (ifExpAS.getElseExpression() != this) {
       ifExpAS.setElseExpression(this);
    }
  }


    
  //uni1to1
  protected OclExpressionAS parent = null;
  public OclExpressionAS getParent() {
   return parent;
  }
  public void setParent(OclExpressionAS parent) {
    this.parent = parent;
  }





  //--- Visitable ---
  public java.lang.Object accept(Visitor visitor, java.lang.Object data) {
    if (visitor instanceof expressionsVisitor)
      return ((expressionsVisitor)visitor).visit(this,data);
    else
      return visitor.visit(this,data);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Factory factory) {
  	factory.destroy("OclExpressionAS",this);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Repository rep) {
  	rep.removeElement("uk.ac.kent.cs.ocl20.syntax.ast.expressions.OclExpressionAS",this);
  }


  //--- java.lang.Object ---

  static int _nextId=0;
   int _id=0;


  public java.lang.String toString() {
      return "OclExpressionAS {"+

  _id+
"}";
  }

  public boolean equals(java.lang.Object o) {
    if (o instanceof OclExpressionAS) {
      try {
        OclExpressionAS obj = (OclExpressionAS)o;
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
    OclExpressionAS obj = new OclExpressionASImpl();

  obj.setIsMarkedPre(_isMarkedPre);

    return obj;
  }
}



