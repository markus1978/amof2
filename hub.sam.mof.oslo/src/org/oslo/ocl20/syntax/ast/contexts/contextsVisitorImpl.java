package org.oslo.ocl20.syntax.ast.contexts;

import uk.ac.kent.cs.kmf.patterns.VisitActions;
import uk.ac.kent.cs.kmf.patterns.Visitable;
import uk.ac.kent.cs.kmf.patterns.VisitorImpl;
import uk.ac.kent.cs.kmf.util.ILog;

public class contextsVisitorImpl
  extends VisitorImpl
  implements contextsVisitor
{
  public contextsVisitorImpl(ILog log) {
    super(log);
  }

  public java.lang.Object visit(Visitable host, java.lang.Object data) {
    java.lang.Object o = null;  	
    return o;
  }
	
  public java.lang.Object visit(ConstraintKindAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.ConstraintKindAS",host,data, this);
    //--- Properties ---

    return node;
  }

  public java.lang.Object visit(OperationAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.OperationAS",host,data, this);
    //--- Properties ---
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.OperationAS.name", "java.lang.String", java.lang.String.class, null, host.getName(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.OperationAS.pathName", "java.util.List", java.util.List.class, null, host.getPathName(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.OperationAS.parameters", "VariableDeclarationAS", VariableDeclarationAS.class, java.util.Set.class, host.getParameters(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.OperationAS.type", "uk.ac.kent.cs.ocl20.syntax.ast.types.TypeAS", org.oslo.ocl20.syntax.ast.types.TypeAS.class, null, host.getType(),data, this), node, this);

    return node;
  }

  public java.lang.Object visit(ConstraintAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.ConstraintAS",host,data, this);
    //--- Properties ---
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.ConstraintAS.kind", "ConstraintKindAS", ConstraintKindAS.class, null, host.getKind(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.ConstraintAS.name", "java.lang.String", java.lang.String.class, null, host.getName(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.ConstraintAS.symbol", "java.lang.Object", java.lang.Object.class, null, host.getSymbol(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.ConstraintAS.contextDeclarationAS", "ContextDeclarationAS", ContextDeclarationAS.class, null, host.getContextDeclarationAS(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.ConstraintAS.bodyExpression", "uk.ac.kent.cs.ocl20.syntax.ast.expressions.OclExpressionAS", org.oslo.ocl20.syntax.ast.expressions.OclExpressionAS.class, null, host.getBodyExpression(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.ConstraintAS.defOperation", "OperationAS", OperationAS.class, null, host.getDefOperation(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.ConstraintAS.defVariable", "VariableDeclarationAS", VariableDeclarationAS.class, null, host.getDefVariable(),data, this), node, this);

    return node;
  }

  public java.lang.Object visit(VariableDeclarationAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.VariableDeclarationAS",host,data, this);
    //--- Properties ---
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.VariableDeclarationAS.name", "java.lang.String", java.lang.String.class, null, host.getName(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.VariableDeclarationAS.initExp", "uk.ac.kent.cs.ocl20.syntax.ast.expressions.OclExpressionAS", org.oslo.ocl20.syntax.ast.expressions.OclExpressionAS.class, null, host.getInitExp(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.VariableDeclarationAS.type", "uk.ac.kent.cs.ocl20.syntax.ast.types.TypeAS", org.oslo.ocl20.syntax.ast.types.TypeAS.class, null, host.getType(),data, this), node, this);

    return node;
  }

  public java.lang.Object visit(PropertyContextDeclAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.PropertyContextDeclAS",host,data, this);
    //--- Properties ---
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.PropertyContextDeclAS.name", "java.lang.String", java.lang.String.class, null, host.getName(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.PropertyContextDeclAS.pathName", "java.util.List", java.util.List.class, null, host.getPathName(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.PropertyContextDeclAS.symbol", "java.lang.Object", java.lang.Object.class, null, host.getSymbol(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.PropertyContextDeclAS.packageDeclarationAS", "PackageDeclarationAS", PackageDeclarationAS.class, null, host.getPackageDeclarationAS(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.PropertyContextDeclAS.constraints", "ConstraintAS", ConstraintAS.class, java.util.Set.class, host.getConstraints(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.PropertyContextDeclAS.type", "uk.ac.kent.cs.ocl20.syntax.ast.types.TypeAS", org.oslo.ocl20.syntax.ast.types.TypeAS.class, null, host.getType(),data, this), node, this);

    return node;
  }

  public java.lang.Object visit(PackageDeclarationAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.PackageDeclarationAS",host,data, this);
    //--- Properties ---
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.PackageDeclarationAS.pathName", "java.util.List", java.util.List.class, null, host.getPathName(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.PackageDeclarationAS.symbol", "java.lang.Object", java.lang.Object.class, null, host.getSymbol(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.PackageDeclarationAS.contextDecls", "ContextDeclarationAS", ContextDeclarationAS.class, java.util.Set.class, host.getContextDecls(),data, this), node, this);

    return node;
  }

  public java.lang.Object visit(OperationContextDeclAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.OperationContextDeclAS",host,data, this);
    //--- Properties ---
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.OperationContextDeclAS.symbol", "java.lang.Object", java.lang.Object.class, null, host.getSymbol(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.OperationContextDeclAS.packageDeclarationAS", "PackageDeclarationAS", PackageDeclarationAS.class, null, host.getPackageDeclarationAS(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.OperationContextDeclAS.constraints", "ConstraintAS", ConstraintAS.class, java.util.Set.class, host.getConstraints(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.OperationContextDeclAS.operation", "OperationAS", OperationAS.class, null, host.getOperation(),data, this), node, this);

    return node;
  }

  public java.lang.Object visit(ClassifierContextDeclAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.ClassifierContextDeclAS",host,data, this);
    //--- Properties ---
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.ClassifierContextDeclAS.pathName", "java.util.List", java.util.List.class, null, host.getPathName(),data, this), node, this);
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.ClassifierContextDeclAS.symbol", "java.lang.Object", java.lang.Object.class, null, host.getSymbol(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.ClassifierContextDeclAS.packageDeclarationAS", "PackageDeclarationAS", PackageDeclarationAS.class, null, host.getPackageDeclarationAS(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.ClassifierContextDeclAS.constraints", "ConstraintAS", ConstraintAS.class, java.util.Set.class, host.getConstraints(),data, this), node, this);

    return node;
  }

  public java.lang.Object visit(ContextDeclarationAS host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.ContextDeclarationAS",host,data, this);
    //--- Properties ---
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.ContextDeclarationAS.symbol", "java.lang.Object", java.lang.Object.class, null, host.getSymbol(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.ContextDeclarationAS.packageDeclarationAS", "PackageDeclarationAS", PackageDeclarationAS.class, null, host.getPackageDeclarationAS(),data, this), node, this);
    actions.linkAssociationEnd(actions.associationEndAction("uk.ac.kent.cs.ocl20.syntax.ast.contexts.ContextDeclarationAS.constraints", "ConstraintAS", ConstraintAS.class, java.util.Set.class, host.getConstraints(),data, this), node, this);

    return node;
  }

}
