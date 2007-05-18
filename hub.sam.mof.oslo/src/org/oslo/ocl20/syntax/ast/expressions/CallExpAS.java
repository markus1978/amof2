
 
 
package org.oslo.ocl20.syntax.ast.expressions;

public interface CallExpAS
  extends expressionsVisitable,
           uk.ac.kent.cs.kmf.patterns.Destroyable
,
      OclExpressionAS

{


  final OclExpressionAS arguments_elementType=null;
  java.util.List getArguments();
  void setArguments(java.util.List arguments);
  boolean addArguments(OclExpressionAS arguments);
  boolean removeArguments(OclExpressionAS arguments);

  
  OclExpressionAS getSource();
  void setSource(OclExpressionAS source);
  
  



  //--- java.lang.Object ---

   public java.lang.String toString();

  public boolean equals(java.lang.Object o);
  public int hashCode();
  public java.lang.Object clone();
}
