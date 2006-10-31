package hub.sam.mof.merge;

import cmof.Property;
import cmof.common.ReflectiveCollection;
import core.abstractions.ownerships.Element;
import hub.sam.mof.mofinstancemodel.MofClassSemantics;
import hub.sam.mof.mofinstancemodel.MofValueSpecificationList;
import hub.sam.util.MultiMap;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * The main Merge class. It is responsible for merging multiple mergedElements into an exisiting merging element. It
 * work in a specific context ({@link MergeContext}).
 */
final class Merge {

    private final Element mergingElement;
    private final Collection<Element> mergedElements;
    private final MergeContext context;
    private final MofClassSemantics semantics;
    private List<Element> allElements = null;
    private final Map<Property, Update> updates = new HashMap<Property, Update>();
    private final Collection<Property> alreadyNormalized = new HashSet<Property>();

    /**
     * Creates a Merge. It cannot be used by the user directly, it is suposed to be created by {@link MergeContext}
     * only.
     *
     * @param mergingElement The element into that should be merged.
     * @param mergedElements The elements that are merged into the merging element.
     * @param context        The context of the merge.
     * @param copy           If the merge is a copy merge, the merging element will be cleared before merged into.
     */
    Merge(Element mergingElement, Collection<Element> mergedElements, MergeContext context, boolean copy) {
        super();
        this.mergingElement = mergingElement;
        this.mergedElements = mergedElements;
        this.context = context;
        this.semantics = context.semanticsForElement(mergingElement);
        checkMergedElements();
        if (copy) {
            clearMergingElement();
        }
    }

    /**
     * Executes the merge. Only call it once in the lifecycle of an instance of this class. It traverses through all
     * properties of the classifier of the merging and merged elements to collect all nessary updates to the properties
     * of the merging element. Properties and their values are handled differently depending on isComposite and whether
     * the property is a merging property. After all updates have been collected, the updates are normalized, all
     * merges that are needed for the updates are executed. Attention: The updates themself are not executed.
     *
     * //@see Merge#copyValuesOfProperty(cmof.Property)
     * //@see Merge#mergeValuesOfProperty(cmof.Property)
     * @see hub.sam.mof.merge.Merge#normalizeUpdates()
     * @see Merge#normalizeUpdates(cmof.Property)
     */
    void execute() {
        // The merge is execute for all meta attribute of the merge member. The values of each of these
        // attributes are merged. The merge is not really done now, instead all necessary updates are remembered.
        PropertyLoop:
        for (Property property : semantics.getFinalProperties()) {
            if (!propertyIsConsideredForMerge(property)) {
                continue PropertyLoop;
            }

            if (isMergingProperty(property)) {
                mergeValuesOfProperty(property, true);
            } else {
                mergeValuesOfProperty(property, false);
            }
        }

        normalizeUpdates();

        for (Update update : updates.values()) {
            update.executeMerges();
        }
    }

    /**
     * All necessary updates for completing this merge are executed. Before the values are set by the updates, all
     * properties of the merging element are cleared (because they will be reset anyway).
     */
    void executeUpdates() {
        for (Update update : updates.values()) {
            update.executeUpdate();
        }
    }

    /**
     * Removes all values from all properties that are considerd for merge.
     */
    void clearMergingElement() {
        MofValueSpecificationList.checkLower = false;
        for (Property property : semantics.getFinalProperties()) {
            if (propertyIsConsideredForMerge(property)) {
                if (semantics.isCollectionProperty(property)) {
                    ((ReflectiveCollection)((cmof.reflection.Object)mergingElement).get(property)).clear();
                } else {
                    ((cmof.reflection.Object)mergingElement).set(property, null);
                }
            }
        }
        MofValueSpecificationList.checkLower = true;
    }

    /**
     * Checks all merged elements of this merge. They have to have the same meta-class than the merging element.
     *
     * @throws MergeException When one merged element has a different meta-class than the merging element.
     */
    private void checkMergedElements() {
        for (Element element : mergedElements) {
            if (!((cmof.reflection.Object)element).getMetaClass()
                    .equals(((cmof.reflection.Object)mergingElement).getMetaClass())) {
                throw new MergeException("Attempt to merge elements with different metaclass");
            }
        }
    }

    /**
     * Getter for all mergedElements.
     *
     * @return Returns the merged elements of this merge.
     */
    private Collection<Element> getMergedElements() {
        return mergedElements;
    }

    /**
     * @return Returns all elements involved in this merge. It is the sum of the merged elements and the merging
     *         element. MergingElement first.
     */
    private List<Element> getAllElements() {
        if (allElements == null) {
            allElements = new Vector<Element>();
            allElements.add(mergingElement);
            allElements.addAll(getMergedElements());
        }
        return allElements;
    }

    /**
     * Retrieves the values of a property from all elements involved in this merge. The values are retrieved
     * independently from each element. This methods does intensive work and does not cache its result.
     *
     * @param property The property to retrieve the values from.
     * @return Returns a multi map with the element as key. There are values for each element in this merge;
     *         for those that have no values for the property an empty value list is created.
     */
    private MultiMap<Element, Object> getValues(Property property) {
        MultiMap<Element, Object> result = new MultiMap<Element, Object>() {
            @Override
            protected Collection<Object> createCollection() {
                return new Vector<Object>();
            }
        };
        for (Element element : getAllElements()) {
            Object values = ((cmof.reflection.Object)element).get(property);
            if (values != null) {
                if (values instanceof ReflectiveCollection) {
                    for (Object o : (ReflectiveCollection)values) {
                        result.put(element, o);
                    }
                } else {
                    result.put(element, values);
                }
            }
        }
        return result;
    }

    /**
     * Checks whether a property is a merging property. The context determines what the overall merging property is.
     * Only the values of this property and its subsets are considered to be merged with each other. A merging property
     * must be a composite property, and the given property is checked for that.
     *
     * @param property The property to examine.
     * @return Returns true if the property is the context's merging property or is a direct or indirect sub set of it.
     */
    private boolean isMergingProperty(Property property) {
        for(Property mergingProperty: context.getMergingProperties()) {
            if (semantics.getSubsettedProperties(property).contains(mergingProperty)) {
                if (property.isComposite()) {
                    return true;
                } else {
                    throw new MergeException("A merging property is not composite.");
                }
            }
        }
        return false;
    }

    /**
     * Merges the values of a property that is either a merging property or a non composite property.
     * Depending on the multiplicity of this property, the values from different elements are forced
     * to be merged at another point in the context of this merge, or the values of different elements are
     * combined in a collection of values. The method only collects updates.
     *
     * @param property The property for that the values shall be merged.
     * @param isRunForMergingProperty Determines the character of the property.
     */
    private void mergeValuesOfProperty(Property property, boolean isRunForMergingProperty) {
        boolean isComposite = property.isComposite();
        MultiMap<Element, Object> values = getValues(property);
        List<Collection<Object>> valueCollections = new Vector<Collection<Object>>();
        for (Element element : getAllElements()) {
            valueCollections.add(values.get(element));
        }
        boolean isSingleMultiplicity = property.getUpper() == 1;
        context.getConfiguration().customMerge(property, valueCollections);
        Collection<MergedValue> mergedValues = mergedValuesForValueCollections(valueCollections, isComposite,
                isRunForMergingProperty, isSingleMultiplicity);

        if (property.getUpper() < mergedValues.size() && property.getUpper() != -1) {
            if (isSingleMultiplicity) {
                mergeConflictingValues(mergedValues, property, values, isComposite, isRunForMergingProperty);
            } else {
                throw new MergeException("Merge would cause a multiplicity violation for " + property + " in "
                        + mergingElement);
            }
        } else {
            updateProperty(property, mergedValues);
        }
    }

    /**
     * Merges collections with values.
     *
     * @param valueCollections The collections of values.
     * @param isComposite      Whether it is a composite merge, has influence on the merged value policy.
     * @param isRunForMergingProperty Whether it is run in the context of an merging property. That is a property
     *                                that forces a merge, like ownedMember in UML.
     * @param isSingleMultiplicity Whether it is run in the context of a property with single multiplicity. For
     *                             those properties the method will give the value of the merging element priority over
     *                             the values of other elements.
     * @return The merged values.
     */
    private Collection<MergedValue> mergedValuesForValueCollections(List<Collection<Object>> valueCollections,
            boolean isComposite, boolean isRunForMergingProperty, boolean isSingleMultiplicity) {
        Collection<MergedValue> mergedValues = new Vector<MergedValue>();
        boolean isRunForMergingElement = true;
        Loop:
        for (Collection<Object> values : valueCollections) {
            Collection<MergedValue> moreMergedValues = new Vector<MergedValue>();

            if (isSingleMultiplicity && !isRunForMergingProperty && isComposite && mergedValues.size() > 0) {
                // all other values will be ignored anyway.
                break Loop;
            }
            for (Object o : values) {
                boolean addedToAMergedValue = false;
                for (MergedValue allReadyMergedValue : mergedValues) {
                    if (allReadyMergedValue.isToMerge(o)) {
                        allReadyMergedValue.addToMerge(o);
                        addedToAMergedValue = true;
                    }
                }
                if (!addedToAMergedValue) {
                    MergedValue result;
                    if (!isComposite || isRunForMergingElement) {
                        result = context.createValue(o, isComposite, isRunForMergingProperty);
                    } else {
                        result = context.createCopy(o, isRunForMergingProperty);
                    }
                    moreMergedValues.add(result);
                }
            }
            mergedValues.addAll(moreMergedValues);
            isRunForMergingElement = false;
        }
        return mergedValues;
    }

    /**
     * Trys to merge conflicting values anyway by using the merge configuration.
     *
     * @param mergedValues The resulting merged values.
     * @param property     The unary property to merge.
     * @param values       The conflicting values.
     * @param composite    Is it a composite merge or not.
     * @throws MergeException If the conflict cannot be resolved.
     */
    private void mergeConflictingValues(
            Collection<MergedValue> mergedValues, Property property, MultiMap<Element, Object> values,
            boolean composite, boolean isRunForMergingProperty) {
        Collection<Object> conflictingValues = new Vector<Object>(mergedValues.size());
        for (MergedValue conflictingValue : mergedValues) {
            conflictingValues.add(conflictingValue.getOriginalValue());
        }
        Object resovledConflictValue = context.getConfiguration()
                .valueForConflictingValues(property, conflictingValues, mergingElement, mergedElements);
        if (resovledConflictValue == null) {
            if (values.get(mergingElement).size() > 0) {
                // In case of conflicting values, the value of the merging element has priority.
                resovledConflictValue = values.get(mergingElement).iterator().next();

            } else {
                throw new MergeException(
                        "Unary property with ambigous value detected for " + property + " in "
                                + mergingElement);
            }
        }

        Collection<MergedValue> singleMergedValue = new Vector<MergedValue>(1);
        singleMergedValue.add(context.createValue(resovledConflictValue, composite, isRunForMergingProperty));
        updateProperty(property, singleMergedValue);
    }

    /**
     * Adds an update to the local updates of this merge.
     *
     * @param property The property to update.
     * @param values   The values that the property shall be updated with.
     */
    @SuppressWarnings("unchecked")
    private void updateProperty(Property property, Collection<MergedValue> values) {
        updates.put(property, new Update(mergingElement, property, values, context));
    }

    /**
     * Normalizes the local updates of this merge. See {@link Merge#normalizeUpdates(cmof.Property)}.
     */
    private void normalizeUpdates() {
        for (Property finalProperty : semantics.getFinalProperties()) {
            normalizeUpdates(finalProperty);
        }
    }

    /**
     * Normalizes the update of this merge for a certain property. This removes all values from all update that would
     * cause a duplication due to subsetting. When an update contains a value that is also contained in the update for
     * an supersetted property it will be removed from that update.
     * <p/>
     * The method work recusivly on properties subsetted properties. Only call it once for a property, and only for
     * final properties. It is only executed for properties with no further supersets. For those properties it first
     * wents up to the must subsetted properties and than recuses down.
     * <p/>
     * It is ony run once for every property.
     *
     * @param property The property for that the updates of this merge shall be normalized.
     */
    private void normalizeUpdates(Property property) {
        if (alreadyNormalized.contains(property)) {
            return;
        }
        if (semantics.getSupersettedProperties(property).size() > 0) {
            return;
        }
        for (Property subsettedProperty : semantics.getSubsettedProperties(property)) {
            normalizeUpdates(subsettedProperty);
            if (!(subsettedProperty.isDerived() || subsettedProperty.isDerivedUnion())) {
                Update update = updates.get(property);
                if (update != null) {
                    update.removeSubsettedValues(updates.get(subsettedProperty));
                }
            }
        }
        alreadyNormalized.add(property);
    }

    /**
     * This method checks whether a property is considered for merge or not. A property is not considered if it is
     * in any form derived or if would be implicitly merged through depending properties.
     *
     * @param property The property to check.
     */
    private boolean propertyIsConsideredForMerge(Property property) {
        if (property.isDerived() || property.isDerivedUnion()) {
            return false;
        } else {
            return !(property.getOpposite() != null && property.getOpposite().isComposite());
        }
    }
}
