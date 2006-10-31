package SDL;

import Pattern.Instanciation.Value;
import hub.sam.sdlplus.SdlCompiler;

public class SdlPidExpressionEvaluationCustom extends SdlPidExpressionEvaluationDlg {

    private static SdlCompositeStateInstance getEnclosingState(SdlInstance context) {
        if (context instanceof SdlCompositeStateInstance) {
            return (SdlCompositeStateInstance)context;
        } else {
            throw new RuntimeException("assert, pid expression not allowed in the used context");
        }
    }

    private static SdlAgentInstance getEnclosingAgent(SdlInstance context) {
        if (context instanceof SdlCompositeStateInstance) {
            return ((SdlCompositeStateInstance)context).getOwningInstance();
        } else {
            return (SdlAgentInstance)context;
        }
    }

    @Override
    public Value getValue() {
        PidValue result = (PidValue)
                SdlCompiler.getCompiler().getPidType().metaGCreateSdlDataValue(PidValue.class.getName());
        SdlPidExpressionKind kind = self.getMetaClassifierSdlPidExpression().getKind();
        if (kind == SdlPidExpressionKind.SENDER) {
            result.setValue(getEnclosingState(self.getContext()).getSender());
        } else if (kind == SdlPidExpressionKind.SELF) {
            result.setValue(getEnclosingAgent(self.getContext()));
        } else if (kind == SdlPidExpressionKind.OFFSPRING) {
            result.setValue(getEnclosingAgent(self.getContext()).getOffspring());
        } else if (kind == SdlPidExpressionKind.PARENT) {
            result.setValue(getEnclosingAgent(self.getContext()).getParent());
        }
        return result;        
    }
}
