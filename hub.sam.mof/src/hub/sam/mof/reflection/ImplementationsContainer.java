package hub.sam.mof.reflection;

import cmof.Property;
import cmof.Operation;
import hub.sam.mof.instancemodel.ClassifierSemantics;

import java.util.List;

public class ImplementationsContainer implements Implementations {

    Implementations[] implementations;

    public ImplementationsContainer(Implementations[] implementations) {
        this.implementations = implementations;
    }

    public boolean hasImplementationFor(Property property, ClassifierSemantics<Property, Operation, String> semantics) {
        for(Implementations implementation: implementations) {
            if (implementation.hasImplementationFor(property, semantics)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasImplementationFor(
            Operation operatoin, ClassifierSemantics<Property, Operation, String> semantics) {
        for(Implementations implementation: implementations) {
            if (implementation.hasImplementationFor(operatoin, semantics)) {
                return true;
            }
        }
        return false;
    }

    public List<ObjectDlg> getDelegates() {
        return null;
    }

    public Object invokeImplementationFor(Property property, cmof.reflection.Object object,
                                          ClassifierSemantics<Property, Operation, String> semantics) {
        for(Implementations implementation: implementations) {
            if (implementation.hasImplementationFor(property, semantics)) {
                return implementation.invokeImplementationFor(property, object, semantics);
            }
        }
        return null;
    }

    public Object invokeImplementationFor(Operation operation, cmof.reflection.Object object, Object[] args,
                                          ClassifierSemantics<Property, Operation, String> semantics) {
        for(Implementations implementation: implementations) {
            if (implementation.hasImplementationFor(operation, semantics)) {
                return implementation.invokeImplementationFor(operation, object, args, semantics);
            }
        }
        return null;
    }
}
