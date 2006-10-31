package Pattern.Evaluation;

import SDL.SdlLiteralExpression;
import SDL.SdlOperationCall;
import SDL.SdlPidExpression;
import SDL.SdlVariableAccess;

public class ExpressionCustom extends ExpressionDlg {
    @Override
    public Evaluation instantiate() {
        Evaluation result = null;
        if (self instanceof SdlVariableAccess) {
            result = ((SdlVariableAccess)self).metaCreateSdlVariableAccessEvaluation();
        } else if (self instanceof SdlOperationCall) {
            result = ((SdlOperationCall)self).metaCreateSdlOperationEvaluation();
        } else if (self instanceof SdlPidExpression) {
            result = ((SdlPidExpression)self).metaCreateSdlPidExpressionEvaluation();
        } else if (self instanceof SdlLiteralExpression) {
            result = ((SdlLiteralExpression)self).metaCreateSdlLiteralEvaluation();
        } else {
            throw new RuntimeException("assert");
        }
        result.initializeSubEvaluations();
        return result;
    }
}
