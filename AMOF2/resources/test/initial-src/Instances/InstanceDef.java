package Instances;


/**
 * <b>InstanceDef</b>, isAbstract, superClass = {Instances.Classifier}
 */
public interface InstanceDef extends Instances.Classifier
{

    /**
     * <b>implicitInstance</b>, multiplicity=(1,1)
     */
    public core.abstractions.instances.InstanceSpecification getImplicitInstance();

    public void setImplicitInstance(core.abstractions.instances.InstanceSpecification value);

}

