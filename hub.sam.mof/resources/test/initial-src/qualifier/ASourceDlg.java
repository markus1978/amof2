package qualifier;


public class ASourceDlg extends hub.sam.mof.reflection.ObjectDlg implements ASource
{
    protected ASource self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (ASource)self;
    }

    public qualifier.BTarget getSuperAttrB(qualifier.BQualifierType qualifier) {
        return (qualifier.BTarget)(java.lang.Object)self.getSuperAttrB(qualifier);
    }

    public void setSuperAttrB(qualifier.BQualifierType qualifier, qualifier.BTarget value) {
        self.setSuperAttrB(qualifier, value);
    }

    public qualifier.ATarget getSuperAttrA(qualifier.AQualifierType qualifier) {
        return (qualifier.ATarget)(java.lang.Object)self.getSuperAttrA(qualifier);
    }

    public void setSuperAttrA(qualifier.AQualifierType qualifier, qualifier.ATarget value) {
        self.setSuperAttrA(qualifier, value);
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

