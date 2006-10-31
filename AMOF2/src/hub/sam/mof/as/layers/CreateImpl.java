package hub.sam.mof.as.layers;

import hub.sam.mof.reflection.Implementation;
import cmof.reflection.Factory;
import cmof.UmlClass;

public class CreateImpl implements Implementation  {
    private final cmof.reflection.Factory factory;
    private final UmlClass classifierToCreateInstanceFrom;

    public CreateImpl(Factory factory, UmlClass classifierToCreateInstanceFrom) {
        super();
        this.factory = factory;
        this.classifierToCreateInstanceFrom = classifierToCreateInstanceFrom;
    }

    public Object invoke(cmof.reflection.Object object, Object[] args) {
        cmof.reflection.Object result = factory.create(classifierToCreateInstanceFrom);
        result.set(M1SemanticModel.getClassifierPropertyName((UmlClass)classifierToCreateInstanceFrom.getMetaClassifier()),
                object);
        return result;
    }
}
