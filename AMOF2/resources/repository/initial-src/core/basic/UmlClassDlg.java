package core.basic;


public class UmlClassDlg extends hub.sam.mof.reflection.ObjectDlg implements UmlClass
{
    protected UmlClass self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (UmlClass)self;
    }

    public boolean isAbstract() {
        return self.isAbstract();
    }

    public void setIsAbstract(boolean value) {
        self.setIsAbstract(value);
    }

    public cmof.common.ReflectiveSequence<? extends core.basic.Property> getOwnedAttribute() {
        return (cmof.common.ReflectiveSequence<? extends core.basic.Property>)(java.lang.Object)self.getOwnedAttribute();
    }

    public cmof.common.ReflectiveSequence<? extends core.basic.Operation> getOwnedOperation() {
        return (cmof.common.ReflectiveSequence<? extends core.basic.Operation>)(java.lang.Object)self.getOwnedOperation();
    }

    public cmof.common.ReflectiveCollection<? extends core.basic.UmlClass> getSuperClass() {
        return (cmof.common.ReflectiveCollection<? extends core.basic.UmlClass>)(java.lang.Object)self.getSuperClass();
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

