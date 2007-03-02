package hub.sam.mof.merge;

import core.abstractions.ownerships.Element;
import hub.sam.util.MultiMap;

import java.util.Collection;

/**
 * There are two scopes when perfoming a merge operation. One is covered by {@link MergeContext} the other by
 * a GlobalMergeContext. Most information is handled in the smaller scope of MergeContext. GlobalMergeContext covers
 * the equivalence relation of e.g. multiple package merges (each with its won MergeContext). Equivalence is used to
 * determine whether two elements have too be copies or merged again when they are merged multiple times in a
 * GlobalMergeContext.
 *
 * For example the UML L>1 meta-model and its merged. Due to the fact that some elements are merged or copied
 * into a package indirectly (thus by being merged from a package that itself is merged into onother package) and
 * those indirections can occur multiple times in a single MergeContext, a element can be replicated several times,
 * unwanted. Package A merged B1 and B2 and B1 merges C and B2 also merges C and Comment c lays in C; this would result
 * in c beging merged into B1 and B2 and thus two times into A.
 *
 * This context holds equivalence. Thus c in B1 and c in B2 are equivalent and only once merged into A.
 */
public class GlobalMergeContext {
    private final MultiMap<Object, Object> equivalence = new MultiMap<Object, Object>();

    /**
     * Declares a merge. It is not executed. Only used to mark the mergingElement being equivalent to the
     * mergedElements. In the case of an copy this equivalence is symentric, all mergedElements also be equivalent
     * to the mergingElement.
     *
     * @param mergingElement The element that is merged into.
     * @param mergedElements The elements that are merged into the merging element.
     * @param copy           The merge to execute is actually just a copy (all values will be removed from the merging element
     *                       before the merge is executed).
     */
    public void merge(Element mergingElement, Collection<Element> mergedElements, boolean copy) {
        Collection<Object> forMergingElement = equivalence.get(mergingElement);
        if (copy) {
            forMergingElement.add(mergingElement);
            for(Element mergedElement :mergedElements) {
                Collection<Object> forMergedElement = equivalence.get(mergedElement);
                forMergingElement.add(mergedElement);
                forMergedElement.add(mergingElement);
                forMergingElement.addAll(forMergedElement);
                for(Object indirectElement : equivalence.get(mergedElement)) {
                    Collection<Object> forIndirectElement = equivalence.get(indirectElement);
                    if (forIndirectElement.contains(mergedElement)) {
                        forIndirectElement.add(mergingElement);
                    }
                }
            }
        } else {
            forMergingElement.add(mergingElement);
            for(Element mergedElement : mergedElements) {
                forMergingElement.add(mergedElement);
                forMergingElement.addAll(equivalence.get(mergedElement));
            }
        }
    }

    /**
     * Determines whether two objects are equivalence based on prior executed merges.
     */
    public boolean isEquivalent(Object o1, Object o2) {
        if (o1 instanceof Element && o2 instanceof Element) {
            if (o1.equals(o2)) {
                return true;
            } else {
                return equivalence.get(o1).contains(o2);
            }
        } else {
            if (o1 == null) {
                return o2 == null;
            } else {
                return o1.equals(o2);
            }
        }
    }

    public Collection<Object> getEquivalents(Object obj) {
        return equivalence.get(obj);
    }
}
