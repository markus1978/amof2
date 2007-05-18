package org.oslo.ocl20.syntax.ast.expressions;

import uk.ac.kent.cs.kmf.patterns.VisitActions;
import uk.ac.kent.cs.kmf.patterns.Visitable;
import uk.ac.kent.cs.kmf.patterns.VisitorImpl;
import uk.ac.kent.cs.kmf.util.ILog;

public class expressionsVisitorImpl
  extends VisitorImpl
  implements expressionsVisitor
{
  public expressionsVisitorImpl(ILog log) {
    super(log);
  }

  public java.lang.Object visit(Visitable host, java.lang.Object data) {
    java.lang.Object o = null;  	
    return o;
  }
	
  public java.lang.Object visit(OclMessageKindAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.OclMessageKindAS",host,data, this);
    //--- Properties ---

    return node;
  }

  public java.lang.Object visit(CollectionKindAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.CollectionKindAS",host,data, this);
    //--- Properties ---

    return node;
  }

  public java.lang.Object visit(IteratorExpAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.IteratorExpAS",host,data, this);
    //--- Properties ---
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.IteratorExpAS.isMarkedPre", "java.lang.Boolean", java.lang.Boolean.class, null, host.getIsMarkedPre(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.IteratorExpAS.name", "java.lang.String", java.lang.String.class, null, host.getName(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.IteratorExpAS.symbol", "java.lang.Object", java.lang.Object.class, null, host.getSymbol(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.IteratorExpAS.ifExpAS", "IfExpAS", IfExpAS.class, null, host.getIfExpAS(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.IteratorExpAS.body", "OclExpressionAS", OclExpressionAS.class, null, host.getBody(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.IteratorExpAS.iterator", "uk.ac.kent.cs.ocl20.syntax.ast.contexts.VariableDeclarationAS", org.oslo.ocl20.syntax.ast.contexts.VariableDeclarationAS.class, null, host.getIterator(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.IteratorExpAS.parent", "OclExpressionAS", OclExpressionAS.class, null, host.getParent(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.IteratorExpAS.result", "uk.ac.kent.cs.ocl20.syntax.ast.contexts.VariableDeclarationAS", org.oslo.ocl20.syntax.ast.contexts.VariableDeclarationAS.class, null, host.getResult(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.IteratorExpAS.source", "OclExpressionAS", OclExpressionAS.class, null, host.getSource(),data, this), node, this);

    return node;
  }

  public java.lang.Object visit(CollectionItemAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.CollectionItemAS",host,data, this);
    //--- Properties ---
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.CollectionItemAS.item", "OclExpressionAS", OclExpressionAS.class, null, host.getItem(),data, this), node, this);

    return node;
  }

  public java.lang.Object visit(CollectionRangeAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.CollectionRangeAS",host,data, this);
    //--- Properties ---
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.CollectionRangeAS.first", "OclExpressionAS", OclExpressionAS.class, null, host.getFirst(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.CollectionRangeAS.last", "OclExpressionAS", OclExpressionAS.class, null, host.getLast(),data, this), node, this);

    return node;
  }

  public java.lang.Object visit(PathNameExpAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.PathNameExpAS",host,data, this);
    //--- Properties ---
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.PathNameExpAS.isMarkedPre", "java.lang.Boolean", java.lang.Boolean.class, null, host.getIsMarkedPre(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.PathNameExpAS.pathName", "java.util.List", java.util.List.class, null, host.getPathName(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.PathNameExpAS.symbol", "java.lang.Object", java.lang.Object.class, null, host.getSymbol(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.PathNameExpAS.ifExpAS", "IfExpAS", IfExpAS.class, null, host.getIfExpAS(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.PathNameExpAS.parent", "OclExpressionAS", OclExpressionAS.class, null, host.getParent(),data, this), node, this);

    return node;
  }

  public java.lang.Object visit(EnumLiteralExpAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.EnumLiteralExpAS",host,data, this);
    //--- Properties ---
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.EnumLiteralExpAS.isMarkedPre", "java.lang.Boolean", java.lang.Boolean.class, null, host.getIsMarkedPre(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.EnumLiteralExpAS.pathName", "java.util.List", java.util.List.class, null, host.getPathName(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.EnumLiteralExpAS.symbol", "java.lang.Object", java.lang.Object.class, null, host.getSymbol(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.EnumLiteralExpAS.ifExpAS", "IfExpAS", IfExpAS.class, null, host.getIfExpAS(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.EnumLiteralExpAS.parent", "OclExpressionAS", OclExpressionAS.class, null, host.getParent(),data, this), node, this);

    return node;
  }

  public java.lang.Object visit(OclMessageExpAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.OclMessageExpAS",host,data, this);
    //--- Properties ---
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.OclMessageExpAS.isMarkedPre", "java.lang.Boolean", java.lang.Boolean.class, null, host.getIsMarkedPre(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.OclMessageExpAS.kind", "OclMessageKindAS", OclMessageKindAS.class, null, host.getKind(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.OclMessageExpAS.name", "java.lang.String", java.lang.String.class, null, host.getName(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.OclMessageExpAS.symbol", "java.lang.Object", java.lang.Object.class, null, host.getSymbol(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.OclMessageExpAS.ifExpAS", "IfExpAS", IfExpAS.class, null, host.getIfExpAS(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.OclMessageExpAS.arguments", "OclMessageArgAS", OclMessageArgAS.class, java.util.Set.class, host.getArguments(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.OclMessageExpAS.parent", "OclExpressionAS", OclExpressionAS.class, null, host.getParent(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.OclMessageExpAS.target", "OclExpressionAS", OclExpressionAS.class, null, host.getTarget(),data, this), node, this);

    return node;
  }

  public java.lang.Object visit(LetExpAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.LetExpAS",host,data, this);
    //--- Properties ---
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.LetExpAS.isMarkedPre", "java.lang.Boolean", java.lang.Boolean.class, null, host.getIsMarkedPre(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.LetExpAS.symbol", "java.lang.Object", java.lang.Object.class, null, host.getSymbol(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.LetExpAS.ifExpAS", "IfExpAS", IfExpAS.class, null, host.getIfExpAS(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.LetExpAS.in", "OclExpressionAS", OclExpressionAS.class, null, host.getIn(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.LetExpAS.parent", "OclExpressionAS", OclExpressionAS.class, null, host.getParent(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.LetExpAS.variables", "uk.ac.kent.cs.ocl20.syntax.ast.contexts.VariableDeclarationAS", org.oslo.ocl20.syntax.ast.contexts.VariableDeclarationAS.class, java.util.Set.class, host.getVariables(),data, this), node, this);

    return node;
  }

  public java.lang.Object visit(IterateExpAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.IterateExpAS",host,data, this);
    //--- Properties ---
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.IterateExpAS.isMarkedPre", "java.lang.Boolean", java.lang.Boolean.class, null, host.getIsMarkedPre(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.IterateExpAS.name", "java.lang.String", java.lang.String.class, null, host.getName(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.IterateExpAS.symbol", "java.lang.Object", java.lang.Object.class, null, host.getSymbol(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.IterateExpAS.ifExpAS", "IfExpAS", IfExpAS.class, null, host.getIfExpAS(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.IterateExpAS.body", "OclExpressionAS", OclExpressionAS.class, null, host.getBody(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.IterateExpAS.iterator", "uk.ac.kent.cs.ocl20.syntax.ast.contexts.VariableDeclarationAS", org.oslo.ocl20.syntax.ast.contexts.VariableDeclarationAS.class, null, host.getIterator(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.IterateExpAS.parent", "OclExpressionAS", OclExpressionAS.class, null, host.getParent(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.IterateExpAS.result", "uk.ac.kent.cs.ocl20.syntax.ast.contexts.VariableDeclarationAS", org.oslo.ocl20.syntax.ast.contexts.VariableDeclarationAS.class, null, host.getResult(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.IterateExpAS.source", "OclExpressionAS", OclExpressionAS.class, null, host.getSource(),data, this), node, this);

    return node;
  }

  public java.lang.Object visit(OperationCallExpAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.OperationCallExpAS",host,data, this);
    //--- Properties ---
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.OperationCallExpAS.isMarkedPre", "java.lang.Boolean", java.lang.Boolean.class, null, host.getIsMarkedPre(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.OperationCallExpAS.symbol", "java.lang.Object", java.lang.Object.class, null, host.getSymbol(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.OperationCallExpAS.ifExpAS", "IfExpAS", IfExpAS.class, null, host.getIfExpAS(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.OperationCallExpAS.arguments", "OclExpressionAS", OclExpressionAS.class, java.util.Set.class, host.getArguments(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.OperationCallExpAS.parent", "OclExpressionAS", OclExpressionAS.class, null, host.getParent(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.OperationCallExpAS.source", "OclExpressionAS", OclExpressionAS.class, null, host.getSource(),data, this), node, this);

    return node;
  }

  public java.lang.Object visit(AssociationCallExpAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.AssociationCallExpAS",host,data, this);
    //--- Properties ---
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.AssociationCallExpAS.isMarkedPre", "java.lang.Boolean", java.lang.Boolean.class, null, host.getIsMarkedPre(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.AssociationCallExpAS.symbol", "java.lang.Object", java.lang.Object.class, null, host.getSymbol(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.AssociationCallExpAS.ifExpAS", "IfExpAS", IfExpAS.class, null, host.getIfExpAS(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.AssociationCallExpAS.arguments", "OclExpressionAS", OclExpressionAS.class, java.util.Set.class, host.getArguments(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.AssociationCallExpAS.parent", "OclExpressionAS", OclExpressionAS.class, null, host.getParent(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.AssociationCallExpAS.source", "OclExpressionAS", OclExpressionAS.class, null, host.getSource(),data, this), node, this);

    return node;
  }

  public java.lang.Object visit(ArrowSelectionExpAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.ArrowSelectionExpAS",host,data, this);
    //--- Properties ---
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.ArrowSelectionExpAS.isMarkedPre", "java.lang.Boolean", java.lang.Boolean.class, null, host.getIsMarkedPre(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.ArrowSelectionExpAS.name", "java.lang.String", java.lang.String.class, null, host.getName(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.ArrowSelectionExpAS.symbol", "java.lang.Object", java.lang.Object.class, null, host.getSymbol(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.ArrowSelectionExpAS.ifExpAS", "IfExpAS", IfExpAS.class, null, host.getIfExpAS(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.ArrowSelectionExpAS.parent", "OclExpressionAS", OclExpressionAS.class, null, host.getParent(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.ArrowSelectionExpAS.source", "OclExpressionAS", OclExpressionAS.class, null, host.getSource(),data, this), node, this);

    return node;
  }

  public java.lang.Object visit(DotSelectionExpAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.DotSelectionExpAS",host,data, this);
    //--- Properties ---
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.DotSelectionExpAS.isMarkedPre", "java.lang.Boolean", java.lang.Boolean.class, null, host.getIsMarkedPre(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.DotSelectionExpAS.name", "java.lang.String", java.lang.String.class, null, host.getName(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.DotSelectionExpAS.symbol", "java.lang.Object", java.lang.Object.class, null, host.getSymbol(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.DotSelectionExpAS.ifExpAS", "IfExpAS", IfExpAS.class, null, host.getIfExpAS(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.DotSelectionExpAS.parent", "OclExpressionAS", OclExpressionAS.class, null, host.getParent(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.DotSelectionExpAS.source", "OclExpressionAS", OclExpressionAS.class, null, host.getSource(),data, this), node, this);

    return node;
  }

  public java.lang.Object visit(OclMessageArgAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.OclMessageArgAS",host,data, this);
    //--- Properties ---
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.OclMessageArgAS.expression", "OclExpressionAS", OclExpressionAS.class, null, host.getExpression(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.OclMessageArgAS.type", "uk.ac.kent.cs.ocl20.syntax.ast.types.TypeAS", org.oslo.ocl20.syntax.ast.types.TypeAS.class, null, host.getType(),data, this), node, this);

    return node;
  }

  public java.lang.Object visit(IfExpAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.IfExpAS",host,data, this);
    //--- Properties ---
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.IfExpAS.isMarkedPre", "java.lang.Boolean", java.lang.Boolean.class, null, host.getIsMarkedPre(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.IfExpAS.symbol", "java.lang.Object", java.lang.Object.class, null, host.getSymbol(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.IfExpAS.ifExpAS", "IfExpAS", IfExpAS.class, null, host.getIfExpAS(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.IfExpAS.condition", "OclExpressionAS", OclExpressionAS.class, null, host.getCondition(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.IfExpAS.elseExpression", "OclExpressionAS", OclExpressionAS.class, null, host.getElseExpression(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.IfExpAS.parent", "OclExpressionAS", OclExpressionAS.class, null, host.getParent(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.IfExpAS.thenExpression", "OclExpressionAS", OclExpressionAS.class, null, host.getThenExpression(),data, this), node, this);

    return node;
  }

  public java.lang.Object visit(NotExpAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.NotExpAS",host,data, this);
    //--- Properties ---
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.NotExpAS.isMarkedPre", "java.lang.Boolean", java.lang.Boolean.class, null, host.getIsMarkedPre(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.NotExpAS.symbol", "java.lang.Object", java.lang.Object.class, null, host.getSymbol(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.NotExpAS.ifExpAS", "IfExpAS", IfExpAS.class, null, host.getIfExpAS(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.NotExpAS.leftOperand", "OclExpressionAS", OclExpressionAS.class, null, host.getLeftOperand(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.NotExpAS.parent", "OclExpressionAS", OclExpressionAS.class, null, host.getParent(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.NotExpAS.rightOperand", "OclExpressionAS", OclExpressionAS.class, null, host.getRightOperand(),data, this), node, this);

    return node;
  }

  public java.lang.Object visit(AndExpAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.AndExpAS",host,data, this);
    //--- Properties ---
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.AndExpAS.isMarkedPre", "java.lang.Boolean", java.lang.Boolean.class, null, host.getIsMarkedPre(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.AndExpAS.symbol", "java.lang.Object", java.lang.Object.class, null, host.getSymbol(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.AndExpAS.ifExpAS", "IfExpAS", IfExpAS.class, null, host.getIfExpAS(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.AndExpAS.leftOperand", "OclExpressionAS", OclExpressionAS.class, null, host.getLeftOperand(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.AndExpAS.parent", "OclExpressionAS", OclExpressionAS.class, null, host.getParent(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.AndExpAS.rightOperand", "OclExpressionAS", OclExpressionAS.class, null, host.getRightOperand(),data, this), node, this);

    return node;
  }

  public java.lang.Object visit(OrExpAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.OrExpAS",host,data, this);
    //--- Properties ---
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.OrExpAS.isMarkedPre", "java.lang.Boolean", java.lang.Boolean.class, null, host.getIsMarkedPre(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.OrExpAS.symbol", "java.lang.Object", java.lang.Object.class, null, host.getSymbol(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.OrExpAS.ifExpAS", "IfExpAS", IfExpAS.class, null, host.getIfExpAS(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.OrExpAS.leftOperand", "OclExpressionAS", OclExpressionAS.class, null, host.getLeftOperand(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.OrExpAS.parent", "OclExpressionAS", OclExpressionAS.class, null, host.getParent(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.OrExpAS.rightOperand", "OclExpressionAS", OclExpressionAS.class, null, host.getRightOperand(),data, this), node, this);

    return node;
  }

  public java.lang.Object visit(XorExpAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.XorExpAS",host,data, this);
    //--- Properties ---
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.XorExpAS.isMarkedPre", "java.lang.Boolean", java.lang.Boolean.class, null, host.getIsMarkedPre(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.XorExpAS.symbol", "java.lang.Object", java.lang.Object.class, null, host.getSymbol(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.XorExpAS.ifExpAS", "IfExpAS", IfExpAS.class, null, host.getIfExpAS(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.XorExpAS.leftOperand", "OclExpressionAS", OclExpressionAS.class, null, host.getLeftOperand(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.XorExpAS.parent", "OclExpressionAS", OclExpressionAS.class, null, host.getParent(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.XorExpAS.rightOperand", "OclExpressionAS", OclExpressionAS.class, null, host.getRightOperand(),data, this), node, this);

    return node;
  }

  public java.lang.Object visit(ImpliesExpAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.ImpliesExpAS",host,data, this);
    //--- Properties ---
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.ImpliesExpAS.isMarkedPre", "java.lang.Boolean", java.lang.Boolean.class, null, host.getIsMarkedPre(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.ImpliesExpAS.symbol", "java.lang.Object", java.lang.Object.class, null, host.getSymbol(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.ImpliesExpAS.ifExpAS", "IfExpAS", IfExpAS.class, null, host.getIfExpAS(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.ImpliesExpAS.leftOperand", "OclExpressionAS", OclExpressionAS.class, null, host.getLeftOperand(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.ImpliesExpAS.parent", "OclExpressionAS", OclExpressionAS.class, null, host.getParent(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.ImpliesExpAS.rightOperand", "OclExpressionAS", OclExpressionAS.class, null, host.getRightOperand(),data, this), node, this);

    return node;
  }

  public java.lang.Object visit(OclExpressionAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.OclExpressionAS",host,data, this);
    //--- Properties ---
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.OclExpressionAS.isMarkedPre", "java.lang.Boolean", java.lang.Boolean.class, null, host.getIsMarkedPre(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.OclExpressionAS.symbol", "java.lang.Object", java.lang.Object.class, null, host.getSymbol(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.OclExpressionAS.ifExpAS", "IfExpAS", IfExpAS.class, null, host.getIfExpAS(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.OclExpressionAS.parent", "OclExpressionAS", OclExpressionAS.class, null, host.getParent(),data, this), node, this);

    return node;
  }

  public java.lang.Object visit(CollectionLiteralExpAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.CollectionLiteralExpAS",host,data, this);
    //--- Properties ---
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.CollectionLiteralExpAS.isMarkedPre", "java.lang.Boolean", java.lang.Boolean.class, null, host.getIsMarkedPre(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.CollectionLiteralExpAS.kind", "CollectionKindAS", CollectionKindAS.class, null, host.getKind(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.CollectionLiteralExpAS.symbol", "java.lang.Object", java.lang.Object.class, null, host.getSymbol(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.CollectionLiteralExpAS.ifExpAS", "IfExpAS", IfExpAS.class, null, host.getIfExpAS(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.CollectionLiteralExpAS.collectionParts", "CollectionLiteralPartAS", CollectionLiteralPartAS.class, java.util.Set.class, host.getCollectionParts(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.CollectionLiteralExpAS.parent", "OclExpressionAS", OclExpressionAS.class, null, host.getParent(),data, this), node, this);

    return node;
  }

  public java.lang.Object visit(TupleLiteralExpAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.TupleLiteralExpAS",host,data, this);
    //--- Properties ---
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.TupleLiteralExpAS.isMarkedPre", "java.lang.Boolean", java.lang.Boolean.class, null, host.getIsMarkedPre(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.TupleLiteralExpAS.symbol", "java.lang.Object", java.lang.Object.class, null, host.getSymbol(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.TupleLiteralExpAS.ifExpAS", "IfExpAS", IfExpAS.class, null, host.getIfExpAS(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.TupleLiteralExpAS.parent", "OclExpressionAS", OclExpressionAS.class, null, host.getParent(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.TupleLiteralExpAS.tupleParts", "uk.ac.kent.cs.ocl20.syntax.ast.contexts.VariableDeclarationAS", org.oslo.ocl20.syntax.ast.contexts.VariableDeclarationAS.class, java.util.Set.class, host.getTupleParts(),data, this), node, this);

    return node;
  }

  public java.lang.Object visit(IntegerLiteralExpAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.IntegerLiteralExpAS",host,data, this);
    //--- Properties ---
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.IntegerLiteralExpAS.isMarkedPre", "java.lang.Boolean", java.lang.Boolean.class, null, host.getIsMarkedPre(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.IntegerLiteralExpAS.symbol", "java.lang.Object", java.lang.Object.class, null, host.getSymbol(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.IntegerLiteralExpAS.value", "java.lang.Integer", java.lang.Integer.class, null, host.getValue(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.IntegerLiteralExpAS.ifExpAS", "IfExpAS", IfExpAS.class, null, host.getIfExpAS(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.IntegerLiteralExpAS.parent", "OclExpressionAS", OclExpressionAS.class, null, host.getParent(),data, this), node, this);

    return node;
  }

  public java.lang.Object visit(RealLiteralExpAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.RealLiteralExpAS",host,data, this);
    //--- Properties ---
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.RealLiteralExpAS.isMarkedPre", "java.lang.Boolean", java.lang.Boolean.class, null, host.getIsMarkedPre(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.RealLiteralExpAS.symbol", "java.lang.Object", java.lang.Object.class, null, host.getSymbol(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.RealLiteralExpAS.value", "java.lang.Double", java.lang.Double.class, null, host.getValue(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.RealLiteralExpAS.ifExpAS", "IfExpAS", IfExpAS.class, null, host.getIfExpAS(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.RealLiteralExpAS.parent", "OclExpressionAS", OclExpressionAS.class, null, host.getParent(),data, this), node, this);

    return node;
  }

  public java.lang.Object visit(BooleanLiteralExpAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.BooleanLiteralExpAS",host,data, this);
    //--- Properties ---
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.BooleanLiteralExpAS.isMarkedPre", "java.lang.Boolean", java.lang.Boolean.class, null, host.getIsMarkedPre(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.BooleanLiteralExpAS.symbol", "java.lang.Object", java.lang.Object.class, null, host.getSymbol(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.BooleanLiteralExpAS.value", "java.lang.Boolean", java.lang.Boolean.class, null, host.getValue(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.BooleanLiteralExpAS.ifExpAS", "IfExpAS", IfExpAS.class, null, host.getIfExpAS(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.BooleanLiteralExpAS.parent", "OclExpressionAS", OclExpressionAS.class, null, host.getParent(),data, this), node, this);

    return node;
  }

  public java.lang.Object visit(StringLiteralExpAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.StringLiteralExpAS",host,data, this);
    //--- Properties ---
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.StringLiteralExpAS.isMarkedPre", "java.lang.Boolean", java.lang.Boolean.class, null, host.getIsMarkedPre(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.StringLiteralExpAS.symbol", "java.lang.Object", java.lang.Object.class, null, host.getSymbol(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.StringLiteralExpAS.value", "java.lang.String", java.lang.String.class, null, host.getValue(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.StringLiteralExpAS.ifExpAS", "IfExpAS", IfExpAS.class, null, host.getIfExpAS(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.expressions.StringLiteralExpAS.parent", "OclExpressionAS", OclExpressionAS.class, null, host.getParent(),data, this), node, this);

    return node;
  }

}
