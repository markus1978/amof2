package SDL;

import cmof.common.ReflectiveCollection;
import hub.sam.mof.util.SetImpl;

public class SdlStateTypeCustom extends SdlStateTypeDlg {
    @Override
    public ReflectiveCollection<? extends SdlState> getState() {
        ReflectiveCollection<? extends SdlState> result = new SetImpl<SdlState>();
        for (SdlNamedState state : self.getStateAutomaton().getNamedState()) {
            if (state instanceof SdlState) {
                result.add(state);
            }
        }
        return result;
    }
}
