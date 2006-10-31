package hub.sam.mof.merge;

import cmof.Property;
import core.abstractions.ownerships.Element;
import core.abstractions.namespaces.NamedElement;

import java.util.Collection;

/**
 * A merge configuration is needed for tasks that the generic merge algorithm cannot decide on its very abstract
 * terms. The generic abstract merge is implemented at the core::abstractions::namespaces level. Different
 * implementations of this interface are needed, e.g. one for cmof an other for uml package merged.
 */
public interface MergeConfiguration {

    /**
     * Gives the properties for that all values are considered to be merged.
     * @return A collection of properties.
     */
    public Collection<Property> getMergingProperties();

    /**
     * Returns an alternative name for named elements that are supposed to have a name but don't, e.g. Associations or
     * non navigable properties.
     *
     * @param forElement The named element that a name is needed for.
     */
    public String getAlternativeName(NamedElement forElement);

    /**
     * When two values conflict, there are more distiguished values for a unary property, this method is called
     * to decide which value will granted priority or to finally throw a merge exception for the conflict.
     *
     * @param property       The property that caused the conflict.
     * @param values         The values with at least two values in conflict.
     * @param mergingElement The element that the values are merged for.
     * @param mergedElements The elements that are merged.
     * @return Returns the value that will prevail.
     * @throws MergeException If the conflict cannot be solved.
     */
    public Object valueForConflictingValues(Property property, Collection<Object> values, Element mergingElement,
                                            Collection<Element> mergedElements);

    /**
     * This method allows the manipulation of values before they are merged. This enables custom merging for the
     * values of some properties.
     *
     * @param property         The property for that the values shall be merged.
     * @param valueCollections The collections of values from the merging and merged elements for the given properties. This is
     *                         supposed to be an inout parameter.
     */
    public void customMerge(Property property, Collection<Collection<Object>> valueCollections);

    /**
     * Determines whether 2 values are equivalent. The standard semantics of equivalence is, that the first object was
     * created from the second, either by copy or merge, thus v2 can be discarded since v1 already
     * contains all features of v2.
     * @param v1 The first object (merging element, copy)
     * @param v2 The second object (merged element, to be copied)
     * @param context The context.
     * @return The boolean value or null if there is no custom semantics for this values.
     */
    public Boolean customEquivalence(Object v1, Object v2, MergeContext context);
}
