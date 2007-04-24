package hub.sam.mof.runtimelayer;

import cmof.Association;
import cmof.Classifier;
import cmof.Operation;
import cmof.Package;
import cmof.Property;
import cmof.UmlClass;
import cmof.cmofFactory;
import cmof.Parameter;
import cmof.PrimitiveType;
import cmof.Type;
import cmof.common.ReflectiveCollection;
import cmof.exception.MetaModelException;
import hub.sam.mof.mofinstancemodel.MofClassSemantics;
import hub.sam.mof.mofinstancemodel.MofClassifierSemantics;
import hub.sam.mof.reflection.ObjectImpl;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

/**
 * Contains functionality to create implicit model elements in the semantic part of a meta-model.
 * This means that you can search in a model for "double-instance" classifiers, create the instance
 * association between them an their corresponding general classifier and create create and delete
 * operations for "double-instance" manipulation.
 *
 * Constraints for the instanceOf relation (M3-constraints)
 *
 * context Classifier
 * inv: self.metaInstances->size() <= 1 and self.metaClassifier->size() <= 1
 *
 * context InstanceOf
 * inv: self.metaInstance.allParents()->collect(metaClassifier)->forAll(c|self.metaClassifier.allParents().contains(c))
 *   and self.metaClassifier.allParents()->collect(metaInstance)->forAll(i|self.metaInstance.allParents().contains(i))
 */
public class M1SemanticModel {

    private static String CREATE_NAME = "metaCreate";
    private static String GENERIC_CREATE_NAME = "metaGCreate";
    private static String DELETE_NAME = "metaDelete";
    private static String INSTANCE_NAME = "metaInstance";
    private static String CLASSIFIER_NAME = "metaClassifier";

    private static String firstUpper(String string) {
        return string.substring(0,1).toUpperCase() + string.substring(1, string.length());
    }

    public static String getCreateOperationName(UmlClass instanceClassifier) {
        return CREATE_NAME + firstUpper(instanceClassifier.getName());
    }

       public static String getGenericCreateOperationName(UmlClass instanceClassifier) {
        return GENERIC_CREATE_NAME + firstUpper(instanceClassifier.getName());
    }

    @SuppressWarnings({"UNUSED_SYMBOL"})
    public static String getDestroyOperationName(UmlClass instanceClassifier) {
        return DELETE_NAME;
    }

    public static String getInstancePropertyName(UmlClass instanceClassifier) {
        return INSTANCE_NAME  + firstUpper(instanceClassifier.getName());
    }

    public static String getClassifierPropertyName(UmlClass classifierClassifier) {
        return CLASSIFIER_NAME  + firstUpper(classifierClassifier.getName());
    }

    private final cmofFactory factory;
    private PrimitiveType stringType = null;

    private Type getStringType(UmlClass extentMember) {
        if (stringType == null) {
            for(Object obj: ((ObjectImpl)extentMember).getExtent().getObject()) {
                if (obj instanceof PrimitiveType && "String".equals(((PrimitiveType)obj).getName())) {
                    stringType = (PrimitiveType)obj;
                }
            }
        }
        return stringType;
    }

    public M1SemanticModel(cmofFactory factory) {
        super();
        this.factory = factory;
    }

    public void createImplicitElements(Collection<Package> model) {
        List<Classifier> allInstanceClassifier = allInstanceClassifier(model);
        ClassifierParentsComparator cmp = new ClassifierParentsComparator();
        hub.sam.tools.Util.sortHO(allInstanceClassifier, cmp);
        for (Classifier instanceClassifier: allInstanceClassifier) {
            if (instanceClassifier instanceof UmlClass) {
                createImplicitElements((UmlClass)instanceClassifier);
            }
        }
    }

    private void createImplicitElements(UmlClass instanceClassifier) {
        UmlClass classifierClassifier = (UmlClass)instanceClassifier.getMetaClassifier();
        Collection<MofClassSemantics> parentInstanceClassifier = getParentInstanceClassifier(instanceClassifier);
        Collection<MofClassSemantics> parentClassifierClassifier = new HashSet<MofClassSemantics>();
        for(MofClassSemantics parentInstance: parentInstanceClassifier) {
            parentClassifierClassifier.add(MofClassifierSemantics.createNewClassClassifierForUmlClass(
                    (UmlClass)parentInstance.getClassifier().getMetaClassifier()));
        }
        if (parentClassifierClassifier.size() != parentInstanceClassifier.size()) {
            throw new MetaModelException("InstanceOf not allowed in this spezialization context: " + instanceClassifier.toString());
        }

        // create operation
        Operation create = factory.createOperation();
        create.setType(instanceClassifier);
        create.setName(getCreateOperationName(instanceClassifier));
        classifierClassifier.getOwnedOperation().add(create);
        for(MofClassSemantics parent: parentClassifierClassifier) {
            for (Classifier metaInstances: parent.getClassifier().getMetaInstances()) {
                add(create.getRedefinedOperation(), parent.getFinalOperation(getCreateOperationName((UmlClass)metaInstances)));
            }
        }

        // create generic create operation
        Operation genericCreate = factory.createOperation();
        genericCreate.setType(instanceClassifier);
        genericCreate.setName(getGenericCreateOperationName(instanceClassifier));
        Parameter parameter = factory.createParameter();
        parameter.setName("className");
        parameter.setType(getStringType(classifierClassifier));
        genericCreate.getFormalParameter().add(parameter);

        classifierClassifier.getOwnedOperation().add(genericCreate);
        for(MofClassSemantics parent: parentClassifierClassifier) {
            for (Classifier metaInstances: parent.getClassifier().getMetaInstances()) {
                add(genericCreate.getRedefinedOperation(),
                        // TODO very ugly coding of the operation name
                        parent.getFinalOperation(getGenericCreateOperationName((UmlClass)metaInstances) + "_" +
                                getStringType(classifierClassifier).getName()));
            }
        }

        Operation delete = factory.createOperation();
        delete.setName(getDestroyOperationName(instanceClassifier));
        instanceClassifier.getOwnedOperation().add(delete);
        for(MofClassSemantics parent: parentInstanceClassifier) {
            add(delete.getRedefinedOperation(), parent.getFinalOperation(getDestroyOperationName(parent.getClassifier())));
        }

        Association instanceOf = factory.createAssociation();
        instanceOf.setName(instanceClassifier.getName() + "InstanceOf");
        instanceClassifier.getPackage().getOwnedType().add(instanceOf);

        Property instance = createProperty(getInstancePropertyName(instanceClassifier),
                instanceClassifier, classifierClassifier, instanceOf, 0, -1);
        for(MofClassSemantics parent: parentClassifierClassifier) {
            for (Classifier metaInstance: parent.getClassifier().getMetaInstances()) {
                add(instance.getRedefinedProperty(),parent.getProperty(getInstancePropertyName((UmlClass)metaInstance)));
            }
        }

        Property classifier = createProperty(getClassifierPropertyName(classifierClassifier),
                classifierClassifier, instanceClassifier, instanceOf, 0, 1);
        for(MofClassSemantics parent: parentInstanceClassifier) {
            UmlClass metaClassifier = (UmlClass)parent.getClassifier().getMetaClassifier();
            if (metaClassifier != null) {
                add(classifier.getRedefinedProperty(), parent.getProperty(getClassifierPropertyName(metaClassifier)));
            }
        }
        instance.setOpposite(classifier);
        classifier.setOpposite(instance);
    }

    private void add(ReflectiveCollection col, Object o) {
        if (o == null) {
            throw new MetaModelException("assert");
        }
        col.add(o);
    }

    private Collection<MofClassSemantics> getParentInstanceClassifier(UmlClass instanceClassifier) {
        Collection<MofClassSemantics> parentInstanceClassifier = new HashSet<MofClassSemantics>();
        List<core.abstractions.umlsuper.Classifier> parentClassifier = new Vector<core.abstractions.umlsuper.Classifier>();
        for (core.abstractions.umlsuper.Classifier parent: instanceClassifier.allParents()) {
            parentClassifier.add(parent);
        }
        ClassifierParentsComparator cmp = new ClassifierParentsComparator();
        hub.sam.tools.Util.sortHO(parentClassifier, cmp);
        Collection<core.abstractions.umlsuper.Classifier> forbidden = new HashSet<core.abstractions.umlsuper.Classifier>();
        for (int i = parentClassifier.size() - 1; i >= 0; i--) {
            if (isInstanceClassifier((UmlClass)parentClassifier.get(i))) {
                if (!forbidden.contains(parentClassifier.get(i))) {
                    for(core.abstractions.umlsuper.Classifier parent: parentClassifier.get(i).allParents()) {
                        forbidden.add(parent);
                    }
                    parentInstanceClassifier.add(MofClassifierSemantics.createNewClassClassifierForUmlClass(
                            (UmlClass)parentClassifier.get(i)));
                }
            }
        }
        return parentInstanceClassifier;
    }

    private Property createProperty(
            String name, UmlClass type, UmlClass owner, Association association, int lower, int upper) {
        Property classifier = factory.createProperty();
        classifier.setType(type);
        classifier.setLower(lower);
        classifier.setUpper(upper);
        classifier.setName(name);
        classifier.setUmlClass(owner);
        classifier.setAssociation(association);
        return classifier;
    }

    private List<Classifier> allInstanceClassifier(Iterable<? extends Package> model) {
        List<Classifier> result = new Vector<Classifier>();
        for (Package aPackage: model) {
            result.addAll(allInstanceClassifier(aPackage.getNestedPackage()));
            for (Object member: aPackage.getOwnedMember()) {
                if (member instanceof Classifier) {
                    Classifier aClass = (Classifier)member;
                    if (isInstanceClassifier(aClass)) {
                        result.add(aClass);
                    }
                }
            }
        }

        return result;
    }

    private boolean isInstanceClassifier(Classifier classifier) {
        return classifier.getMetaClassifier() != null;
    }

    class ClassifierParentsComparator implements Comparator<core.abstractions.umlsuper.Classifier> {
        public int compare(core.abstractions.umlsuper.Classifier o1, core.abstractions.umlsuper.Classifier o2) {
            if (o2.allParents().contains(o1)) {
                return 1;
            } else {
                return -1;
            }
        }
    }
}
