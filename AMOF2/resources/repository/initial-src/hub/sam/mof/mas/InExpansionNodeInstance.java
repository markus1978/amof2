package hub.sam.mof.mas;


/**
 * <b>InExpansionNodeInstance</b>, superClass = {petrinets.PlaceInstance, mas.PinInstance}
 */
public interface InExpansionNodeInstance extends cmof.reflection.Object, hub.sam.mof.petrinets.PlaceInstance, hub.sam.mof.mas.PinInstance
{

    /**
     * <b>metaDelete</b>, multiplicity=(1,1)
     */
    public void metaDelete() ;

    /**
     * <b>metaClassifierInExpansionNode</b>, multiplicity=(0,1), redefinedProperty = {mas.PinInstance.metaClassifierInputPin}
     */
    public hub.sam.mof.mas.InExpansionNode getMetaClassifierInExpansionNode();

    public void setMetaClassifierInExpansionNode(hub.sam.mof.mas.InExpansionNode value);

    public void setMetaClassifierInputPin(hub.sam.mof.mas.InputPin value);

    public void setMetaClassifierPlace(hub.sam.mof.petrinets.Place value);

}

