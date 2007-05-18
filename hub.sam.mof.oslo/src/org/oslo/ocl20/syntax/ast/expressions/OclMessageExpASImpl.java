
package org.oslo.ocl20.syntax.ast.expressions;

import uk.ac.kent.cs.kmf.patterns.Visitor;

public class OclMessageExpASImpl

  extends
    
      OclExpressionASImpl
    
  
  implements OclMessageExpAS,
             expressionsVisitable
{
  //--- Constructors ---
  public OclMessageExpASImpl() {

    _id=_nextId++;

  }




  OclMessageKindAS _kind = null;
  public OclMessageKindAS getKind()
  { return _kind; }
  public void setKind(OclMessageKindAS kind)
  { _kind = kind; }

  java.lang.String _name = null;
  public java.lang.String getName()
  { return _name; }
  public void setName(java.lang.String name)
  { _name = name; }




    
  //uni1to1
  protected OclExpressionAS target = null;
  public OclExpressionAS getTarget() {
   return target;
  }
  public void setTarget(OclExpressionAS target) {
    this.target = target;
  }


    
  //uniNto1
  protected java.util.List arguments = new java.util.Vector();
  public java.util.List getArguments() {
   return arguments;
  }
  public void setArguments(java.util.List arguments) {
    this.arguments = arguments;
  }
  public boolean addArguments(OclMessageArgAS p) {
	boolean b = this.arguments.add(p);
	return b;
  }
  public boolean removeArguments(OclMessageArgAS p) {
	boolean b = this.arguments.remove(p);
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
  	factory.destroy("OclMessageExpAS",this);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Repository rep) {
  	rep.removeElement("uk.ac.kent.cs.ocl20.syntax.ast.expressions.OclMessageExpAS",this);
  }


  //--- java.lang.Object ---

  static int _nextId=0;
   int _id=0;


  public java.lang.String toString() {
      return "OclMessageExpAS {"+

  _id+
"}";
  }

  public boolean equals(java.lang.Object o) {
    if (o instanceof OclMessageExpAS) {
      try {
        OclMessageExpAS obj = (OclMessageExpAS)o;
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
    OclMessageExpAS obj = new OclMessageExpASImpl();

  obj.setKind(_kind);

  obj.setName(_name);

    return obj;
  }
}



