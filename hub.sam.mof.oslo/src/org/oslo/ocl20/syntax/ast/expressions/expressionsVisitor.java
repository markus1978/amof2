
package org.oslo.ocl20.syntax.ast.expressions;

import uk.ac.kent.cs.kmf.patterns.Visitor;

public interface expressionsVisitor
extends Visitor
{
  public java.lang.Object visit(OclMessageKindAS host, java.lang.Object data);
  public java.lang.Object visit(CollectionKindAS host, java.lang.Object data);
  public java.lang.Object visit(IteratorExpAS host, java.lang.Object data);
  public java.lang.Object visit(CollectionItemAS host, java.lang.Object data);
  public java.lang.Object visit(CollectionRangeAS host, java.lang.Object data);
  public java.lang.Object visit(PathNameExpAS host, java.lang.Object data);
  public java.lang.Object visit(EnumLiteralExpAS host, java.lang.Object data);
  public java.lang.Object visit(OclMessageExpAS host, java.lang.Object data);
  public java.lang.Object visit(LetExpAS host, java.lang.Object data);
  public java.lang.Object visit(IterateExpAS host, java.lang.Object data);
  public java.lang.Object visit(OperationCallExpAS host, java.lang.Object data);
  public java.lang.Object visit(AssociationCallExpAS host, java.lang.Object data);
  public java.lang.Object visit(ArrowSelectionExpAS host, java.lang.Object data);
  public java.lang.Object visit(DotSelectionExpAS host, java.lang.Object data);
  public java.lang.Object visit(OclMessageArgAS host, java.lang.Object data);
  public java.lang.Object visit(IfExpAS host, java.lang.Object data);
  public java.lang.Object visit(NotExpAS host, java.lang.Object data);
  public java.lang.Object visit(AndExpAS host, java.lang.Object data);
  public java.lang.Object visit(OrExpAS host, java.lang.Object data);
  public java.lang.Object visit(XorExpAS host, java.lang.Object data);
  public java.lang.Object visit(ImpliesExpAS host, java.lang.Object data);
  public java.lang.Object visit(OclExpressionAS host, java.lang.Object data);
  public java.lang.Object visit(CollectionLiteralExpAS host, java.lang.Object data);
  public java.lang.Object visit(TupleLiteralExpAS host, java.lang.Object data);
  public java.lang.Object visit(IntegerLiteralExpAS host, java.lang.Object data);
  public java.lang.Object visit(RealLiteralExpAS host, java.lang.Object data);
  public java.lang.Object visit(BooleanLiteralExpAS host, java.lang.Object data);
  public java.lang.Object visit(StringLiteralExpAS host, java.lang.Object data);

}
