package SDL;

import Pattern.Evaluation.Expression;

public class SdlInstanceCustom extends SdlInstanceDlg {

    @Override
    public void initialize() {
        self.createSlots();
    }

    @Override
    public void createSlots() {
        for(SdlVariable variable : self.getMetaClassifierSdlClassifier().getVariable()) {
            SdlVariableSlot slot = variable.metaCreateSdlVariableSlot();
            self.setVariable(variable, slot);
            Expression expr = variable.getInitExpression();
            if (expr != null) {
                SdlEvaluation eval = (SdlEvaluation)expr.instantiate();
                eval.updateContext(self);
                SdlDataValue value = (SdlDataValue)eval.getValue();
                slot.updateValue(value);
            }
        }
    }

    @Override
    public SdlVariableSlot resolveSlot(SdlVariable v) {
        SdlInstance instance = self;
        while (instance != null) {
            SdlVariableSlot result = instance.getVariable(v);
            if (result != null) {
                return result;
            } else {
                instance = getContainingInstance();
            }
        }
        return null;
    }
}
