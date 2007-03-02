package core.basic;


public class EnumerationLiteralDlg extends hub.sam.mof.reflection.ObjectDlg implements EnumerationLiteral
{
    protected EnumerationLiteral self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (EnumerationLiteral)self;
    }

    public core.basic.Enumeration getEnumeration() {
        return (core.basic.Enumeration)(java.lang.Object)self.getEnumeration();
    }

    public void setEnumeration(core.basic.Enumeration value) {
        self.setEnumeration(value);
    }

    public java.lang.String getName() {
        return self.getName();
    }

    public void setName(java.lang.String value) {
        self.setName(value);
    }

}

