package hub.sam.mof.mas;


/**
 * <b>AttachedNodeList</b>, isAbstract
 */
public interface AttachedNodeList extends cmof.reflection.Object
{

    /**
     * <b>node</b>, multiplicity=(0,*), isDerived, isUnique, isOrdered
     */
    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.AttachedNode> getNode();

    /**
     * <b>attachedTo</b>, multiplicity=(0,1)
     */
    public hub.sam.mof.mas.ActivityNode getAttachedTo();

    public void setAttachedTo(hub.sam.mof.mas.ActivityNode value);

}

