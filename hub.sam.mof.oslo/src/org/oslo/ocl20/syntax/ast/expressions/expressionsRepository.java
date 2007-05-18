package org.oslo.ocl20.syntax.ast.expressions;

import uk.ac.kent.cs.kmf.patterns.RepositoryImpl;
import uk.ac.kent.cs.kmf.util.ILog;

public class expressionsRepository extends RepositoryImpl {

  public expressionsRepository(ILog log) {
    super(log);
	super.setFactory( new expressionsFactory(log, this) );
    super.registerElementType("IfExpAS");
    super.registerElementType("OclMessageArgAS");
    super.registerElementType("DotSelectionExpAS");
    super.registerElementType("ArrowSelectionExpAS");
    super.registerElementType("CallExpAS");
    super.registerElementType("LoopExpAS");
    super.registerElementType("AssociationCallExpAS");
    super.registerElementType("OperationCallExpAS");
    super.registerElementType("IterateExpAS");
    super.registerElementType("LetExpAS");
    super.registerElementType("OclMessageExpAS");
    super.registerElementType("LiteralExpAS");
    super.registerElementType("EnumLiteralExpAS");
    super.registerElementType("PathNameExpAS");
    super.registerElementType("CollectionLiteralPartAS");
    super.registerElementType("CollectionRangeAS");
    super.registerElementType("CollectionItemAS");
    super.registerElementType("NotExpAS");
    super.registerElementType("AndExpAS");
    super.registerElementType("OrExpAS");
    super.registerElementType("XorExpAS");
    super.registerElementType("ImpliesExpAS");
    super.registerElementType("LogicalExpAS");
    super.registerElementType("PrimaryExpAS");
    super.registerElementType("SelectionExpAS");
    super.registerElementType("OclExpressionAS");
    super.registerElementType("CollectionLiteralExpAS");
    super.registerElementType("TupleLiteralExpAS");
    super.registerElementType("PrimitiveLiteralExpAS");
    super.registerElementType("IntegerLiteralExpAS");
    super.registerElementType("RealLiteralExpAS");
    super.registerElementType("BooleanLiteralExpAS");
    super.registerElementType("StringLiteralExpAS");
    super.registerElementType("IteratorExpAS");
  }

  public void saveXMI(java.lang.String fileName) {
    super.saveXMI(fileName, new expressionsVisitorImpl(_log));
  }
  
  public java.lang.String toString() { return "expressions";}
}
