
package org.oslo.ocl20.syntax.ast.expressions;

import java.util.Iterator;

import uk.ac.kent.cs.kmf.patterns.Visitor;

public class PathNameExpASImpl

  extends
    
      PrimaryExpASImpl
    
  
  implements PathNameExpAS,
             expressionsVisitable
{
  //--- Constructors ---
  public PathNameExpASImpl() {

    _id=_nextId++;

  }




  java.util.List _pathName = null;
  public java.util.List getPathName()
  { return _pathName; }
  public void setPathName(java.util.List pathName)
  { _pathName = pathName; }







  //--- Visitable ---
  public java.lang.Object accept(Visitor visitor, java.lang.Object data) {
    if (visitor instanceof expressionsVisitor)
      return ((expressionsVisitor)visitor).visit(this,data);
    else
      return visitor.visit(this,data);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Factory factory) {
  	factory.destroy("PathNameExpAS",this);
  }

  public void destroy(uk.ac.kent.cs.kmf.patterns.Repository rep) {
  	rep.removeElement("uk.ac.kent.cs.ocl20.syntax.ast.expressions.PathNameExpAS",this);
  }


  //--- java.lang.Object ---

  static int _nextId=0;
   int _id=0;


  public java.lang.String toString() {
      String sn = "";
      Iterator i = getPathName().iterator();
      while (i.hasNext()) {
          String s = (String)i.next();
          sn+=s;
          if (i.hasNext()) sn+="::";
      }
      return sn;
  }

  public boolean equals(java.lang.Object o) {
    if (o instanceof PathNameExpAS) {
      try {
        PathNameExpAS obj = (PathNameExpAS)o;
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
    PathNameExpAS obj = new PathNameExpASImpl();

  obj.setPathName(_pathName);

    return obj;
  }
}



