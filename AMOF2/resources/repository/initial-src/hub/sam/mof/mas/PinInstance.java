package hub.sam.mof.mas;


/**
 * <b>PinInstance</b>, superClass = {petrinets.PlaceInstance}
 */
public interface PinInstance extends cmof.reflection.Object, hub.sam.mof.petrinets.PlaceInstance
{

    /**
     * <b>value</b>, multiplicity=(1,1)
     */
    public java.lang.Object getValue();

    public void setValue(java.lang.Object value);

    /**
     * <b>metaDelete</b>, multiplicity=(1,1)
     */
    public void metaDelete() ;

    /**
     * <b>metaClassifierInputPin</b>, multiplicity=(0,1), redefinedProperty = {petrinets.PlaceInstance.metaClassifierPlace}
     */
    public hub.sam.mof.mas.InputPin getMetaClassifierInputPin();

    public void setMetaClassifierInputPin(hub.sam.mof.mas.InputPin value);

    public void setMetaClassifierPlace(hub.sam.mof.petrinets.Place value);

}

