package as;


/**
 * <b>ActivityEdge</b>, isAbstract, superClass = {cmof.NamedElement}
 */
public interface ActivityEdge extends cmof.NamedElement
{

    /**
     * <b>guardSpecification</b>, multiplicity=(0,1), isComposite, subsettedProperty = {cmof.Element.ownedElement}
     */
    public as.GuardSpecification getGuardSpecification();

    public void setGuardSpecification(as.GuardSpecification value);

    /**
     * <b>source</b>, multiplicity=(1,1)
     */
    public as.ActivityNode getSource();

    public void setSource(as.ActivityNode value);

    /**
     * <b>target</b>, multiplicity=(1,1)
     */
    public as.ActivityNode getTarget();

    public void setTarget(as.ActivityNode value);

    /**
     * <b>activity</b>, multiplicity=(0,1)
     */
    public as.Activity getActivity();

    public void setActivity(as.Activity value);

}

