package StateAutomaton.Syntax;


/**
 * <b>Create</b>, superClass = {StateAutomaton.Syntax.Action}
 */
public interface Create extends StateAutomaton.Syntax.Action
{

    /**
     * <b>automaton</b>, multiplicity=(1,1)
     */
    public StateAutomaton.Syntax.Automaton getAutomaton();

    public void setAutomaton(StateAutomaton.Syntax.Automaton value);

}

