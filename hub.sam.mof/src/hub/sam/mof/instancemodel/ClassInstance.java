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

import cmof.Property;
import cmof.exception.*;

import hub.sam.mof.Repository;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ClassInstance<C,P,DataValue> extends hub.sam.util.Identity {
    private final Map<P, StructureSlot<C,P,DataValue>> slots = createSlots();
    private C classifier;
    private ClassInstance<C,P,DataValue> composite = null;
    private Collection<ClassInstance<C,P,DataValue>> components = new HashSet<ClassInstance<C,P,DataValue>>();
    private InstanceModel<C,P,DataValue> model;
    private boolean valid = true;

    protected ClassInstance(String id, C classifier, InstanceModel<C,P,DataValue> model) {
        super(id);
        setParentIdentity(model);
        this.classifier = classifier;
        this.model = model;
    }

    protected Map<P, StructureSlot<C,P,DataValue>> createSlots() {
        return new HashMap<P, StructureSlot<C,P,DataValue>>();
    }

    protected InstanceModel<C,P,DataValue> getModel() {
        return model;
    }

    protected Collection<? extends ClassInstance<C,P,DataValue>> getComponents() {
        return components;
    }

    public void addValue(P feature, ValueSpecificationImpl<C,P,DataValue> value,
                         ValueSpecification<C,P,DataValue> qualifier) {
        if (feature == null) {
            throw new NullPointerException();
        }
        StructureSlot<C,P,DataValue> slot = slots.get(feature);
        if (slot == null) {
            slot = new StructureSlot<C,P,DataValue>(feature, this);
            slots.put(feature, slot);
        }
        slot.addValue(value, qualifier);
    }

    public C getClassifier() {
        return classifier;
    }

    public void setClassifier(C classifier) {
        if (this.classifier == null) {
            this.classifier = classifier;
        } else {
            throw new cmof.exception.IllegalAccessException("this property is final");
        }
    }

    public Collection<StructureSlot<C,P,DataValue>> getSlots() {
        return slots.values();
    }

    public StructureSlot<C,P,DataValue> get(P property) {
        return slots.get(property);
    }

    @Override
    public String getId() {
        return super.getId().toString();
    }

    public ClassInstance<C, P, DataValue> getComposite() {
        return composite;
    }

    public final void setComposite(ClassInstance<C, P, DataValue> composite) {
        if (getComposite() != null && composite != null && composite != getComposite()) {        
        	Property name = (Property)Repository.getLocalRepository().getExtent(Repository.CMOF_EXTENT_NAME).query("Package:cmof/Class:NamedElement/Property:name");
        	System.out.println(getComposite().get((P)name));
        	System.out.println(composite.get((P)name));
            throw new CompositeViolation(composite);
        }
        if (this.composite != null) {
            this.composite.components.remove(this);
        }
        this.composite = composite;
        if (this.composite != null) {
            this.composite.components.add(this);
        }
        if (this.composite != null) {
            model.removeOutermostComposite(this);
        } else {
            model.addOutermostComposite(this);
        }
    }

    public final void changeComposite(ClassInstance<C, P, DataValue> newComposite) {
        if (this.composite == null && newComposite != null) {
            model.removeOutermostComposite(this);
        } else if (newComposite == null) {
            model.addOutermostComposite(this);
        }
        if (this.composite != null) {
            this.composite.components.remove(this);
        }
        this.composite = newComposite;
        if (this.composite != null) {
            this.composite.components.add(this);
        }
    }

    public Collection<ClassInstance<C,P,DataValue>> getComponentsAsCollection() {
        return Collections.unmodifiableCollection(components);
    }

    public void delete() {
        valid = false;
        setComposite(null);
        model.deleteInstance(this);
        myFinalize();
    }

    public boolean isValid() {
        return valid;
    }

    @SuppressWarnings("unchecked")
    protected void myFinalize() {
        if (slots != null) {
            for (StructureSlot slot: slots.values()) {
                slot.myFinalize();
            }
            slots.clear();
        }
        if (components != null) {
	        components.clear();
	        components = null;
        }
        model = null;
        super.myFinalize();
    }
}
