package StateAutomaton.Semantics;


public interface SemanticsFactory extends cmof.reflection.Factory {

    public StateAutomaton.Semantics.SignalInstance createSignalInstance();

    public StateAutomaton.Semantics.AutomatonInstance createAutomatonInstance();

    public StateAutomaton.Semantics.SingleAutomatonInstance createSingleAutomatonInstance();

    public StateAutomaton.Semantics.ComponentInstance createComponentInstance();

    public StateAutomaton.Semantics.SystemInstance createSystemInstance();

    public StateAutomaton.Semantics.PortInstance createPortInstance();

}

