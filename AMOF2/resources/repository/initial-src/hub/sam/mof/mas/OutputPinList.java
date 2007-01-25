package hub.sam.mof.mas;


/**
 * <b>OutputPinList</b>, superClass = {mas.PinList}
 */
public interface OutputPinList extends cmof.reflection.Object, hub.sam.mof.mas.PinList
{

    /**
     * <b>pin</b>, multiplicity=(0,*), isDerived, isUnique, isOrdered, redefinedProperty = {mas.AttachedNodeList.node}
     */
    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.OutputPin> getPin();

}

