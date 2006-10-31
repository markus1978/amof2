package propertyNotificationTest;


public class ContainerClientImpl extends hub.sam.mof.reflection.client.impl.ClientObjectImpl implements Container
{
    public ContainerClientImpl(hub.sam.mof.reflection.server.ServerObject remote) {
        super(remote);
    }

    public cmof.common.ReflectiveCollection<? extends propertyNotificationTest.Element> getContent() {
        java.lang.Object value = get("content");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperSetImpl((cmof.common.ReflectiveCollection)value, this, "content");
        }
    }

    public cmof.common.ReflectiveSequence<? extends propertyNotificationTest.Element> getOrderedContent() {
        java.lang.Object value = get("orderedContent");
        if (value == null) {
           return null;
        } else {
            return new hub.sam.mof.util.TypeWrapperListImpl((cmof.common.ReflectiveSequence)value, this, "orderedContent");
        }
    }

}

