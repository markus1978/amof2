package SDL;

public class SdlStateAutomatonCustom extends SdlStateAutomatonDlg {

    @Override
    public Start getStart() {
        for(SdlAbstractState state: self.getState()) {
            if (state instanceof Start) {
                return (Start)state;
            }
        }
        return null;
    }
}
