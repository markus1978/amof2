package hub.sam.mof.mas;


/**
 * <b>InputPin</b>, superClass = {mas.ASPlace, mas.Pin}
 */
public interface InputPin extends cmof.reflection.Object, hub.sam.mof.mas.ASPlace, hub.sam.mof.mas.Pin
{

    /**
     * <b>metaCreatePinInstance</b>, multiplicity=(1,1)
     */
    public hub.sam.mof.mas.PinInstance metaCreatePinInstance() ;

    /**
     * <b>metaCreatePlaceInstance</b>, multiplicity=(1,1)
     */
    public hub.sam.mof.petrinets.PlaceInstance metaCreatePlaceInstance() ;

    /**
     * <b>metaGCreatePinInstance</b>, multiplicity=(1,1)
     */
    public hub.sam.mof.mas.PinInstance metaGCreatePinInstance(java.lang.String className) ;

    /**
     * <b>metaGCreatePlaceInstance</b>, multiplicity=(1,1)
     */
    public hub.sam.mof.petrinets.PlaceInstance metaGCreatePlaceInstance(java.lang.String className) ;

    /**
     * <b>metaInstancePinInstance</b>, multiplicity=(0,*), isUnique, redefinedProperty = {petrinets.Place.metaInstancePlaceInstance}
     */
    public cmof.common.ReflectiveCollection<? extends hub.sam.mof.mas.PinInstance> getMetaInstancePinInstance();

}

