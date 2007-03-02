package StateAutomaton.Syntax;


/**
 * <b>System</b>, superClass = {StateAutomaton.Syntax.Component}
 */
public interface System extends StateAutomaton.Syntax.Component
{

    /**
     * <b>automaton</b>, multiplicity=(0,*), isComposite, isUnique, subsettedProperty = {Instances.Classifier.ownedInstance}
     */
    public cmof.common.ReflectiveCollection<? extends StateAutomaton.Syntax.Automaton> getAutomaton();

    /**
     * <b>channel</b>, multiplicity=(0,*), isComposite, isUnique, subsettedProperty = {core.abstractions.ownerships.Element.ownedElement}
     */
    public cmof.common.ReflectiveCollection<? extends StateAutomaton.Syntax.Channel> getChannel();

    /**
     * <b>signal</b>, multiplicity=(0,*), isComposite, isUnique, subsettedProperty = {core.abstractions.namespaces.Namespace.ownedMember}
     */
    public cmof.common.ReflectiveCollection<? extends StateAutomaton.Syntax.Signal> getSignal();

}

