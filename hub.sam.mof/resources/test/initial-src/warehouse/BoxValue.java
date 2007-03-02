package warehouse;


public class BoxValue extends hub.sam.mof.jocl.standardlib.OclModelElement<Box>
{
    public BoxValue (Box self) {
        super(self);
    }



    public hub.sam.mof.jocl.standardlib.OclSet<warehouse.ItemValue,warehouse.Item> getItem() {
        return (hub.sam.mof.jocl.standardlib.OclSet<warehouse.ItemValue,warehouse.Item>)get("item", hub.sam.mof.jocl.standardlib.OclSet.class);
    }

    public hub.sam.mof.jocl.standardlib.OclInteger getWeight() {
        return (hub.sam.mof.jocl.standardlib.OclInteger)get("weight", hub.sam.mof.jocl.standardlib.OclInteger.class);
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

