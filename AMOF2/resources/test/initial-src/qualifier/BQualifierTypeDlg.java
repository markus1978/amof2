package qualifier;


public class BQualifierTypeDlg extends hub.sam.mof.reflection.ObjectDlg implements BQualifierType
{
    protected BQualifierType self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (BQualifierType)self;
    }

    public java.lang.String getName() {
        return self.getName();
    }

    public void setName(java.lang.String value) {
        self.setName(value);
    }

}

