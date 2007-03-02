package propertyNotificationTest;


public class ContainerImpl extends hub.sam.mof.reflection.ObjectImpl implements Container
{
    public ContainerImpl(hub.sam.mof.instancemodel.ClassInstance instance, hub.sam.mof.reflection.ExtentImpl extent) {
        super(instance, extent);
    }
    public ContainerImpl(hub.sam.mof.reflection.Identifier id, hub.sam.mof.reflection.ExtentImpl extent, hub.sam.mof.reflection.Identifier metaId, java.lang.String implementationClassName, java.lang.String[] delegateClassNames) {
        super(id, extent, metaId, implementationClassName, delegateClassNames);
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

