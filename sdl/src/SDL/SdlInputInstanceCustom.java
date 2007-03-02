package SDL;

import Pattern.Communication.Event;

public class SdlInputInstanceCustom extends SdlInputInstanceDlg {
    @Override
    public boolean listensTo(Event e) {
        SdlSignalInstance signal = (SdlSignalInstance)e;
        SdlAgentInstance instance = signal.getReceiver();
        if (instance != null) {
            if (!instance.equals(self.getOwningStateInstance().getOwningInstance())) {
                return false;
            }
        }

        if (!signal.getMetaClassifierSdlSignal().equals(self.getMetaClassifierInput().getType())) {
             return false;
        }

        return true;
    }
}
