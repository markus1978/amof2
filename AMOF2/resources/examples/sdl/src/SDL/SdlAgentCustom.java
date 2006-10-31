package SDL;

import Pattern.Instanciation.Slot;

public class SdlAgentCustom extends SdlAgentDlg {
    @Override
    public Slot instanciate() {
        SdlAgentInstanceSet result = self.metaCreateSdlAgentInstanceSet();
        result.initialize();
        return result;
    }
}
