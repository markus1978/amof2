
package org.oslo.ocl20.syntax.ast;

import uk.ac.kent.cs.kmf.patterns.FactoryImpl;
import uk.ac.kent.cs.kmf.patterns.Repository;
import uk.ac.kent.cs.kmf.util.ILog;

public class astFactory
extends FactoryImpl
{
  public astFactory(ILog log) { this(log, null); }
  
  Repository rep;
  public astFactory(ILog log, Repository rep) {
    super(log);
    this.rep = rep;
    
    contexts = new org.oslo.ocl20.syntax.ast.contexts.contextsFactory(log);
    expressions = new org.oslo.ocl20.syntax.ast.expressions.expressionsFactory(log);
    types = new org.oslo.ocl20.syntax.ast.types.typesFactory(log);

  }
  public org.oslo.ocl20.syntax.ast.contexts.contextsFactory contexts = null;
  public org.oslo.ocl20.syntax.ast.expressions.expressionsFactory expressions = null;
  public org.oslo.ocl20.syntax.ast.types.typesFactory types = null;

  public SyntaxElement createSyntaxElement() {
     return new SyntaxElementImpl();
   }
   public void destroySyntaxElement(SyntaxElement object) {
    // Attributes
    object.setSymbol(null);
    // Non Navigable AssociationEnds
    // Standard AssociationEnds
    // Aggregate Parts
    // Composite Parts

   }
   

}
