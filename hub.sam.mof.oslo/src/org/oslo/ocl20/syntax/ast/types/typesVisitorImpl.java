package org.oslo.ocl20.syntax.ast.types;

import uk.ac.kent.cs.kmf.patterns.VisitActions;
import uk.ac.kent.cs.kmf.patterns.Visitable;
import uk.ac.kent.cs.kmf.patterns.VisitorImpl;
import uk.ac.kent.cs.kmf.util.ILog;

public class typesVisitorImpl
  extends VisitorImpl
  implements typesVisitor
{
  public typesVisitorImpl(ILog log) {
    super(log);
  }

  public java.lang.Object visit(Visitable host, java.lang.Object data) {
    java.lang.Object o = null;  	
    return o;
  }
	
  public java.lang.Object visit(OrderedSetTypeAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.types.OrderedSetTypeAS",host,data, this);
    //--- Properties ---
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.types.OrderedSetTypeAS.isMarkedPre", "java.lang.Boolean", java.lang.Boolean.class, null, host.getIsMarkedPre(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.types.OrderedSetTypeAS.symbol", "java.lang.Object", java.lang.Object.class, null, host.getSymbol(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.types.OrderedSetTypeAS.ifExpAS", "uk.ac.kent.cs.ocl20.syntax.ast.expressions.IfExpAS", org.oslo.ocl20.syntax.ast.expressions.IfExpAS.class, null, host.getIfExpAS(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.types.OrderedSetTypeAS.elementType", "TypeAS", TypeAS.class, null, host.getElementType(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.types.OrderedSetTypeAS.parent", "uk.ac.kent.cs.ocl20.syntax.ast.expressions.OclExpressionAS", org.oslo.ocl20.syntax.ast.expressions.OclExpressionAS.class, null, host.getParent(),data, this), node, this);

    return node;
  }

  public java.lang.Object visit(ClassifierAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.types.ClassifierAS",host,data, this);
    //--- Properties ---
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.types.ClassifierAS.isMarkedPre", "java.lang.Boolean", java.lang.Boolean.class, null, host.getIsMarkedPre(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.types.ClassifierAS.pathName", "java.util.List", java.util.List.class, null, host.getPathName(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.types.ClassifierAS.symbol", "java.lang.Object", java.lang.Object.class, null, host.getSymbol(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.types.ClassifierAS.ifExpAS", "uk.ac.kent.cs.ocl20.syntax.ast.expressions.IfExpAS", org.oslo.ocl20.syntax.ast.expressions.IfExpAS.class, null, host.getIfExpAS(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.types.ClassifierAS.parent", "uk.ac.kent.cs.ocl20.syntax.ast.expressions.OclExpressionAS", org.oslo.ocl20.syntax.ast.expressions.OclExpressionAS.class, null, host.getParent(),data, this), node, this);

    return node;
  }

  public java.lang.Object visit(CollectionTypeAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.types.CollectionTypeAS",host,data, this);
    //--- Properties ---
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.types.CollectionTypeAS.isMarkedPre", "java.lang.Boolean", java.lang.Boolean.class, null, host.getIsMarkedPre(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.types.CollectionTypeAS.symbol", "java.lang.Object", java.lang.Object.class, null, host.getSymbol(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.types.CollectionTypeAS.ifExpAS", "uk.ac.kent.cs.ocl20.syntax.ast.expressions.IfExpAS", org.oslo.ocl20.syntax.ast.expressions.IfExpAS.class, null, host.getIfExpAS(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.types.CollectionTypeAS.elementType", "TypeAS", TypeAS.class, null, host.getElementType(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.types.CollectionTypeAS.parent", "uk.ac.kent.cs.ocl20.syntax.ast.expressions.OclExpressionAS", org.oslo.ocl20.syntax.ast.expressions.OclExpressionAS.class, null, host.getParent(),data, this), node, this);

    return node;
  }

  public java.lang.Object visit(TupleTypeAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.types.TupleTypeAS",host,data, this);
    //--- Properties ---
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.types.TupleTypeAS.isMarkedPre", "java.lang.Boolean", java.lang.Boolean.class, null, host.getIsMarkedPre(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.types.TupleTypeAS.symbol", "java.lang.Object", java.lang.Object.class, null, host.getSymbol(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.types.TupleTypeAS.ifExpAS", "uk.ac.kent.cs.ocl20.syntax.ast.expressions.IfExpAS", org.oslo.ocl20.syntax.ast.expressions.IfExpAS.class, null, host.getIfExpAS(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.types.TupleTypeAS.parent", "uk.ac.kent.cs.ocl20.syntax.ast.expressions.OclExpressionAS", org.oslo.ocl20.syntax.ast.expressions.OclExpressionAS.class, null, host.getParent(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.types.TupleTypeAS.variableDeclarationList", "uk.ac.kent.cs.ocl20.syntax.ast.contexts.VariableDeclarationAS", org.oslo.ocl20.syntax.ast.contexts.VariableDeclarationAS.class, java.util.Set.class, host.getVariableDeclarationList(),data, this), node, this);

    return node;
  }

  public java.lang.Object visit(BagTypeAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.types.BagTypeAS",host,data, this);
    //--- Properties ---
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.types.BagTypeAS.isMarkedPre", "java.lang.Boolean", java.lang.Boolean.class, null, host.getIsMarkedPre(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.types.BagTypeAS.symbol", "java.lang.Object", java.lang.Object.class, null, host.getSymbol(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.types.BagTypeAS.ifExpAS", "uk.ac.kent.cs.ocl20.syntax.ast.expressions.IfExpAS", org.oslo.ocl20.syntax.ast.expressions.IfExpAS.class, null, host.getIfExpAS(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.types.BagTypeAS.elementType", "TypeAS", TypeAS.class, null, host.getElementType(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.types.BagTypeAS.parent", "uk.ac.kent.cs.ocl20.syntax.ast.expressions.OclExpressionAS", org.oslo.ocl20.syntax.ast.expressions.OclExpressionAS.class, null, host.getParent(),data, this), node, this);

    return node;
  }

  public java.lang.Object visit(SetTypeAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.types.SetTypeAS",host,data, this);
    //--- Properties ---
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.types.SetTypeAS.isMarkedPre", "java.lang.Boolean", java.lang.Boolean.class, null, host.getIsMarkedPre(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.types.SetTypeAS.symbol", "java.lang.Object", java.lang.Object.class, null, host.getSymbol(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.types.SetTypeAS.ifExpAS", "uk.ac.kent.cs.ocl20.syntax.ast.expressions.IfExpAS", org.oslo.ocl20.syntax.ast.expressions.IfExpAS.class, null, host.getIfExpAS(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.types.SetTypeAS.elementType", "TypeAS", TypeAS.class, null, host.getElementType(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.types.SetTypeAS.parent", "uk.ac.kent.cs.ocl20.syntax.ast.expressions.OclExpressionAS", org.oslo.ocl20.syntax.ast.expressions.OclExpressionAS.class, null, host.getParent(),data, this), node, this);

    return node;
  }

  public java.lang.Object visit(SequenceTypeAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.types.SequenceTypeAS",host,data, this);
    //--- Properties ---
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.types.SequenceTypeAS.isMarkedPre", "java.lang.Boolean", java.lang.Boolean.class, null, host.getIsMarkedPre(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.types.SequenceTypeAS.symbol", "java.lang.Object", java.lang.Object.class, null, host.getSymbol(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.types.SequenceTypeAS.ifExpAS", "uk.ac.kent.cs.ocl20.syntax.ast.expressions.IfExpAS", org.oslo.ocl20.syntax.ast.expressions.IfExpAS.class, null, host.getIfExpAS(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.types.SequenceTypeAS.elementType", "TypeAS", TypeAS.class, null, host.getElementType(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.types.SequenceTypeAS.parent", "uk.ac.kent.cs.ocl20.syntax.ast.expressions.OclExpressionAS", org.oslo.ocl20.syntax.ast.expressions.OclExpressionAS.class, null, host.getParent(),data, this), node, this);

    return node;
  }

}
