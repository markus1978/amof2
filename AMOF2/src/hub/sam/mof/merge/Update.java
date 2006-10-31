package hub.sam.mof.merge;

import cmof.Property;
import cmof.exception.MultiplicityViolation;
import cmof.common.ReflectiveCollection;
import core.abstractions.ownerships.Element;
import hub.sam.mof.mofinstancemodel.MofClassSemantics;

import java.util.Collection;
import java.util.Vector;

/**
 * Instances of this class represent an update of a single property in a single element. It is used to set properties
 * of merging elements with new values. The values are merged values; they contain results of merged in the context
 * that the update is run in.
 */
final class Update {
    private final MergeContext context;
    private final Element mergingElement;
    private final Property property;
    private final Collection<MergedValue> values;

    /**
     * Creates an update for an merging element and a property in a certain context.
     *
     * @param mergingElement The element that this update will be applied too.
     * @param property       The property that is to be updated.
     * @param values         The values that the property is to be updated with.
     */
    Update(Element mergingElement, Property property, Collection<MergedValue> values, MergeContext context) {
        super();
        this.mergingElement = mergingElement;
        this.property = property;
        this.values = values;
        this.context = context;
    }

    /**
     * Removes all values from a other update that are also values of this update. When the other update is an update
     * for a superset of this update, the method removes values that would cause duplication due to subsetting.
     * This is used to normalize updates ({@link Merge#normalizeUpdates()}).
     * <p/>
     * A value is removed from the superset if the exact same merged value (will the same values merged) is contained
     * in the subset. A value is removed from the subset if a merged value in the subset describes the same merge but
     * with at least on value to merge left.
     *
     * @param superUpdate An other update; only updates for properties that are supersets to the property of this update are
     *                    resonable.
     */
    void removeSubsettedValues(Update superUpdate) {
        Collection<MergedValue> supersettedValues = superUpdate.values;
        Collection<MergedValue> removeFromSuperset = new Vector<MergedValue>();
        Collection<MergedValue> removeFromSubset = new Vector<MergedValue>();
        for (MergedValue subsettedValue : values) {
            for (MergedValue supersettedValue : supersettedValues) {
                if (subsettedValue.isSameValue(supersettedValue)) {
                    if (subsettedValue.isSupersetMerge(supersettedValue)) {
                        removeFromSuperset.add(supersettedValue);
                    } else {
                        removeFromSubset.add(subsettedValue);
                    }
                }
            }
        }
        supersettedValues.removeAll(removeFromSuperset);
        values.removeAll(removeFromSubset);
    }

    /**
     * Execute merges on all values of this update. An update containes merged values ({@link MergedValue}); those
     * have to be explicitly merge in order to retrieve the final merged value from them. This method should be
     * called before executing the update itself.
     */
    void executeMerges() {
        for (MergedValue value : values) {
            value.executeMerge();
        }
    }

    /**
     * Executes the update. The values set for the update's property in its object are removed and replaces by
     * the merged values references by this update. The values have to be merged first.
     */
    @SuppressWarnings("unchecked")
    void executeUpdate() {
        MofClassSemantics semantics = context.semanticsForElement(mergingElement);
        if (semantics.isCollectionProperty(property)) {
            ReflectiveCollection<Object> objectValues = (ReflectiveCollection)
                    ((cmof.reflection.Object)mergingElement).get(property);
            for (MergedValue value : values) {
                Object mergedValue = value.getMergedValue();
                if (context.isNewLink(property, mergedValue, mergingElement)) {
                    try {
                        objectValues.add(mergedValue);
                        context.addNewLink(property, mergedValue, mergingElement);
                    } catch (MultiplicityViolation e) {
                        // For unknown reason, this still happens for some complicated models. What happens is that
                        // association, memberEnd at a Property/Association is set in a way that causes a multiplicity
                        // violation at either association (1) or memberEnd (2). It is not pretty, but can be
                        // basically ignored.
                        System.out.println("WARNING" + e.getMessage());  //TODO
                        //throw new MergeException("Merge caused multiplicity violation for " + property + " on value " +
                        //        mergingElement + " for value " + mergedValue);
                    }
                }
            }
        } else {
            if (values.size() > 0) {
                Object value = values.iterator().next().getMergedValue();
                if (context.isNewLink(property, value, mergingElement)) {
                    ((cmof.reflection.Object)mergingElement).set(property, value);
                    context.addNewLink(property, value, mergingElement);
                }
            }
        }
    }
}
