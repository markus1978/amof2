package hub.sam.mof.mas;


/**
 * <b>ExpansionRegion</b>, superClass = {mas.StructuredActivityNode, mas.Margin}
 */
public interface ExpansionRegion extends cmof.reflection.Object, hub.sam.mof.mas.StructuredActivityNode, hub.sam.mof.mas.Margin
{

    /**
     * <b>inList</b>, multiplicity=(0,1), subsettedProperty = {mas.ExpansionRegion.list}
     */
    public hub.sam.mof.mas.InExpansionNodeList getInList();

    public void setInList(hub.sam.mof.mas.InExpansionNodeList value);

    /**
     * <b>outList</b>, multiplicity=(0,1), subsettedProperty = {mas.ExpansionRegion.list}
     */
    public hub.sam.mof.mas.OutExpansionNodeList getOutList();

    public void setOutList(hub.sam.mof.mas.OutExpansionNodeList value);

    /**
     * <b>list</b>, multiplicity=(0,*), isDerivedUnion, isDerived, isComposite, isUnique, isOrdered, redefinedProperty = {mas.Action.pinList}
     */
    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ExpansionNodeList> getList();

    /**
     * <b>body</b>, multiplicity=(0,1), isComposite
     */
    public hub.sam.mof.mas.ExpansionRegionBody getBody();

    public void setBody(hub.sam.mof.mas.ExpansionRegionBody value);

    /**
     * <b>mode</b>, multiplicity=(1,1)
     */
    public hub.sam.mof.mas.ExpansionKind getMode();

    public void setMode(hub.sam.mof.mas.ExpansionKind value);

    /**
     * <b>inputElement</b>, multiplicity=(1,*), isComposite, isUnique, isOrdered, redefinedProperty = {mas.Action.input}
     */
    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.InExpansionNode> getInputElement();

    /**
     * <b>outputElement</b>, multiplicity=(0,*), isComposite, isUnique, isOrdered
     */
    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.OutExpansionNode> getOutputElement();

    /**
     * <b>metaCreateExpansionRegionInstance</b>, multiplicity=(1,1)
     */
    public hub.sam.mof.mas.ExpansionRegionInstance metaCreateExpansionRegionInstance() ;

    /**
     * <b>metaCreateActivityInstance</b>, multiplicity=(1,1)
     */
    public hub.sam.mof.mas.ActivityInstance metaCreateActivityInstance() ;

    /**
     * <b>metaCreateNetInstance</b>, multiplicity=(1,1)
     */
    public hub.sam.mof.petrinets.NetInstance metaCreateNetInstance() ;

    /**
     * <b>metaGCreateExpansionRegionInstance</b>, multiplicity=(1,1)
     */
    public hub.sam.mof.mas.ExpansionRegionInstance metaGCreateExpansionRegionInstance(java.lang.String className) ;

    /**
     * <b>metaGCreateActivityInstance</b>, multiplicity=(1,1)
     */
    public hub.sam.mof.mas.ActivityInstance metaGCreateActivityInstance(java.lang.String className) ;

    /**
     * <b>metaGCreateNetInstance</b>, multiplicity=(1,1)
     */
    public hub.sam.mof.petrinets.NetInstance metaGCreateNetInstance(java.lang.String className) ;

    /**
     * <b>metaInstanceExpansionRegionInstance</b>, multiplicity=(0,*), isUnique, redefinedProperty = {mas.Activity.metaInstanceActivityInstance}
     */
    public cmof.common.ReflectiveCollection<? extends hub.sam.mof.mas.ExpansionRegionInstance> getMetaInstanceExpansionRegionInstance();

}

