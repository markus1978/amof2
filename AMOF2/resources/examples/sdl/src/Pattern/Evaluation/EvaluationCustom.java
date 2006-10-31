package Pattern.Evaluation;

import cmof.common.ReflectiveSequence;

public class EvaluationCustom extends EvaluationDlg {
    @Override
    public void initializeSubEvaluations() {
        ReflectiveSequence<? extends Evaluation> evaluations = self.getSubEvaluation();
        for(Expression subExpr: self.getMetaClassifierExpression().getSubExpression()) {
            evaluations.add(subExpr.instantiate());
        }
    }
}
