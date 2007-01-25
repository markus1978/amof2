package hub.sam.mof.mas;


/**
 * <b>ContextPinList</b>, superClass = {mas.InputPinList}
 */
public interface ContextPinList extends cmof.reflection.Object, hub.sam.mof.mas.InputPinList
{

    /**
     * <b>pin</b>, multiplicity=(0,*), isDerived, isUnique, isOrdered, redefinedProperty = {mas.InputPinList.pin}
     */
    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ContextPin> getPin();

    /**
     * <b>decisionNode</b>, multiplicity=(0,1), redefinedProperty = {mas.PinList.action}
     */
    public hub.sam.mof.mas.DecisionNode getDecisionNode();

    public void setDecisionNode(hub.sam.mof.mas.DecisionNode value);

    public void setAction(hub.sam.mof.mas.Action value);

    public void setAttachedTo(hub.sam.mof.mas.ActivityNode value);

}

