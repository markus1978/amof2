package hub.sam.mof.mas;


/**
 * <b>OutExpansionNodeList</b>, superClass = {mas.ExpansionNodeList}
 */
public interface OutExpansionNodeList extends cmof.reflection.Object, hub.sam.mof.mas.ExpansionNodeList
{

    /**
     * <b>node</b>, multiplicity=(0,*), isDerived, isUnique, isOrdered, redefinedProperty = {mas.AttachedNodeList.node}
     */
    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.OutExpansionNode> getNode();

}

