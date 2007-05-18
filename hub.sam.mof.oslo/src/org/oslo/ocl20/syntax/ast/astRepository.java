package org.oslo.ocl20.syntax.ast;

import uk.ac.kent.cs.kmf.patterns.RepositoryImpl;
import uk.ac.kent.cs.kmf.util.ILog;

public class astRepository extends RepositoryImpl {

  public astRepository(ILog log) {
    super(log);
	super.setFactory( new astFactory(log, this) );
    super.registerSubRepository("types",new org.oslo.ocl20.syntax.ast.types.typesRepository(log));
    super.registerSubRepository("expressions",new org.oslo.ocl20.syntax.ast.expressions.expressionsRepository(log));
    super.registerSubRepository("contexts",new org.oslo.ocl20.syntax.ast.contexts.contextsRepository(log));
    super.registerElementType("SyntaxElement");
  }

  public void saveXMI(java.lang.String fileName) {
    super.saveXMI(fileName, new astVisitorImpl(_log));
  }
  
  public java.lang.String toString() { return "ast";}
}
