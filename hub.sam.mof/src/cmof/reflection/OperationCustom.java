package cmof.reflection;

import cmof.NamedElement;
import cmof.Namespace;
import cmof.Operation;
import cmof.OperationDlg;
import cmof.Parameter;
import cmof.ParameterDirectionKind;
import cmof.Type;

import java.util.Collection;
import java.util.List;
import java.util.Vector;

public class OperationCustom extends OperationDlg {

    /**
     * Operations can also be distiguished by their parameter.
     *
     * @param n  The other element.
     * @param ns The namespace in whish have to reside together.
     * @return True if both elements can be distinguished from each other.
     */
    @Override
    public boolean isDistinguishableFrom(NamedElement n, Namespace ns) {
        boolean result = super.isDistinguishableFrom(n, ns);
        if (result) {
            return true;
        } else {
            Operation otherOperation = (Operation)n;
            Collection<Parameter> parameterForThis = getParameterForOperation(this);
            Collection<Parameter> parameterForOther = getParameterForOperation(otherOperation);
            if (parameterForThis.size() != parameterForOther.size()) {
                return true;
            }
            for (Parameter parameter1 : parameterForThis) {
                Type type1 = (Type)parameter1.getType();
                for (Parameter parameter2 : parameterForOther) {
                    Type type2 = (Type)parameter2.getType();
                    if (!type2.conformsTo(type1) && !type1.conformsTo(type2)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    private List<Parameter> getParameterForOperation(Operation operation) {
        List<Parameter> result = new Vector<Parameter>();
        for (Parameter parameter : operation.getFormalParameter()) {
            if (!parameter.getDirection().equals(ParameterDirectionKind.RETURN)) {
                result.add(parameter);
            }
        }
        return result;
    }
}
