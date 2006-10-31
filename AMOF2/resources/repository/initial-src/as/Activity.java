package as;


/**
 * <b>Activity</b>, superClass = {cmof.NamedElement}
 */
public interface Activity extends cmof.NamedElement
{

    /**
     * <b>argument</b>, multiplicity=(0,*), isUnique, isOrdered
     */
    public cmof.common.ReflectiveSequence<? extends as.ValueNode> getArgument();

    /**
     * <b>node</b>, multiplicity=(0,*), isComposite, isUnique, subsettedProperty = {cmof.Element.ownedElement}
     */
    public cmof.common.ReflectiveCollection<? extends as.ActivityNode> getNode();

    /**
     * <b>edge</b>, multiplicity=(0,*), isComposite, isUnique, subsettedProperty = {cmof.Element.ownedElement}
     */
    public cmof.common.ReflectiveCollection<? extends as.ActivityEdge> getEdge();

}

