package hub.sam.mof.reflection;

public class ImplementationsManagerContainer<ClassifierType> implements ImplementationsManager<ClassifierType> {

    private final ImplementationsManager<ClassifierType>[] managers;

    public ImplementationsManagerContainer(ImplementationsManager<ClassifierType>[] managers) {
        this.managers = managers;
    }

    public Implementations getImplementationsForClassifier(ClassifierType metaClass) {
        Implementations[] implementations = new Implementations[managers.length];
        for(int i = 0; i < managers.length; i++) {
            implementations[i] = managers[i].getImplementationsForClassifier(metaClass);
        }
        return new ImplementationsContainer(implementations);
    }
}
