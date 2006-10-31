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

package hub.sam.mof.mofinstancemodel;

import cmof.DataType;
import cmof.Property;
import cmof.Type;
import cmof.UmlClass;
import cmof.exception.IllegalArgumentException;
import cmof.exception.IllegalAccessException;
import hub.sam.mof.instancemodel.ClassInstance;
import hub.sam.mof.instancemodel.StructureSlot;
import hub.sam.mof.instancemodel.ValueSpecificationImpl;
import hub.sam.mof.instancemodel.ValueSpecification;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class MofClassInstance extends ClassInstance<UmlClass,Property,java.lang.Object> {
    protected MofClassInstance(UmlClass classifier, MofInstanceModel model) {
        this(MofClassifierSemantics.createClassClassifierForUmlClass(classifier), model);
    }

    private MofClassInstance(MofClassSemantics instanceClassifier, MofInstanceModel model) {
        super(hub.sam.util.Unique.getUnique().toString(), instanceClassifier.getClassifier(), model);
        instanceClass = instanceClassifier;
        if (instanceClassifier != null) {
            initialize();
        }
    }

    private MofClassSemantics instanceClass;
    private Map<Property, MofStructureSlot> slotForProperty = new HashMap<Property, MofStructureSlot>();

    @Override
	protected Map<Property, StructureSlot<UmlClass,Property,java.lang.Object>> createSlots() {
    	return null;
    }

    /**
     * Creates all slots for the instance and sets default values.
     */
    protected void initialize() {
        for (Property property: instanceClass.getFinalProperties()) {
            MofStructureSlot newSlot = createSlot(property);
            slotForProperty.put(property, newSlot);
        }

        // set default values
        for (core.abstractions.elements.Element member: getClassifier().getMember()) {
            if (member instanceof Property) {
                Property property = (Property)member;
                if (!property.isDerived()) {
                    Type type = (cmof.Type)property.getType();
                    String defaultValue = collectDefaultValue(property);
                    if (type instanceof cmof.DataType && defaultValue != null) {
                        addValue(property, getModel().createPrimitiveValue(
                                hub.sam.mof.reflection.FactoryImpl.staticCreateFromString(
                                        (DataType)type, defaultValue)), null);
                    }
                }
            }
        }
    }

    protected MofStructureSlot createSlot(Property property) {
        return new MofStructureSlot(this, property);
    }

    private String collectDefaultValue(Property property) {
        String result = property.getDefault();
        if (result != null) {
            return result;
        } else {
            for (Property redefinedProperty: property.getRedefinedProperty()) {
                result = collectDefaultValue(redefinedProperty);
                if (result != null) {
                    return result;
                }
            }
            return null;
        }
    }

    @Override
	public UmlClass getClassifier() {
        return super.getClassifier();
    }

    /** Returns all slots of this instance. A Slot contains the value of a signle detinct feature of the defining metaclass.*/
    public Collection<MofStructureSlot> getSlot() {
        checkValid();
        return new HashSet<MofStructureSlot>(slotForProperty.values());
    }

    /** Returns the slot that carries the value for the feature that is the final redefining feature for the given feature
     * and the metaclass of this instance. */
    @Override
	public MofStructureSlot get(Property definingFeature) {
        checkValid();
        return slotForProperty.get(instanceClass.getFinalProperty(definingFeature));
    }

    /** Returns the values for the feature that is the final redefining feature for the given feature and the metaclass of
     * this instance.
     */
    public MofValueSpecificationList getValuesOfFeature(Property definingFeature,
            ValueSpecification<UmlClass,Property,java.lang.Object> qualifier) {
        MofStructureSlot theSlot = get(definingFeature);
        if (theSlot == null) {
            throw new IllegalArgumentException(definingFeature);
        } else {
            return theSlot.getValuesAsList(qualifier);
        }
    }

    @Override
	public void addValue(Property feature, ValueSpecificationImpl<UmlClass,Property,java.lang.Object> value,
                      ValueSpecification<UmlClass,Property, Object> qualifier) {
        if (feature.getUpper() == 1) {
            MofValueSpecificationList values = getValuesOfFeature(feature, qualifier);
            if (values.size() == 0) {
                values.add(value);
            } else {
                values.set(0, value);
            }
        } else {
            getValuesOfFeature(feature, qualifier).add(value);
        }
    }

    private void checkValid() {
        if (!isValid()) {
            throw new IllegalAccessException("access of an invalid instance");
        }
    }

    /**
     * Deletes itself, all components, removes all references to itself or to
     * all components.
     */
    @Override
	public void delete() {
        for (MofClassInstance component: new HashSet<MofClassInstance>(getComponents())) {
            component.delete();
        }
        MofValueSpecificationList.deleteInstance(this);
        slotForProperty = null;
        super.delete();
    }

    public MofClassSemantics getInstanceClassifier() {
        return instanceClass;
    }

    @Override
    @SuppressWarnings("unchecked")
	public Collection<MofClassInstance> getComponents() {
        return (Collection<MofClassInstance>)super.getComponents();
    }


	@SuppressWarnings({"unchecked", "RedundantCast"})
	@Override
	public Collection<StructureSlot<UmlClass,Property,java.lang.Object>> getSlots() {
        return (Collection)slotForProperty.values();
    }

    @Override
	protected void myFinalize() {
        super.myFinalize();
        instanceClass = null;
        if (slotForProperty != null) {
        	slotForProperty.clear();
        	slotForProperty = null;
        }
    }
}
