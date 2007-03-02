package hub.sam.mof.reflection;


public interface ImplementationsManager<ClassifierType> {
    public Implementations getImplementationsForClassifier(ClassifierType metaClass);
}
