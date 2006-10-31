package warehouse;


public class ContainerValue extends hub.sam.mof.jocl.standardlib.OclModelElement<Container>
{
    public ContainerValue (Container self) {
        super(self);
    }


    public hub.sam.mof.jocl.standardlib.OclSet<warehouse.ElementValue,warehouse.Element> getContent() {
        return (hub.sam.mof.jocl.standardlib.OclSet<warehouse.ElementValue,warehouse.Element>)get("content", hub.sam.mof.jocl.standardlib.OclSet.class);
    }

    public hub.sam.mof.jocl.standardlib.OclString getIdentifier() {
        return (hub.sam.mof.jocl.standardlib.OclString)get("identifier", hub.sam.mof.jocl.standardlib.OclString.class);
    }


    public hub.sam.mof.jocl.standardlib.OclString getPosition() {
        return (hub.sam.mof.jocl.standardlib.OclString)get("position", hub.sam.mof.jocl.standardlib.OclString.class);
    }


    public warehouse.ContainerValue getContainer() {
        return (warehouse.ContainerValue)get("container", warehouse.ContainerValue.class);
    }



}

