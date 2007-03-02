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
import hub.sam.mof.instancemodel.InstanceModel;
import hub.sam.mof.instancemodel.StructureSlot;
import hub.sam.mof.instancemodel.ValueSpecificationImpl;
import hub.sam.mof.reflection.ObjectImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class BootstrapModel extends InstanceModel<ClassInstance<ClassInstance,ClassInstance,Object>,ClassInstance<ClassInstance,ClassInstance,Object>,Object> {

    private BootstrapExtent extent = null;
    private InstanceModel<UmlClass,Property,Object> mofModel = new InstanceModel<UmlClass,Property,Object>();
    private Map<ClassInstance,ClassInstanceWrapper> wrapper = new HashMap<ClassInstance,ClassInstanceWrapper>();
    private Map<ClassInstance,ClassifierSemanticsWrapper> semanticsWrapper = new HashMap<ClassInstance,ClassifierSemanticsWrapper>();
    private Map<String, ClassInstance<ClassInstance,ClassInstance,Object>> propertyNames = null;

    @Override
	public ClassInstance<ClassInstance<ClassInstance,ClassInstance,Object>,ClassInstance<ClassInstance,ClassInstance,Object>,Object> createAInstance(String id, ClassInstance<ClassInstance,ClassInstance,Object> classifier) {
        return new BootstrapInstance(id, classifier, this);
    }

    public ClassInstanceWrapper getWrapper(ClassInstance instance) {
        if (instance == null) {
            return null;
        }
        ClassInstanceWrapper result = wrapper.get(instance);
        if (result == null) {
            result = new ClassInstanceWrapper(instance, this, extent);
            wrapper.put(instance,result);
        }
        return result;
    }

    protected void setExtent(BootstrapExtent extent) {
        this.extent = extent;
    }

    private Map<ClassInstance, BootstrapSemantics> semantics = new HashMap<ClassInstance,BootstrapSemantics>();
    protected BootstrapSemantics createBootstrapSemantics(ClassInstance<ClassInstance,ClassInstance,Object> classifier) {
        BootstrapSemantics result = semantics.get(classifier);
        if (result == null) {
            result = new BootstrapSemantics(classifier, this);
            semantics.put(classifier, result);
        }
        return result;
    }

    protected void setPropertyNames(Map<String, ClassInstance<ClassInstance,ClassInstance,Object>> propertyNames) {
        this.propertyNames = propertyNames;
    }

    protected Map<String, ClassInstance<ClassInstance,ClassInstance,Object>> getPropertyNames() {
        return propertyNames;
    }

    @SuppressWarnings("unchecked")
	public ClassifierSemanticsWrapper getSemanticsWrapper(ClassInstance classifier) {
        ClassifierSemanticsWrapper result = semanticsWrapper.get(classifier);
        if (result == null) {
            result = new ClassifierSemanticsWrapper(createBootstrapSemantics(classifier), this);
            semanticsWrapper.put(classifier, result);
        }
        return result;
    }

    protected InstanceModel<UmlClass,Property,Object> getMofModel() {
        return mofModel;
    }

    protected ClassInstance<ClassInstance,ClassInstance,Object> getBootstrapElement(cmof.Element forObject) {
        return ((ClassInstanceWrapper)((ObjectImpl)forObject).getClassInstance()).getInstance();
    }

    protected cmof.reflection.Object getObjectImpl(ClassInstance<ClassInstance,ClassInstance,Object> forInstance) {
        return extent.getObjectForInstance(getWrapper(forInstance));
    }

    @SuppressWarnings("unchecked")
	protected Object get(ClassInstance<ClassInstance,ClassInstance,Object> instance, String property) {
        BootstrapSemantics semantics = createBootstrapSemantics(instance.getClassifier());
        StructureSlot<ClassInstance,ClassInstance,Object> slot =
        		instance.get(semantics.getProperty(property));
        if (slot == null) {
        	return null;
        }
        ValueSpecificationImpl<ClassInstance,ClassInstance,Object> value = slot.getValues(null).get(0);
        if (value.asDataValue() == null) {
            return value.asInstanceValue().getInstance();
        } else {
            return value.asDataValue().getValue();
        }
    }

    @SuppressWarnings("unchecked")
	protected List<ValueSpecificationImpl<ClassInstance,ClassInstance,Object>> getList(ClassInstance<ClassInstance,ClassInstance,Object> instance, String property) {
        try {
            BootstrapSemantics semantics = createBootstrapSemantics(instance.getClassifier());
            StructureSlot slot = instance.get(semantics.getProperty(property));
            if (slot == null) {
            	return new Vector();
            } else {
            	return slot.getValues(null);
            }
        } catch (Exception e) {
            return new Vector<ValueSpecificationImpl<ClassInstance,ClassInstance,Object>>();
        }
    }

    protected void setDependendProperties() {
        for (ClassInstance<ClassInstance<ClassInstance,ClassInstance,Object>,ClassInstance<ClassInstance,ClassInstance,Object>,Object> instance: getInstances()) {
            ((BootstrapInstance)instance).insertDefaults();
            ((BootstrapInstance)instance).insertOppositeValues();
        }
        for (ClassInstance<ClassInstance<ClassInstance,ClassInstance,Object>,ClassInstance<ClassInstance,ClassInstance,Object>,Object> instance: getInstances()) {
            ((BootstrapInstance)instance).insertSupersetValues();
        }
    }
}
