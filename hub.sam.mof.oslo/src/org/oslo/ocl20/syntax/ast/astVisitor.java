
package org.oslo.ocl20.syntax.ast;

import uk.ac.kent.cs.kmf.patterns.Visitor;

public interface astVisitor
extends Visitor
{
  public java.lang.Object visit(SyntaxElement host, java.lang.Object data);

}
