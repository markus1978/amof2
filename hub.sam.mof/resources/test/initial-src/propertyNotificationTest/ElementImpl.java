package propertyNotificationTest;


public class ElementImpl extends hub.sam.mof.reflection.ObjectImpl implements Element
{
    public ElementImpl(hub.sam.mof.instancemodel.ClassInstance instance, hub.sam.mof.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public ElementImpl(hub.sam.mof.reflection.Identifier id, hub.sam.mof.reflection.ExtentImpl extent, hub.sam.mof.reflection.Identifier metaId, java.lang.String implementationClassName, java.lang.String[] delegateClassNames) {
        super(id, extent, metaId, implementationClassName, delegateClassNames);
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

