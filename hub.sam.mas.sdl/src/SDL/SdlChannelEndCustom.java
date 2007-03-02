package SDL;

public class SdlChannelEndCustom extends SdlChannelEndDlg {
    @Override
    public SdlChannelEnd getOpposite() {
        for(SdlChannelEnd end: self.getChannel().getRelatedElement()) {
            if (!end.equals(self)) {
                return end;
            }
        }
        return self;
    }
}
