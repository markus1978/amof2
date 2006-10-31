package core.abstractions.instances;


public interface instancesFactory extends cmof.reflection.Factory {

    public core.abstractions.instances.Slot createSlot();

    public core.abstractions.instances.InstanceSpecification createInstanceSpecification();

    public core.abstractions.instances.InstanceValue createInstanceValue();

}

