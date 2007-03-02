package hub.sam.sdlplus.semantics;

import InfrastructureLibrary.Core.Abstractions.Elements.Element;
import InfrastructureLibrary.Core.Abstractions.Namespaces.NamedElement;
import Pattern.Evaluation.Expression;
import SDL.ConcreteSyntax.SdlIdentifier;
import SDL.SdlDataType;
import SDL.SdlLiteral;
import SDL.SdlLiteralExpression;
import SDL.SdlOperation;
import SDL.SdlOperationCall;
import SDL.SdlVariable;
import SDL.SdlVariableAccess;
import SDL.SdlNowExpression;
import SDL.SdlPidExpression;
import cmof.Property;
import cmof.UmlClass;
import cmof.common.ReflectiveCollection;
import cmof.reflection.Extent;
import hub.sam.mof.mofinstancemodel.MofClassSemantics;
import hub.sam.mof.mofinstancemodel.MofClassifierSemantics;
import hub.sam.mopa.Name;
import hub.sam.mopa.Pattern;
import hub.sam.mopa.PatternClass;
import hub.sam.mopa.PatternList;
import hub.sam.mopa.trees.TreeNode;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.HashSet;

public class ResolveConcreteSyntaxExtensions extends PatternClass {

    private NameTable names;
    private Extent oExtent;
    private List<SemanticError> errors;
    private Collection<cmof.reflection.Object> toDelete;

    /**
     * Creates a ModelTransformator for oExtent. All errors that are encountered in further transformation steps
     * are added to errors.
     */
    public ResolveConcreteSyntaxExtensions(Extent oExtent, List<SemanticError> errors) {
        super();
        this.oExtent = oExtent;
        this.errors = errors;
        Iterable<TreeNode> model = hub.sam.mof.mopatree.Mof2TreeNode.createNodes(oExtent.outermostComposites());
        names = new NameTable(model);
    }

    private boolean checkIdentified(NamedElement identified, SdlIdentifier reference, UmlClass referenceTypeConstraint) {
        if (identified == null) {
            errorNoIndentified(reference);
        } else if (!((cmof.reflection.Object)identified).getMetaClass().conformsTo(referenceTypeConstraint)) {
            errorWrongIdentified(reference, identified, referenceTypeConstraint);
        } else {
            return true;
        }
        return false;
    }

    /**
     * Transforms the model by resolving StringReferences. When a reference could not be resolved a
     * SemanticError is created and added to the list of errors.
     */
    public void resolveSdlIdentifier() {
        System.out.println("Resolve identifier.");
        toDelete = new Vector<cmof.reflection.Object>();
        resolveSdlIdentifiers();
        for(cmof.reflection.Object delete: toDelete) {
            delete.delete();
        }
    }

    private void errorNoIndentified(SdlIdentifier identifier) {
        errors.add(new SemanticError((cmof.reflection.Object)identifier, "identifier reference a non existing element"));
    }

    private void errorWrongIdentified(SdlIdentifier identifier, NamedElement identified, UmlClass reqType) {
        errors.add(new SemanticError((cmof.reflection.Object)identifier, "identifier reference element of wrong type " +
                identified.getClass().getName() + ", it should reference to a " + reqType.getName()));
    }

    private void errorCannotDetermineTypeOfExpression(Expression expr) {
        errors.add(new SemanticError((cmof.reflection.Object)expr, "unable to determine the type of the expression"));
    }

    /**
     * This is to certify that element type has allready been checked for cses, and all cses have allready be resolved.
     * When not this method will trigger the resolve process. All this is nessesary because of the undetermed order of the
     * elements.
     */
    private void checkIfValueDataTypeIsResolved(cmof.reflection.Object resolved, Element resolvee) {
        if (!(resolved instanceof SdlDataType)) {
            resolveSdlIdentifiers((cmof.reflection.Object)resolvee);
        }
    }

    private SdlDataType determineTypeOfExpression(TreeNode node) {
        Collection<TreeNode> nodes = new Vector<TreeNode>(1);
        nodes.add(node);
        try {
            return(SdlDataType)run(nodes, null, "determineType", 0);
        } catch(Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    //OperationApplication(operation=type:Operation)
    @PatternList(set="determineType",order=10,value={
        @Pattern(type= SdlOperationCall.class,children="c1"),
        @Pattern(type= SdlOperation.class,variable="operation",property="feature",name="c1")})
    public void determineTypeOperation(@Name("operation")SdlOperation operation) {
        returnPattern(operation.getOwner());
    }

    //LiteralExpression(literal=literal:Literal)
    @PatternList(set="determineType",order=9,value={
        @Pattern(type= SdlLiteralExpression.class,children="c1"),
        @Pattern(type= SdlLiteral.class,variable="literal",property="literal",name="c1")})
    public void determineTypeLiteral(@Name("literal")SdlLiteral literal) {
        returnPattern(literal.getOwner());
    }

    //SdlVariableAccess(variable=variable:Variable)
    @PatternList(set="determineType",order=8,value={
        @Pattern(type= SdlVariableAccess.class,children="c1"),
        @Pattern(type=SdlVariable.class,variable="variable",property="feature",name="c1")})
    public void determineTypeVariable(@Name("variable") SdlVariable variable) {
        checkIfValueDataTypeIsResolved((cmof.reflection.Object)variable.getType(), variable);
        if (variable.getType() instanceof SdlDataType) {
              returnPattern(variable.getType());
        } else {
            returnPattern(null);
        }
    }

    @Pattern(set="determineType",order=7,type= SdlNowExpression.class,variable="expr")
    public void determineTypeNowExpr(@Name("expr")Expression expr) {
        returnPattern(oExtent.query("SdlPackage:predefined/SdlDataType:Time"));
    }

    @Pattern(set="determineType",order=7,type= SdlPidExpression.class,variable="expr")
    public void determineTypePidExpr(@Name("expr")Expression expr) {
        returnPattern(oExtent.query("SdlPackage:predefined/SdlDataType:Pid"));
    }

    //expr=Expression provided ( expr.determineType() instanceof ValueDataType)
    @Pattern(set="determineType",order=6,type=Expression.class,variable="expr")
    public void determineTypeExpr(@Name("expr")Expression expr) {
        returnPattern(expr.getType());
    }

    //default
    @Pattern(set="determineType")
    public void determineTypeDefault() {
        System.err.println("unreachable code - 1");
        returnPattern(null);
    }

    private NamedElement resolveSdlIdentifier(NamedElement element, Element context, UmlClass requiredType) {
        SdlIdentifier identifier = element.getIdentifier();
        if (context instanceof SdlOperationCall) {
            SdlDataType typeOfArgument = null;

            try {
                resolveSdlIdentifiers((cmof.reflection.Object)((SdlOperationCall)context).getArgument().get(0));
                typeOfArgument = determineTypeOfExpression(
                    hub.sam.mof.mopatree.Mof2TreeNode.createNode((cmof.reflection.Object)((SdlOperationCall)context).
                            getArgument().get(0)));
            } catch (Exception e) {
                // empty
            }
            if (typeOfArgument == null) {
                errorCannotDetermineTypeOfExpression(((SdlOperationCall)context).getArgument().get(0));
                return null;
            }
            NamedElement identified = names.resolveAIdentifierInContainer(identifier, typeOfArgument,
                    requiredType);
            if (identified == null || !checkIdentified(identified, identifier, requiredType)) {
                errorNoIndentified(identifier);
                return null;
            } else {
                return identified;
            }
        } else {
            NamedElement identified = names.resolveAIdentifier(identifier, context, requiredType);
            if (checkIdentified(identified, identifier, requiredType)) {
                return identified;
            } else {
                return null;
            }
        }
    }

    private void resolveSdlIdentifiers() {
        for(cmof.reflection.Object o: oExtent.outermostComposites()) {
            resolveSdlIdentifiers(o);
        }
    }

    private Collection<cmof.reflection.Object> resolved = new HashSet<cmof.reflection.Object>();

    private void resolveSdlIdentifiers(cmof.reflection.Object o) {
        if (resolved.contains(o)) {
            return;
        } else {
            resolved.add(o);
        }
        MofClassSemantics semantics = MofClassifierSemantics.createClassClassifierForUmlClass(o.getMetaClass());
        for (Property property: semantics.getFinalProperties()) {
            if ((semantics.getSupersettedProperties(property).size() == 0) && !property.isDerived()) {
                Object value = (o.get(property));
                if (value instanceof cmof.reflection.Object) {
                    if (value instanceof NamedElement) {
                        if (((NamedElement)value).getIdentifier() != null) {
                            NamedElement resolved = resolveSdlIdentifier((NamedElement)value, (Element)o,
                                    (UmlClass)property.getType());
                            if (resolved != null) {
                                o.set(property, resolved);
                                resolveSdlIdentifiers((cmof.reflection.Object)resolved);
                                toDelete.add((cmof.reflection.Object)value);
                            }
                        }
                    }
                } else if (value instanceof Iterable) {
                    Map<NamedElement, NamedElement> oldAndNewValus = new HashMap<NamedElement, NamedElement>();
                    for (Object singleValue: (Iterable)value) {
                        if (singleValue instanceof NamedElement) {
                            if (((NamedElement)singleValue).getIdentifier() != null) {
                                NamedElement resolved = resolveSdlIdentifier((NamedElement)singleValue, (Element)o,
                                        (UmlClass)property.getType());
                                if (resolved != null) {
                                    oldAndNewValus.put((NamedElement)singleValue, resolved);
                                }
                            }
                        }
                    }
                    ReflectiveCollection values = (ReflectiveCollection)o.get(property);
                    for (NamedElement oldValue: oldAndNewValus.keySet()) {
                        values.remove(oldValue);
                        values.add(oldAndNewValus.get(oldValue));
                        resolveSdlIdentifiers((cmof.reflection.Object)oldAndNewValus.get(oldValue));
                        toDelete.add((cmof.reflection.Object)oldValue);
                    }
                }
            }
        }
        for (cmof.reflection.Object component: o.getComponents()) {
            resolveSdlIdentifiers(component);
        }
    }
}
