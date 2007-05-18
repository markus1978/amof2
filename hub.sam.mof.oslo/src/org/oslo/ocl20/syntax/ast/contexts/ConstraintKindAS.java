package org.oslo.ocl20.syntax.ast.contexts;
import uk.ac.kent.cs.kmf.patterns.Visitor;

abstract
public class ConstraintKindAS
  implements contextsVisitable,
             java.util.Enumeration
{
  public static ConstraintKindAS INIT = new ConstraintKindAS() {
    public java.lang.Object accept(Visitor visitor, java.lang.Object data) {
      if (visitor instanceof contextsVisitor)
        return ((contextsVisitor)visitor).visit(this,data);
      else
        return visitor.visit(this,data);
    }
    public boolean hasMoreElements() {
      return true;
    }
    public java.lang.Object nextElement() {
      return ConstraintKindAS.DERIVE;
    }
    
    public java.lang.String toString() { return "INIT";}
    public boolean equals(java.lang.Object o) { return this==o; }
    public int hashCode() { return toString().hashCode();}
    
  };
  public static ConstraintKindAS DERIVE = new ConstraintKindAS() {
    public java.lang.Object accept(Visitor visitor, java.lang.Object data) {
      if (visitor instanceof contextsVisitor)
        return ((contextsVisitor)visitor).visit(this,data);
      else
        return visitor.visit(this,data);
    }
    public boolean hasMoreElements() {
      return true;
    }
    public java.lang.Object nextElement() {
      return ConstraintKindAS.INV;
    }
    
    public java.lang.String toString() { return "DERIVE";}
    public boolean equals(java.lang.Object o) { return this==o; }
    public int hashCode() { return toString().hashCode();}
    
  };
  public static ConstraintKindAS INV = new ConstraintKindAS() {
    public java.lang.Object accept(Visitor visitor, java.lang.Object data) {
      if (visitor instanceof contextsVisitor)
        return ((contextsVisitor)visitor).visit(this,data);
      else
        return visitor.visit(this,data);
    }
    public boolean hasMoreElements() {
      return true;
    }
    public java.lang.Object nextElement() {
      return ConstraintKindAS.DEF;
    }
    
    public java.lang.String toString() { return "INV";}
    public boolean equals(java.lang.Object o) { return this==o; }
    public int hashCode() { return toString().hashCode();}
    
  };
  public static ConstraintKindAS DEF = new ConstraintKindAS() {
    public java.lang.Object accept(Visitor visitor, java.lang.Object data) {
      if (visitor instanceof contextsVisitor)
        return ((contextsVisitor)visitor).visit(this,data);
      else
        return visitor.visit(this,data);
    }
    public boolean hasMoreElements() {
      return true;
    }
    public java.lang.Object nextElement() {
      return ConstraintKindAS.PRE;
    }
    
    public java.lang.String toString() { return "DEF";}
    public boolean equals(java.lang.Object o) { return this==o; }
    public int hashCode() { return toString().hashCode();}
    
  };
  public static ConstraintKindAS PRE = new ConstraintKindAS() {
    public java.lang.Object accept(Visitor visitor, java.lang.Object data) {
      if (visitor instanceof contextsVisitor)
        return ((contextsVisitor)visitor).visit(this,data);
      else
        return visitor.visit(this,data);
    }
    public boolean hasMoreElements() {
      return true;
    }
    public java.lang.Object nextElement() {
      return ConstraintKindAS.POST;
    }
    
    public java.lang.String toString() { return "PRE";}
    public boolean equals(java.lang.Object o) { return this==o; }
    public int hashCode() { return toString().hashCode();}
    
  };
  public static ConstraintKindAS POST = new ConstraintKindAS() {
    public java.lang.Object accept(Visitor visitor, java.lang.Object data) {
      if (visitor instanceof contextsVisitor)
        return ((contextsVisitor)visitor).visit(this,data);
      else
        return visitor.visit(this,data);
    }
    public boolean hasMoreElements() {
      return true;
    }
    public java.lang.Object nextElement() {
      return ConstraintKindAS.BODY;
    }
    
    public java.lang.String toString() { return "POST";}
    public boolean equals(java.lang.Object o) { return this==o; }
    public int hashCode() { return toString().hashCode();}
    
  };
  public static ConstraintKindAS BODY = new ConstraintKindAS() {
    public java.lang.Object accept(Visitor visitor, java.lang.Object data) {
      if (visitor instanceof contextsVisitor)
        return ((contextsVisitor)visitor).visit(this,data);
      else
        return visitor.visit(this,data);
    }
    public boolean hasMoreElements() {
      return false;
    }
    public java.lang.Object nextElement() {
      return null;
    }
    
    public java.lang.String toString() { return "BODY";}
    public boolean equals(java.lang.Object o) { return this==o; }
    public int hashCode() { return toString().hashCode();}
    
  };

}
