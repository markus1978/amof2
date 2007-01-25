package hub.sam.mof.mas;


public class MarginDlg extends hub.sam.mof.reflection.ObjectDlg implements Margin
{
    protected Margin self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (Margin)self;
    }

    public int getMargin() {
        return self.getMargin();
    }

    public void setMargin(int value) {
        self.setMargin(value);
    }

}

