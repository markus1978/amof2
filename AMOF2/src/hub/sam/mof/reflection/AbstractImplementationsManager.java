package hub.sam.mof.reflection;

import hub.sam.mof.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;
import java.util.Map;
import java.util.List;

public abstract class AbstractImplementationsManager<ClassifierType> implements ImplementationsManager<ClassifierType> {

    protected abstract Collection<? extends ClassifierType> getSuperClassForClassifier(ClassifierType subClass);
    protected abstract String getJavaImplementationClassNameForClassifier(ClassifierType classifier);

    /*
     * This cache causes thread unsafety, because without it, each ObjectImpl instance will get its own implementations
     * and therefore there cannot be any cuncurrent access to its delegators.
     */
    private Map<ClassifierType, Implementations> implementations = new HashMap<ClassifierType, Implementations>();

    @SuppressWarnings("unchecked")
    private List<ClassifierType> getNextLevelSuperClassifier(List<ClassifierType> priorLevel) {
        List<ClassifierType> result = new Vector<ClassifierType>();
        for (ClassifierType instance: priorLevel) {
            result.addAll(getSuperClassForClassifier(instance));
        }
        return result;
    }

    public Implementations getImplementationsForClassifier(ClassifierType metaClass) {
        Implementations result = implementations.get(metaClass);
        if (result == null) {
            List<ClassifierType> sortedSuperClassifier = new Vector<ClassifierType>();
            List<ClassifierType> actualSuperClassifierLevel = new Vector<ClassifierType>();
            actualSuperClassifierLevel.add(metaClass);
            while (actualSuperClassifierLevel.size() > 0) {
                for (ClassifierType superClassifier: actualSuperClassifierLevel) {
                    if (sortedSuperClassifier.contains(superClassifier)) {
                        sortedSuperClassifier.remove(superClassifier);
                    }
                    sortedSuperClassifier.add(superClassifier);
                }
                actualSuperClassifierLevel = getNextLevelSuperClassifier(actualSuperClassifierLevel);
            }

            List<ObjectDlg> delegates = new Vector<ObjectDlg>();
            for(ClassifierType umlClass: sortedSuperClassifier) {
                ObjectDlg delegate = null;
                try {
                    delegate = (ObjectDlg)createAnObjectImplInstance(getJavaImplementationClassNameForClassifier(umlClass));
                } catch (ClassNotFoundException ex) {
                    // empty
                } catch (Exception ex) {
                    throw new cmof.exception.IllegalArgumentException(ex);
                }
                if (delegate != null) {
                    delegates.add(delegate);
                }
            }
            result = createImplementations(delegates, metaClass);
            if (!Repository.threadsafe) {
                implementations.put(metaClass, result);
            }
        }
        return result;
    }

    protected Implementations createImplementations(List<ObjectDlg> delegates, ClassifierType forMetaClass) {
        return new ImplementationsImpl(delegates, null);
    }

    /**
     * Creates an object based on an existing instance and a given java class name.
     */
    private cmof.reflection.Object createAnObjectImplInstance(String className) throws Exception {
        Boolean exists = FactoryImpl.javaClassExists.get(className);
        if (exists == null) {
            java.lang.reflect.Constructor implementation = null;
            try {
                implementation = Thread.currentThread().getContextClassLoader().loadClass(className).getConstructor(new java.lang.Class[] {});
            } catch (Exception e) {
                // empty
            }
            if (implementation == null) {
                exists = Boolean.FALSE;
            } else {
                exists = Boolean.TRUE;
                FactoryImpl.javaClasses.put(className, implementation);
            }
            FactoryImpl.javaClassExists.put(className, exists);
        }
        if (exists) {
                return (cmof.reflection.Object)FactoryImpl.javaClasses.get(className).newInstance();
        } else {
            return null;
        }
    }
}
