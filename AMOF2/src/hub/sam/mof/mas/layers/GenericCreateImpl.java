package hub.sam.mof.mas.layers;

import hub.sam.mof.reflection.Implementation;
import hub.sam.mof.reflection.FactoryImpl;
import cmof.UmlClass;
import cmof.reflection.Factory;

public class GenericCreateImpl implements Implementation  {
    private final cmof.reflection.Factory factory;
    private final UmlClass classifierToCreateInstanceFrom;

    public GenericCreateImpl(Factory factory, UmlClass classifierToCreateInstanceFrom) {
        super();
        this.factory = factory;
        this.classifierToCreateInstanceFrom = classifierToCreateInstanceFrom;
    }

    public Object invoke(cmof.reflection.Object object, Object[] args) {
        String className = (String)args[args.length - 1];
        cmof.reflection.Object result = ((FactoryImpl)factory).create(className);
        result.set(M1SemanticModel.getClassifierPropertyName((UmlClass)classifierToCreateInstanceFrom.getMetaClassifier()),
                object);
        return result;
    }
}
