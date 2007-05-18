
package org.oslo.ocl20.syntax.ast.expressions;

import uk.ac.kent.cs.kmf.patterns.Visitor;

public class CallExpASImpl

  extends
    
      OclExpressionASImpl
    
  
  implements CallExpAS,
             expressionsVisitable
{
  //--- Constructors ---
  public CallExpASImpl() {

    _id=_nextId++;

  }







    
  //uniNto1
  protected java.util.List arguments = new java.util.Vector();
  public java.util.List getArguments() {
   return arguments;
  }
  public void setArguments(java.util.List arguments) {
    this.arguments = arguments;
  }
  public boolean addArguments(OclExpressionAS p) {
	boolean b = this.arguments.add(p);
	return b;
  }
  public boolean removeArguments(OclExpressionAS p) {
	boolean b = this.arguments.remove(p);
	return b;
  }


    
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
  	factory.destroy("CallExpAS",this);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Repository rep) {
  	rep.removeElement("uk.ac.kent.cs.ocl20.syntax.ast.expressions.CallExpAS",this);
  }


  //--- java.lang.Object ---

  static int _nextId=0;
   int _id=0;


  public java.lang.String toString() {
      return "CallExpAS {"+

  _id+
"}";
  }

  public boolean equals(java.lang.Object o) {
    if (o instanceof CallExpAS) {
      try {
        CallExpAS obj = (CallExpAS)o;
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
    CallExpAS obj = new CallExpASImpl();

    return obj;
  }
}



