package propertyNotificationTest;


public class ContainerDlg extends hub.sam.mof.reflection.ObjectDlg implements Container
{
    protected Container self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (Container)self;
    }

    public cmof.common.ReflectiveCollection<? extends propertyNotificationTest.Element> getContent() {
        return (cmof.common.ReflectiveCollection<? extends propertyNotificationTest.Element>)(java.lang.Object)self.getContent();
    }

    public cmof.common.ReflectiveSequence<? extends propertyNotificationTest.Element> getOrderedContent() {
        return (cmof.common.ReflectiveSequence<? extends propertyNotificationTest.Element>)(java.lang.Object)self.getOrderedContent();
    }

}

