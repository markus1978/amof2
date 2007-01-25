package hub.sam.mof.mas;


/**
 * <b>ActivityNode</b>, isAbstract, superClass = {mas.ConstrainedNode, cmof.NamedElement, mas.ActivityChild, mas.CommentedNode}
 */
public interface ActivityNode extends cmof.reflection.Object, hub.sam.mof.mas.ConstrainedNode, cmof.NamedElement, hub.sam.mof.mas.ActivityChild, hub.sam.mof.mas.CommentedNode
{

    /**
     * <b>list</b>, multiplicity=(0,*), isComposite, isUnique, isOrdered
     */
    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.AttachedNodeList> getList();

    /**
     * <b>activityAsNode</b>, multiplicity=(0,1)
     */
    public hub.sam.mof.mas.Activity getActivityAsNode();

    public void setActivityAsNode(hub.sam.mof.mas.Activity value);

    /**
     * <b>incoming</b>, multiplicity=(0,*), isUnique, isOrdered
     */
    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ActivityEdge> getIncoming();

    /**
     * <b>outgoing</b>, multiplicity=(0,*), isUnique, isOrdered
     */
    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ActivityEdge> getOutgoing();

}

