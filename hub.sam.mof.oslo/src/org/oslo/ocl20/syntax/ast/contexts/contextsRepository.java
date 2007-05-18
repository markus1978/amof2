package org.oslo.ocl20.syntax.ast.contexts;

import uk.ac.kent.cs.kmf.patterns.RepositoryImpl;
import uk.ac.kent.cs.kmf.util.ILog;

public class contextsRepository extends RepositoryImpl {

  public contextsRepository(ILog log) {
    super(log);
	super.setFactory( new contextsFactory(log, this) );
    super.registerElementType("ContextDeclarationAS");
    super.registerElementType("ClassifierContextDeclAS");
    super.registerElementType("OperationContextDeclAS");
    super.registerElementType("PackageDeclarationAS");
    super.registerElementType("PropertyContextDeclAS");
    super.registerElementType("VariableDeclarationAS");
    super.registerElementType("ConstraintAS");
    super.registerElementType("OperationAS");
  }

  public void saveXMI(java.lang.String fileName) {
    super.saveXMI(fileName, new contextsVisitorImpl(_log));
  }
  
  public java.lang.String toString() { return "contexts";}
}
