
 
 
package org.oslo.ocl20.syntax.ast.expressions;

public interface CollectionLiteralExpAS
  extends expressionsVisitable,
           uk.ac.kent.cs.kmf.patterns.Destroyable
,
      LiteralExpAS

{

  CollectionKindAS getKind();
  void setKind(CollectionKindAS kind);


  final CollectionLiteralPartAS collectionParts_elementType=null;
  java.util.List getCollectionParts();
  void setCollectionParts(java.util.List collectionParts);
  boolean addCollectionParts(CollectionLiteralPartAS collectionParts);
  boolean removeCollectionParts(CollectionLiteralPartAS collectionParts);



  //--- java.lang.Object ---

   public java.lang.String toString();

  public boolean equals(java.lang.Object o);
  public int hashCode();
  public java.lang.Object clone();
}
