
package org.oslo.ocl20.syntax.ast.contexts;

import uk.ac.kent.cs.kmf.patterns.Visitor;

public class PropertyContextDeclASImpl

  extends
    
      ContextDeclarationASImpl
    
  
  implements PropertyContextDeclAS,
             contextsVisitable
{
  //--- Constructors ---
  public PropertyContextDeclASImpl() {

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
  	factory.destroy("PropertyContextDeclAS",this);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Repository rep) {
  	rep.removeElement("uk.ac.kent.cs.ocl20.syntax.ast.contexts.PropertyContextDeclAS",this);
  }


  //--- java.lang.Object ---

  static int _nextId=0;
   int _id=0;


  public java.lang.String toString() {
      return "PropertyContextDeclAS {"+

  _id+
"}";
  }

  public boolean equals(java.lang.Object o) {
    if (o instanceof PropertyContextDeclAS) {
      try {
        PropertyContextDeclAS obj = (PropertyContextDeclAS)o;
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
    PropertyContextDeclAS obj = new PropertyContextDeclASImpl();

  obj.setName(_name);

  obj.setPathName(_pathName);

    return obj;
  }
}



