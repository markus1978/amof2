package SDL;

import Pattern.Evaluation.Evaluation;
import Pattern.Instanciation.Value;
import hub.sam.sdlplus.datatypes.DataValueRepresentation;

import java.util.Collection;
import java.util.Vector;

public class SdlOperationEvaluationCustom extends SdlOperationEvaluationDlg {

    private static Object getValue(Value value) {
        if (value instanceof SdlGeneralValue) {
            Object result = ((SdlGeneralValue)value).getValue();
            value.metaDelete();
            return result;
        } else {
            return value;
        }
    }

    @Override
    public SdlDataValue getValue() {
        boolean first = true;
        SdlGeneralValue operand = null;
        Object[] arguments = new Object[self.getSubEvaluation().size() - 1];
        int i = 0;
        for (Evaluation subEvaluation: self.getSubEvaluation()) {
            if (first) {
                first = false;
                operand = (SdlGeneralValue)subEvaluation.getValue();
            } else {
                arguments[i++] = getValue(subEvaluation.getValue());
            }
        }

        String operationName = self.getMetaClassifierSdlOperationCall().getFeature().getName();
        Object result = ((DataValueRepresentation)operand.getValue()).callOperand(operationName, arguments);

        Collection<Value> toDelete = new Vector<Value>();
        for(Object arg: arguments) {
            if (arg instanceof Value) {
            	toDelete.add((Value)arg);
            }
        }
        for(Value value: toDelete) {
            value.metaDelete();
        }
        operand.metaDelete();

        if (result instanceof SdlDataValue) {
            return (SdlDataValue)result;
        } else {
            SdlDataType type = (SdlDataType)((SdlOperation)self.getMetaClassifierSdlOperationCall().getFeature()).getType();
            SdlGeneralValue value = (SdlGeneralValue)type.metaGCreateSdlDataValue(SdlGeneralValue.class.getName());
            value.setValue(result);
            return value;
        }
    }
}
