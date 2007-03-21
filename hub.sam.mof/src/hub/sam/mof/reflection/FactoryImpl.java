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

import java.util.*;

import cmof.*;
import cmof.Package;
import cmof.exception.*;
import cmof.exception.IllegalArgumentException;
import cmof.reflection.*;
import hub.sam.mof.PlugInActivator;
import hub.sam.mof.instancemodel.*;

public class FactoryImpl extends hub.sam.util.Identity implements Factory {

    private final ExtentImpl extent;
    private static final hub.sam.mof.javamapping.JavaMapping javaMapping = hub.sam.mof.javamapping.JavaMapping.mapping;
    private cmof.Package metaModel = null;

    protected FactoryImpl(ExtentImpl extent) {
        super();
        setParentIdentity(extent);
        this.extent = extent;
    }

    protected FactoryImpl(ExtentImpl extent, cmof.Package metaModel) {
        super();
        setParentIdentity(extent);
        this.extent = extent;
        this.metaModel = metaModel;
    }

    public static FactoryImpl createFactory(Extent extent, cmof.Package forPackage) {
        if (forPackage == null) {
            return new FactoryImpl((ExtentImpl)extent);
        }
        
        String className = javaMapping.getFullQualifiedImplFactoryNameForPackage(forPackage);

        FactoryImpl factory = null;
        try {            
            java.lang.Class implementation = PlugInActivator.getClassLoader().loadClass(className);
            java.lang.reflect.Constructor constructor = implementation.getConstructor(new java.lang.Class[] {ExtentImpl.class, cmof.Package.class });
            factory = (FactoryImpl) constructor.newInstance(extent, forPackage);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return factory;
    }
    
    private static void collectAllPackages(Iterable<? extends cmof.Package> packages, Collection<cmof.Package> allPackages) {
    	for(cmof.Package aPackage: packages) {
    		allPackages.add(aPackage);
    		collectAllPackages(aPackage.getNestedPackage(), allPackages);
    	}
    }
    
    public static <T extends Factory> T createFactory(Extent extent, Iterable<? extends cmof.Package> metaModel, Class<T> factoryClass) {
    	Collection<cmof.Package> allPackages = new Vector<Package>();
    	collectAllPackages(metaModel, allPackages);
    	for (cmof.Package aPackage: allPackages) {
    		String className = javaMapping.getFullQualifiedImplFactoryNameForPackage(aPackage);
    		if (className.equals(factoryClass.getCanonicalName())) {
    	        Factory factory = null;
    	        try {            
    	            java.lang.Class implementation = PlugInActivator.getClassLoader().loadClass(className);
    	            java.lang.reflect.Constructor constructor = implementation.getConstructor(new java.lang.Class[] {ExtentImpl.class, cmof.Package.class });
    	            factory = (FactoryImpl) constructor.newInstance(extent, aPackage);
    	        } catch (Exception ex) {
    	            throw new RuntimeException(ex);
    	        }
    	        return (T)factory;
    		}
    	}
    	return null;
    }

    // TODO
    public static java.lang.Object staticCreateFromString(DataType dataType, String string) {
        try {
            if (dataType instanceof PrimitiveType) {
                if (dataType.getName().equals(core.primitivetypes.String.class.getSimpleName())) {
                    return string;
                } else if (dataType.getName().equals(core.primitivetypes.Object.class.getSimpleName())) {
                    return null;
                } else if (dataType.getName().equals(core.primitivetypes.Boolean.class.getSimpleName())) {
                    return Boolean.valueOf(string);
                } else if (dataType.getName().equals(core.primitivetypes.Integer.class.getSimpleName())) {
                    return new Integer(string);
                } else if (dataType.getName().equals(core.primitivetypes.UnlimitedNatural.class.getSimpleName())) {
                    if (string.equals("*")) {
                        return (long)-1;
                    } else {
                        return new Long(string);
                    }
                } else {
                    throw new IllegalArgumentException(dataType);
                }
            } else if (dataType instanceof cmof.Enumeration) {
                for (cmof.EnumerationLiteral literal: ((cmof.Enumeration)dataType).getOwnedLiteral()) {
                    if (literal.getName().equals(string)) {
                        try {
                            java.lang.Class implementation = PlugInActivator.getClassLoader().loadClass(
                                javaMapping.getFullQualifiedJavaIdentifier(dataType));
                            for (java.lang.Object enumConstant: implementation.getEnumConstants()) {
                                if (enumConstant.toString().equals(javaMapping.getJavaEnumConstantForLiteral(literal))) {
                                    return enumConstant;
                                }
                            }
                        } catch (Exception e) {
                            throw new RuntimeException("cannot create enum value");
                        }
                    }
                }
                throw new IllegalArgumentException("Unrecognized enum value");
            } else {
                throw new IllegalArgumentException(dataType);
            }
        } catch (Exception ex) {
            // some kind of parse exception
            if (ex instanceof IllegalArgumentException) {
                throw (RuntimeException) ex;
            } else {
                throw new IllegalArgumentException(ex);
            }
        }
    }

    public java.lang.Object createFromString(DataType dataType, String string) {
        return staticCreateFromString(dataType, string);
    }

    public String convertToString(DataType dataType, java.lang.Object value) {
        return null;
    }

    private Map<String, UmlClass> umlClassForName = new HashMap<String, UmlClass>();

    /**
     * Creates on object for a given meta-class. Uses {@link create(UmlClass)}.
     */
    public cmof.reflection.Object create(String metaClassName) {
        UmlClass metaClass = umlClassForName.get(metaClassName);
        if (metaClass == null) {
            if (metaModel != null) {
                loop: for (cmof.Element element: metaModel.getMember()) {
                    if (element instanceof UmlClass) {
                        if (((UmlClass)element).getQualifiedName().equals(metaClassName)) {
                            metaClass = (UmlClass)element;
                            break loop;
                        }
                    }
                }
            }
            if (metaClass != null) {
                umlClassForName.put(metaClassName, metaClass);
            }
        }
        if (metaClass == null) {
            throw new RuntimeException("Class not found: " + metaClassName);
        } else {
            return create(metaClass);
        }
    }

    /**
     * Creates an object based on existing instance. Uses the meta-class to construct java implementation class names.
     * Uses {@link createAnObjectImplInstance}.
     */
    @SuppressWarnings("unchecked")
	private cmof.reflection.Object createWithOutExtent(ClassInstance<UmlClass,Property,java.lang.Object> instance) {
        ObjectImpl result;

        String javaClassName = javaMapping.getImplClassNameForMetaClass(instance.getClassifier());

        try {
            result = (ObjectImpl)createAnObjectImplInstance(javaClassName, instance);
        } catch (Exception ex) {
            throw new cmof.exception.IllegalArgumentException(ex);
        }

        result.setImplementations(extent.getImplementationsManager().getImplementationsForClassifier(instance.getClassifier()));
        return result;
    }

    /**
     * Creates an object for an existing instance. Is based on {@link createWithOutExtent}.
     */
    public cmof.reflection.Object create(ClassInstance<UmlClass,Property,java.lang.Object> instance) {
        cmof.reflection.Object result = createWithOutExtent(instance);
        extent.addObject(result);
        return result;
    }

    /**
     * Creates a hole new instance and object arround it. Uses {@link createWithOutExtent}.
     */
    public cmof.reflection.Object create(UmlClass metaClass) {
        if (metaClass == null) {
            throw new NullPointerException();
        }

        ClassInstance<UmlClass,Property,java.lang.Object> instance = extent.model.createInstance(null,metaClass);
        cmof.reflection.Object result = createWithOutExtent(instance);

        extent.addObject(result);
        return result;
    }

    protected static final Map<String, java.lang.reflect.Constructor> javaClasses = new HashMap<String, java.lang.reflect.Constructor>();
    protected static final Map<String, Boolean> javaClassExists = new HashMap<String, Boolean>();

    /**
     * Creates an object based on an existing instance and a given java class name.
     */
    public cmof.reflection.Object createAnObjectImplInstance(String className, ClassInstance<UmlClass,Property,java.lang.Object> instance) throws Exception {
        Boolean exists = javaClassExists.get(className);
        if (exists == null) {
            java.lang.reflect.Constructor implementation = null;
            try {
            	implementation = PlugInActivator.getClassLoader().loadClass(className).getConstructor(new java.lang.Class[] {hub.sam.mof.instancemodel.ClassInstance.class, ExtentImpl.class });
            } catch (Exception e) {
                // empty
            }
            if (implementation == null) {
                exists = Boolean.FALSE;
            } else {
                exists = Boolean.TRUE;
                javaClasses.put(className, implementation);
            }
            javaClassExists.put(className, exists);
        }
        if (exists) {
        	return (cmof.reflection.Object)javaClasses.get(className).newInstance(instance, extent);
        } else {
            return null;
        }
    }

    public cmof.reflection.Link createLink(Association association, cmof.reflection.Object firstObject, cmof.reflection.Object secondObject) {
        return null;
    }

    public cmof.Package getMetaModel() {
    	return metaModel;
    }
}
