package warehouse;


public class warehouseFactoryImpl extends hub.sam.mof.reflection.FactoryImpl implements warehouseFactory {
    public warehouseFactoryImpl(hub.sam.mof.reflection.ExtentImpl extent, cmof.Package path) {
        super(extent, path);
    }

    public warehouse.Rack createRack() {
        return (warehouse.Rack) create("warehouse.Rack");
    }

    public warehouse.Box createBox() {
        return (warehouse.Box) create("warehouse.Box");
    }

    public warehouse.Item createItem() {
        return (warehouse.Item) create("warehouse.Item");
    }

}

