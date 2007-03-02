package warehouse;


public class BoxDlg extends hub.sam.mof.reflection.ObjectDlg implements Box
{
    protected Box self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (Box)self;
    }



    public cmof.common.ReflectiveCollection<? extends warehouse.Item> getItem() {
        return (cmof.common.ReflectiveCollection<? extends warehouse.Item>)(java.lang.Object)self.getItem();
    }

    public int getWeight() {
        return self.getWeight();
    }

    public void setWeight(int value) {
        self.setWeight(value);
    }

    public cmof.common.ReflectiveCollection<? extends warehouse.Element> getContent() {
        return (cmof.common.ReflectiveCollection<? extends warehouse.Element>)(java.lang.Object)self.getContent();
    }

    public java.lang.String getIdentifier() {
        return self.getIdentifier();
    }

    public void setIdentifier(java.lang.String value) {
        self.setIdentifier(value);
    }

    public java.lang.String getFoo() {
        return self.getFoo();
    }

    public void setFoo(java.lang.String value) {
        self.setFoo(value);
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

    public void fooOperation()  {
        self.fooOperation();
    }

}

