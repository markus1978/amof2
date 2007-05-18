
package org.oslo.ocl20.syntax.ast.types;

import uk.ac.kent.cs.kmf.patterns.Visitor;

public interface typesVisitor
extends Visitor
{
  public java.lang.Object visit(OrderedSetTypeAS host, java.lang.Object data);
  public java.lang.Object visit(ClassifierAS host, java.lang.Object data);
  public java.lang.Object visit(CollectionTypeAS host, java.lang.Object data);
  public java.lang.Object visit(TupleTypeAS host, java.lang.Object data);
  public java.lang.Object visit(BagTypeAS host, java.lang.Object data);
  public java.lang.Object visit(SetTypeAS host, java.lang.Object data);
  public java.lang.Object visit(SequenceTypeAS host, java.lang.Object data);

}
