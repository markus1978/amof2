
package org.oslo.ocl20.syntax.ast.contexts;

import uk.ac.kent.cs.kmf.patterns.Visitor;

public class OperationASImpl

  
  implements OperationAS,
             contextsVisitable
{
  //--- Constructors ---
  public OperationASImpl() {

    _id=_nextId++;

  }




  java.lang.String _name = null;
  public java.lang.String getName()
  { return _name; }
  public void setName(java.lang.String name)
  { _name = name; }

  java.util.List _pathName = null;
  public java.util.List getPathName()
  { return _pathName; }
  public void setPathName(java.util.List pathName)
  { _pathName = pathName; }




    
  //uniNto1
  protected java.util.List parameters = new java.util.Vector();
  public java.util.List getParameters() {
   return parameters;
  }
  public void setParameters(java.util.List parameters) {
    this.parameters = parameters;
  }
  public boolean addParameters(VariableDeclarationAS p) {
	boolean b = this.parameters.add(p);
	return b;
  }
  public boolean removeParameters(VariableDeclarationAS p) {
	boolean b = this.parameters.remove(p);
	return b;
  }


    
  //uni1to1
  protected org.oslo.ocl20.syntax.ast.types.TypeAS type = null;
  public org.oslo.ocl20.syntax.ast.types.TypeAS getType() {
   return type;
  }
  public void setType(org.oslo.ocl20.syntax.ast.types.TypeAS type) {
    this.type = type;
  }





  //--- Visitable ---
  public java.lang.Object accept(Visitor visitor, java.lang.Object data) {
    if (visitor instanceof contextsVisitor)
      return ((contextsVisitor)visitor).visit(this,data);
    else
      return visitor.visit(this,data);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Factory factory) {
  	factory.destroy("OperationAS",this);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Repository rep) {
  	rep.removeElement("uk.ac.kent.cs.ocl20.syntax.ast.contexts.OperationAS",this);
  }


  //--- java.lang.Object ---

  static int _nextId=0;
   int _id=0;


  public java.lang.String toString() {
      return "OperationAS {"+

  _id+
"}";
  }

  public boolean equals(java.lang.Object o) {
    if (o instanceof OperationAS) {
      try {
        OperationAS obj = (OperationAS)o;
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
    OperationAS obj = new OperationASImpl();

  obj.setName(_name);

  obj.setPathName(_pathName);

    return obj;
  }
}



