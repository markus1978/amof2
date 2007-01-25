package hub.sam.mof.mas;


/**
 * <b>InExpansionNode</b>, superClass = {mas.InputPin, mas.ExpansionNode}
 */
public interface InExpansionNode extends cmof.reflection.Object, hub.sam.mof.mas.InputPin, hub.sam.mof.mas.ExpansionNode
{

    /**
     * <b>regionAsInput</b>, multiplicity=(0,1)
     */
    public hub.sam.mof.mas.ExpansionRegion getRegionAsInput();

    public void setRegionAsInput(hub.sam.mof.mas.ExpansionRegion value);

    /**
     * <b>metaCreateInExpansionNodeInstance</b>, multiplicity=(1,1)
     */
    public hub.sam.mof.mas.InExpansionNodeInstance metaCreateInExpansionNodeInstance() ;

    /**
     * <b>metaCreatePinInstance</b>, multiplicity=(1,1)
     */
    public hub.sam.mof.mas.PinInstance metaCreatePinInstance() ;

    /**
     * <b>metaCreatePlaceInstance</b>, multiplicity=(1,1)
     */
    public hub.sam.mof.petrinets.PlaceInstance metaCreatePlaceInstance() ;

    /**
     * <b>metaGCreateInExpansionNodeInstance</b>, multiplicity=(1,1)
     */
    public hub.sam.mof.mas.InExpansionNodeInstance metaGCreateInExpansionNodeInstance(java.lang.String className) ;

    /**
     * <b>metaGCreatePinInstance</b>, multiplicity=(1,1)
     */
    public hub.sam.mof.mas.PinInstance metaGCreatePinInstance(java.lang.String className) ;

    /**
     * <b>metaGCreatePlaceInstance</b>, multiplicity=(1,1)
     */
    public hub.sam.mof.petrinets.PlaceInstance metaGCreatePlaceInstance(java.lang.String className) ;

    /**
     * <b>metaInstanceInExpansionNodeInstance</b>, multiplicity=(0,*), isUnique, redefinedProperty = {mas.InputPin.metaInstancePinInstance}
     */
    public cmof.common.ReflectiveCollection<? extends hub.sam.mof.mas.InExpansionNodeInstance> getMetaInstanceInExpansionNodeInstance();

}

