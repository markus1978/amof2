package StateAutomaton.Syntax;


/**
 * <b>Automaton</b>, superClass = {StateAutomaton.Syntax.Component}
 */
public interface Automaton extends StateAutomaton.Syntax.Component
{

    /**
     * <b>state</b>, multiplicity=(1,*), isComposite, isUnique, subsettedProperty = {core.abstractions.ownerships.Element.ownedElement}
     */
    public cmof.common.ReflectiveCollection<? extends StateAutomaton.Syntax.State> getState();

    /**
     * <b>start</b>, multiplicity=(1,1)
     */
    public StateAutomaton.Syntax.State getStart();

    public void setStart(StateAutomaton.Syntax.State value);

    /**
     * <b>initial</b>, multiplicity=(1,1)
     */
    public int getInitial();

    public void setInitial(int value);

    /**
     * <b>transition</b>, multiplicity=(0,*), isComposite, isUnique, subsettedProperty = {core.abstractions.ownerships.Element.ownedElement}
     */
    public cmof.common.ReflectiveCollection<? extends StateAutomaton.Syntax.Transition> getTransition();

    /**
     * <b>system</b>, multiplicity=(1,1)
     */
    public StateAutomaton.Syntax.System getSystem();

    public void setSystem(StateAutomaton.Syntax.System value);

}

