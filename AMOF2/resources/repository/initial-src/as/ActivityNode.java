package as;


/**
 * <b>ActivityNode</b>, isAbstract, superClass = {cmof.NamedElement}
 */
public interface ActivityNode extends cmof.NamedElement
{

    /**
     * <b>incoming</b>, multiplicity=(0,*), isUnique
     */
    public cmof.common.ReflectiveCollection<? extends as.ActivityEdge> getIncoming();

    /**
     * <b>activity</b>, multiplicity=(0,1)
     */
    public as.Activity getActivity();

    public void setActivity(as.Activity value);

    /**
     * <b>outgoing</b>, multiplicity=(0,*), isUnique
     */
    public cmof.common.ReflectiveCollection<? extends as.ActivityEdge> getOutgoing();

}

