package org.oslo.ocl20.syntax.ast.expressions;
import uk.ac.kent.cs.kmf.patterns.Visitor;

abstract
public class CollectionKindAS
  implements expressionsVisitable,
             java.util.Enumeration
{
  public static CollectionKindAS SET = new CollectionKindAS() {
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
      return CollectionKindAS.BAG;
    }
    
    public java.lang.String toString() { return "SET";}
    public boolean equals(java.lang.Object o) { return this==o; }
    public int hashCode() { return toString().hashCode();}
    
  };
  public static CollectionKindAS BAG = new CollectionKindAS() {
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
      return CollectionKindAS.SEQUENCE;
    }
    
    public java.lang.String toString() { return "BAG";}
    public boolean equals(java.lang.Object o) { return this==o; }
    public int hashCode() { return toString().hashCode();}
    
  };
  public static CollectionKindAS SEQUENCE = new CollectionKindAS() {
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
      return CollectionKindAS.COLLECTION;
    }
    
    public java.lang.String toString() { return "SEQUENCE";}
    public boolean equals(java.lang.Object o) { return this==o; }
    public int hashCode() { return toString().hashCode();}
    
  };
  public static CollectionKindAS COLLECTION = new CollectionKindAS() {
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
      return CollectionKindAS.ORDERED_SET;
    }
    
    public java.lang.String toString() { return "COLLECTION";}
    public boolean equals(java.lang.Object o) { return this==o; }
    public int hashCode() { return toString().hashCode();}
    
  };
  public static CollectionKindAS ORDERED_SET = new CollectionKindAS() {
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
    
    public java.lang.String toString() { return "ORDERED_SET";}
    public boolean equals(java.lang.Object o) { return this==o; }
    public int hashCode() { return toString().hashCode();}
    
  };

}
