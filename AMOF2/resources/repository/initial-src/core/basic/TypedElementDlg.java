package core.basic;


public class TypedElementDlg extends hub.sam.mof.reflection.ObjectDlg implements TypedElement
{
    protected TypedElement self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (TypedElement)self;
    }

    public java.lang.String getName() {
        return self.getName();
    }

    public void setName(java.lang.String value) {
        self.setName(value);
    }

}

