package qualifier;


public class SourceDlg extends hub.sam.mof.reflection.ObjectDlg implements Source
{
    protected Source self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (Source)self;
    }

    public qualifier.Target getSuperAttr(qualifier.QualifierType qualifier) {
        return (qualifier.Target)(java.lang.Object)self.getSuperAttr(qualifier);
    }

    public void setSuperAttr(qualifier.QualifierType qualifier, qualifier.Target value) {
        self.setSuperAttr(qualifier, value);
    }

    public java.lang.String getName() {
        return self.getName();
    }

    public void setName(java.lang.String value) {
        self.setName(value);
    }

}

