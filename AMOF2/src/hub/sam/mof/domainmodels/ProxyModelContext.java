package hub.sam.mof.domainmodels;

import cmof.Package;
import cmof.Property;
import cmof.Tag;
import cmof.Type;
import cmof.UmlClass;
import cmof.reflection.Extent;
import cmof.reflection.Factory;
import hub.sam.mof.Repository;
import hub.sam.mof.instancemodel.ClassInstance;
import hub.sam.mof.instancemodel.InstanceValue;
import hub.sam.mof.instancemodel.ValueSpecification;
import hub.sam.mof.mofinstancemodel.MofInstanceModel;
import hub.sam.mof.mofinstancemodel.MofPrimitiveDataValue;
import hub.sam.mof.reflection.ExtentImpl;
import hub.sam.mof.reflection.ObjectImpl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides auxillery functionality used to maintain proxy instances.
 */
public class ProxyModelContext {

    private final ProxyInstanceModel model;
    private final Map<Class, UmlClass> classes;
    private final Map<UmlClass, Factory> factories;

    @SuppressWarnings({"OverlyNestedMethod"})
    public ProxyModelContext(Collection<Package> packages, Repository repository, Extent extent) {
        super();
        model = (ProxyInstanceModel)((ExtentImpl)extent).getModel();
        classes = new HashMap<Class, UmlClass>();
        factories = new HashMap<UmlClass, Factory>();
        Map<Package, Factory> factoryForPackage = new HashMap<Package, Factory>();
        for (Package aPackage : packages) {
            for (Type aType : aPackage.getOwnedType()) {
                if (aType instanceof UmlClass) {
                    for (Tag aTag : aType.getTag()) {
                        if (aTag.getName().equals(JavaModelGenerationDoclet.PROXY_TAG)) {
                            String className = aTag.getValue();
                            Class javaClass;
                            try {
                                javaClass = Thread.currentThread().getContextClassLoader().loadClass(className);
                            } catch (ClassNotFoundException e) {
                                throw new ProxyModelException("Java class not found " + className);
                            }
                            classes.put(javaClass, (UmlClass)aType);

                            Factory factory = factoryForPackage.get(aPackage);
                            if (factory == null) {
                                factory = repository.createFactory(extent, aPackage);
                                factoryForPackage.put(aPackage, factory);
                            }
                            factories.put((UmlClass)aType, factory);
                        }
                    }
                }
            }
        }
    }

    boolean isProxyInstanceClass(UmlClass classifier) {
        for (Tag aTag: classifier.getTag()) {
            if (aTag.getName().equals(JavaModelGenerationDoclet.PROXY_TAG)) {
                return true;
            }
        }
        return false;
    }

    boolean isProxyClassClass(UmlClass classifier) {
        for (Tag aTag: classifier.getTag()) {
            if (aTag.getName().equals(JavaModelGenerationDoclet.CLASS_PROXY_TAG)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Takes an normal Java object and wrappes into a MOF value. That is either a primitive, enum, or proxy
     * object value.
     * @param object The Java Object
     * @return A ValueSpecification containing a according MOF value.
     */
    ValueSpecification<UmlClass, Property,Object> mantle(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Integer || object instanceof Long || object instanceof Boolean ||
                object instanceof String || object instanceof Enum) {
            return model.createPrimitiveValue(object);
        } else {
            UmlClass theClass = classes.get(object.getClass());
            if (theClass == null) {
                throw new ProxyModelException("Java value has a class not known to the proxy model.");
            }
            ObjectImpl obj = (ObjectImpl)factories.get(theClass).create(theClass);
            ((ProxyObjectInstance)obj.getClassInstance()).setTheObject(object);
            return model.createInstanceValue(obj.getClassInstance());
        }
    }

    Object mantleToRefObject(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Integer || object instanceof Long || object instanceof Boolean ||
                object instanceof String || object instanceof Enum) {
            return object;
        } else {
            UmlClass theClass = classes.get(object.getClass());
            if (theClass == null) {
                throw new ProxyModelException("Java value has a class not known to the proxy model.");
            }
            ObjectImpl obj = (ObjectImpl)factories.get(theClass).create(theClass);
            ((ProxyObjectInstance)obj.getClassInstance()).setTheObject(object);
            return obj;
        }
    }

    @SuppressWarnings({"unchecked"})
    Object disMantle(Object object) {
        if (object instanceof MofPrimitiveDataValue) {
            return ((MofPrimitiveDataValue)object).getValue();
        } else if (object instanceof InstanceValue) {
            ClassInstance<UmlClass,Property,Object> classInstance = ((InstanceValue)object).getInstance();
            if (classInstance instanceof ProxyObjectInstance) {
                return ((ProxyObjectInstance)classInstance).getTheObject();
            } else {
                throw new ProxyModelException("Try to use a non Java value in a proxy object context.");
            }
        } else {
            throw new ProxyModelException("assert");
        }
    }

    @SuppressWarnings({"unchecked"})
    Object disMantleFromRefObject(Object object) {
        if (object instanceof Integer || object instanceof Long || object instanceof Boolean ||
                object instanceof String || object instanceof Enum) {
            return object;
        } else if (object instanceof ObjectImpl) {
            ClassInstance<UmlClass,Property,Object> classInstance = ((ObjectImpl)object).getClassInstance();
            if (classInstance instanceof ProxyObjectInstance) {
                return ((ProxyObjectInstance)classInstance).getTheObject();
            } else {
                throw new ProxyModelException("Try to use a non Java value in a proxy object context.");
            }
        } else {
            throw new ProxyModelException("assert");
        }
    }

    public MofInstanceModel getModel() {
        return model;
    }

}
