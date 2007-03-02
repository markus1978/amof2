package SDL;

import Pattern.Instanciation.Instance;

public class SdlSignalCustom extends SdlSignalDlg {
    @Override
    public Instance instanciate() {
        Instance result = self.metaCreateSdlSignalInstance();
        result.initialize();
        return result;
    }
}
