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

package hub.sam.mof.reflection;

import cmof.Operation;
import cmof.Parameter;
import cmof.ParameterDirectionKind;
import cmof.Property;
import cmof.UmlClass;
import cmof.PrimitiveType;
import cmof.Type;
import cmof.CallConcurrencyKind;
import cmof.reflection.ObjectEventHandler;
import cmof.reflection.Argument;
import cmof.reflection.Extent;
import cmof.common.ReflectiveCollection;
import cmof.common.ReflectiveSequence;
import cmof.exception.IllegalArgumentException;
import cmof.exception.MetaModelException;
import hub.sam.mof.instancemodel.ClassInstance;
import hub.sam.mof.instancemodel.ClassifierSemantics;
import hub.sam.mof.instancemodel.StructureSlot;
import hub.sam.mof.instancemodel.ValueSpecification;
import hub.sam.mof.instancemodel.ValueSpecificationImpl;
import hub.sam.mof.instancemodel.ValueSpecificationList;
import hub.sam.mof.mofinstancemodel.MofClassInstance;
import hub.sam.mof.mofinstancemodel.MofClassifierSemantics;
import hub.sam.mof.mofinstancemodel.events.InsertEvent;
import hub.sam.mof.mofinstancemodel.events.PropertyChangeEventListener;
import hub.sam.mof.mofinstancemodel.events.RemoveEvent;
import hub.sam.mof.mofinstancemodel.events.SetEvent;
import hub.sam.mof.ocl.MofEvaluationAdaptor;
import hub.sam.mof.util.AssertionException;
import hub.sam.mof.util.SetImpl;
import hub.sam.mof.xmi.CMOFToXmi;
import hub.sam.mof.javamapping.JavaMapping;
import hub.sam.mof.jocl.standardlib.OclModelElement;
import hub.sam.util.AbstractFluxBox;
import hub.sam.util.Identity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ObjectImpl extends hub.sam.util.Identity implements cmof.reflection.Object {
    protected boolean isStatic; //TODO

    private ClassInstance<UmlClass, Property, java.lang.Object> instance;
    private ClassifierSemantics<Property, Operation, String> semantics;
    private Identifier id;
    private ExtentImpl extent;
    private Implementations implementation = null;
    private List<ObjectEventHandler> handler = null;
    private PropertyChangeEventListener fPropertyChangeListener;

    protected ObjectImpl(ClassInstance<UmlClass, Property, java.lang.Object> instance, ExtentImpl extent) {
        super();
        setPrimaryIdentity(instance, true);
        this.instance = instance;
        this.extent = extent;
        this.id = new Identifier();
        this.metaId = null;
        this.implementingClassName = null;
        this.isStatic = false;
        if (instance instanceof hub.sam.mof.mofinstancemodel.MofClassInstance) {
            this.semantics = ((hub.sam.mof.mofinstancemodel.MofClassInstance)instance).getInstanceClassifier(); //TODO
            fPropertyChangeListener = new PropertyChangeEventListener() {
				@Override
				public void insert(InsertEvent event) {
					firePropertyChange(event.getProperty().getName(), null, null);
				}
				@Override
				public void remove(RemoveEvent event) {
					firePropertyChange(event.getProperty().getName(), null, null);
				}
				@Override
				public void set(SetEvent event) {
					if (event.getProperty().getUpper() == 1) {
						firePropertyChange(event.getProperty().getName(), event.getOldValue(), event.getNewValue());
					} else {
						firePropertyChange(event.getProperty().getName(), null, null);
					}
				}           			
    		};
            ((hub.sam.mof.mofinstancemodel.MofClassInstance)instance).addPropertyChangeListener(fPropertyChangeListener);            	
        }  else {
        	fPropertyChangeListener = null;
        }
    }

    public void setImplementations(Implementations implementations) {
        this.implementation = implementations;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    public cmof.UmlClass getMetaClass() {
        if (instance != null) {
            return instance.getClassifier();
        } else if (attributes != null) {
            java.lang.Object metaClass = attributes.get("__metaClass");
            if (metaClass == null) {
                return null;
            } else if (metaClass instanceof String) {
                return (
                        (ExtentImpl)hub.sam.mof.Repository.getLocalRepository()
                                .getExtent(hub.sam.mof.Repository.CMOF_EXTENT_NAME))
                        .resolveM3Element((String)metaClass);
            } else {
                return (UmlClass)valueForStaticValue(metaClass);
            }
        } else {
            return null;
        }
    }

    public ClassifierSemantics<Property, Operation, String> getSemantics() {
        return semantics;
    }

    public void setSemantics(ClassifierSemantics<Property, Operation, String> semantics) {
        this.semantics = semantics;
    }

    public void setIsStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }

    public ClassInstance<UmlClass, Property, java.lang.Object> getClassInstance() {
        return instance;
    }

    private Map<String, Identity> staticCollectionValueIds = new HashMap<String, Identity>();

    public java.lang.Object get(String propertyName) {
        return get(propertyName, null);
    }

    public synchronized java.lang.Object get(String propertyName, java.lang.Object qualifier) {
        if (attributes != null) {
            if (qualifier != null) {
                throw new IllegalArgumentException("qualifiers not allows for static models");
            }
            java.lang.Object result = staticCollectionValueIds.get(propertyName);
            if (result == null) {
                result = valueForStaticValue(attributes.get(propertyName));
                if (result instanceof Identity && result instanceof ReflectiveCollection) {
                    ((Identity)result).setParentIdentity(this);
                    staticCollectionValueIds.put(propertyName, (Identity)result);
                }
            }
            return result;
        }
        Property property = semantics.getProperty(propertyName);
        if (property == null) {
            // this is ugly, only to prevent exception during toString, which make some debugging
            // processes really nasty
            if (propertyName.equals("qualifiedName") || propertyName.equals("name")) {
                return null;
            } else {
                throw new cmof.exception.IllegalArgumentException(propertyName);
            }
        } else {
            return get(property, qualifier);
        }
    }

    public java.lang.Object get(Property property) {
        return get(property,  null);
    }

    public synchronized java.lang.Object get(Property property, java.lang.Object qualifier) {
        if (attributes != null) {
            if (qualifier != null) {
                throw new IllegalArgumentException("qualifiers not allows for static models");
            }
            return get(property.getName());
        }
        if (implementation.hasImplementationFor(semantics.getFinalProperty(property), semantics)) {
            if (qualifier != null) {
                throw new IllegalArgumentException("qualifiers not allowed for derived properties");
            }
            return implementation.invokeImplementationFor(semantics.getFinalProperty(property), this, semantics);
        }
        checkQualifierType(qualifier, property);
        ValueSpecificationList<UmlClass, Property, java.lang.Object> values = instance.get(property).
                getValuesAsList(extent.specificationForValue(qualifier));
        if (!semantics.isCollectionProperty(property)) {
            if (values.size() == 0) {
                return null;
            } else {
                return extent.valueForSpecification(values.get(0));
            }
        } else {
            return extent.valuesForSpecificationList(values);
        }
    }

    public synchronized void set(String propertyName, java.lang.Object value) {
        set(propertyName, null, value);
    }

    public synchronized void set(String propertyName, java.lang.Object qualifier, java.lang.Object value) {
        Property property = semantics.getProperty(propertyName);
        if (property == null) {
            throw new IllegalArgumentException(propertyName);
        } else {
            set(property, qualifier, value);
        }
    }

    /**
     * @return True if type check is successful.
     */
    private boolean typeCheckValue(java.lang.Object value, cmof.Type type) {
        if (value == null) {
            return true;
        }
        if (type instanceof cmof.PrimitiveType) {
            if (type.getName().equals(core.primitivetypes.String.class.getSimpleName())) {
                return value instanceof String;
            } else if (type.getName().equals(core.primitivetypes.Integer.class.getSimpleName())) {
                return value instanceof Integer;
            } else if (type.getName().equals(core.primitivetypes.Boolean.class.getSimpleName())) {
                return value instanceof Boolean;
            } else if (type.getName().equals(core.primitivetypes.Object.class.getSimpleName())) {
                return value instanceof Object;
            } else if (type.getName().equals(core.primitivetypes.UnlimitedNatural.class.getSimpleName())) {
                return value instanceof Long;
            } else {
                return value.getClass().getCanonicalName().equals(
                        JavaMapping.mapping.getJavaTypeNameForPrimitiveType((PrimitiveType)type));
            }
        } else if (type instanceof cmof.Enumeration) {
            return value.getClass().isEnum() && value.getClass().getName()
                    .equals(hub.sam.mof.javamapping.JavaMapping.mapping.getFullQualifiedJavaIdentifier(type));
        } else if (type instanceof cmof.UmlClass) {
            if (value instanceof cmof.reflection.Object) {
                //return ((cmof.reflection.Object)value).getMetaClass().equals(type);
                return ((cmof.reflection.Object)value).isInstanceOfType((cmof.UmlClass)type, true);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void set(Property property, java.lang.Object value) {
        set(property, null, value);
    }

    public synchronized void set(Property property, java.lang.Object qualifier, java.lang.Object value) {
        if (value == null) {
            unset(property, qualifier);
        } else {
            if (instance == null) {
                throw new MetaModelException("Static modelelements cant be changed");
            }
            if (property.getUpper() == 1) {
                if (value instanceof ReflectiveCollection) {
                    throw new IllegalArgumentException(value);
                }
                if (!typeCheckValue(value, (cmof.Type)property.getType())) {
                    throw new IllegalArgumentException(value);
                }
                checkQualifierType(qualifier, property);
                ReflectiveSequence<? extends ValueSpecification<UmlClass, Property, java.lang.Object>> values =
                        instance.get(property).getValuesAsList(extent.specificationForValue(qualifier));

                java.lang.Object oldValue = get(property);

                if (values.size() == 0) {
                    values.add(0, extent.specificationForValue(value));
                } else {
                    values.set(0, extent.specificationForValue(value));
                }
                if (propertyChangeListeners.hasListeners(null)) {
                    propertyChangeListeners.firePropertyChange(property.getName(), oldValue, value);
                }
            } else {
                throw new IllegalArgumentException(property);
            }
        }
    }

    public boolean isSet(Property property) {
        return isSet(property, null);
    }

    public synchronized boolean isSet(Property property, java.lang.Object qualifier) {
        if (instance == null) {
            if (qualifier != null) {
                throw new IllegalArgumentException("qualifiers are not allowed in static models");
            }
            return attributes.get(property.getName()) != null;
        } else {
            checkQualifierType(qualifier, property);
            return instance.get(property).getValuesAsList(extent.specificationForValue(qualifier)).size() != 0;
        }
    }

    public void unset(Property property) {
        unset(property, null);
    }

    public synchronized void unset(Property property, java.lang.Object qualifier) {
        if (instance == null) {
            throw new MetaModelException("Static modelelements cant be changed");
        }
        boolean wasSetProperty = isSet(property);
        checkQualifierType(qualifier, property);
        instance.get(property).getValuesAsList(null).removeAll(
                new hub.sam.mof.util.SetImpl<ValueSpecification<UmlClass, Property, java.lang.Object>>(
                        instance.get(property).getValuesAsList(null)));

        if (wasSetProperty && propertyChangeListeners.hasListeners(null)) {
            propertyChangeListeners.firePropertyChange(property.getName(), null, null);
        }
    }

    private void checkQualifierType(Object qualifier, Property property) {
        if (qualifier != null) {
            Property qualifierProperty = property.getQualifier();
            if (!typeCheckValue(qualifier, (Type)qualifierProperty.getType())) {
                throw new IllegalArgumentException(qualifier);
            }
        }
    }

    @Override
    public int hashCode() {
        if (instance != null) {
            return instance.hashCode();
        } else {
            return id.hashCode();
        }
    }

    @Override
    public boolean equals(java.lang.Object element) {
        if (element instanceof ObjectImpl) {
            if (instance != null) {
                return instance.equals(((ObjectImpl)element).instance) && element.getClass().equals(this.getClass());
            } else {
                return id.equals(((ObjectImpl)element).id) && element.getClass().equals(this.getClass());
            }
        } else {
            return false;
        }
    }

    public synchronized void delete() {
        if (instance == null || isStatic) {
            throw new MetaModelException("Static modelelements cant be changed");
        }
        if (handler != null) {
            for (ObjectEventHandler aHandler : handler) {
                aHandler.handleDelete(this);
            }
        }
        for (cmof.reflection.Object component : getComponents()) {
            component.delete();
        }
        if (fPropertyChangeListener != null) {
        	((MofClassInstance)instance).removePropertyChangeListener(fPropertyChangeListener);
        	fPropertyChangeListener = null;
        }
        extent.removeObject(this, getClassInstance());
    }

    class ConcurrentOperationInvoker extends Thread {
        private final ObjectImpl self;
        private final Operation operation;
        private final java.lang.Object[] arguments;

        ConcurrentOperationInvoker(ObjectImpl self, Operation operation,
                                          java.lang.Object[] arguments) {
            super();
            this.self = self;
            this.operation = operation;
            this.arguments = arguments;
        }

        @Override
        public void run() {
            self.firstHold();
            self.invokeCustomImplementation(operation, arguments);
        }
    }

    private static final Map<cmof.reflection.Object, Integer> scheduledObjects =
            new HashMap<cmof.reflection.Object, Integer>();
    private static final Map<Integer, cmof.reflection.Object> oracle =
            new HashMap<Integer, cmof.reflection.Object>();
    private static int unique = 0;

    private static boolean firstSpawn = true;

    public synchronized void hold() {
        if (!oracle.isEmpty()) {
            Integer nextSeed = oracle.keySet().iterator().next();
            cmof.reflection.Object nextObject = oracle.get(nextSeed);
            oracle.remove(nextSeed);
            scheduledObjects.remove(nextObject);
            ((ObjectImpl)nextObject).synchronizedNotify();
        }
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private synchronized void synchronizedNotify() {
        this.notify();
    }

    private synchronized void firstHold() {
        if (firstSpawn) {
            firstSpawn = false;
        } else {
            try {
                schedule(this);
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    public synchronized void schedule(cmof.reflection.Object object) {
        if (scheduledObjects.get(object) == null) {
            Integer seed = new Integer(unique);
            unique++;
            scheduledObjects.put(object, seed);
            oracle.put(seed, object);
        }
    }

    public synchronized java.lang.Object invokeOperation(String opName, java.lang.Object[] args) {
        if (semantics == null) {
            // lazy semantics innitialisation is nessessary to avoid endless recusion in bootstrap and static models
            if (getMetaClass() != null) {
                this.semantics = MofClassifierSemantics.createClassClassifierForUmlClass(getMetaClass());
            } else {
                // possible, there are no entities, oops there are no meta-model references in static models when the static model does not contain is its own meta-model
                throw new MetaModelException("not possible for the static model");
            }
        }
        // TODO multipolymorphy
        Operation op = semantics.getFinalOperation(opName);
        if (op == null) {
            throw new IllegalArgumentException("wrong op"); // TODO
        }
        if (implementation.hasImplementationFor(op, semantics)) {
            if (op.getConcurrency() == CallConcurrencyKind.CONCURRENT) {
                new ConcurrentOperationInvoker(this, op, args).start();
                return null;
            } else {
                return invokeCustomImplementation(op, args);
            }
        } else {
            throw new MetaModelException("no implementation found for " + opName);
        }
    }

    private java.lang.Object invokeCustomImplementation(Operation op, Object[] args) {
        java.lang.Object result;
        try {
            result = implementation.invokeImplementationFor(op, this, args, semantics);
        } catch (RuntimeException ex) {
            throw ex;
        }
        return result;
    }

    public void addObjectEventHandler(ObjectEventHandler handler) {
        if (this.handler == null) {
            this.handler = new Vector<ObjectEventHandler>(1);
        }
        this.handler.add(handler);
    }

    private String getOperationNameForOperation(cmof.Operation op) {
        StringBuffer opName = new StringBuffer(op.getName());
        for (Parameter parameter : op.getFormalParameter()) {
            if (!parameter.getDirection().equals(ParameterDirectionKind.RETURN)) {
                opName.append("_");
                opName.append(parameter.getType().getQualifiedName());
            }
        }
        return opName.toString();
    }

    public synchronized java.lang.Object invokeOperation(cmof.Operation op, ReflectiveSequence<Argument> arguments) {
        java.lang.Object[] args = new Object[arguments.size()];
        int i = 0;
        for (Argument arg : arguments) {
            args[i++] = arg.getValue();
        }
        return invokeOperation(getOperationNameForOperation(op), args);
    }

    public boolean isInstanceOfType(cmof.UmlClass type, boolean includeSubTypes) {
        ReflectiveCollection<cmof.UmlClass> allPossibleTypes = new SetImpl<cmof.UmlClass>();
        allPossibleTypes.add(getMetaClass());
        if (includeSubTypes) {
            collectSuperClass(getMetaClass(), allPossibleTypes);
        }
        return allPossibleTypes.contains(type);
    }

    private void collectSuperClass(cmof.UmlClass forUmlClass, ReflectiveCollection<cmof.UmlClass> superClasses) {
        superClasses.add(forUmlClass);
        for (cmof.UmlClass umlClass : forUmlClass.getSuperClass()) {
            collectSuperClass(umlClass, superClasses);
        }
    }

    @Override
    public String toString() {
        String result = "";
        try {
            try {
                result = (String)get("qualifiedName");
            } catch (Throwable e) {
                // empty
            }
            if (result == null || result.equals("")) {
                result = (String)get("name");
            }
        } catch (Throwable e) {
            result = "[" + getMetaClass().toString() + "]";
            if (container() != null) {
                result+= " in " + container().toString();
            }
            String superString = super.toString();
            result += " (" + superString.substring(superString.lastIndexOf('@') + 1, superString.length()) + ")";
        }
        //result += "(" + hashCode() + ")";
        //if (instance != null) {
        //    result += " " + (instance.isValid() ? "valid" : "invalid");
        //}
        return result;
    }


    protected void setExtent(ExtentImpl extent) {
        this.extent = extent;
    }

    public Extent getExtent() {
        return extent;
    }

    public synchronized cmof.reflection.Object container() {
        if (attributes != null) {
            return (cmof.reflection.Object)valueForStaticValue(attributes.get("__container"));
        } else {
            return extent.getObjectForInstance(instance.getComposite());
        }
    }

    @SuppressWarnings("unchecked")
    public ReflectiveCollection<cmof.reflection.Object> getComponents() {
        if (attributes != null) {
            return (ReflectiveCollection)valueForStaticValue(attributes.get("__components"));
        } else if (instance instanceof hub.sam.mof.mofinstancemodel.MofClassInstance) {
            ReflectiveCollection<ClassInstance<UmlClass, Property, java.lang.Object>> contents =
                    new hub.sam.mof.util.SetImpl<ClassInstance<UmlClass, Property, java.lang.Object>>(
                            ((hub.sam.mof.mofinstancemodel.MofClassInstance)instance)
                                    .getComponents());
            ReflectiveCollection<cmof.reflection.Object> result =
                    new hub.sam.mof.util.ListImpl<cmof.reflection.Object>();
            for (ClassInstance<UmlClass, Property, java.lang.Object> instance : contents) {
                result.add(extent.getObjectForInstance(instance));
            }
            return result;
        } else {
            ReflectiveCollection<ClassInstance<UmlClass, Property, java.lang.Object>> contents =
                    new hub.sam.mof.util.SetImpl<ClassInstance<UmlClass, Property, java.lang.Object>>(
                            ((hub.sam.mof.instancemodel.ClassInstance)instance).getComponentsAsCollection());
            ReflectiveCollection<cmof.reflection.Object> result =
                    new hub.sam.mof.util.ListImpl<cmof.reflection.Object>();
            for (ClassInstance<UmlClass, Property, java.lang.Object> instance : contents) {
                result.add(extent.getObjectForInstance(instance));
            }
            return result;
        }
    }

    public cmof.reflection.Object getOutermostContainer() {
        cmof.reflection.Object iterator = this;
        cmof.reflection.Object actualComposite = iterator.container();
        while (actualComposite != null) {
            iterator = actualComposite;
            actualComposite = actualComposite.container();
        }
        return iterator;
    }

    // the static part
    private Map<String, java.lang.Object> attributes = null;
    private final String implementingClassName;
    private final Identifier metaId;
    private String[] delegateClassNames = null;

    private static java.lang.Object[] getValuesForObjects(ReflectiveCollection<cmof.reflection.Object> valueSet) {
        java.lang.Object[] values = new java.lang.Object[valueSet.size()];
        java.util.Iterator<cmof.reflection.Object> valueSetIterator = valueSet.iterator();
        for (int i = 0; i < valueSet.size(); i++) {
            values[i] = staticValueForObject(valueSetIterator.next());
        }
        return values;
    }

    @SuppressWarnings("unchecked")
    protected static ObjectImpl createFromObject(ObjectImpl object) {
        String[] delegateClassNames = new String[object.implementation.getDelegates().size()];
        int index = 0;
        for (ObjectDlg delegate : object.implementation.getDelegates()) {
            delegateClassNames[index] = delegate.getClass().getCanonicalName();
            index++;
        }
        ObjectImpl result = new ObjectImpl(
                object.getId(), object.extent, null, object.getClass().getCanonicalName(), delegateClassNames);
        for (Property property : object.semantics.getFinalProperties()) {
            String propertyName = object.semantics.getName(property);
            java.lang.Object value = object.get(propertyName);
            if (value instanceof cmof.common.ReflectiveCollection) {
                result.putAttribute(propertyName, getValuesForObjects((ReflectiveCollection)value));
            } else {
                result.putAttribute(propertyName, staticValueForObject(value));
            }
        }
        result.putAttribute("__components", getValuesForObjects(object.getComponents()));
        result.putAttribute("__container", staticValueForObject(object.container()));
        if (object.getMetaClass() instanceof cmof.reflection.Object) {
            cmof.reflection.Object metaClass = (cmof.reflection.Object)object.getMetaClass();
            if (object.extent.contains(metaClass)) {
                result.putAttribute("__metaClass", staticValueForObject(metaClass));
            } else {
                result.putAttribute("__metaClass", ((UmlClass)metaClass).getQualifiedName());
            }
        }
        return result;
    }

    protected ObjectImpl(
            Identifier id, ExtentImpl extent, Identifier metaId, String implementingClassName,
            String[] delegateClassNames) {
        super();
        isStatic = true;
        attributes = new HashMap<String, java.lang.Object>();
        this.delegateClassNames = delegateClassNames;
        this.extent = extent;
        instance = null;
        fPropertyChangeListener = null;
        this.id = id;
        this.metaId = metaId;
        this.implementingClassName = implementingClassName;

        if (this.extent.getObjectForId().get(id) != null) {
            throw new AssertionException("Duplicate static model id: " + id + ".");
        }
        this.extent.getObjectForId().put(id, this);
        List<ObjectDlg> delegates = new Vector<ObjectDlg>();
        try {
            for (String delegateClassName : delegateClassNames) {
                java.lang.Class implementation =
                        Thread.currentThread().getContextClassLoader().loadClass(delegateClassName);
                java.lang.reflect.Constructor constructor = implementation.getConstructor(new java.lang.Class[]{});
                ObjectDlg object = (ObjectDlg)constructor.newInstance();
                delegates.add(object);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        setImplementations(new ImplementationsImpl(delegates, null));
    }

    public void putAttribute(String name, java.lang.Object values) {
        attributes.put(name, values);
    }

    private String serializeValue(java.lang.Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Integer) {
            return "new Integer(" + value + ")";
        } else if (value instanceof Long) {
            return "new Long(" + value + ")";
        } else if (value instanceof String) {
            value = quoteCharacter((String)value, "\n", "\\n");
            value = quoteCharacter((String)value, "\"", "\\\"");
            return "\"" +value + "\"";
        } else if (value instanceof Boolean) {
            return "new Boolean(" + value + ")";
        } else if (value.getClass().isEnum()) {
            return value.getClass().getCanonicalName() + "." + value;
        } else if (value instanceof Identifier) {
            return "_" + value;
        } else {
            throw new RuntimeException("assert");
        }
    }

    private String quoteCharacter(String stringValue, String character, String quote) {
        StringBuffer quotedValue = new StringBuffer();
        int pos = 0;
        int occurence = stringValue.indexOf(character, pos);
        while (occurence != -1) {
            quotedValue.append(stringValue.substring(pos, occurence));
            quotedValue.append(quote);
            pos = occurence + 1;
            occurence = stringValue.indexOf(character, pos);
        }
        quotedValue.append(stringValue.substring(pos, stringValue.length()));
        return quotedValue.toString();
    }

    public String serialize() {
        String delegateClassNamesString = "new java.lang.String[]{";
        boolean first = true;
        for (String delegate : delegateClassNames) {
            delegateClassNamesString += (first ? "" : ", ") + "\"" + delegate + "\"";
            first = false;
        }
        delegateClassNamesString += "}";
        StringBuffer result = new StringBuffer();
        if (metaId == null) {
            result.append("object = new ").append(implementingClassName).append("(_").append(id)
                    .append(", extent, null, \"").append(implementingClassName).append("\", ")
                    .append(delegateClassNamesString).append(");");
        } else {
            result.append("object = new ").append(implementingClassName).append("(_").append(id).append(", extent, _")
                    .append(metaId).append(", \"").append(implementingClassName).append("\", ")
                    .append(delegateClassNamesString).append(");");
        }
        for (String attrName : attributes.keySet()) {
            result.append("((").append(ObjectImpl.class.getCanonicalName()).append(")object).putAttribute(\"")
                    .append(attrName).append("\", ");
            java.lang.Object value = attributes.get(attrName);
            if (value instanceof java.lang.Object[]) {
                result.append("new java.lang.Object[] {");
                java.lang.Object[] values = (java.lang.Object[])value;
                int index = 0;
                for (java.lang.Object itera : values) {
                    result.append(serializeValue(itera));
                    index++;
                    if (index < values.length) {
                        result.append(",");
                    }
                }
                result.append("}");
            } else {
                result.append(serializeValue(value));
            }
            result.append(");");
        }
        return result.toString();
    }

    private class StaticValueList extends Identity implements cmof.common.ReflectiveSequence {
        final java.util.List<java.lang.Object> values;

        StaticValueList(java.lang.Object[] values) {
            super();
            this.values = java.util.Arrays.asList(values);
        }

        public java.lang.Object get(int index) {
            return valueForStaticValue(values.get(index));
        }

        public java.lang.Object set(int index, java.lang.Object element) {
            throw new IllegalArgumentException("This object is static.");
        }

        public void add(int index, java.lang.Object element) {
            throw new IllegalArgumentException("This object is static.");
        }

        public boolean addAll(int index, Iterable elements) {
            throw new IllegalArgumentException("This object is static.");
        }

        public java.lang.Object remove(int index) {
            throw new IllegalArgumentException("This object is static.");
        }

        public ReflectiveSequence subList(int from, int to) {
            throw new IllegalArgumentException("This object is static.");
        }

        public boolean add(java.lang.Object element) {
            throw new IllegalArgumentException("This object is static.");
        }

        public boolean contains(java.lang.Object element) {
            return values.contains(staticValueForObject(element));
        }

        public boolean remove(java.lang.Object element) {
            throw new IllegalArgumentException("This object is static.");
        }

        public boolean addAll(Iterable elements) {
            throw new IllegalArgumentException("This object is static.");
        }

        public boolean containsAll(Iterable elements) {
            for (java.lang.Object element : elements) {
                if (!values.contains(staticValueForObject(element))) {
                    return false;
                }
            }
            return true;
        }

        public boolean removeAll(Iterable elements) {
            throw new IllegalArgumentException("This object is static.");
        }

        public int size() {
            return values.size();
        }

        class Iterator implements java.util.Iterator {
            final java.util.Iterator<java.lang.Object> values;

            Iterator(java.util.Iterator<java.lang.Object> values) {
                super();
                this.values = values;
            }

            public boolean hasNext() {
                return values.hasNext();
            }

            public java.lang.Object next() {
                return valueForStaticValue(values.next());
            }

            public void remove() {
                throw new IllegalArgumentException("This object is static.");
            }
        }

        public java.util.Iterator iterator() {
            return new Iterator(values.iterator());
        }

        @Override
        public String toString() {
            return values.toString();
        }

        public void clear() {
            values.clear();
        }
    }

    private java.lang.Object valueForStaticValue(java.lang.Object o) {
        if (o == null) {
            return null;
        } else if (o instanceof Identifier) {
            return this.extent.getObjectForId().get(o);
        } else if (o instanceof java.lang.Object[]) {
            return new StaticValueList((java.lang.Object[])o);
        } else {
            return o;
        }
    }

    private static java.lang.Object staticValueForObject(java.lang.Object o) {
        if (o instanceof ObjectImpl) {
            return ((ObjectImpl)o).getId();
        } else {
            return o;
        }
    }

    protected void myFinalize() {
    	if (instance != null && instance instanceof MofClassInstance) {
        	((MofClassInstance)instance).removePropertyChangeListener(fPropertyChangeListener);
        }
    	instance = null;
        semantics = null;
        extent = null;
        handler = null;        
    }

    /* This is super dirty, I am really sorry. Ocl allows additional property,value-pairs added temporarely to
      * a value. Because oslo only uses a getter method identified by name to access properties and Java simply
      * cannot have temporary methods, this solution has to work for it. For additional values the ocl interpreter
      * will use this getter method; the actual property to access and its values are stores staticly.
      */
    public Object getOclAdditionalValue() {
        return MofEvaluationAdaptor.currentValue;
    }

    /**
     * Copies all values of all features of this object to the target object. It uses
     * {@link CMOFToXmi} to deal with duplicate values due to subsetting.
     *
     * @param target The target Object, it is recommended to be empty. It must be an object of the same meta-class.
     */
    @SuppressWarnings({"unchecked"})
    public void copyAllValues(
            ObjectImpl target, AbstractFluxBox<cmof.reflection.Object, cmof.reflection.Object, Object> fluxBox)
            throws hub.sam.mof.instancemodel.MetaModelException {
        for (StructureSlot<UmlClass, Property, Object> slot : instance.getSlots()) {
            Property property = slot.getProperty();
            if (!property.isDerived() && !property.isDerivedUnion()) {
                for (ValueSpecification<UmlClass, Property, Object> value : slot.getValuesAsList(null)) {
                    if (CMOFToXmi.staticDoConvert((ValueSpecificationImpl)value, slot, instance)) {
                        ValueSpecification<UmlClass, Property, Object> targetValue;
                        if (value.asDataValue() != null) {
                            targetValue = target.extent.model.createPrimitiveValue(value.asDataValue().getValue());
                        } else if (value.asInstanceValue() != null) {
                            targetValue = target.extent.model.createInstanceValue(((ObjectImpl)(
                                    fluxBox.getObject((cmof.reflection.Object)extent.valueForSpecification(value),
                                    null))).instance);
                        } else {
                            throw new RuntimeException("assert");
                        }
                        target.instance.get(property).getValuesAsList(null).add(targetValue);
                    }
                }
            }
        }
    }

    protected PropertyChangeSupport propertyChangeListeners = new PropertyChangeSupport(this);

    public void addListener(PropertyChangeListener listener) {
    	propertyChangeListeners.addPropertyChangeListener(listener);
    }

    public void removeListener(PropertyChangeListener listener) {
    	propertyChangeListeners.removePropertyChangeListener(listener);
    }

    

    private void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
    	propertyChangeListeners.firePropertyChange(propertyName, oldValue, newValue);
    }

    public OclModelElement ocl() {
        return null; // TODO
    }
}

