package propertyNotificationTest;


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

    public propertyNotificationTest.Container getContainer() {
        return (propertyNotificationTest.Container)(java.lang.Object)self.getContainer();
    }

    public void setContainer(propertyNotificationTest.Container value) {
        self.setContainer(value);
    }

    public propertyNotificationTest.Container getOrderedContainer() {
        return (propertyNotificationTest.Container)(java.lang.Object)self.getOrderedContainer();
    }

    public void setOrderedContainer(propertyNotificationTest.Container value) {
        self.setOrderedContainer(value);
    }

}

