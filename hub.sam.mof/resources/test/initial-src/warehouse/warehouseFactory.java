package warehouse;


public interface warehouseFactory extends cmof.reflection.Factory {

    public warehouse.Rack createRack();

    public warehouse.Box createBox();

    public warehouse.Item createItem();

}

