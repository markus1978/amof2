package qualifier;


public class AQualifierTypeDlg extends hub.sam.mof.reflection.ObjectDlg implements AQualifierType
{
    protected AQualifierType self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (AQualifierType)self;
    }

    public java.lang.String getName() {
        return self.getName();
    }

    public void setName(java.lang.String value) {
        self.setName(value);
    }

}

