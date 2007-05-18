
package org.oslo.ocl20.syntax.ast.types;

import uk.ac.kent.cs.kmf.patterns.Visitor;

public class TypeASImpl

  extends
    
      org.oslo.ocl20.syntax.ast.expressions.LiteralExpASImpl
    
  
  implements TypeAS,
             typesVisitable
{
  //--- Constructors ---
  public TypeASImpl() {

    _id=_nextId++;

  }










  //--- Visitable ---
  public java.lang.Object accept(Visitor visitor, java.lang.Object data) {
    if (visitor instanceof typesVisitor)
      return ((typesVisitor)visitor).visit(this,data);
    else
      return visitor.visit(this,data);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Factory factory) {
  	factory.destroy("TypeAS",this);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Repository rep) {
  	rep.removeElement("uk.ac.kent.cs.ocl20.syntax.ast.types.TypeAS",this);
  }


  //--- java.lang.Object ---

  static int _nextId=0;
   int _id=0;


  public java.lang.String toString() {
      return "TypeAS {"+

  _id+
"}";
  }

  public boolean equals(java.lang.Object o) {
    if (o instanceof TypeAS) {
      try {
        TypeAS obj = (TypeAS)o;
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
    TypeAS obj = new TypeASImpl();

    return obj;
  }
}



