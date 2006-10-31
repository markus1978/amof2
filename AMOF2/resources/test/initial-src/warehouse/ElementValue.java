package warehouse;


public class ElementValue extends hub.sam.mof.jocl.standardlib.OclModelElement<Element>
{
    public ElementValue (Element self) {
        super(self);
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

