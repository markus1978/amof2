
package org.oslo.ocl20.syntax.ast.types;

import uk.ac.kent.cs.kmf.patterns.Visitor;

public class CollectionTypeASImpl

  extends
    
      TypeASImpl
    
  
  implements CollectionTypeAS,
             typesVisitable
{
  //--- Constructors ---
  public CollectionTypeASImpl() {

    _id=_nextId++;

  }







    
  //uni1to1
  protected TypeAS elementType = null;
  public TypeAS getElementType() {
   return elementType;
  }
  public void setElementType(TypeAS elementType) {
    this.elementType = elementType;
  }





  //--- Visitable ---
  public java.lang.Object accept(Visitor visitor, java.lang.Object data) {
    if (visitor instanceof typesVisitor)
      return ((typesVisitor)visitor).visit(this,data);
    else
      return visitor.visit(this,data);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Factory factory) {
  	factory.destroy("CollectionTypeAS",this);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Repository rep) {
  	rep.removeElement("uk.ac.kent.cs.ocl20.syntax.ast.types.CollectionTypeAS",this);
  }


  //--- java.lang.Object ---

  static int _nextId=0;
   int _id=0;


  public java.lang.String toString() {
      return "CollectionTypeAS {"+

  _id+
"}";
  }

  public boolean equals(java.lang.Object o) {
    if (o instanceof CollectionTypeAS) {
      try {
        CollectionTypeAS obj = (CollectionTypeAS)o;
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
    CollectionTypeAS obj = new CollectionTypeASImpl();

    return obj;
  }
}



