package hub.sam.mof.mas;


/**
 * <b>InExpansionNodeList</b>, superClass = {mas.ExpansionNodeList}
 */
public interface InExpansionNodeList extends cmof.reflection.Object, hub.sam.mof.mas.ExpansionNodeList
{

    /**
     * <b>node</b>, multiplicity=(0,*), isDerived, isUnique, isOrdered, redefinedProperty = {mas.AttachedNodeList.node}
     */
    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.InExpansionNode> getNode();

}

