package org.oslo.ocl20.syntax.ast;

public interface SyntaxElement
  extends astVisitable,
           uk.ac.kent.cs.kmf.patterns.Destroyable,
           uk.ac.kent.cs.kmf.patterns.association.AssociationEnd
{
  java.lang.Object getSymbol();
  void setSymbol(java.lang.Object symbol);


  //--- java.lang.Object ---
  public java.lang.String toString();
  public boolean equals(java.lang.Object o);
  public int hashCode();
  public java.lang.Object clone();
}
