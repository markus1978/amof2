package StateAutomaton.Syntax;


public interface SyntaxFactory extends cmof.reflection.Factory {

    public StateAutomaton.Syntax.Offspring createOffspring();

    public StateAutomaton.Syntax.Port createPort();

    public StateAutomaton.Syntax.State createState();

    public StateAutomaton.Syntax.Sender createSender();

    public StateAutomaton.Syntax.Input createInput();

    public StateAutomaton.Syntax.Automaton createAutomaton();

    public StateAutomaton.Syntax.Output createOutput();

    public StateAutomaton.Syntax.Transition createTransition();

    public StateAutomaton.Syntax.System createSystem();

    public StateAutomaton.Syntax.Create createCreate();

    public StateAutomaton.Syntax.Signal createSignal();

    public StateAutomaton.Syntax.Channel createChannel();

    public StateAutomaton.Syntax.SignalHandler createSignalHandler();

}

