package warehouse;


public class ItemValue extends hub.sam.mof.jocl.standardlib.OclModelElement<Item>
{
    public ItemValue (Item self) {
        super(self);
    }


    public hub.sam.mof.jocl.standardlib.OclInteger getWeight() {
        return (hub.sam.mof.jocl.standardlib.OclInteger)get("weight", hub.sam.mof.jocl.standardlib.OclInteger.class);
    }


    public hub.sam.mof.jocl.standardlib.OclString getVisibility() {
        return (hub.sam.mof.jocl.standardlib.OclString)get("visibility", hub.sam.mof.jocl.standardlib.OclString.class);
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

