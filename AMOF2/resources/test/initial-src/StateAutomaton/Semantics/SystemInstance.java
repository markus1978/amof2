package StateAutomaton.Semantics;


/**
 * <b>SystemInstance</b>, superClass = {StateAutomaton.Semantics.ComponentInstance}
 */
public interface SystemInstance extends StateAutomaton.Semantics.ComponentInstance
{

    /**
     * <b>automaton</b>, multiplicity=(0,*), isComposite, isUnique
     */
    public cmof.common.ReflectiveCollection<? extends StateAutomaton.Semantics.AutomatonInstance> getAutomaton();

    /**
     * <b>classifier</b>, multiplicity=(1,1), isUnique, redefinedProperty = {core.abstractions.instances.InstanceSpecification.classifier}
     */
    public cmof.common.ReflectiveCollection<? extends StateAutomaton.Syntax.System> getClassifier();

}

