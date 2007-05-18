
package org.oslo.ocl20.syntax.ast.types;

import uk.ac.kent.cs.kmf.patterns.FactoryImpl;
import uk.ac.kent.cs.kmf.patterns.Repository;
import uk.ac.kent.cs.kmf.util.ILog;

public class typesFactory
extends FactoryImpl
{
  public typesFactory(ILog log) { this(log, null); }
  
  Repository rep;
  public typesFactory(ILog log, Repository rep) {
    super(log);
    this.rep = rep;
    

  }

  public SequenceTypeAS createSequenceTypeAS() {
     return new SequenceTypeASImpl();
   }
   public void destroySequenceTypeAS(SequenceTypeAS object) {
    // Attributes
    object.setIsMarkedPre(null);
    object.setSymbol(null);
    // Standard AssociationEnds
    object.setIfExpAS(null);
    object.setElementType(null);
    object.setParent(null);
    // Aggregate Parts
    // Composite Parts

   }
   
  public SetTypeAS createSetTypeAS() {
     return new SetTypeASImpl();
   }
   public void destroySetTypeAS(SetTypeAS object) {
    // Attributes
    object.setIsMarkedPre(null);
    object.setSymbol(null);
    // Standard AssociationEnds
    object.setIfExpAS(null);
    object.setElementType(null);
    object.setParent(null);
    // Aggregate Parts
    // Composite Parts

   }
   
  public BagTypeAS createBagTypeAS() {
     return new BagTypeASImpl();
   }
   public void destroyBagTypeAS(BagTypeAS object) {
    // Attributes
    object.setIsMarkedPre(null);
    object.setSymbol(null);
    // Standard AssociationEnds
    object.setIfExpAS(null);
    object.setElementType(null);
    object.setParent(null);
    // Aggregate Parts
    // Composite Parts

   }
   
  public TupleTypeAS createTupleTypeAS() {
     return new TupleTypeASImpl();
   }
   public void destroyTupleTypeAS(TupleTypeAS object) {
    // Attributes
    object.setIsMarkedPre(null);
    object.setSymbol(null);
    // Standard AssociationEnds
    object.setIfExpAS(null);
    object.setParent(null);
    object.getVariableDeclarationList().clear();
    // Aggregate Parts
    // Composite Parts

   }
   
  public CollectionTypeAS createCollectionTypeAS() {
     return new CollectionTypeASImpl();
   }
   public void destroyCollectionTypeAS(CollectionTypeAS object) {
    // Attributes
    object.setIsMarkedPre(null);
    object.setSymbol(null);
    // Standard AssociationEnds
    object.setIfExpAS(null);
    object.setElementType(null);
    object.setParent(null);
    // Aggregate Parts
    // Composite Parts

   }
   
  public ClassifierAS createClassifierAS() {
     return new ClassifierASImpl();
   }
   public void destroyClassifierAS(ClassifierAS object) {
    // Attributes
    object.setIsMarkedPre(null);
    object.setPathName(null);
    object.setSymbol(null);
    // Standard AssociationEnds
    object.setIfExpAS(null);
    object.setParent(null);
    // Aggregate Parts
    // Composite Parts

   }
   
  public TypeAS createTypeAS() {
     return new TypeASImpl();
   }
   public void destroyTypeAS(TypeAS object) {
    // Attributes
    object.setIsMarkedPre(null);
    object.setSymbol(null);
    // Standard AssociationEnds
    object.setIfExpAS(null);
    object.setParent(null);
    // Aggregate Parts
    // Composite Parts

   }
   
  public OrderedSetTypeAS createOrderedSetTypeAS() {
     return new OrderedSetTypeASImpl();
   }
   public void destroyOrderedSetTypeAS(OrderedSetTypeAS object) {
    // Attributes
    object.setIsMarkedPre(null);
    object.setSymbol(null);
    // Standard AssociationEnds
    object.setIfExpAS(null);
    object.setElementType(null);
    object.setParent(null);
    // Aggregate Parts
    // Composite Parts

   }
   

}
