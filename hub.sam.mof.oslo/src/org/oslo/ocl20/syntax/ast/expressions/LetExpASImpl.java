
package org.oslo.ocl20.syntax.ast.expressions;

import org.oslo.ocl20.syntax.ast.contexts.VariableDeclarationAS;

import uk.ac.kent.cs.kmf.patterns.Visitor;

public class LetExpASImpl

  extends
    
      OclExpressionASImpl
    
  
  implements LetExpAS,
             expressionsVisitable
{
  //--- Constructors ---
  public LetExpASImpl() {

    _id=_nextId++;

  }







    
  //uniNto1
  protected java.util.List variables = new java.util.Vector();
  public java.util.List getVariables() {
   return variables;
  }
  public void setVariables(java.util.List variables) {
    this.variables = variables;
  }
  public boolean addVariables(VariableDeclarationAS p) {
	boolean b = this.variables.add(p);
	return b;
  }
  public boolean removeVariables(VariableDeclarationAS p) {
	boolean b = this.variables.remove(p);
	return b;
  }


    
  //uni1to1
  protected OclExpressionAS in = null;
  public OclExpressionAS getIn() {
   return in;
  }
  public void setIn(OclExpressionAS in) {
    this.in = in;
  }





  //--- Visitable ---
  public java.lang.Object accept(Visitor visitor, java.lang.Object data) {
    if (visitor instanceof expressionsVisitor)
      return ((expressionsVisitor)visitor).visit(this,data);
    else
      return visitor.visit(this,data);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Factory factory) {
  	factory.destroy("LetExpAS",this);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Repository rep) {
  	rep.removeElement("uk.ac.kent.cs.ocl20.syntax.ast.expressions.LetExpAS",this);
  }


  //--- java.lang.Object ---

  static int _nextId=0;
   int _id=0;


  public java.lang.String toString() {
      return "LetExpAS {"+

  _id+
"}";
  }

  public boolean equals(java.lang.Object o) {
    if (o instanceof LetExpAS) {
      try {
        LetExpAS obj = (LetExpAS)o;
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
    LetExpAS obj = new LetExpASImpl();

    return obj;
  }
}



