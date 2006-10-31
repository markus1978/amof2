package qualifier;


public class NamedElementDlg extends hub.sam.mof.reflection.ObjectDlg implements NamedElement
{
    protected NamedElement self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (NamedElement)self;
    }

    public java.lang.String getName() {
        return self.getName();
    }

    public void setName(java.lang.String value) {
        self.setName(value);
    }

}

