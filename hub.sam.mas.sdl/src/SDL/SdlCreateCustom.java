package SDL;

public class SdlCreateCustom  extends SdlCreateDlg {
    @Override
    public SdlAgentType getClassifier() {
        return self.getAgent().getType();
    }
}
