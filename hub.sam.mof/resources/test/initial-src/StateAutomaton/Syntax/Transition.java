package StateAutomaton.Syntax;


/**
 * <b>Transition</b>, superClass = {core.abstractions.relationships.DirectedRelationship}
 */
public interface Transition extends core.abstractions.relationships.DirectedRelationship
{

    /**
     * <b>targetState</b>, multiplicity=(1,1), subsettedProperty = {core.abstractions.relationships.DirectedRelationship.target}
     */
    public StateAutomaton.Syntax.State getTargetState();

    public void setTargetState(StateAutomaton.Syntax.State value);

    /**
     * <b>sourceState</b>, multiplicity=(1,1), subsettedProperty = {core.abstractions.relationships.DirectedRelationship.source}
     */
    public StateAutomaton.Syntax.State getSourceState();

    public void setSourceState(StateAutomaton.Syntax.State value);

    /**
     * <b>input</b>, multiplicity=(1,1), isComposite, subsettedProperty = {core.abstractions.ownerships.Element.ownedElement}
     */
    public StateAutomaton.Syntax.Input getInput();

    public void setInput(StateAutomaton.Syntax.Input value);

    /**
     * <b>action</b>, multiplicity=(0,*), isComposite, isUnique, isOrdered, subsettedProperty = {core.abstractions.ownerships.Element.ownedElement}
     */
    public cmof.common.ReflectiveSequence<? extends StateAutomaton.Syntax.Action> getAction();

}

