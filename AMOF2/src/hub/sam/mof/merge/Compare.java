package hub.sam.mof.merge;

import cmof.Property;
import cmof.common.ReflectiveCollection;
import cmof.common.ReflectiveSequence;
import core.abstractions.ownerships.Element;
import hub.sam.mof.mofinstancemodel.MofClassSemantics;
import hub.sam.mof.mofinstancemodel.MofClassifierSemantics;
import hub.sam.util.MultiMap;

import java.util.Collection;
import java.util.Collections;

/**
 * Compare allows recursive comparison of two models; the recursion is along composition.
 */
public class Compare {

    @SuppressWarnings({"unchecked"})
    public static Difference cachedCompare(Object o1, Object o2) {
        return new Compare(Collections.EMPTY_LIST).compare(o1,o2);
    }

    private final MultiMap<Object, Object> comparedElements = new MultiMap<Object, Object>();
    private final Collection<Property> doNotConvert;

    public Compare(Collection<Property> doNotConvert) {
        super();
        this.doNotConvert = doNotConvert;
    }

    public Difference compare(Object o1, Object o2) {
        if (!compareAsPrimitives(o1, o2)) {
            return new Difference("Objects differ primitively.", o1, o2, null, null, null, null);
        }

        if (comparedElements.get(o1).contains(o2) || comparedElements.get(o2).contains(o1)) {
            return null;
        } else {
            comparedElements.put(o1, o2);
            comparedElements.put(o2, o1);
        }

        if ((o1 instanceof Element) && (o2 instanceof Element)) {
            return compareAsElement((Element)o1, (Element)o2);
        } else {
            return null;
        }
    }

    /**
     * Compares two objects as being primitive values. Returns true even if they are not necessarily equal but
     * instanced of Element; in this case they still have to be compared by element.
     */
    private boolean compareAsPrimitives(Object o1, Object o2) {
        if ((o1 == null || o2 == null) && (o1 != null || o2 != null)) {
            return false;
        } else if (o1 == o2) {
            return true;
        } else if (o1.equals(o2)) {
            return true;
        } else if (!o1.getClass().equals(o2.getClass())) {
            return false;
        } else {
            return (o1 instanceof Element) && (o2 instanceof Element);
        }
    }

    @SuppressWarnings({"unchecked", "OverlyNestedMethod", "OverlyLongMethod"})
    private Difference compareAsElement(Element e1, Element e2) {
        MofClassSemantics semantics =
                MofClassifierSemantics.createClassClassifierForUmlClass(((cmof.reflection.Object)e1).getMetaClass());

        PropertyLoop:
        for (Property property : semantics.getFinalProperties()) {
            if (doNotConvert.contains(property) || property.isDerived() || property.isDerivedUnion() ||
                    (property.getOpposite() != null && property.getOpposite().isComposite())) {
                continue PropertyLoop;
            }

            if (semantics.isCollectionProperty(property)) {
                ReflectiveCollection values1 =
                        (ReflectiveCollection)((cmof.reflection.Object)e1).get(property);
                ReflectiveCollection values2 =
                        (ReflectiveCollection)((cmof.reflection.Object)e2).get(property);
                if (values1.size() != values2.size()) {
                    return new Difference(
                            "The objects have differenc numbers of values for a property",
                            e1, e2, property, values1, values2, null);
                }
                if (property.isOrdered()) {
                    int size = values1.size();
                    for (int i = 0; i < size; i++) {
                        Object v1 = ((ReflectiveSequence)values1).get(i);
                        Object v2 = ((ReflectiveSequence)values1).get(i);
                        Difference difference = compare(v1, v2);
                        if (difference != null) {
                            return new Difference("(2) Two values differ.", e1, e2, property, null, null, difference);
                        }
                    }
                } else {
                    for (Object v1 : values1) {
                        boolean foundV1 = false;
                        for (Object v2 : values2) {
                            Difference compareResult = compare(v1, v2);
                            if (compareResult == null) {
                                foundV1 = true;
                            }
                        }
                        if (!foundV1) {
                            return new Difference(
                                    "The value " + v1 + " of object one could not be found in the other object", e1, e2,
                                    property, values1, values2, null);
                        }
                    }
                }
            } else {
                Object v1 = ((cmof.reflection.Object)e1).get(property);
                Object v2 = ((cmof.reflection.Object)e2).get(property);
                Difference difference = compare(v1, v2);
                if (difference != null) {
                    return new Difference("(1) Two values differ.", e1, e2, property, null, null, difference);
                }
            }
        }
        return null;
    }
}
