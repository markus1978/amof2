package core.basic;


public class PackageDlg extends hub.sam.mof.reflection.ObjectDlg implements Package
{
    protected Package self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (Package)self;
    }

    public cmof.common.ReflectiveCollection<? extends core.basic.Package> getNestedPackage() {
        return (cmof.common.ReflectiveCollection<? extends core.basic.Package>)(java.lang.Object)self.getNestedPackage();
    }

    public core.basic.Package getNestingPackage() {
        return (core.basic.Package)(java.lang.Object)self.getNestingPackage();
    }

    public void setNestingPackage(core.basic.Package value) {
        self.setNestingPackage(value);
    }

    public cmof.common.ReflectiveCollection<? extends core.basic.Type> getOwnedType() {
        return (cmof.common.ReflectiveCollection<? extends core.basic.Type>)(java.lang.Object)self.getOwnedType();
    }

    public java.lang.String getName() {
        return self.getName();
    }

    public void setName(java.lang.String value) {
        self.setName(value);
    }

}

