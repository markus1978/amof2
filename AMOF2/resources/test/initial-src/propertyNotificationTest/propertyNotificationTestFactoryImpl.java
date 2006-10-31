package propertyNotificationTest;


public class propertyNotificationTestFactoryImpl extends hub.sam.mof.reflection.FactoryImpl implements propertyNotificationTestFactory {
    public propertyNotificationTestFactoryImpl(hub.sam.mof.reflection.ExtentImpl extent, cmof.Package path) {
        super(extent, path);
    }

    public propertyNotificationTest.Element createElement() {
        return (propertyNotificationTest.Element) create("propertyNotificationTest.Element");
    }

    public propertyNotificationTest.Container createContainer() {
        return (propertyNotificationTest.Container) create("propertyNotificationTest.Container");
    }

}

