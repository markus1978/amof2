package org.oslo.ocl20.syntax.ast.types;

import uk.ac.kent.cs.kmf.patterns.RepositoryImpl;
import uk.ac.kent.cs.kmf.util.ILog;

public class typesRepository extends RepositoryImpl {

  public typesRepository(ILog log) {
    super(log);
	super.setFactory( new typesFactory(log, this) );
    super.registerElementType("SequenceTypeAS");
    super.registerElementType("SetTypeAS");
    super.registerElementType("BagTypeAS");
    super.registerElementType("TupleTypeAS");
    super.registerElementType("CollectionTypeAS");
    super.registerElementType("ClassifierAS");
    super.registerElementType("TypeAS");
    super.registerElementType("OrderedSetTypeAS");
  }

  public void saveXMI(java.lang.String fileName) {
    super.saveXMI(fileName, new typesVisitorImpl(_log));
  }
  
  public java.lang.String toString() { return "types";}
}
