package hub.sam.mof.mas;


/**
 * <b>DecisionNode</b>, superClass = {mas.ControlNode}
 */
public interface DecisionNode extends cmof.reflection.Object, hub.sam.mof.mas.ControlNode
{

    /**
     * <b>selected</b>, multiplicity=(1,1)
     */
    public hub.sam.mof.mas.ActivityEdge getSelected();

    public void setSelected(hub.sam.mof.mas.ActivityEdge value);

    /**
     * <b>contextList</b>, multiplicity=(0,1), isComposite, isUnique, isOrdered, redefinedProperty = {mas.ActivityNode.list}
     */
    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ContextPinList> getContextList();

    /**
     * <b>body</b>, multiplicity=(1,1)
     */
    public java.lang.String getBody();

    public void setBody(java.lang.String value);

    /**
     * <b>context</b>, multiplicity=(0,*), isComposite, isUnique, isOrdered, subsettedProperty = {cmof.Element.ownedElement}
     */
    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ContextPin> getContext();

}

