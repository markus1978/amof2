
package org.oslo.ocl20.syntax.ast.expressions;

import uk.ac.kent.cs.kmf.patterns.Visitor;

public class CollectionLiteralExpASImpl

  extends
    
      LiteralExpASImpl
    
  
  implements CollectionLiteralExpAS,
             expressionsVisitable
{
  //--- Constructors ---
  public CollectionLiteralExpASImpl() {

    _id=_nextId++;

  }




  CollectionKindAS _kind = null;
  public CollectionKindAS getKind()
  { return _kind; }
  public void setKind(CollectionKindAS kind)
  { _kind = kind; }




    
  //uniNto1
  protected java.util.List collectionParts = new java.util.Vector();
  public java.util.List getCollectionParts() {
   return collectionParts;
  }
  public void setCollectionParts(java.util.List collectionParts) {
    this.collectionParts = collectionParts;
  }
  public boolean addCollectionParts(CollectionLiteralPartAS p) {
	boolean b = this.collectionParts.add(p);
	return b;
  }
  public boolean removeCollectionParts(CollectionLiteralPartAS p) {
	boolean b = this.collectionParts.remove(p);
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
  	factory.destroy("CollectionLiteralExpAS",this);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Repository rep) {
  	rep.removeElement("uk.ac.kent.cs.ocl20.syntax.ast.expressions.CollectionLiteralExpAS",this);
  }


  //--- java.lang.Object ---

  static int _nextId=0;
   int _id=0;


  public java.lang.String toString() {
      return "CollectionLiteralExpAS {"+

  _id+
"}";
  }

  public boolean equals(java.lang.Object o) {
    if (o instanceof CollectionLiteralExpAS) {
      try {
        CollectionLiteralExpAS obj = (CollectionLiteralExpAS)o;
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
    CollectionLiteralExpAS obj = new CollectionLiteralExpASImpl();

  obj.setKind(_kind);

    return obj;
  }
}



