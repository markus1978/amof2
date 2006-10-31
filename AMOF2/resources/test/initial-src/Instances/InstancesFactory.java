package Instances;


public interface InstancesFactory extends cmof.reflection.Factory {

    public Instances.Classifier createClassifier();

    public Instances.UmlClass createUmlClass();

    public Instances.Instance createInstance();

}

