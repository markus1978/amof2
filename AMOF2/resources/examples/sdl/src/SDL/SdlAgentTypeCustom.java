package SDL;

public class SdlAgentTypeCustom extends SdlAgentTypeDlg {
    @Override
    public SdlAgentInstance instanciate() {
        SdlAgentInstance result = self.metaCreateSdlAgentInstance();
        result.initialize();
        return result;
    }
}
