package as;


/**
 * <b>Action</b>, isAbstract, superClass = {as.ActivityNode}
 */
public interface Action extends as.ActivityNode
{

    /**
     * <b>output</b>, multiplicity=(0,*), isComposite, isUnique, isOrdered, subsettedProperty = {cmof.Element.ownedElement}
     */
    public cmof.common.ReflectiveSequence<? extends as.OutputPin> getOutput();

    /**
     * <b>input</b>, multiplicity=(0,*), isComposite, isUnique, isOrdered, subsettedProperty = {cmof.Element.ownedElement}
     */
    public cmof.common.ReflectiveSequence<? extends as.InputPin> getInput();

    /**
     * <b>context</b>, multiplicity=(0,1), isDerived
     */
    public cmof.Classifier getContext();

    public void setContext(cmof.Classifier value);

}

