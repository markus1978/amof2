package propertyNotificationTest;


public class propertyNotificationTestFactoryClientImpl extends hub.sam.mof.reflection.client.impl.ClientFactoryImpl implements propertyNotificationTestFactory {
    public propertyNotificationTestFactoryClientImpl(hub.sam.mof.reflection.server.ServerFactory serverFactory) {
        super(serverFactory);
    }

    public propertyNotificationTest.Element createElement() {
        return (propertyNotificationTest.Element) create("propertyNotificationTest.Element");
    }

    public propertyNotificationTest.Container createContainer() {
        return (propertyNotificationTest.Container) create("propertyNotificationTest.Container");
    }

}

