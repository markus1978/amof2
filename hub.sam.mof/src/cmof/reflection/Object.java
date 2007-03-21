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

package cmof.reflection;

import java.beans.PropertyChangeListener;

import cmof.*;
import cmof.common.*;
import cmof.exception.*;
import cmof.exception.IllegalArgumentException;
import hub.sam.mof.jocl.standardlib.OclModelElement;
import hub.sam.util.IAdaptable;

/**
 * Every Object has a Class which describes its properties and operations. The
 * Object is an Instance of this Class. Object extends
 * {@link core.abstractions.elements.Element}. All model elements that
 * specialize Object inherit reflective capabilities. In particular, this
 * includes all model elements from UML2 Infrastructure.<br/> If any reflective
 * operation attempts to create cyclic containment, an
 * {@link CompositeViolation} is thrown.<br/> <b>Constraints</b>: No additional
 * constraints.<br/> <b>SemanticsClass</b>: Object is the superclass of all
 * model elements in MOF, and is the superclass of all instances of MOF model
 * elements. Each object can access its metaClass in order to obtain a Class
 * that provides a reflective description of that object. By having both MOF and
 * instances of MOF be rooted in class Object, MOF supports any number of meta
 * layers as described in Chapter 1, MOF Architecture. The following describes
 * the interaction between default values, null, isSet, and unSet.<br/>
 * <b>Single-valued properties:</b>
 * <p>
 * If a single-valued property has a default:
 * <ul>
 * <li>It is set to that default value when the object is created. isSet=false</li>
 * <li>If the value of that property is later explicitly set, even to the
 * default value, isSet=true</li>
 * <li>If the property is unSet, then the value of the property returns to the
 * default, and isSet=false</li>
 * </ul>
 * If a single-valued property does not have a default:
 * <ul>
 * <li> At creation, its value is null. isSet=false</li>
 * <li>If the value of that property is later explicitly set, even to null,
 * isSet=true</li>
 * <li>If the property is unSet, then the value of the property returns to
 * null, and isSet=false</li>
 * </ul>
 * <b>Multi-valued properties:</b>
 * <ul>
 * <li>When the object is created, it is an empty list. isSet=false</li>
 * <li>If the list is modified in any way (except unSet), isSet=true</li>
 * <li>If the list is unSet, it is cleared and becomes an empty list.
 * <li>
 * </ul>
 * The implementation of isSet is up to the implementer. In the worst case it
 * can be implemented by having an additional boolean, but usually for a
 * particular implementation it can be implemented more efficiently (e.g. by
 * having an internal distinguished value used to represent "no value set"). For
 * default values, implementations are not required to access stored metadata at
 * runtime. It is adequate to generate a constant in the implementation class
 * for the default.
 * <p>
 * <b>Rationale</b>
 * <p>
 * Object is introduced in package Reflection so that it can be combined with
 * Core::Basic to produce EMOF which can then be merged into CMOF to provide
 * reflective capability to MOF and all instances of MOF.
 */
public interface Object extends IAdaptable {

    /**
     * Returns the Class that describes this object.
     */
    public UmlClass getMetaClass();

    /**
     * Returns the parent container of this object if any. Return Null if there
     * is no containing object.
     */
    public Object container();

    /**
     * Gets the value of the given property. If the Property has multiplicity
     * upper bound of 1, get() returns the value of the Property. If Property
     * has multiplicity upper bound >1, get() returns a
     * {@link cmof.common.ReflectiveSequence} containing the values of the
     * Property. If there are no values, the ReflectiveSequence returned is
     * empty.
     *
     * @throws IllegalArgumentException
     *             if Property is not a member of the Class from class().
     */
    public java.lang.Object get(Property property);

    public java.lang.Object get(Property property, java.lang.Object qualifier);

    /**
     * If the Property has multiplicity upper bound = 1, set() atomically
     * updates the value of the Property to the Element parameter. If Property
     * has multiplicity upper bound >1, the Element may be either a
     * ReflectiveCollection or a ReflectiveSequence. The behavior is identical
     * to the following operations performed atomically:<p/>
     *
     * <pre>
     * ReflectiveSequence list = object.get(property);
     * list.clear();
     * list.addAll((ReflectiveSequence) element);
     * </pre>
     *
     * @throws IllegalArgumentException
     *             if Property is not a member of the Class from
     *             getMeta-Class().
     * @throws ClassCastException
     *             if the Property’s type isInstance(element) returns false and
     *             Property has multiplicity upper bound = 1
     * @throws ClassCastException
     *             if Element is not a ReflectiveSequence and Property has
     *             multiplicity upper bound > 1
     * @throws IllegalArgumentException
     *             if element is null, Property is of type Class, and the
     *             multi-plicity upper bound > 1.
     */
    public void set(Property property, java.lang.Object value);

    public void set(Property property, java.lang.Object qualifier, java.lang.Object value);

    /**
     * If the Property has multiplicity upper bound of 1, isSet() returns true
     * if the value of the Property is different than the default value of that
     * property. If Property has multiplicity upper bound >1, isSet() returns
     * true if the number of elements in the list is > 0.
     *
     * @throws IllegalArgumentException
     *             if Property is not a member of the Class from class().
     */
    public boolean isSet(Property property) throws IllegalArgumentException;

    public boolean isSet(Property property, java.lang.Object qualifier) throws IllegalArgumentException;

    /**
     * If the Property has multiplicity upper bound of 1, unset() atomically
     * sets the value of the Property to its default value for DataType type
     * properties and null for Class type properties. If Property has
     * multiplicity upper bound >1, unset() clears the ReflectiveSequence of
     * values of the Property. The behavior is identical to the following
     * operations performed atomically:
     *
     * <pre>
     * ReflectiveSequence list = object.get(property);
     * list.clear();
     * </pre>
     *
     * After unset() is called:
     *
     * <pre>
     * object.isSet(property) == false
     * </pre>.
     *
     * @throws IllegalArgumentException
     *             if Property is not a member of the Class from getMetaClass().
     */
    public void unset(Property property);

    public void unset(Property property, java.lang.Object qualifier);

    /**
     * Determines if the element equals this Object instance. For instances of
     * Class, returns true if the element and this Object instance are
     * references to the same Element. For instances of DataType, returns true
     * if the element has the same value as this Object instance. Returns false
     * for all other cases.
     */
    public boolean equals(java.lang.Object element);

    /**
     * Deletes the instance, all its contained objects and all references to
     * this instance.
     */
    public void delete();

    /**
     * Invokes a given operation. TODO
     */
    public java.lang.Object invokeOperation(Operation op,
                                            ReflectiveSequence<Argument> arguments);

    /**
     * Checks whether the instance's type conforms to another type. Returns
     * true:
     * <ul>
     * <li>if type equals the instance's type</li>
     * <li>if includeSubTypes==true and type is a supertype of the instance's
     * type</li>
     * </ul>
     * Returns false in all other cases.
     */
    public boolean isInstanceOfType(UmlClass type, boolean includeSubTypes);

    /**
     * Returns the outermost container of the instance. The outermost container
     * is a object that is not contained in another object and that directly or
     * inderectly contains this instance.
     *
     * <i>This is not part of the MOF 2.0</i>
     */
    public Object getOutermostContainer();

    /**
     * Returns a collection of objects that are contained in this instance. The
     * returned collection contains all contained objects.
     *
     * <i>This is not part of the MOF 2.0</i>
     */
    public ReflectiveCollection<? extends cmof.reflection.Object> getComponents();

    public Extent getExtent();

    public java.lang.Object get(String property);

    public java.lang.Object get(String property, java.lang.Object qualifier);

    public void set(String property, java.lang.Object value);

    public void set(String property, java.lang.Object qualifier, java.lang.Object value);

    public java.lang.Object invokeOperation(String opName, java.lang.Object[] args);

    // TODO align with PropertyChangeListener stuff
    public void addObjectEventHandler(ObjectChangeListener handler);
    
    public void removeObjectEventHandler(ObjectChangeListener handler);

    public OclModelElement ocl();

    public void addListener(PropertyChangeListener listener);
    
    public void addListener(String propertyName, PropertyChangeListener listener);

    public void removeListener(PropertyChangeListener listener);
    
    public void removeListener(String propertyName, PropertyChangeListener listener);
}
