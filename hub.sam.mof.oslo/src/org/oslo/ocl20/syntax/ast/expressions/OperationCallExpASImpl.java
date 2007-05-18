
package org.oslo.ocl20.syntax.ast.expressions;

import java.util.Iterator;

import uk.ac.kent.cs.kmf.patterns.Visitor;

public class OperationCallExpASImpl

  extends
    
      CallExpASImpl
    
  
  implements OperationCallExpAS,
             expressionsVisitable
{
  //--- Constructors ---
  public OperationCallExpASImpl() {

    _id=_nextId++;

  }










  //--- Visitable ---
  public java.lang.Object accept(Visitor visitor, java.lang.Object data) {
    if (visitor instanceof expressionsVisitor)
      return ((expressionsVisitor)visitor).visit(this,data);
    else
      return visitor.visit(this,data);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Factory factory) {
  	factory.destroy("OperationCallExpAS",this);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Repository rep) {
  	rep.removeElement("uk.ac.kent.cs.ocl20.syntax.ast.expressions.OperationCallExpAS",this);
  }


  //--- java.lang.Object ---

  static int _nextId=0;
   int _id=0;


  public java.lang.String toString() {
      String s = "";
      s+=getSource()+"(";
      Iterator i = getArguments().iterator();
      while (i.hasNext()) {
          s+=i.next();
          if (i.hasNext()) s+=", ";
      }
      s+=")";
      return s;
  }

  public boolean equals(java.lang.Object o) {
    if (o instanceof OperationCallExpAS) {
      try {
        OperationCallExpAS obj = (OperationCallExpAS)o;
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
    OperationCallExpAS obj = new OperationCallExpASImpl();

    return obj;
  }
}



