package hub.sam.mof.petrinets;


/**
 * <b>Place</b>
 */
public interface Place extends cmof.reflection.Object
{

    /**
     * <b>initialTokesn</b>, multiplicity=(1,1)
     */
    public int getInitialTokesn();

    public void setInitialTokesn(int value);

    /**
     * <b>metaCreatePlaceInstance</b>, multiplicity=(1,1)
     */
    public hub.sam.mof.petrinets.PlaceInstance metaCreatePlaceInstance() ;

    /**
     * <b>metaGCreatePlaceInstance</b>, multiplicity=(1,1)
     */
    public hub.sam.mof.petrinets.PlaceInstance metaGCreatePlaceInstance(java.lang.String className) ;

    /**
     * <b>metaInstancePlaceInstance</b>, multiplicity=(0,*), isUnique
     */
    public cmof.common.ReflectiveCollection<? extends hub.sam.mof.petrinets.PlaceInstance> getMetaInstancePlaceInstance();

}

