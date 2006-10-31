package qualifier;


public class QualifierTypeDlg extends hub.sam.mof.reflection.ObjectDlg implements QualifierType
{
    protected QualifierType self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (QualifierType)self;
    }

    public java.lang.String getName() {
        return self.getName();
    }

    public void setName(java.lang.String value) {
        self.setName(value);
    }

}

