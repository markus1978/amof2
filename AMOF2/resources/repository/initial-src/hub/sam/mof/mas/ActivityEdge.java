package hub.sam.mof.mas;


/**
 * <b>ActivityEdge</b>, isAbstract, superClass = {mas.ConnectionElement, cmof.NamedElement}
 */
public interface ActivityEdge extends cmof.reflection.Object, hub.sam.mof.mas.ConnectionElement, cmof.NamedElement
{

    /**
     * <b>guardSpecification</b>, multiplicity=(0,1), isComposite, subsettedProperty = {cmof.Element.ownedElement}
     */
    public hub.sam.mof.mas.GuardSpecification getGuardSpecification();

    public void setGuardSpecification(hub.sam.mof.mas.GuardSpecification value);

    /**
     * <b>activity</b>, multiplicity=(0,1)
     */
    public hub.sam.mof.mas.Activity getActivity();

    public void setActivity(hub.sam.mof.mas.Activity value);

    /**
     * <b>target</b>, multiplicity=(0,1)
     */
    public hub.sam.mof.mas.ActivityNode getTarget();

    public void setTarget(hub.sam.mof.mas.ActivityNode value);

    /**
     * <b>source</b>, multiplicity=(0,1)
     */
    public hub.sam.mof.mas.ActivityNode getSource();

    public void setSource(hub.sam.mof.mas.ActivityNode value);

}

