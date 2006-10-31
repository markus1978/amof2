package propertyNotificationTest;


public class ElementClientImpl extends hub.sam.mof.reflection.client.impl.ClientObjectImpl implements Element
{
    public ElementClientImpl(hub.sam.mof.reflection.server.ServerObject remote) {
        super(remote);
    }

    public java.lang.String getIdentifier() {
        java.lang.Object value = get("identifier");
        if (value == null) {
           return null;
        } else {
            return (java.lang.String)value;
        }
    }

    public void setIdentifier(java.lang.String value) {
        set("identifier", value);
    }

    public propertyNotificationTest.Container getContainer() {
        java.lang.Object value = get("container");
        if (value == null) {
           return null;
        } else {
            return (propertyNotificationTest.Container)value;
        }
    }

    public void setContainer(propertyNotificationTest.Container value) {
        set("container", value);
    }

    public propertyNotificationTest.Container getOrderedContainer() {
        java.lang.Object value = get("orderedContainer");
        if (value == null) {
           return null;
        } else {
            return (propertyNotificationTest.Container)value;
        }
    }

    public void setOrderedContainer(propertyNotificationTest.Container value) {
        set("orderedContainer", value);
    }

}

