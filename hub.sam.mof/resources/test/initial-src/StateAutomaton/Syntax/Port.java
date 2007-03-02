package StateAutomaton.Syntax;


/**
 * <b>Port</b>, superClass = {Instances.InstanceDef}
 */
public interface Port extends Instances.InstanceDef
{

    /**
     * <b>implicitInstance</b>, multiplicity=(1,1), redefinedProperty = {Instances.InstanceDef.implicitInstance}
     */
    public StateAutomaton.Semantics.PortInstance getImplicitInstance();

    public void setImplicitInstance(StateAutomaton.Semantics.PortInstance value);

    public void setImplicitInstance(core.abstractions.instances.InstanceSpecification value);

    /**
     * <b>direction</b>, multiplicity=(1,1)
     */
    public StateAutomaton.Syntax.Direction getDirection();

    public void setDirection(StateAutomaton.Syntax.Direction value);

    /**
     * <b>component</b>, multiplicity=(0,1)
     */
    public StateAutomaton.Syntax.Component getComponent();

    public void setComponent(StateAutomaton.Syntax.Component value);

    /**
     * <b>channel</b>, multiplicity=(1,1)
     */
    public StateAutomaton.Syntax.Channel getChannel();

    public void setChannel(StateAutomaton.Syntax.Channel value);

    /**
     * <b>directionForComponent</b>, multiplicity=(1,1)
     */
    public StateAutomaton.Syntax.Direction directionForComponent(StateAutomaton.Syntax.Component component) ;

}

