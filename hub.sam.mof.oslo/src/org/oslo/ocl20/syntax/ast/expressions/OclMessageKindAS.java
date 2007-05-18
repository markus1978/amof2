package org.oslo.ocl20.syntax.ast.expressions;
import uk.ac.kent.cs.kmf.patterns.Visitor;

abstract
public class OclMessageKindAS
  implements expressionsVisitable,
             java.util.Enumeration
{
  public static OclMessageKindAS UP = new OclMessageKindAS() {
    public java.lang.Object accept(Visitor visitor, java.lang.Object data) {
      if (visitor instanceof expressionsVisitor)
        return ((expressionsVisitor)visitor).visit(this,data);
      else
        return visitor.visit(this,data);
    }
    public boolean hasMoreElements() {
      return true;
    }
    public java.lang.Object nextElement() {
      return OclMessageKindAS.UP_UP;
    }
    
    public java.lang.String toString() { return "UP";}
    public boolean equals(java.lang.Object o) { return this==o; }
    public int hashCode() { return toString().hashCode();}
    
  };
  public static OclMessageKindAS UP_UP = new OclMessageKindAS() {
    public java.lang.Object accept(Visitor visitor, java.lang.Object data) {
      if (visitor instanceof expressionsVisitor)
        return ((expressionsVisitor)visitor).visit(this,data);
      else
        return visitor.visit(this,data);
    }
    public boolean hasMoreElements() {
      return false;
    }
    public java.lang.Object nextElement() {
      return null;
    }
    
    public java.lang.String toString() { return "UP_UP";}
    public boolean equals(java.lang.Object o) { return this==o; }
    public int hashCode() { return toString().hashCode();}
    
  };

}
