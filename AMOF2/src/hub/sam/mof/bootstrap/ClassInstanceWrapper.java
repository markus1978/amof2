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

package hub.sam.mof.bootstrap;

import cmof.Property;
import cmof.UmlClass;
import hub.sam.mof.instancemodel.ClassInstance;
import hub.sam.mof.instancemodel.StructureSlot;
import hub.sam.mof.instancemodel.ValueSpecification;
import hub.sam.mof.instancemodel.ValueSpecificationList;
import hub.sam.mof.reflection.ObjectImpl;
import hub.sam.mof.util.ReadOnlyListWrapper;
import hub.sam.mof.util.Wrapper;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

public class ClassInstanceWrapper extends ClassInstance<UmlClass,Property,java.lang.Object> {

    private final ClassInstance<ClassInstance,ClassInstance,Object> instance;
    private final BootstrapModel model;
    private final BootstrapExtent extent;

    @SuppressWarnings("unchecked")
	protected ClassInstanceWrapper(ClassInstance instance, BootstrapModel model, BootstrapExtent extent) {
        super(null, null, null);
        setPrimaryIdentity(instance);
        this.instance = instance;
        this.model = model;
        this.extent = extent;
    }

    protected ClassInstance<ClassInstance,ClassInstance,Object> getInstance() {
        return instance;
    }

    @Override
	public UmlClass getClassifier() {
        return (UmlClass)extent.getObjectForInstance(model.getWrapper(instance.getClassifier()));
    }

    @Override
	public StructureSlot<UmlClass,Property,java.lang.Object> get(Property property) {
        return new StructureSlotWrapper(instance.get(((ClassInstanceWrapper)((ObjectImpl)property).getClassInstance()).instance));
    }

    @Override
	public ClassInstance<UmlClass,Property,java.lang.Object> getComposite() {
        return model.getWrapper(instance.getComposite());
    }

    @Override
	public Collection<ClassInstance<UmlClass,Property,java.lang.Object>> getComponentsAsCollection() {
        Collection<ClassInstance<UmlClass,Property,java.lang.Object>> result = new HashSet<ClassInstance<UmlClass,Property,java.lang.Object>>();
        for (ClassInstance<ClassInstance, ClassInstance, java.lang.Object> component: instance.getComponentsAsCollection()) {
            result.add(model.getWrapper(component));
        }
        return Collections.unmodifiableCollection(result);
    }

    class StructureSlotWrapper extends StructureSlot<UmlClass,Property,Object> {
        private final StructureSlot<ClassInstance,ClassInstance,Object> slot;
        protected StructureSlotWrapper(StructureSlot<ClassInstance,ClassInstance,Object> slot) {
            super(null, null);
            setPrimaryIdentity(slot);
            this.slot = slot;
        }
        @Override
		public ValueSpecificationList<UmlClass,Property,Object> getValuesAsList(
                ValueSpecification<UmlClass,Property,Object> qualifier) {
            if (slot == null) {
                return new ValueList(new Vector<ValueSpecification<ClassInstance,ClassInstance,Object>>(), null);
            }
            return new ValueList(slot.getValues(null), new Wrapper<ValueSpecification<UmlClass,Property,Object>,ValueSpecification<ClassInstance,ClassInstance,Object>>() {
                public ValueSpecification<UmlClass, Property, Object> getE(ValueSpecification<ClassInstance, ClassInstance, Object> forO) {
                    if (forO.asDataValue() != null) {
                        return model.getMofModel().createPrimitiveValue(forO.asDataValue().getValue());
                    } else {
                        return model.getMofModel().createInstanceValue(model.getWrapper(forO.asInstanceValue().getInstance()));
                    }
                }

                @SuppressWarnings("unchecked")
				public ValueSpecification<ClassInstance, ClassInstance, Object> getO(ValueSpecification<UmlClass, Property, Object> forE) {
                    if (forE.asDataValue() != null) {
                        return (ValueSpecification)model.createPrimitiveValue(forE.asDataValue().getValue());
                    } else {
                        return (ValueSpecification)model.createInstanceValue((ClassInstance)((ClassInstanceWrapper)forE.asInstanceValue().getInstance()).instance);
                    }
                }
            });
        }

        class ValueList extends ReadOnlyListWrapper<ValueSpecification<UmlClass,Property,Object>,ValueSpecification<ClassInstance,ClassInstance,Object>> implements ValueSpecificationList<UmlClass,Property,Object> {
            ValueList(List<? extends ValueSpecification<ClassInstance, ClassInstance, Object>> wrapped, Wrapper<ValueSpecification<UmlClass, Property, Object>, ValueSpecification<ClassInstance, ClassInstance, Object>> wrapper) {
                super(wrapped, wrapper);
            }
        }
    }
}