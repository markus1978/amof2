package StateAutomaton.Semantics;


/**
 * <b>AutomatonInstance</b>, superClass = {StateAutomaton.Semantics.ComponentInstance}
 */
public interface AutomatonInstance extends StateAutomaton.Semantics.ComponentInstance
{

    /**
     * <b>possibleSignals</b>, multiplicity=(0,*), isDerived, isUnique
     */
    public cmof.common.ReflectiveCollection<? extends StateAutomaton.Semantics.SignalInstance> getPossibleSignals();

    /**
     * <b>classifier</b>, multiplicity=(1,1), isUnique, redefinedProperty = {core.abstractions.instances.InstanceSpecification.classifier}
     */
    public cmof.common.ReflectiveCollection<? extends StateAutomaton.Syntax.Automaton> getClassifier();

    /**
     * <b>instance</b>, multiplicity=(0,*), isComposite, isUnique
     */
    public cmof.common.ReflectiveCollection<? extends StateAutomaton.Semantics.SingleAutomatonInstance> getInstance();

    /**
     * <b>consume</b>, multiplicity=(1,1)
     */
    public void consume() ;

    /**
     * <b>create</b>, multiplicity=(1,1)
     */
    public int create() ;

    /**
     * <b>instanceForId</b>, multiplicity=(1,1)
     */
    public StateAutomaton.Semantics.SingleAutomatonInstance instanceForId(int id) ;

}

