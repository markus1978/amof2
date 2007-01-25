package hub.sam.mof.mas;


public class ActivityChildDlg extends hub.sam.mof.reflection.ObjectDlg implements ActivityChild
{
    protected ActivityChild self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (ActivityChild)self;
    }

}

