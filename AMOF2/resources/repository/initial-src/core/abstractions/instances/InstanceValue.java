package core.abstractions.instances;


/**
 * <b>InstanceValue</b>, superClass = {core.abstractions.expressions.ValueSpecification}
 */
public interface InstanceValue extends core.abstractions.expressions.ValueSpecification
{

    /**
     * <b>instance</b>, multiplicity=(1,1)
     */
    public core.abstractions.instances.InstanceSpecification getInstance();

    public void setInstance(core.abstractions.instances.InstanceSpecification value);

}

