package StateAutomaton.Semantics;


/**
 * <b>ComponentInstance</b>, superClass = {core.abstractions.instances.InstanceSpecification}
 */
public interface ComponentInstance extends core.abstractions.instances.InstanceSpecification
{

    /**
     * <b>port</b>, multiplicity=(0,*), isComposite, isUnique
     */
    public cmof.common.ReflectiveCollection<? extends StateAutomaton.Semantics.PortInstance> getPort();

}

