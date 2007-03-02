package StateAutomaton.Syntax;


/**
 * <b>SignalHandler</b>, superClass = {core.abstractions.typedelements.TypedElement}
 */
public interface SignalHandler extends core.abstractions.typedelements.TypedElement
{

    /**
     * <b>type</b>, multiplicity=(1,1), redefinedProperty = {core.abstractions.typedelements.TypedElement.type}
     */
    public StateAutomaton.Syntax.Signal getType();

    public void setType(StateAutomaton.Syntax.Signal value);

    public void setType(core.abstractions.typedelements.Type value);

}

