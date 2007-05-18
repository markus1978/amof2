
package org.oslo.ocl20.syntax.ast.contexts;

import uk.ac.kent.cs.kmf.patterns.FactoryImpl;
import uk.ac.kent.cs.kmf.patterns.Repository;
import uk.ac.kent.cs.kmf.util.ILog;

public class contextsFactory
extends FactoryImpl
{
  public contextsFactory(ILog log) { this(log, null); }
  
  Repository rep;
  public contextsFactory(ILog log, Repository rep) {
    super(log);
    this.rep = rep;
    

  }

  public ContextDeclarationAS createContextDeclarationAS() {
     return new ContextDeclarationASImpl();
   }
   public void destroyContextDeclarationAS(ContextDeclarationAS object) {
    // Attributes
    object.setSymbol(null);
    // Standard AssociationEnds
    object.setPackageDeclarationAS(null);
    object.getConstraints().clear();
    // Aggregate Parts
    // Composite Parts

   }
   
  public ClassifierContextDeclAS createClassifierContextDeclAS() {
     return new ClassifierContextDeclASImpl();
   }
   public void destroyClassifierContextDeclAS(ClassifierContextDeclAS object) {
    // Attributes
    object.setPathName(null);
    object.setSymbol(null);
    // Standard AssociationEnds
    object.setPackageDeclarationAS(null);
    object.getConstraints().clear();
    // Aggregate Parts
    // Composite Parts

   }
   
  public OperationContextDeclAS createOperationContextDeclAS() {
     return new OperationContextDeclASImpl();
   }
   public void destroyOperationContextDeclAS(OperationContextDeclAS object) {
    // Attributes
    object.setSymbol(null);
    // Standard AssociationEnds
    object.setPackageDeclarationAS(null);
    object.getConstraints().clear();
    object.setOperation(null);
    // Aggregate Parts
    // Composite Parts

   }
   
  public PackageDeclarationAS createPackageDeclarationAS() {
     return new PackageDeclarationASImpl();
   }
   public void destroyPackageDeclarationAS(PackageDeclarationAS object) {
    // Attributes
    object.setPathName(null);
    object.setSymbol(null);
    // Standard AssociationEnds
    object.getContextDecls().clear();
    // Aggregate Parts
    // Composite Parts

   }
   
  public PropertyContextDeclAS createPropertyContextDeclAS() {
     return new PropertyContextDeclASImpl();
   }
   public void destroyPropertyContextDeclAS(PropertyContextDeclAS object) {
    // Attributes
    object.setName(null);
    object.setPathName(null);
    object.setSymbol(null);
    // Standard AssociationEnds
    object.setPackageDeclarationAS(null);
    object.getConstraints().clear();
    object.setType(null);
    // Aggregate Parts
    // Composite Parts

   }
   
  public VariableDeclarationAS createVariableDeclarationAS() {
     return new VariableDeclarationASImpl();
   }
   public void destroyVariableDeclarationAS(VariableDeclarationAS object) {
    // Attributes
    object.setName(null);
    // Standard AssociationEnds
    object.setInitExp(null);
    object.setType(null);
    // Aggregate Parts
    // Composite Parts

   }
   
  public ConstraintAS createConstraintAS() {
     return new ConstraintASImpl();
   }
   public void destroyConstraintAS(ConstraintAS object) {
    // Attributes
    object.setKind(null);
    object.setName(null);
    object.setSymbol(null);
    // Standard AssociationEnds
    object.setContextDeclarationAS(null);
    object.setBodyExpression(null);
    object.setDefOperation(null);
    object.setDefVariable(null);
    // Aggregate Parts
    // Composite Parts

   }
   
  public OperationAS createOperationAS() {
     return new OperationASImpl();
   }
   public void destroyOperationAS(OperationAS object) {
    // Attributes
    object.setName(null);
    object.setPathName(null);
    // Standard AssociationEnds
    object.getParameters().clear();
    object.setType(null);
    // Aggregate Parts
    // Composite Parts

   }
   

}
