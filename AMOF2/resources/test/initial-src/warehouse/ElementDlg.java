package warehouse;


public class ElementDlg extends hub.sam.mof.reflection.ObjectDlg implements Element
{
    protected Element self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (Element)self;
    }

    public java.lang.String getIdentifier() {
        return self.getIdentifier();
    }

    public void setIdentifier(java.lang.String value) {
        self.setIdentifier(value);
    }

    public java.lang.String getPosition() {
        return self.getPosition();
    }

    public void setPosition(java.lang.String value) {
        self.setPosition(value);
    }

    public warehouse.Container getContainer() {
        return (warehouse.Container)(java.lang.Object)self.getContainer();
    }

    public void setContainer(warehouse.Container value) {
        self.setContainer(value);
    }

    public void test()  {
        self.test();
    }

}

