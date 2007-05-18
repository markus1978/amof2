
package org.oslo.ocl20.syntax.ast.expressions;

import java.util.List;

import uk.ac.kent.cs.kmf.patterns.Visitor;

public class LoopExpASImpl

  extends
    
      OclExpressionASImpl
    
  
  implements LoopExpAS,
             expressionsVisitable
{
  //--- Constructors ---
  public LoopExpASImpl() {

    _id=_nextId++;

  }




  java.lang.String _name = null;
  public java.lang.String getName()
  { return _name; }
  public void setName(java.lang.String name)
  { _name = name; }




    
  //uni1to1
  protected OclExpressionAS body = null;
  public OclExpressionAS getBody() {
   return body;
  }
  public void setBody(OclExpressionAS body) {
    this.body = body;
  }


    
  //uni1to1
  List iterator = null;
  public List getIterator() {
   return iterator;
  }
  public void setIterator(List iterator) {
    this.iterator = iterator;
  }


    
  //uni1to1
  protected org.oslo.ocl20.syntax.ast.contexts.VariableDeclarationAS result = null;
  public org.oslo.ocl20.syntax.ast.contexts.VariableDeclarationAS getResult() {
   return result;
  }
  public void setResult(org.oslo.ocl20.syntax.ast.contexts.VariableDeclarationAS result) {
    this.result = result;
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
  	factory.destroy("LoopExpAS",this);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Repository rep) {
  	rep.removeElement("uk.ac.kent.cs.ocl20.syntax.ast.expressions.LoopExpAS",this);
  }


  //--- java.lang.Object ---

  static int _nextId=0;
   int _id=0;


  public java.lang.String toString() {
      return "LoopExpAS {"+

  _id+
"}";
  }

  public boolean equals(java.lang.Object o) {
    if (o instanceof LoopExpAS) {
      try {
        LoopExpAS obj = (LoopExpAS)o;
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
    LoopExpAS obj = new LoopExpASImpl();

  obj.setName(_name);

    return obj;
  }
}



