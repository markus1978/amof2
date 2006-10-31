package warehouse;


public class ItemDlg extends hub.sam.mof.reflection.ObjectDlg implements Item
{
    protected Item self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (Item)self;
    }

    public int getWeight() {
        return self.getWeight();
    }

    public void setWeight(int value) {
        self.setWeight(value);
    }

    public warehouse.VisibilityKind getVisibility() {
        return (warehouse.VisibilityKind)(java.lang.Object)self.getVisibility();
    }

    public void setVisibility(warehouse.VisibilityKind value) {
        self.setVisibility(value);
    }

    public hub.sam.mof.test.TestClass getJavaTypeTest() {
        return self.getJavaTypeTest();
    }

    public void setJavaTypeTest(hub.sam.mof.test.TestClass value) {
        self.setJavaTypeTest(value);
    }

    public cmof.common.ReflectiveCollection<hub.sam.mof.test.TestClass> getJavaTypeTestList() {
        return self.getJavaTypeTestList();
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

