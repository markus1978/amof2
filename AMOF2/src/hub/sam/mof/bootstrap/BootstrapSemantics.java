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

import hub.sam.mof.instancemodel.ClassInstance;
import hub.sam.mof.instancemodel.ClassifierSemantics;
import hub.sam.mof.instancemodel.CommonClassifierSemantics;
import hub.sam.mof.instancemodel.StructureSlot;
import hub.sam.mof.instancemodel.ValueSpecificationImpl;
import hub.sam.mof.javamapping.JavaMapping;

import java.util.Collection;
import java.util.List;
import java.util.Vector;

public class BootstrapSemantics extends CommonClassifierSemantics<ClassInstance<ClassInstance,ClassInstance,Object>,ClassInstance<ClassInstance,ClassInstance,Object>,Object,String>
        implements ClassifierSemantics<ClassInstance<ClassInstance,ClassInstance,Object>,Object,String> {

    private final BootstrapModel model;
    private final JavaMapping mapping = hub.sam.mof.javamapping.JavaMapping.mapping;

    public BootstrapSemantics(ClassInstance<ClassInstance,ClassInstance,Object> classifier, BootstrapModel model) {
        super(classifier);
        this.model = model;
        initialize();
    }

    public String getName(ClassInstance<ClassInstance, ClassInstance, Object> forProperty) {
        StructureSlot<ClassInstance,ClassInstance,Object> slot = forProperty.get(model.getPropertyNames().get("cmof.NamedElement.name"));
        if (slot == null) {
            throw new NullPointerException();
        }
        return slot.getValues(null).get(0).asDataValue().getValue().toString();
    }

    public boolean isCollectionProperty(ClassInstance<ClassInstance, ClassInstance, Object> forProperty) {
        StructureSlot<ClassInstance,ClassInstance,Object> slot = forProperty.get(model.getPropertyNames().get("core.abstractions.multiplicities.MultiplicityElement.upper"));
        if (slot == null) {
            return false;
        } else {
            return new Integer(slot.getValues(null).get(0).asDataValue().getValue().toString()).intValue() != 1;
        }
    }

    @SuppressWarnings("unchecked")
	@Override
	public String getJavaGetMethodNameForProperty(ClassInstance<ClassInstance, ClassInstance, Object> forProperty) {
        String name = (String)model.get(forProperty, "name");
        ClassInstance type = (ClassInstance)model.get(forProperty, "type");
        if (model.get((ClassInstance)type.getClassifier(), "name").equals(cmof.PrimitiveType.class.getSimpleName())) {
            if (model.get(type,"name").equals(core.primitivetypes.Boolean.class.getSimpleName())) {
                return mapping.getJavaGetMethodNameForProperty(name, true);
            }
        }
        return mapping.getJavaGetMethodNameForProperty(name, false);
    }

    private Collection<ClassInstance<ClassInstance,ClassInstance,Object>> get(ClassInstance<ClassInstance,ClassInstance,Object> instance, String property) {
        StructureSlot<ClassInstance,ClassInstance,Object> slot = instance.get(model.getPropertyNames().get(property));
        if (slot == null) {
            return new Vector<ClassInstance<ClassInstance,ClassInstance,Object>>(0);
        }
        List<ValueSpecificationImpl<ClassInstance,ClassInstance,Object>> values = slot.getValues(null);
        Collection<ClassInstance<ClassInstance,ClassInstance,Object>> result = new Vector<ClassInstance<ClassInstance,ClassInstance,Object>>(values.size());
        for (ValueSpecificationImpl<ClassInstance,ClassInstance,Object> value: values) {
            result.add(value.asInstanceValue().getInstance());
        }
        return result;
    }

    @Override
	protected Collection<? extends ClassInstance<ClassInstance, ClassInstance, Object>> ownedProperties(ClassInstance<ClassInstance, ClassInstance, Object> c) {
        return get(c, "cmof.Class.ownedAttribute");
    }

    @Override
	protected Collection<? extends ClassInstance<ClassInstance, ClassInstance, Object>> redefinedProperties(ClassInstance<ClassInstance, ClassInstance, Object> p) {
        return get(p, "cmof.Property.redefinedProperty");
    }

    @Override
	protected Collection<? extends ClassInstance<ClassInstance, ClassInstance, Object>> subsettedProperties(ClassInstance<ClassInstance, ClassInstance, Object> p) {
        return get(p, "cmof.Property.subsettedProperty");
    }

    @Override
	protected Collection<? extends ClassInstance<ClassInstance, ClassInstance, Object>> superClasses(ClassInstance<ClassInstance, ClassInstance, Object> c) {
        return get(c, "cmof.Class.superClass");
    }
}
