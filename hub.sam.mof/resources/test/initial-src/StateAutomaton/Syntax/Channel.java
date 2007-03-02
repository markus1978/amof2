package StateAutomaton.Syntax;


/**
 * <b>Channel</b>, superClass = {core.abstractions.relationships.DirectedRelationship}
 */
public interface Channel extends core.abstractions.relationships.DirectedRelationship
{

    /**
     * <b>sourcePort</b>, multiplicity=(1,1), subsettedProperty = {core.abstractions.relationships.DirectedRelationship.source}
     */
    public StateAutomaton.Syntax.Port getSourcePort();

    public void setSourcePort(StateAutomaton.Syntax.Port value);

    /**
     * <b>targetPort</b>, multiplicity=(1,1), subsettedProperty = {core.abstractions.relationships.DirectedRelationship.target}
     */
    public StateAutomaton.Syntax.Port getTargetPort();

    public void setTargetPort(StateAutomaton.Syntax.Port value);

}

