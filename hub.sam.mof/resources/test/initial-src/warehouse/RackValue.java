package warehouse;


public class RackValue extends hub.sam.mof.jocl.standardlib.OclModelElement<Rack>
{
    public RackValue (Rack self) {
        super(self);
    }



    public hub.sam.mof.jocl.standardlib.OclSet<warehouse.BoxValue,warehouse.Box> getBox() {
        return (hub.sam.mof.jocl.standardlib.OclSet<warehouse.BoxValue,warehouse.Box>)get("box", hub.sam.mof.jocl.standardlib.OclSet.class);
    }

    public hub.sam.mof.jocl.standardlib.OclSet<warehouse.ElementValue,warehouse.Element> getContent() {
        return (hub.sam.mof.jocl.standardlib.OclSet<warehouse.ElementValue,warehouse.Element>)get("content", hub.sam.mof.jocl.standardlib.OclSet.class);
    }

    public hub.sam.mof.jocl.standardlib.OclString getIdentifier() {
        return (hub.sam.mof.jocl.standardlib.OclString)get("identifier", hub.sam.mof.jocl.standardlib.OclString.class);
    }


    public hub.sam.mof.jocl.standardlib.OclString getFoo() {
        return (hub.sam.mof.jocl.standardlib.OclString)get("foo", hub.sam.mof.jocl.standardlib.OclString.class);
    }


    public hub.sam.mof.jocl.standardlib.OclString getPosition() {
        return (hub.sam.mof.jocl.standardlib.OclString)get("position", hub.sam.mof.jocl.standardlib.OclString.class);
    }


    public warehouse.ContainerValue getContainer() {
        return (warehouse.ContainerValue)get("container", warehouse.ContainerValue.class);
    }




}

