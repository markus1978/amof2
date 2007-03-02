package core.basic;


public class EnumerationDlg extends hub.sam.mof.reflection.ObjectDlg implements Enumeration
{
    protected Enumeration self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (Enumeration)self;
    }

    public cmof.common.ReflectiveSequence<? extends core.basic.EnumerationLiteral> getOwnedLiteral() {
        return (cmof.common.ReflectiveSequence<? extends core.basic.EnumerationLiteral>)(java.lang.Object)self.getOwnedLiteral();
    }

    public core.basic.Package getPackage() {
        return (core.basic.Package)(java.lang.Object)self.getPackage();
    }

    public void setPackage(core.basic.Package value) {
        self.setPackage(value);
    }

    public java.lang.String getName() {
        return self.getName();
    }

    public void setName(java.lang.String value) {
        self.setName(value);
    }

}

