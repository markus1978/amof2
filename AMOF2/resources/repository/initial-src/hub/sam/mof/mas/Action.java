package hub.sam.mof.mas;


/**
 * <b>Action</b>, isAbstract, superClass = {mas.ASPlace, mas.ASTransition, mas.ActivityNode}
 */
public interface Action extends cmof.reflection.Object, hub.sam.mof.mas.ASPlace, hub.sam.mof.mas.ASTransition, hub.sam.mof.mas.ActivityNode
{

    /**
     * <b>pinList</b>, multiplicity=(0,*), isDerivedUnion, isDerived, isComposite, isUnique, isOrdered, redefinedProperty = {mas.ActivityNode.list}
     */
    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.PinList> getPinList();

    /**
     * <b>outputList</b>, multiplicity=(0,1), subsettedProperty = {mas.Action.pinList}
     */
    public hub.sam.mof.mas.OutputPinList getOutputList();

    public void setOutputList(hub.sam.mof.mas.OutputPinList value);

    /**
     * <b>inputList</b>, multiplicity=(0,1), subsettedProperty = {mas.Action.pinList}
     */
    public hub.sam.mof.mas.InputPinList getInputList();

    public void setInputList(hub.sam.mof.mas.InputPinList value);

    /**
     * <b>output</b>, multiplicity=(0,*), isComposite, isUnique, isOrdered, subsettedProperty = {cmof.Element.ownedElement}
     */
    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.OutputPin> getOutput();

    /**
     * <b>input</b>, multiplicity=(0,*), isComposite, isUnique, isOrdered, subsettedProperty = {cmof.Element.ownedElement}
     */
    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.InputPin> getInput();

    /**
     * <b>context</b>, multiplicity=(0,1), isDerived
     */
    public cmof.Classifier getContext();

    public void setContext(cmof.Classifier value);

}

