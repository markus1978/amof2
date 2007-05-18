
package org.oslo.ocl20.syntax.ast.contexts;

import org.oslo.ocl20.syntax.ast.types.TypeAS;

import uk.ac.kent.cs.kmf.patterns.Visitor;

public class ClassifierContextDeclASImpl

  extends
    
      ContextDeclarationASImpl
    
  
  implements ClassifierContextDeclAS,
             contextsVisitable
{
  //--- Constructors ---
  public ClassifierContextDeclASImpl() {

  }




  java.util.List _pathName = null;
  public java.util.List getPathName()
  { return _pathName; }
  public void setPathName(java.util.List pathName)
  { _pathName = pathName; }


  TypeAS _type = null;
  public TypeAS getType()
  { return _type; }
  public void setType(TypeAS t)
  { _type = t; }




  //--- Visitable ---
  public java.lang.Object accept(Visitor visitor, java.lang.Object data) {
    if (visitor instanceof contextsVisitor)
      return ((contextsVisitor)visitor).visit(this,data);
    else
      return visitor.visit(this,data);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Factory factory) {
  	factory.destroy("ClassifierContextDeclAS",this);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Repository rep) {
  	rep.removeElement("uk.ac.kent.cs.ocl20.syntax.ast.contexts.ClassifierContextDeclAS",this);
  }


  //--- java.lang.Object ---


  public java.lang.String toString() {
      return "ClassifierContextDeclAS {"+

"pathName = "+this.getPathName()+
"}";
  }

  public boolean equals(java.lang.Object o) {
    if (o instanceof ClassifierContextDeclAS) {
      try {
        ClassifierContextDeclAS obj = (ClassifierContextDeclAS)o;
        return (
obj.getPathName().equals( this.getPathName() )
);
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
    ClassifierContextDeclAS obj = new ClassifierContextDeclASImpl();

  obj.setPathName(_pathName);

    return obj;
  }
}



