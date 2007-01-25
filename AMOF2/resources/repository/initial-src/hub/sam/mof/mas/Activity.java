package hub.sam.mof.mas;


/**
 * <b>Activity</b>, superClass = {petrinets.Net, cmof.NamedElement}
 */
public interface Activity extends cmof.reflection.Object, hub.sam.mof.petrinets.Net, cmof.NamedElement
{

    /**
     * <b>transitions</b>, multiplicity=(0,*), isDerived, isComposite, isUnique, redefinedProperty = {petrinets.Net.transitions}
     */
    public cmof.common.ReflectiveCollection<? extends hub.sam.mof.mas.ASTransition> getTransitions();

    /**
     * <b>places</b>, multiplicity=(0,*), isComposite, isUnique, redefinedProperty = {petrinets.Net.places}
     */
    public cmof.common.ReflectiveCollection<? extends hub.sam.mof.mas.ASPlace> getPlaces();

    /**
     * <b>argument</b>, multiplicity=(0,*), isUnique, isOrdered
     */
    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ValueNode> getArgument();

    /**
     * <b>edge</b>, multiplicity=(0,*), isComposite, isUnique, isOrdered, subsettedProperty = {cmof.Element.ownedElement}
     */
    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ActivityEdge> getEdge();

    /**
     * <b>node</b>, multiplicity=(0,*), isComposite, isUnique, isOrdered, subsettedProperty = {cmof.Element.ownedElement, mas.Activity.gefChildren}
     */
    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ActivityNode> getNode();

    /**
     * <b>group</b>, multiplicity=(0,*), isComposite, isUnique, isOrdered, subsettedProperty = {mas.Activity.node}
     */
    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ActivityGroup> getGroup();

    /**
     * <b>gefChildren</b>, multiplicity=(0,*), isDerivedUnion, isDerived, isUnique, isOrdered
     */
    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ActivityChild> getGefChildren();

    /**
     * <b>instantiate</b>, multiplicity=(1,1)
     */
    public hub.sam.mof.mas.ActivityInstance instantiate() ;

    /**
     * <b>metaCreateActivityInstance</b>, multiplicity=(1,1)
     */
    public hub.sam.mof.mas.ActivityInstance metaCreateActivityInstance() ;

    /**
     * <b>metaCreateNetInstance</b>, multiplicity=(1,1)
     */
    public hub.sam.mof.petrinets.NetInstance metaCreateNetInstance() ;

    /**
     * <b>metaGCreateActivityInstance</b>, multiplicity=(1,1)
     */
    public hub.sam.mof.mas.ActivityInstance metaGCreateActivityInstance(java.lang.String className) ;

    /**
     * <b>metaGCreateNetInstance</b>, multiplicity=(1,1)
     */
    public hub.sam.mof.petrinets.NetInstance metaGCreateNetInstance(java.lang.String className) ;

    /**
     * <b>metaInstanceActivityInstance</b>, multiplicity=(0,*), isUnique, redefinedProperty = {petrinets.Net.metaInstanceNetInstance}
     */
    public cmof.common.ReflectiveCollection<? extends hub.sam.mof.mas.ActivityInstance> getMetaInstanceActivityInstance();

}

