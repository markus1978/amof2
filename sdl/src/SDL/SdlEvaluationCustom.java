package SDL;

import Pattern.Evaluation.Evaluation;

public class SdlEvaluationCustom extends SdlEvaluationDlg {
    @Override
    public void updateContext(SdlInstance c) {
        self.setContext(c);
        for (Evaluation eval: self.getSubEvaluation()) {
            if (eval instanceof SdlEvaluation) {
                ((SdlEvaluation)eval).updateContext(c);
            }
        }
    }
}
