package Instances;


/**
 * <b>Instance</b>, superClass = {Instances.Class}
 */
public interface Instance extends Instances.UmlClass
{

    /**
     * <b>instantiates</b>, multiplicity=(1,1)
     */
    public Instances.UmlClass getInstantiates();

    public void setInstantiates(Instances.UmlClass value);

}

