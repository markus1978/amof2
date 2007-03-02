/*
A MOF 2 Java -- The MOF Repository tool for Java
Copyright (C) 2005 Markus Scheidgen

    This library is free software; you can redistribute it and/or modify it
under the terms of the GNU Lesser General Public License as published by the
Free Software Foundation; either version 2.1 of the License, or any later
version.

    This library is distributed in the hope that it will be useful, but WITHOUT
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
details.

    You should have received a copy of the GNU Lesser General Public License
along with this library; if not, write to the Free Software Foundation, Inc.,
59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
*/

package hub.sam.mof.instancemodel;

import hub.sam.util.MultiMap;
import hub.sam.util.Tree;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Vector;


public abstract class CommonClassifierSemantics<C,P,O,Names> implements ClassifierSemantics<P, O, Names> {
    private final C classifier;
    private Collection<P> propertys = null;
    private Collection<P> finalPropertys = null;
    private Collection<P> finalPropertysByName = null;
    private final Map<P, P> finalRedefinitionForProperty = new HashMap<P, P>();
    private MultiMap<P, P> subsetGraph = null;
    private MultiMap<P, P> supersetGraph = null;
    private Map<Names, P> propertyForName = new HashMap<Names, P>();

    protected CommonClassifierSemantics(C classifier) {
        super();
        this.classifier = classifier;
    }

    protected abstract Collection<? extends P> ownedProperties(C c);

    protected abstract Collection<? extends P> redefinedProperties(P p);

    protected abstract Collection<? extends P> subsettedProperties(P p);

    protected abstract Collection<? extends C> superClasses(C c);

    protected void initialize() {
        // Classifier::member is the only set containing really all possibile
        // features (containing especially inherited features
        Collection<P> finalPropertys = new HashSet<P>();
        Collection<P> allPropertiesIncludeRedefined = allPropertiesIncludeRedefined(classifier);

        for (P possibleFeature : memberProperties(classifier)) {
            finalPropertys.add(possibleFeature);
            propertyForName.put(getName(possibleFeature), possibleFeature);
        }

        // setup redefinition graph, subset and superset graph
        Tree<P> redefinitions = new Tree<P>();
        Tree<P> redefineds = new Tree<P>();
        Tree<P> subsetParents = new Tree<P>();
        Tree<P> supersetParents = new Tree<P>();
        for (P property : allPropertiesIncludeRedefined) {
            for (P redefinedProperty : redefinedProperties(property)) {
                redefinitions.put(redefinedProperty, property);
                redefineds.put(property, redefinedProperty);
            }
        }
        for (P property : allPropertiesIncludeRedefined) {
            Collection<P> finalProperties = redefinitions.getLeaves(property);
            if (finalProperties.size() != 1) {
                throw new cmof.exception.MetaModelException(
                        "A property has multiple final properties within one classifier: \n" +
                                "   the classifier is: " + classifier + "\n" +
                                "   the property is: " + property + "\n" +
                                "   the final properties are: " + finalProperties);
            }
            finalRedefinitionForProperty.put(property, finalProperties.iterator().next());
        }
        MultiMap<P, P> allRedefineds = redefineds.collapseTree();
        for (P property : allPropertiesIncludeRedefined) {
            for (P subsettedProperty : subsettedProperties(property)) {
                supersetParents.put(property, subsettedProperty);
                subsetParents.put(subsettedProperty, property);
            }
            for (P redefinedProperty : allRedefineds.get(property)) {
                for (P subsettedProperty : subsettedProperties(redefinedProperty)) {
                    supersetParents.put(property, subsettedProperty);
                    subsetParents.put(subsettedProperty, property);
                }
            }
        }

        this.propertys = allPropertiesIncludeRedefined;
        this.finalPropertysByName = finalPropertys;
        this.finalPropertys = new Vector<P>();
        for (P property : finalPropertysByName) {
            if (property.equals(getFinalProperty(property))) {
                this.finalPropertys.add(property);
            }
        }
        this.subsetGraph = subsetParents.collapseTree();
        this.supersetGraph = supersetParents.collapseTree();
    }

    /**
     * Returns the represented classifier.
     */
    public C getClassifier() {
        return classifier;
    }

    /**
     * Returns a property that redefines the given property (not nessesarily direct) but that
     * is not redefined itself, atleast not within the represented class. This takes inherited
     * propertys into account.
     */
    public P getFinalProperty(P forProperty) {
        if (!propertys.contains(forProperty)) {
            throw new cmof.exception.IllegalArgumentException(forProperty);
        }
        return finalRedefinitionForProperty.get(forProperty);
    }

    /**
     * Returns all propertys that are not redefined by a property within the represented class.
     * This takes inherited propertys into account.
     */
    public Collection<P> getFinalProperties() {
        return finalPropertys;
    }

    /**
     * Returns all propertys that are not redefined by a property with the same name within the represented class.
     * This takes inherited propertys into account.
     */
    public Collection<P> getFinalPropertysByName() {
        return finalPropertysByName;
    }

    /**
     * Returns all propertys, even redefined and inherited ones.
     */
    public Collection<P> getProperties() {
        return propertys;
    }

    /**
     * Returns all propertys (within the represented classifier) that the given property is a superset for.
     */
    public Collection<P> getSupersettedProperties(P forProperty) {
        return subsetGraph.get(forProperty);
    }

    /**
     * Returns all propertys (within the representd classifier) that the given property is a subset of.
     */
    public Collection<P> getSubsettedProperties(P forProperty) {
        return supersetGraph.get(forProperty);
    }

    public P getProperty(Names name) {
        return propertyForName.get(name);
    }

    public String getJavaGetMethodNameForProperty(P forProperty) {
        return "";
    }

    private Collection<P> memberProperties(C classifier) {
        Collection<P> members = new Vector<P>(ownedProperties(classifier));
        Collection<P> inheritedMembers = getInheritedProperties(classifier);
        members.addAll(inheritedMembers);
        return members;
    }

    private Collection<P> getInheritedProperties(C classifier) {
        Collection<P> inheritedMembers = new Vector<P>();
        for (C superClass : superClasses(classifier)) {
            inheritedMembers.addAll(memberProperties(superClass));
        }
        Collection<P> result = new Vector<P>(inheritedMembers);
        for (P inheritedMember : inheritedMembers) {

            for (P redefinedInheritedMember : redefinedProperties(inheritedMember)) {
                if (getName(redefinedInheritedMember).equals(getName(inheritedMember))) {
                    result.remove(redefinedInheritedMember);
                }
            }
        }
        for (P ownedMember : ownedProperties(classifier)) {
            for (P redefinedOwnedElement : redefinedProperties(ownedMember)) {
                if (getName(redefinedOwnedElement).equals(getName(ownedMember))) {
                    result.remove(redefinedOwnedElement);
                }
            }
        }
        return result;
    }

    private Collection<P> allPropertiesIncludeRedefined(C classifier) {
        Collection<P> members = new HashSet<P>(ownedProperties(classifier));
        for (C superClass : superClasses(classifier)) {
            members.addAll(allPropertiesIncludeRedefined(superClass));
        }
        return members;
    }


    public O getFinalOperation(Names name) {
        return null;
    }
}
