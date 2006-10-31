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

import java.util.*;

public class InstanceModel<C,P,DataValue> extends hub.sam.util.Identity {

    private final Map<String, ClassInstance<C,P,DataValue>> instanceForId = new HashMap<String, ClassInstance<C,P,DataValue>>();
    private final Collection<ValueSpecification<C,P,DataValue>> outermostComposites = new HashSet<ValueSpecification<C,P,DataValue>>();

    public final ClassInstance<C,P,DataValue> createInstance(String id, C classifier) {
        ClassInstance<C,P,DataValue> instance = null;
        if (id == null || id.equals("")) {
            instance = createAInstance(id, classifier);
        } else {
            instance = instanceForId.get(id);
            if (instance == null) {
                instance = createAInstance(id, classifier);
                instanceForId.put(id, instance);
            }
        }
        if (instance.getComposite() == null) {
            outermostComposites.add(createInstanceValue(instance));
        }
        return instance;
    }

    public final ClassInstance<C,P,DataValue> createInstance(String id, C classifier, ClassInstance<C,P,DataValue> composite) {
        ClassInstance<C,P,DataValue> instance = null;
        if (id == null || id.equals("")) {
            instance = createAInstance(id, classifier);
        } else {
            instance = instanceForId.get(id);
            if (instance == null) {
                instance = createAInstance(id, classifier);
                instanceForId.put(id, instance);
            }
        }
        instance.setComposite(composite);
        return instance;
    }

    protected ClassInstance<C,P,DataValue> createAInstance(String id, C classifier) {
        return new ClassInstance<C, P, DataValue>(id, classifier, this);
    }

    public Collection<ReferenceValue<C,P,DataValue>> createReferences(String idString) {
        return createReferences(idString, null);
    }

    public Collection<ReferenceValue<C,P,DataValue>> createReferences(String idString, String prefix) {
        String[] ids = idString.split(" ");
        Collection<ReferenceValue<C,P,DataValue>> result = new Vector<ReferenceValue<C,P,DataValue>>(ids.length);
        for (String id: ids) {
            ReferenceValue<C,P,DataValue> ref;
            if (prefix == null) {
                ref = new ReferenceValue<C,P,DataValue>(id, this);
            } else {
                ref = new ReferenceValue<C,P,DataValue>(prefix + id, this);
            }
            result.add(ref);
        }
        return result;
    }

    public UnspecifiedValue<C,P,DataValue> createUnspecifiedValue(Object unspecifiedData) {
        UnspecifiedValue<C,P,DataValue> result = new UnspecifiedValue<C,P,DataValue>(unspecifiedData);
        return result;
    }

    public UnspecifiedValue<C,P,DataValue> createUnspecifiedValue(Object unspecifiedData, Object parameter) {
        UnspecifiedValue<C,P,DataValue> result = new UnspecifiedValue<C,P,DataValue>(unspecifiedData, parameter);
        return result;
    }

    public InstanceValue<C,P,DataValue> createInstanceValue(ClassInstance<C,P,DataValue> instance) {
        return new InstanceValue<C,P,DataValue>(instance);
    }

    public PrimitiveDataValue<C,P,DataValue> createPrimitiveValue(DataValue primitiveData) {
        PrimitiveDataValue<C,P,DataValue> result = new PrimitiveDataValue<C,P,DataValue>(primitiveData);
        return result;
    }

    public ClassInstance<C,P,DataValue> getInstance(String id) {
        return instanceForId.get(id);
    }

    public Collection<ClassInstance<C,P,DataValue>> getInstances() {
        return instanceForId.values();
    }

    public Collection<ValueSpecification<C,P,DataValue>> getOutermostComposites() {
        return outermostComposites;
    }

    protected void removeOutermostComposite(ClassInstance<C,P,DataValue> instance) {
        outermostComposites.remove(createInstanceValue(instance));
    }

    protected void addOutermostComposite(ClassInstance<C,P,DataValue> instance) {
        outermostComposites.add(createInstanceValue(instance));
    }

    protected void deleteInstance(ClassInstance<C,P,DataValue> instance) {
        if (instance.getComposite() == null) {
            removeOutermostComposite(instance);
        }
        instanceForId.remove(instance.getId());
    }

    @SuppressWarnings("unchecked")
	public void myFinalize() {
        for (ClassInstance instance: instanceForId.values()) {
            instance.myFinalize();
        }
        instanceForId.clear();
        outermostComposites.clear();
    }
}

