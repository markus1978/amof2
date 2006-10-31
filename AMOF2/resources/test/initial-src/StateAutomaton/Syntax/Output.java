package StateAutomaton.Syntax;


/**
 * <b>Output</b>, superClass = {StateAutomaton.Syntax.SignalHandler, StateAutomaton.Syntax.Action}
 */
public interface Output extends StateAutomaton.Syntax.SignalHandler, StateAutomaton.Syntax.Action
{

    /**
     * <b>via</b>, multiplicity=(1,1)
     */
    public StateAutomaton.Syntax.Port getVia();

    public void setVia(StateAutomaton.Syntax.Port value);

    /**
     * <b>receiver</b>, multiplicity=(0,1)
     */
    public StateAutomaton.Syntax.Expression getReceiver();

    public void setReceiver(StateAutomaton.Syntax.Expression value);

}

