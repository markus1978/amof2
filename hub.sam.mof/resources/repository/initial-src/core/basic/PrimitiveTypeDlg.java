package core.basic;


public class PrimitiveTypeDlg extends hub.sam.mof.reflection.ObjectDlg implements PrimitiveType
{
    protected PrimitiveType self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (PrimitiveType)self;
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

