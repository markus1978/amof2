package org.oslo.ocl20.syntax.ast;

import uk.ac.kent.cs.kmf.patterns.VisitActions;
import uk.ac.kent.cs.kmf.patterns.Visitable;
import uk.ac.kent.cs.kmf.patterns.VisitorImpl;
import uk.ac.kent.cs.kmf.util.ILog;

public class astVisitorImpl
  extends VisitorImpl
  implements astVisitor
{
  public astVisitorImpl(ILog log) {
    super(log);
    super.register("contexts",new org.oslo.ocl20.syntax.ast.contexts.contextsVisitorImpl(log));
    super.register("expressions",new org.oslo.ocl20.syntax.ast.expressions.expressionsVisitorImpl(log));
    super.register("types",new org.oslo.ocl20.syntax.ast.types.typesVisitorImpl(log));
  }

  public java.lang.Object visit(Visitable host, java.lang.Object data) {
    java.lang.Object o = null;  	
    if (o==null && host instanceof org.oslo.ocl20.syntax.ast.contexts.contextsVisitable)
      o = host.accept(subVisitor("contexts"), data);
    if (o==null && host instanceof org.oslo.ocl20.syntax.ast.expressions.expressionsVisitable)
      o = host.accept(subVisitor("expressions"), data);
    if (o==null && host instanceof org.oslo.ocl20.syntax.ast.types.typesVisitable)
      o = host.accept(subVisitor("types"), data);
    return o;
  }
	
  public java.lang.Object visit(SyntaxElement host, java.lang.Object data)  {
	VisitActions actions = (VisitActions)((java.util.Map)data).get("actions");
    java.lang.Object node = actions.nodeAction("uk.ac.kent.cs.ocl20.syntax.ast.SyntaxElement",host,data, this);
    //--- Properties ---
    actions.linkAttribute(actions.attributeAction("uk.ac.kent.cs.ocl20.syntax.ast.SyntaxElement.symbol", "java.lang.Object", java.lang.Object.class, null, host.getSymbol(),data, this), node, this);

    return node;
  }

}
