package StateAutomaton.Semantics;


/**
 * <b>PortInstance</b>, superClass = {core.abstractions.instances.InstanceSpecification}
 */
public interface PortInstance extends core.abstractions.instances.InstanceSpecification
{

    /**
     * <b>signal</b>, multiplicity=(0,*), isComposite, isUnique, isOrdered
     */
    public cmof.common.ReflectiveSequence<? extends StateAutomaton.Semantics.SignalInstance> getSignal();

    /**
     * <b>component</b>, multiplicity=(1,1)
     */
    public StateAutomaton.Semantics.ComponentInstance getComponent();

    public void setComponent(StateAutomaton.Semantics.ComponentInstance value);

    /**
     * <b>classifier</b>, multiplicity=(1,1), isUnique, redefinedProperty = {core.abstractions.instances.InstanceSpecification.classifier}
     */
    public cmof.common.ReflectiveCollection<? extends StateAutomaton.Syntax.Port> getClassifier();

    /**
     * <b>opposite</b>, multiplicity=(0,1), isDerived
     */
    public StateAutomaton.Semantics.PortInstance getOpposite();

    /**
     * <b>send</b>, multiplicity=(1,1)
     */
    public void send() ;

}

