
package org.oslo.ocl20.syntax.ast.expressions;

import uk.ac.kent.cs.kmf.patterns.FactoryImpl;
import uk.ac.kent.cs.kmf.patterns.Repository;
import uk.ac.kent.cs.kmf.util.ILog;

public class expressionsFactory
extends FactoryImpl
{
  public expressionsFactory(ILog log) { this(log, null); }
  
  Repository rep;
  public expressionsFactory(ILog log, Repository rep) {
    super(log);
    this.rep = rep;
    

  }

  public IfExpAS createIfExpAS() {
     return new IfExpASImpl();
   }
   public void destroyIfExpAS(IfExpAS object) {
    // Attributes
    object.setIsMarkedPre(null);
    object.setSymbol(null);
    // Standard AssociationEnds
    object.setIfExpAS(null);
    object.setCondition(null);
    object.setElseExpression(null);
    object.setParent(null);
    object.setThenExpression(null);
    // Aggregate Parts
    // Composite Parts

   }
   
  public OclMessageArgAS createOclMessageArgAS() {
     return new OclMessageArgASImpl();
   }
   public void destroyOclMessageArgAS(OclMessageArgAS object) {
    // Attributes
    // Standard AssociationEnds
    object.setExpression(null);
    object.setType(null);
    // Aggregate Parts
    // Composite Parts

   }
   
  public DotSelectionExpAS createDotSelectionExpAS() {
     return new DotSelectionExpASImpl();
   }
   public void destroyDotSelectionExpAS(DotSelectionExpAS object) {
    // Attributes
    object.setIsMarkedPre(null);
    object.setName(null);
    object.setSymbol(null);
    // Standard AssociationEnds
    object.setIfExpAS(null);
    object.setParent(null);
    object.setSource(null);
    // Aggregate Parts
    // Composite Parts

   }
   
  public ArrowSelectionExpAS createArrowSelectionExpAS() {
     return new ArrowSelectionExpASImpl();
   }
   public void destroyArrowSelectionExpAS(ArrowSelectionExpAS object) {
    // Attributes
    object.setIsMarkedPre(null);
    object.setName(null);
    object.setSymbol(null);
    // Standard AssociationEnds
    object.setIfExpAS(null);
    object.setParent(null);
    object.setSource(null);
    // Aggregate Parts
    // Composite Parts

   }
   
  public CallExpAS createCallExpAS() {
     return new CallExpASImpl();
   }
   public void destroyCallExpAS(CallExpAS object) {
    // Attributes
    object.setIsMarkedPre(null);
    object.setSymbol(null);
    // Standard AssociationEnds
    object.setIfExpAS(null);
    object.getArguments().clear();
    object.setParent(null);
    object.setSource(null);
    // Aggregate Parts
    // Composite Parts

   }
   
  public LoopExpAS createLoopExpAS() {
     return new LoopExpASImpl();
   }
   public void destroyLoopExpAS(LoopExpAS object) {
    // Attributes
    object.setIsMarkedPre(null);
    object.setName(null);
    object.setSymbol(null);
    // Standard AssociationEnds
    object.setIfExpAS(null);
    object.setBody(null);
    object.setIterator(null);
    object.setParent(null);
    object.setResult(null);
    object.setSource(null);
    // Aggregate Parts
    // Composite Parts

   }
   
  public AssociationCallExpAS createAssociationCallExpAS() {
     return new AssociationCallExpASImpl();
   }
   public void destroyAssociationCallExpAS(AssociationCallExpAS object) {
    // Attributes
    object.setIsMarkedPre(null);
    object.setSymbol(null);
    // Standard AssociationEnds
    object.setIfExpAS(null);
    object.getArguments().clear();
    object.setParent(null);
    object.setSource(null);
    // Aggregate Parts
    // Composite Parts

   }
   
  public OperationCallExpAS createOperationCallExpAS() {
     return new OperationCallExpASImpl();
   }
   public void destroyOperationCallExpAS(OperationCallExpAS object) {
    // Attributes
    object.setIsMarkedPre(null);
    object.setSymbol(null);
    // Standard AssociationEnds
    object.setIfExpAS(null);
    object.getArguments().clear();
    object.setParent(null);
    object.setSource(null);
    // Aggregate Parts
    // Composite Parts

   }
   
  public IterateExpAS createIterateExpAS() {
     return new IterateExpASImpl();
   }
   public void destroyIterateExpAS(IterateExpAS object) {
    // Attributes
    object.setIsMarkedPre(null);
    object.setName(null);
    object.setSymbol(null);
    // Standard AssociationEnds
    object.setIfExpAS(null);
    object.setBody(null);
    object.setIterator(null);
    object.setParent(null);
    object.setResult(null);
    object.setSource(null);
    // Aggregate Parts
    // Composite Parts

   }
   
  public LetExpAS createLetExpAS() {
     return new LetExpASImpl();
   }
   public void destroyLetExpAS(LetExpAS object) {
    // Attributes
    object.setIsMarkedPre(null);
    object.setSymbol(null);
    // Standard AssociationEnds
    object.setIfExpAS(null);
    object.setIn(null);
    object.setParent(null);
    object.getVariables().clear();
    // Aggregate Parts
    // Composite Parts

   }
   
  public OclMessageExpAS createOclMessageExpAS() {
     return new OclMessageExpASImpl();
   }
   public void destroyOclMessageExpAS(OclMessageExpAS object) {
    // Attributes
    object.setIsMarkedPre(null);
    object.setKind(null);
    object.setName(null);
    object.setSymbol(null);
    // Standard AssociationEnds
    object.setIfExpAS(null);
    object.getArguments().clear();
    object.setParent(null);
    object.setTarget(null);
    // Aggregate Parts
    // Composite Parts

   }
   
  public LiteralExpAS createLiteralExpAS() {
     return new LiteralExpASImpl();
   }
   public void destroyLiteralExpAS(LiteralExpAS object) {
    // Attributes
    object.setIsMarkedPre(null);
    object.setSymbol(null);
    // Standard AssociationEnds
    object.setIfExpAS(null);
    object.setParent(null);
    // Aggregate Parts
    // Composite Parts

   }
   
  public EnumLiteralExpAS createEnumLiteralExpAS() {
     return new EnumLiteralExpASImpl();
   }
   public void destroyEnumLiteralExpAS(EnumLiteralExpAS object) {
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
   
  public PathNameExpAS createPathNameExpAS() {
     return new PathNameExpASImpl();
   }
   public void destroyPathNameExpAS(PathNameExpAS object) {
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
   
  public CollectionLiteralPartAS createCollectionLiteralPartAS() {
     return new CollectionLiteralPartASImpl();
   }
   public void destroyCollectionLiteralPartAS(CollectionLiteralPartAS object) {
    // Attributes
    // Standard AssociationEnds
    // Aggregate Parts
    // Composite Parts

   }
   
  public CollectionRangeAS createCollectionRangeAS() {
     return new CollectionRangeASImpl();
   }
   public void destroyCollectionRangeAS(CollectionRangeAS object) {
    // Attributes
    // Standard AssociationEnds
    object.setFirst(null);
    object.setLast(null);
    // Aggregate Parts
    // Composite Parts

   }
   
  public CollectionItemAS createCollectionItemAS() {
     return new CollectionItemASImpl();
   }
   public void destroyCollectionItemAS(CollectionItemAS object) {
    // Attributes
    // Standard AssociationEnds
    object.setItem(null);
    // Aggregate Parts
    // Composite Parts

   }
   
  public NotExpAS createNotExpAS() {
     return new NotExpASImpl();
   }
   public void destroyNotExpAS(NotExpAS object) {
    // Attributes
    object.setIsMarkedPre(null);
    object.setSymbol(null);
    // Standard AssociationEnds
    object.setIfExpAS(null);
    object.setLeftOperand(null);
    object.setParent(null);
    object.setRightOperand(null);
    // Aggregate Parts
    // Composite Parts

   }
   
  public AndExpAS createAndExpAS() {
     return new AndExpASImpl();
   }
   public void destroyAndExpAS(AndExpAS object) {
    // Attributes
    object.setIsMarkedPre(null);
    object.setSymbol(null);
    // Standard AssociationEnds
    object.setIfExpAS(null);
    object.setLeftOperand(null);
    object.setParent(null);
    object.setRightOperand(null);
    // Aggregate Parts
    // Composite Parts

   }
   
  public OrExpAS createOrExpAS() {
     return new OrExpASImpl();
   }
   public void destroyOrExpAS(OrExpAS object) {
    // Attributes
    object.setIsMarkedPre(null);
    object.setSymbol(null);
    // Standard AssociationEnds
    object.setIfExpAS(null);
    object.setLeftOperand(null);
    object.setParent(null);
    object.setRightOperand(null);
    // Aggregate Parts
    // Composite Parts

   }
   
  public XorExpAS createXorExpAS() {
     return new XorExpASImpl();
   }
   public void destroyXorExpAS(XorExpAS object) {
    // Attributes
    object.setIsMarkedPre(null);
    object.setSymbol(null);
    // Standard AssociationEnds
    object.setIfExpAS(null);
    object.setLeftOperand(null);
    object.setParent(null);
    object.setRightOperand(null);
    // Aggregate Parts
    // Composite Parts

   }
   
  public ImpliesExpAS createImpliesExpAS() {
     return new ImpliesExpASImpl();
   }
   public void destroyImpliesExpAS(ImpliesExpAS object) {
    // Attributes
    object.setIsMarkedPre(null);
    object.setSymbol(null);
    // Standard AssociationEnds
    object.setIfExpAS(null);
    object.setLeftOperand(null);
    object.setParent(null);
    object.setRightOperand(null);
    // Aggregate Parts
    // Composite Parts

   }
   
  public LogicalExpAS createLogicalExpAS() {
     return new LogicalExpASImpl();
   }
   public void destroyLogicalExpAS(LogicalExpAS object) {
    // Attributes
    object.setIsMarkedPre(null);
    object.setSymbol(null);
    // Standard AssociationEnds
    object.setIfExpAS(null);
    object.setLeftOperand(null);
    object.setParent(null);
    object.setRightOperand(null);
    // Aggregate Parts
    // Composite Parts

   }
   
  public PrimaryExpAS createPrimaryExpAS() {
     return new PrimaryExpASImpl();
   }
   public void destroyPrimaryExpAS(PrimaryExpAS object) {
    // Attributes
    object.setIsMarkedPre(null);
    object.setSymbol(null);
    // Standard AssociationEnds
    object.setIfExpAS(null);
    object.setParent(null);
    // Aggregate Parts
    // Composite Parts

   }
   
  public SelectionExpAS createSelectionExpAS() {
     return new SelectionExpASImpl();
   }
   public void destroySelectionExpAS(SelectionExpAS object) {
    // Attributes
    object.setIsMarkedPre(null);
    object.setName(null);
    object.setSymbol(null);
    // Standard AssociationEnds
    object.setIfExpAS(null);
    object.setParent(null);
    object.setSource(null);
    // Aggregate Parts
    // Composite Parts

   }
   
  public OclExpressionAS createOclExpressionAS() {
     return new OclExpressionASImpl();
   }
   public void destroyOclExpressionAS(OclExpressionAS object) {
    // Attributes
    object.setIsMarkedPre(null);
    object.setSymbol(null);
    // Standard AssociationEnds
    object.setIfExpAS(null);
    object.setParent(null);
    // Aggregate Parts
    // Composite Parts

   }
   
  public CollectionLiteralExpAS createCollectionLiteralExpAS() {
     return new CollectionLiteralExpASImpl();
   }
   public void destroyCollectionLiteralExpAS(CollectionLiteralExpAS object) {
    // Attributes
    object.setIsMarkedPre(null);
    object.setKind(null);
    object.setSymbol(null);
    // Standard AssociationEnds
    object.setIfExpAS(null);
    object.getCollectionParts().clear();
    object.setParent(null);
    // Aggregate Parts
    // Composite Parts

   }
   
  public TupleLiteralExpAS createTupleLiteralExpAS() {
     return new TupleLiteralExpASImpl();
   }
   public void destroyTupleLiteralExpAS(TupleLiteralExpAS object) {
    // Attributes
    object.setIsMarkedPre(null);
    object.setSymbol(null);
    // Standard AssociationEnds
    object.setIfExpAS(null);
    object.setParent(null);
    object.getTupleParts().clear();
    // Aggregate Parts
    // Composite Parts

   }
   
  public PrimitiveLiteralExpAS createPrimitiveLiteralExpAS() {
     return new PrimitiveLiteralExpASImpl();
   }
   public void destroyPrimitiveLiteralExpAS(PrimitiveLiteralExpAS object) {
    // Attributes
    object.setIsMarkedPre(null);
    object.setSymbol(null);
    // Standard AssociationEnds
    object.setIfExpAS(null);
    object.setParent(null);
    // Aggregate Parts
    // Composite Parts

   }
   
  public IntegerLiteralExpAS createIntegerLiteralExpAS() {
     return new IntegerLiteralExpASImpl();
   }
   public void destroyIntegerLiteralExpAS(IntegerLiteralExpAS object) {
    // Attributes
    object.setIsMarkedPre(null);
    object.setSymbol(null);
    object.setValue(null);
    // Standard AssociationEnds
    object.setIfExpAS(null);
    object.setParent(null);
    // Aggregate Parts
    // Composite Parts

   }
   
  public RealLiteralExpAS createRealLiteralExpAS() {
     return new RealLiteralExpASImpl();
   }
   public void destroyRealLiteralExpAS(RealLiteralExpAS object) {
    // Attributes
    object.setIsMarkedPre(null);
    object.setSymbol(null);
    object.setValue(null);
    // Standard AssociationEnds
    object.setIfExpAS(null);
    object.setParent(null);
    // Aggregate Parts
    // Composite Parts

   }
   
  public BooleanLiteralExpAS createBooleanLiteralExpAS() {
     return new BooleanLiteralExpASImpl();
   }
   public void destroyBooleanLiteralExpAS(BooleanLiteralExpAS object) {
    // Attributes
    object.setIsMarkedPre(null);
    object.setSymbol(null);
    object.setValue(null);
    // Standard AssociationEnds
    object.setIfExpAS(null);
    object.setParent(null);
    // Aggregate Parts
    // Composite Parts

   }
   
  public StringLiteralExpAS createStringLiteralExpAS() {
     return new StringLiteralExpASImpl();
   }
   public void destroyStringLiteralExpAS(StringLiteralExpAS object) {
    // Attributes
    object.setIsMarkedPre(null);
    object.setSymbol(null);
    object.setValue(null);
    // Standard AssociationEnds
    object.setIfExpAS(null);
    object.setParent(null);
    // Aggregate Parts
    // Composite Parts

   }
   
  public IteratorExpAS createIteratorExpAS() {
     return new IteratorExpASImpl();
   }
   public void destroyIteratorExpAS(IteratorExpAS object) {
    // Attributes
    object.setIsMarkedPre(null);
    object.setName(null);
    object.setSymbol(null);
    // Standard AssociationEnds
    object.setIfExpAS(null);
    object.setBody(null);
    object.setIterator(null);
    object.setParent(null);
    object.setResult(null);
    object.setSource(null);
    // Aggregate Parts
    // Composite Parts

   }
   

}
