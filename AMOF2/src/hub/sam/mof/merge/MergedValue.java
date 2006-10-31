package hub.sam.mof.merge;

import core.abstractions.namespaces.NamedElement;
import core.abstractions.namespaces.Namespace;
import core.abstractions.ownerships.Element;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

/**
 * Merges values represent a set of values from different elements, either merging element or merged elements.
 * The values of one merged value are intended to represent a single value after the merge. Thus a merged value can
 * represent a simple copy of a single element, a set of reference values that have to be merged at a different
 * point in the context of this value, or a set of values for that this merged value will schedule a merge.
 * <p/>
 * The lifecycle of merged values are as follows. At construction time one value, isCopy, and isComposite are given.
 * After that values can be added to the merged value. After that the merge is executed or not, depending on
 * the merged values properties isCopy, isComposite. Finally the real merged value can be retrieved from this
 * merged value.
 * <p/>
 * A merged value must have a original value, that is a value owned by the merging element of the merge that this
 * merged value is used in. In the case that there is no value of this merged value owned by the merging element copy
 * has to be used to achive implicitly create such a value in the merging value.
 */
final class MergedValue {
    private Object original;
    private final List<Object> values = new Vector<Object>();
    private final boolean composite;
    private final MergeContext context;
    private final boolean isRunForMergingProperty;
    private boolean merged = false;

    /**
     * Use to create a composite merged value. Only object values can be merged in a composite merged value.
     *
     * @param original The original value, or null for a copy.
     * @param context  The context of the merged value.
     */
    MergedValue(Object original, MergeContext context, boolean isRunForMergingProperty) {
        super();
        this.isRunForMergingProperty = isRunForMergingProperty;
        this.original = original;
        this.context = context;
        this.composite = true;
        if (original != null && !(original instanceof cmof.reflection.Object)) {
            throw new MergeException("Attempt to merge non object values in a composite merged value.");
        }
    }

    /**
     * Use to create a reference merged value.
     *
     * @param context The context of the merged value.
     */
    MergedValue(MergeContext context, boolean isRunForMergingProperty) {
        super();
        this.isRunForMergingProperty = isRunForMergingProperty;
        this.original = null;
        this.context = context;
        this.composite = false;
    }

    /**
     * Adds another value to this merged value. If its a reference merged value the first value will be the original
     * value.
     *
     * @param value The value to add.
     */
    void addToMerge(Object value) {
        if (composite && !(value instanceof cmof.reflection.Object)) {
            throw new MergeException("Attempt to merge non object values in a composite merged value.");
        }
        if (!isToMerge(value)) {
            throw new MergeException("Value added to incompatible merged value.");
        }
        if (!composite && original == null) {
            original = value;
        } else {
            if (!isRunForMergingProperty && composite) {
                // for copys: equivalent values are ignored, in order not to create unecessary copies
                if (original != null) {
                    if (context.isEquivalent(original, value)) {
                        return;
                    }
                }
                for (Object o: values) {
                    if (context.isEquivalent(o, value)) {
                        return;
                    }
                }
            }
            values.add(value);
        }
    }

    /**
     * Checks whether the value lays in the actual merge context, and can thus be merged with other values.
     * In order to be merged a value must be a NamedElement and it must be member of a namespace that is or lays in
     * one of the toplevel namespaces of this merge context.
     *
     * @param value The value to check.
     */
    boolean inMergeContext(Object value) {
        if (value instanceof NamedElement) {
            Collection<Namespace> nss = new HashSet<Namespace>();
            Namespace actualNs = ((NamedElement)value).getNamespace();
            while (actualNs != null) {
                nss.add(actualNs);
                actualNs = actualNs.getNamespace();
            }
            for (Namespace potentialMergedNamespace : context.getToplevelMergedNamespaces()) {
                if (nss.contains(potentialMergedNamespace)) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    /**
     * Checks whether a value can be added to this merged value or not. To be addable the value must have
     * the same meta-class than the other values in this merged value. If this meta-class is a decendant of
     * NamedElement the name of the element must not be distiguishable from the other values names. Furthermore,
     * the element must not be already a member of this merged value and all values must be in the
     * same merge context ({@link MergedValue#inMergeContext(Object)}). For non element values
     * (that are values that cannot be merged, e.g. primitive values) it will be checked that all values are equal.
     *
     * @param v2 The value that shall be added to the merged value.
     */
    boolean isToMerge(Object v2) {
        if (original == null && values.size() == 0) {
            return true;
        } else {
            Object comparetToValue;
            if (original == null) {
                comparetToValue = values.get(0);
            } else {
                comparetToValue = original;
            }
            return isToMergeWithValue(comparetToValue, v2);
        }
    }

    /**
     * There are to causes that make to object to be merged with each other. One they are structurally the same,
     * for example the same UML Comment merged in twice in a complicated merge. The other is, they have to be merged
     * because they are values of a merging property and have names that indicate a merge.
     *
     * @param comparetToValue
     * @param v2
     * @return Whether the objects have to be merged.
     */
    private boolean isToMergeWithValue(Object comparetToValue, Object v2) {
        if (isRunForMergingProperty) {
            return valuesCanBeMerged(comparetToValue, v2);
        } else if (!isRunForMergingProperty && !composite) {
            return context.isEquivalent(comparetToValue, v2) || valuesCanBeMerged(comparetToValue, v2);
        } else {
            return context.isEquivalent(comparetToValue, v2);
        }
    }

    /**
     * Determines whether two objects have to be merged
     * because they have names that indicate a merge.
     *
     * @param comparetToValue
     * @param v2
     * @return Whether the objects have to be merged.
     */
    private boolean valuesCanBeMerged(Object comparetToValue, Object v2) {
        if (comparetToValue.getClass().equals(v2.getClass())) {
            if (comparetToValue.equals(v2)) {
                return true;
            } else {
                if (comparetToValue instanceof NamedElement && v2 instanceof NamedElement) {
                    NamedElement originalValue = (NamedElement)comparetToValue;
                    NamedElement v2NamedElement = (NamedElement)v2;
                    if (getNameForNamedElement(originalValue).equals(getNameForNamedElement(v2NamedElement))) {
                        if (this.inMergeContext(originalValue) && inMergeContext(v2NamedElement)) {
                            return !values.contains(v2);
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    private String getNameForNamedElement(NamedElement namedElement) {
        String result = context.getConfiguration().getAlternativeName(namedElement);

        if (result == null) {
            result = namedElement.getName();
        }

        if (result == null) {
            throw new MergeException("No name could be created for " + namedElement);
        }
        return result;
    }

    /**
     * Checks whether this merged values represent at least the values than an other merged value.
     * They have to be representing the same finally merged.
     *
     * @param other The other merged value.
     */
    boolean isSupersetMerge(MergedValue other) {
        return isSameValue(other) && containsAllOf(other);
    }

    private boolean containsAllOf(MergedValue other) {
        if (other.original != null) {
            if (!contains(other.original)) {
                return false;
            }
        }
        for (Object otherValue : other.values) {
            if (!contains(otherValue)) {
                return false;
            }
        }
        return true;
    }

    private boolean contains(Object other) {
        return (other.equals(original) || values.contains(other));
    }

    /**
     * Checks whether to merged values represent the same original value.
     *
     * @param other The other merged value.
     */
    boolean isSameValue(MergedValue other) {
        return getOriginalValue().equals(other.getOriginalValue());
    }

    /**
     * Executes necessary merges. For a copy it will create the copy and merges this new merging element with the
     * other valuesl; for a composite it will merge all values into the original value.
     */
    @SuppressWarnings("unchecked")
    void executeMerge() {
        if (composite) {
            boolean copy = false;
            if (original == null) {
                original =
                        context.getFactory().create(((cmof.reflection.Object)values.iterator().next()).getMetaClass());
                copy = true;
            }
            if (original instanceof Element) {
                context.merge((Element)original, (Collection)values, copy);
            }
        }
        merged = true;
    }

    /**
     * Returns the original value of this merged value. This is for composite values the value of the merging element.
     * in all others the first value that was added to the merge.
     */
    Object getOriginalValue() {
        if (original == null) {
            return values.get(0);
        } else {
            return original;
        }

    }

    /**
     * Returns the finally merged value. Searches for merges in the context to return a single value that is the
     * result of a merge of all values in this merged value. For composite values this is the original value, for
     * copies the finally copies and merged value, for references the result of a merge executed at some point else
     * in the context. All merges in the whole context have to be executed prior to the call of this method.
     */
    Object getMergedValue() {
        if (!merged) {
            throw new MergeException("Attempt to retrieve merged value from merged value without prior merge.");
        }
        if (composite || !(original instanceof Element)) {
            return original;
        } else {
            return context.getMergedElement((Element)original);
        }        
    }
}
