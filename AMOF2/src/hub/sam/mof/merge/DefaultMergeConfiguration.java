package hub.sam.mof.merge;

import cmof.Comment;
import cmof.Constraint;
import cmof.Operation;
import cmof.PackageImport;
import cmof.Parameter;
import cmof.ParameterDirectionKind;
import cmof.Property;
import cmof.Type;
import cmof.Association;
import core.abstractions.namespaces.NamedElement;
import core.abstractions.ownerships.Element;
import core.abstractions.visibilities.VisibilityKind;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * This is the default implementation, suitable for cmof package merges.
 */
public class DefaultMergeConfiguration implements MergeConfiguration {

    public static final Map<String, Object> knownConflicts = new HashMap<String, Object>();
    static {
        knownConflicts.put("InfrastructureLibrary.Core.Constructs.Property.subsettingContext.null.type, UML.Classes.Kernel.Property.subsettingContext.null.type", "UML.Classes.Kernel.Type");
        knownConflicts.put("InfrastructureLibrary.Core.Constructs.VisibilityKind.bestVisibility.null.type, UML.Classes.Kernel.VisibilityKind.bestVisibility.null.type", "UML.Classes.Kernel.VisibilityKind");
        knownConflicts.put("UML.Activities.BasicActivities.Pin.isAbstract, UML.Activities.FundamentalActivities.Pin.isAbstract", Boolean.FALSE);
        knownConflicts.put("UML.Classes.Interfaces.BehavioralFeature.raisedException.type, UML.CommonBehaviors.Communications.BehavioralFeature.raisedException.type", "UML.Classes.Interfaces.Type");
        knownConflicts.put("UML.Actions.BasicActions.Pin.isAbstract, UML.Activities.BasicActivities.Pin.isAbstract, null.isAbstract", Boolean.FALSE);
        knownConflicts.put("UML.CompositeStructures.InvocationActions.Trigger.isAbstract, UML.CompositeStructures.Ports.Trigger.isAbstract", Boolean.FALSE);
        knownConflicts.put("UML.Activities.BasicActivities.Pin.isAbstract, UML.Activities.StructuredActivities.Pin.isAbstract, null.isAbstract", Boolean.FALSE);
        knownConflicts.put("UML.Activities.CompleteActivities.Pin.isAbstract, UML.CompositeStructures.StructuredActivities.Pin.isAbstract, null.isAbstract", Boolean.FALSE);
        knownConflicts.put("UML.Actions.BasicActions.Pin.isAbstract, UML.Activities.BasicActivities.Pin.isAbstract, UML.Activities.FundamentalActivities.Pin.isAbstract, null.isAbstract", Boolean.FALSE);
        knownConflicts.put("L1.Pin.isAbstract, UML.CompositeStructures.InvocationActions.Pin.isAbstract, null.isAbstract", Boolean.FALSE);
        knownConflicts.put("L1.Pin.isAbstract, UML.Activities.IntermediateActivities.Pin.isAbstract, UML.Activities.StructuredActivities.Pin.isAbstract, UML.CompositeStructures.InvocationActions.Pin.isAbstract, null.isAbstract", Boolean.FALSE);
        knownConflicts.put("L2.Pin.isAbstract, UML.Activities.CompleteActivities.Pin.isAbstract, UML.Activities.CompleteStructuredActivities.Pin.isAbstract, UML.Activities.ExtraStructuredActivities.Pin.isAbstract, UML.CompositeStructures.StructuredActivities.Pin.isAbstract, null.isAbstract", Boolean.FALSE);
        knownConflicts.put("L2.Port.required.type, UML.Components.PackagingComponents.Port.required.type, UML.Deployments.ComponentDeployments.Port.required.type, UML.StateMachines.ProtocolStateMachines.Port.required.type, null.type", "L2.Interface");
        knownConflicts.put("L2.Port.provided.type, UML.Components.PackagingComponents.Port.provided.type, UML.Deployments.ComponentDeployments.Port.provided.type, UML.StateMachines.ProtocolStateMachines.Port.provided.type, null.type", "L2.Interface");
    }

    private final Map<String, Object> conflicts;
    private final Map<NamedElement, String> alternativeNames = new HashMap<NamedElement, String>();
    private final Collection<Property> mergingProperties;
    private final GlobalMergeContext globalContext;

    public DefaultMergeConfiguration(Map<String, Object> conflicts, Collection<Property> mergingProperties,
                                     GlobalMergeContext globalContext) {
        super();
        this.globalContext = globalContext;
        this.conflicts = conflicts;
        this.mergingProperties = mergingProperties;
    }

    /*
    DefaultMergeConfiguration() {
        super();
        this.conflicts = knownConflicts;
    } */

    public Collection<Property> getMergingProperties() {
        return this.mergingProperties;
    }

    public String getAlternativeName(NamedElement forElement) {
        String result = alternativeNames.get(forElement);
        if (result == null) {
            result = getNewAlternativeName(forElement);
            if (result != null) {
                alternativeNames.put(forElement, result);
            }
        }
        return result;
    }

    @SuppressWarnings({"OverlyLongMethod"})
    private String getNewAlternativeName(NamedElement forElement) {
        if (forElement instanceof cmof.Association) {
            if (forElement.getName() != null) {
                return forElement.getName();
            }
            List<String> names = new Vector<String>(2);
            for (Property end : ((cmof.Association)forElement).getMemberEnd()) {
                String name = end.getName();
                if (name == null) {
                    name = end.getType().getName();
                }
                names.add(name);
            }
            Collections.sort(names);
            return names.toString();
        } else if (forElement instanceof cmof.Property) {
            String name = forElement.getName();
            if (name == null) {
                String typeName = ((Property)forElement).getType().getName();
                return typeName.substring(0, 1).toLowerCase() + typeName.substring(1);
            } else {
                return name;
            }
        } else if (forElement instanceof Operation) {
            Operation op = (Operation)forElement;
            StringBuffer opName = new StringBuffer(op.getName());
            for (Parameter parameter : op.getFormalParameter()) {
                if (!parameter.getDirection().equals(ParameterDirectionKind.RETURN)) {
                    opName.append("_");
                    opName.append(parameter.getType().getName());
                }
            }
            return opName.toString();
        } else if (forElement instanceof cmof.Parameter) {
            if (((Parameter)forElement).getDirection() == ParameterDirectionKind.RETURN) {
                return "return";
            } else {
                int position = 0;
                int i = 1;
                for (Parameter otherParameter: ((Parameter)forElement).getOperation().getFormalParameter()) {
                    if (otherParameter.equals(forElement)) {
                        position = i;
                    }
                    if (otherParameter.getDirection() != ParameterDirectionKind.RETURN) {
                        i++;
                    }
                }
                if (position == 0) {
                    throw new MergeException("assert");
                }
                Type type = (cmof.Type)((Parameter)forElement).getType();
                if (type != null) {
                    return type.getName() + position;
                } else {
                    return null;
                }
            }
        } else if (forElement instanceof cmof.Constraint) {
            String name = forElement.getName();
            if (name == null) {
                Constraint constraint = (Constraint)forElement;
                Loop:
                for(Element constraintedElement: constraint.getConstrainedElement()) {
                    if (constraintedElement instanceof Operation) {
                        if (((Operation)constraintedElement).getPrecondition().contains(constraint)) {
                            name = "pre";
                        } else if (((Operation)constraintedElement).getPostcondition().contains(constraint)) {
                            name = "post";
                        } else if (((Operation)constraintedElement).getBodyCondition().equals(constraint)) {
                            name = "body";
                        }
                    } else if (constraintedElement instanceof Association) {
                        name = getAlternativeName((NamedElement)constraintedElement);
                        if (name == null) {
                            System.out.println("halt2");
                        } else {
                            name += "_constraint";
                        }
                    }
                    if (name != null) {
                        break Loop;
                    }
                }
            }
            return name;
        } else {
            return null;
        }
    }

    @SuppressWarnings({"unchecked", "RedundantCast"})
    public Object valueForConflictingValues(Property property, Collection<Object> values, Element mergingElement,
                                            Collection<Element> mergedElements) {
        String propertyName = property.getName();
        if (propertyName.equals("lower")) {
            return Collections.max((Collection)values);
        } else if (propertyName.equals("upper")) {
            Collection<Long> uppers = new Vector<Long>(values.size());
            for (Object value : values) {
                if ((Long)value != -1) {
                    uppers.add((Long)value);
                }
            }
            return Collections.min((Collection)uppers);
        } else if (propertyName.equals("isUnique") || propertyName.equals("isOrdered") ||
                propertyName.equals("isComposite")) {
            return Boolean.TRUE;
        } else if (propertyName.equals("isReadOnly")) {
            return Boolean.FALSE;
        } else if (propertyName.equals("isDerived") || propertyName.equals("isDerivedUnion")) {
            return Boolean.TRUE;
        } else if (propertyName.equals("visibility")) {
            // only PRIVATE and PUBLIC exist in cmof
            return VisibilityKind.PUBLIC;
        } else if (propertyName.equals("name")) {
            return null;
        } else if (propertyName.equals("type")) {
            boolean equivalent = true;
            for (Object o1: values) {
                for (Object o2: values) {
                    if (!globalContext.isEquivalent(o1, o2)) { // TODO, this should be used commonly not only for type
                        equivalent = false;
                    }
                }
            }
            if (equivalent) {
                return values.iterator().next();
            } else {
                try {
                    return resolveBasedOnConflicts(mergingElement, mergedElements, values, property);
                } catch (MergeException e) {// TODO
                    System.out.println("WARNING: autoresolve exception: ");
                    System.out.println(e.getMessage());
                    return values.iterator().next();
                }
            }
        } else if (values.iterator().next() instanceof cmof.Package) {
            // TODO: here is a more comprehensive check needed, only when the container of a toplevel merging element
            // is requested, null should be returned.
            return null;
        } else {
            return resolveBasedOnConflicts(mergingElement, mergedElements, values, property);
        }
    }

    private Object resolveBasedOnConflicts(Element mergingElement, Collection<Element> mergedElements, Collection<Object> values, Property property) {
        String propertyName = property.getName();
        String serializedConflictKey = null;
        if (mergingElement instanceof NamedElement) {
            String[] conflictKey = new String[mergedElements.size() + 1];
            conflictKey[0] = getElementUnique(mergingElement) + "." + propertyName;
            int i = 1;
            for (Element mergedElement: mergedElements) {
                conflictKey[i] = getElementUnique(mergedElement) + "." + propertyName;
                i++;
            }
            Arrays.sort(conflictKey);
            StringBuffer serializedConflictKeyBuffer = new StringBuffer();
            boolean first = true;
            for (String conflictKeyPart: conflictKey) {
                if (!first) {
                    serializedConflictKeyBuffer.append(", ");
                } else {
                    first = false;
                }
                serializedConflictKeyBuffer.append(conflictKeyPart);
            }
            serializedConflictKey = serializedConflictKeyBuffer.toString();
            if (conflicts.get(serializedConflictKey) != null) {
                Object solution = conflicts.get(serializedConflictKey);
                for(Object value: values) {
                    if (value instanceof NamedElement) {
                        if (solution.equals(((NamedElement)value).getQualifiedName())) {
                            return value;
                        }
                    } else {
                        if (solution.equals(value)) {
                            return value;
                        }
                    }
                }
            }
        }

        throw new MergeException("conflicting values for merging property " + property.getQualifiedName() +
                " in merging element " + mergingElement + ". The conflect key is " + serializedConflictKey + ".");
    }

    public void customMerge(Property property, Collection<Collection<Object>> valueCollections) {
        if (property.getQualifiedName().equals(cmof.Parameter.class.getCanonicalName())) {
            customMergeOperationParameter(valueCollections);
        }
    }

    public Boolean customEquivalence(Object v1, Object v2, MergeContext context) {
        if (v1 instanceof PackageImport) {
            cmof.Package p1 = ((PackageImport)v1).getImportedPackage();
            cmof.Package p2 = ((PackageImport)v2).getImportedPackage();
            if (context.isEquivalent(p1,p2) || context.isEquivalent(p2,p1)) {
                return Boolean.TRUE;
            } else {
                return null;
            }
        } else if (v1 instanceof Comment) {
            String body1 = ((Comment)v1).getBody();
            String body2 = ((Comment)v2).getBody();
            return (body1 != null) && body1.equals(body2);
        } else {
            return null;
        }
    }

    /**
     * Executes a custom merge for parameter. It simply puts all result parameter to the ends of the value collections,
     * because the return parameter position has no semantic influence but can result in a merge conflict.
     *
     * @param valueCollections The value collections to manipulate.
     */
    private void customMergeOperationParameter(Collection<Collection<Object>> valueCollections) {
        for (Collection<Object> values : valueCollections) {
            Parameter result = null;
            for (Object value : values) {
                Parameter parameter = (Parameter)value;
                if (parameter.getDirection().equals(ParameterDirectionKind.RETURN)) {
                    result = parameter;
                }
            }
            if (result != null) {
                values.remove(result);
                values.add(result);
            }
        }
    }

    private String getElementUnique(Element element) {
        if (element instanceof NamedElement) {
            return ((NamedElement)element).getQualifiedName();
        } else {
            return getElementUnique(element.getOwner()) + ".null";
        }
    }
}
