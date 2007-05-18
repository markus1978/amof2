
 
 
package org.oslo.ocl20.syntax.ast.expressions;

public interface OclMessageExpAS
  extends expressionsVisitable,
           uk.ac.kent.cs.kmf.patterns.Destroyable
,
      OclExpressionAS

{

  OclMessageKindAS getKind();
  void setKind(OclMessageKindAS kind);

  java.lang.String getName();
  void setName(java.lang.String name);


  
  OclExpressionAS getTarget();
  void setTarget(OclExpressionAS target);
  
  

  final OclMessageArgAS arguments_elementType=null;
  java.util.List getArguments();
  void setArguments(java.util.List arguments);
  boolean addArguments(OclMessageArgAS arguments);
  boolean removeArguments(OclMessageArgAS arguments);



  //--- java.lang.Object ---

   public java.lang.String toString();

  public boolean equals(java.lang.Object o);
  public int hashCode();
  public java.lang.Object clone();
}
