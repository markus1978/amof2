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

import cmof.Association;
import cmof.NamedElement;
import cmof.Property;
import cmof.UmlClass;
import cmof.common.ReflectiveCollection;
import cmof.common.ReflectiveSequence;
import cmof.reflection.Extent;
import hub.sam.mof.Repository;
import hub.sam.mof.instancemodel.ClassInstance;
import hub.sam.mof.instancemodel.InstanceModel;
import hub.sam.mof.instancemodel.ValueSpecification;
import hub.sam.mof.instancemodel.ValueSpecificationList;
import hub.sam.mof.domainmodels.ProxyInstanceModel;
import hub.sam.mof.mofinstancemodel.MofValueSpecificationList;
import hub.sam.mof.reflection.query.ParseException;
import hub.sam.mof.reflection.query.Query;
import hub.sam.mof.util.ListImpl;
import hub.sam.mof.util.SetImpl;
import hub.sam.util.Identity;
import hub.sam.util.MultiMap;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ExtentImpl extends hub.sam.util.Identity implements cmof.reflection.Extent {
    private final boolean bootstrap;
    private ReflectiveCollection<? extends cmof.reflection.Object> bootstrapOutermostComposites = null;
    private ReflectiveCollection<cmof.reflection.Object> objects;
    private final Map<String, UmlClass> m3ElementCache = new HashMap<String, UmlClass>();
    private final Map<ClassInstance<UmlClass,Property,java.lang.Object>, cmof.reflection.Object> objectForInstance = new HashMap<ClassInstance<UmlClass,Property,java.lang.Object>, cmof.reflection.Object>();
    private final Map<cmof.reflection.Object, ClassInstance<UmlClass,Property,java.lang.Object>> instanceForObject = new HashMap<cmof.reflection.Object, ClassInstance<UmlClass,Property,java.lang.Object>>();
    private Map<Identifier, ObjectImpl> objectForId = null;
    private final MultiMap<UmlClass, cmof.reflection.Object> objectsForTypes = new MultiMap<UmlClass, cmof.reflection.Object>();
    private final MultiMap<UmlClass, cmof.reflection.Object> objectsForTypesWithSubtypes = new MultiMap<UmlClass, cmof.reflection.Object>();
    private ImplementationsManager implementationsManager = null;
    protected final InstanceModel<UmlClass,Property,java.lang.Object> model = new ProxyInstanceModel();//TODO

    protected ImplementationsManager createImplementationManager() {
        return new ImplementationsManagerImpl();
    }

    public void setCustomImplementationsManager(ImplementationsManager manager) {
        implementationsManager = manager;
    }

    private ExtentImpl(boolean bootstrap, String path) {
        super(path);
        this.bootstrap = bootstrap;
        setPrimaryIdentity(model);
        objects = new SetImpl<cmof.reflection.Object>();
    }

    public ExtentImpl(boolean bootstrap) {
        this(bootstrap, null);
    }

    public ExtentImpl(String path) {
        this(false, path);
    }

    public ExtentImpl() {
        this(false, null);
    }

    public InstanceModel<UmlClass,Property,java.lang.Object> getModel() {
        return model;
    }

    protected cmof.reflection.Object getObjectForInstance(ClassInstance<UmlClass,Property,java.lang.Object> instance) {
        return objectForInstance.get(instance);
    }

    protected final java.lang.Object valueForSpecification(ValueSpecification<UmlClass,Property,java.lang.Object> spec) {
        if (spec == null) {
            return null;
        }
        if (spec.asDataValue() != null) {
            return spec.asDataValue().getValue();
        } else if (spec.asInstanceValue() != null) {
            if (getObjectForInstance(spec.asInstanceValue().getInstance())==null) {
                throw new NullPointerException();
            }
            return getObjectForInstance(spec.asInstanceValue().getInstance());
        } else {
            throw new RuntimeException("assert");
        }
    }

    protected final ValueSpecification<UmlClass,Property,java.lang.Object> specificationForValue(java.lang.Object value) {
        if (value == null) {
            return null;//TODO
        }
        if (value instanceof cmof.reflection.Object) {
            cmof.reflection.Object object = (cmof.reflection.Object)value;
            if (!objectsOfType(null, true).contains(object)) {
                throw new NullPointerException();
            }
            return model.createInstanceValue(instanceForObject.get(value));
        } else if (value instanceof Integer || value instanceof Long || value instanceof Boolean || value instanceof String || value instanceof Enum) {
            return model.createPrimitiveValue(value);
        } else {
            return model.createPrimitiveValue(value);
        }
    }

    protected final ReflectiveSequence<java.lang.Object> valuesForSpecificationList(ValueSpecificationList<UmlClass,Property,java.lang.Object> list) {
        return new ValueList(list);
    }

    public class ValueList extends Identity implements cmof.common.ReflectiveSequence<java.lang.Object> {
        private final ReflectiveSequence<ValueSpecification<UmlClass,Property,java.lang.Object>> valueSpecs;

        ValueList(ReflectiveSequence<ValueSpecification<UmlClass,Property,java.lang.Object>> valueSpecs) {
            super("AValueList");
            if (valueSpecs instanceof MofValueSpecificationList) {
                setPrimaryIdentity(((MofValueSpecificationList)valueSpecs).getIdentity());
            }
            this.valueSpecs = valueSpecs;
        }

        public MofValueSpecificationList getValues() {
            return (MofValueSpecificationList)valueSpecs;
        }

        public java.lang.Object get(int index) {
            return valueForSpecification(valueSpecs.get(index));
        }

        public java.lang.Object set(int index, java.lang.Object element) {
            return valueForSpecification(valueSpecs.set(index, specificationForValue(element)));
            //return valueSpecs.set(index, specificationForValue(element));
        }

        public void add(int index, java.lang.Object element) {
            valueSpecs.add(index, specificationForValue(element));
        }


        public java.lang.Object remove(int index) {
            return valueForSpecification(valueSpecs.get(index));
        }


        public ReflectiveSequence<java.lang.Object> subList(int from, int to) {
            return new ValueList(valueSpecs.subList(from, to));
        }

        public boolean add(java.lang.Object element) {
            return valueSpecs.add(specificationForValue(element));
        }

        public boolean contains(java.lang.Object element) {
            return valueSpecs.contains(specificationForValue(element));
        }

        public boolean remove(java.lang.Object element) {
            return valueSpecs.remove(specificationForValue(element));
        }

        class SpecificationIterable implements Iterable<java.lang.Object>, Iterator<java.lang.Object> {
            private final Iterator<? extends java.lang.Object> values;

            SpecificationIterable(Iterable<? extends java.lang.Object> values) {
                super();
                this.values = values.iterator();
            }

            public Iterator<java.lang.Object> iterator() {
                return this;
            }

            public boolean hasNext() {
                return values.hasNext();
            }

            public ValueSpecification<UmlClass,Property,java.lang.Object> next() {
                return specificationForValue(values.next());
            }

            public void remove() {
                values.remove();
            }
        }

        public boolean addAll(Iterable<? extends java.lang.Object> elements) {
            return valueSpecs.addAll(new SpecificationIterable(elements));
        }

        public boolean containsAll(Iterable<? extends java.lang.Object> elements) {
            return valueSpecs.containsAll(new SpecificationIterable(elements));
        }

        public boolean removeAll(Iterable<? extends java.lang.Object> elements) {
            return valueSpecs.removeAll(new SpecificationIterable(elements));
        }

        class ValueIterator implements Iterator<java.lang.Object> {
            private Iterator<ValueSpecification<UmlClass,Property,java.lang.Object>> specIterator = valueSpecs.iterator();

            public boolean hasNext() {
                return specIterator.hasNext();
            }

            public java.lang.Object next() {
                return valueForSpecification(specIterator.next());
            }

            public void remove() {
                specIterator.remove();
            }

        }

        public Iterator<java.lang.Object> iterator() {
            return new ValueIterator();
        }

        public boolean addAll(int index, Iterable<? extends java.lang.Object> elements) {
            return valueSpecs.addAll(index, new SpecificationIterable(elements));
        }

        public int size() {
            return valueSpecs.size();
        }

        @Override
        public String toString() {
            return valueSpecs.toString();
        }

        public java.util.List<java.lang.Object> toJavaUtil() {
            java.util.List<java.lang.Object> result = new java.util.Vector<java.lang.Object>();
            for (java.lang.Object o: this) {
                result.add(o);
            }
            return Collections.unmodifiableList(result);
        }

        public void clear() {
            valueSpecs.clear();
        }
    }

    public ReflectiveCollection<? extends cmof.reflection.Object> objectsOfType(UmlClass type, boolean includeSubtypes) {
        ReflectiveCollection<? extends cmof.reflection.Object> result;
        if (type == null) {
            return objects;
        } else {
            result = new SetImpl<cmof.reflection.Object>((!includeSubtypes) ? objectsForTypes.get(type) : objectsForTypesWithSubtypes.get(type));
        }
        return result;
    }

    public ReflectiveCollection<cmof.reflection.Link> linksOfType(Association type) {
        return null;
    }

    public ReflectiveCollection<cmof.reflection.Object> linkedObjects(Association association, cmof.reflection.Object endObject, boolean end1to2direction) {
        return null;
    }

    public boolean linkExists(Association association, cmof.reflection.Object firstObject, cmof.reflection.Object secondObject) {
        return false;
    }

    public void addObject(cmof.reflection.Object object) {
        if (!(object instanceof ObjectImpl)) {
            objects.add(object);
        } else if (!((ObjectImpl)object).isStatic) {
            addObject((ObjectImpl)object);
        } else {
            objects.add(object);
            ((ObjectImpl)object).setExtent(this);
            ((ObjectImpl)object).setParentIdentity(this);
        }
    }

    protected void addObject(ObjectImpl object) {
        instanceForObject.put(object, object.getClassInstance());
        objectForInstance.put(object.getClassInstance(), object);
        UmlClass metaClass = object.getMetaClass();
        if (metaClass != null) {
            objectsForTypes.put(metaClass, object);
            objectsForTypesWithSubtypes.put(metaClass, object);
            //if (!((ObjectImpl)metaClass).isStatic) {
                ReflectiveCollection<? extends core.abstractions.umlsuper.Classifier> superClasses = metaClass.allParents();
                for (core.abstractions.umlsuper.Classifier superClass: superClasses) {
                    if (superClass instanceof UmlClass) {
                        objectsForTypesWithSubtypes.put((UmlClass)superClass, object);
                    }
                }
            //}
        }

        this.objects.add(object);
        object.setParentIdentity(this);
        object.setExtent(this);
    }

    protected void removeObject(cmof.reflection.Object object, ClassInstance<UmlClass,Property,java.lang.Object> instance) {
        if (!objects.remove(object)) {
            throw new RuntimeException("assert");
        }
        instanceForObject.remove(object);
        objectForInstance.remove(instance);
        if (object.getMetaClass() != null) {
            objectsForTypes.removeValue(object);
            objectsForTypesWithSubtypes.removeValue(object);
        }
        instance.delete();
    }

    public static void writeStaticModel(String fileName, String packageName, String className, Extent extent) throws IOException {
        new File(fileName.substring(0,fileName.lastIndexOf("/"))).mkdir();
        PrintWriter out = new PrintWriter(new File(fileName));
        if (packageName != null) {
            out.println("package " + packageName + ";");
        }
        out.println("public class " + className + "{");
        int index = 0;
        for (java.lang.Object o: extent.objectsOfType(null, true)) {
            if (o instanceof ObjectImpl) {
                ObjectImpl obj = (ObjectImpl)o;
                out.println("    private static " + Identifier.class.getCanonicalName() + " _" + obj.getId() + " = new " + Identifier.class.getCanonicalName() + "();");
                out.println("    private static " + cmof.reflection.Object.class.getCanonicalName() + " getObject" + index + "(" + ExtentImpl.class.getCanonicalName() + " extent) {");
                out.println("        " + cmof.reflection.Object.class.getCanonicalName() + " " + ObjectImpl.createFromObject((ObjectImpl)o).serialize());
                out.println("        return object;");
                out.println("    }");
                index ++;
            }
        }
        out.println("    public static " + Extent.class.getCanonicalName() + " createModel() {");
        out.println("        " + ExtentImpl.class.getCanonicalName() + " extent = new " + ExtentImpl.class.getCanonicalName() + "(true);");
        out.println("        " + cmof.reflection.Object.class.getCanonicalName() + " object = null;");
        int index1 = 0;
        for (java.lang.Object o: extent.objectsOfType(null, true)) {
            if (o instanceof ObjectImpl) {
                out.println("extent.addObject(getObject" + index1 + "(extent));");
                index1++;
            }
        }
        out.println("        return extent;");
        out.println("    }");
        out.println("}");
        out.close();
    }

    public ReflectiveCollection<cmof.reflection.Object> getObject() {
        return objects;
    }

    public cmof.reflection.Object query(String queryString) throws cmof.exception.QueryParseException {
        Query query;
        try {
            query = Query.createQuery(queryString);
        } catch (ParseException ex) {
            throw new cmof.exception.QueryParseException(ex.getMessage());
        }
        cmof.reflection.Object result = null;
        for (cmof.reflection.Object outermost: getObject()) {
            result = query.evaluate(outermost);
            if (result != null) {
                return result;
            }
        }
        return result;
    }

    protected boolean contains(cmof.reflection.Object object) {
        return objects.contains(object);
    }

    public void myFinalize() {
        for (cmof.reflection.Object obj: objects) {
            if (obj instanceof ObjectImpl) {
                ((ObjectImpl)obj).myFinalize();
            }
        }
        objects.clear();
        model.myFinalize();
    }

    @SuppressWarnings({"unchecked"})
    public ReflectiveCollection<? extends cmof.reflection.Object> outermostComposites() {
        if (bootstrap) {
            if (bootstrapOutermostComposites == null) {
                bootstrapOutermostComposites = new ListImpl<cmof.reflection.Object>();
                for (cmof.reflection.Object o : getObject()) {
                    if (o.container() == null) {
                        bootstrapOutermostComposites.add(o);
                    }
                }
            }
            return bootstrapOutermostComposites;
        } else {
            ReflectiveCollection<cmof.reflection.Object> result = new ListImpl<cmof.reflection.Object>();
            for (ValueSpecification<UmlClass, Property, Object> value : model.getOutermostComposites()) {
                result.add(valueForSpecification(value));
            }
            return result;
        }
    }

    public UmlClass resolveM3Element(String qualifiedName) {
        if (!Repository.getLocalRepository().getExtent(Repository.CMOF_EXTENT_NAME).equals(this)) {
            return null;
        }
        UmlClass m3Element = m3ElementCache.get(qualifiedName);
        if (m3Element == null) {
            cmof.reflection.Object result = null;
            String[] names = qualifiedName.split("\\.");
            ReflectiveCollection<? extends cmof.reflection.Object> elements = outermostComposites();
            names: for (String name : names) {
                for (cmof.reflection.Object o : elements) {
                    if (o instanceof NamedElement) {
                        if (name.equals(((NamedElement)o).getName())) {
                            elements = o.getComponents();
                            result = o;
                            continue names;
                        }
                    }
                }
                return null;
            }
            if (result instanceof UmlClass) {
                m3ElementCache.put(qualifiedName, (UmlClass)result);
                m3Element = (UmlClass)result;
            }
        }
        return m3Element;
    }

    protected Map<Identifier, ObjectImpl> getObjectForId() {
        if (objectForId == null) {
            objectForId = new HashMap<Identifier, ObjectImpl>();
        }
        return objectForId;
    }

    protected ImplementationsManager getImplementationsManager() {
        if (implementationsManager == null) {
            implementationsManager = createImplementationManager();
        }
        return implementationsManager;
    }
}
