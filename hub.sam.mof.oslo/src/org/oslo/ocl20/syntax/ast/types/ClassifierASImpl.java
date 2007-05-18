
package org.oslo.ocl20.syntax.ast.types;

import uk.ac.kent.cs.kmf.patterns.Visitor;

public class ClassifierASImpl

  extends
    
      TypeASImpl
    
  
  implements ClassifierAS,
             typesVisitable
{
  //--- Constructors ---
  public ClassifierASImpl() {

  }




  java.util.List _pathName = null;
  public java.util.List getPathName()
  { return _pathName; }
  public void setPathName(java.util.List pathName)
  { _pathName = pathName; }







  //--- Visitable ---
  public java.lang.Object accept(Visitor visitor, java.lang.Object data) {
    if (visitor instanceof typesVisitor)
      return ((typesVisitor)visitor).visit(this,data);
    else
      return visitor.visit(this,data);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Factory factory) {
  	factory.destroy("ClassifierAS",this);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Repository rep) {
  	rep.removeElement("uk.ac.kent.cs.ocl20.syntax.ast.types.ClassifierAS",this);
  }


  //--- java.lang.Object ---


  public java.lang.String toString() {
      return "ClassifierAS {"+

"pathName = "+this.getPathName()+
"}";
  }

  public boolean equals(java.lang.Object o) {
    if (o instanceof ClassifierAS) {
      try {
        ClassifierAS obj = (ClassifierAS)o;
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
    ClassifierAS obj = new ClassifierASImpl();

  obj.setPathName(_pathName);

    return obj;
  }
}



