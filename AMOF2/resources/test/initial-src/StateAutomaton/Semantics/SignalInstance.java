package StateAutomaton.Semantics;


/**
 * <b>SignalInstance</b>, superClass = {core.abstractions.instances.InstanceSpecification}
 */
public interface SignalInstance extends core.abstractions.instances.InstanceSpecification
{

    /**
     * <b>destinationId</b>, multiplicity=(1,1)
     */
    public int getDestinationId();

    public void setDestinationId(int value);

    /**
     * <b>sourceId</b>, multiplicity=(1,1)
     */
    public int getSourceId();

    public void setSourceId(int value);

    /**
     * <b>classifier</b>, multiplicity=(1,1), isUnique, redefinedProperty = {core.abstractions.instances.InstanceSpecification.classifier}
     */
    public cmof.common.ReflectiveCollection<? extends StateAutomaton.Syntax.Signal> getClassifier();

}

