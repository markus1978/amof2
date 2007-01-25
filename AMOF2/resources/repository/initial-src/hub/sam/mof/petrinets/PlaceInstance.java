package hub.sam.mof.petrinets;


/**
 * <b>PlaceInstance</b>
 */
public interface PlaceInstance extends cmof.reflection.Object
{

    /**
     * <b>tokens</b>, multiplicity=(1,1)
     */
    public int getTokens();

    public void setTokens(int value);

    /**
     * <b>metaDelete</b>, multiplicity=(1,1)
     */
    public void metaDelete() ;

    /**
     * <b>metaClassifierPlace</b>, multiplicity=(0,1)
     */
    public hub.sam.mof.petrinets.Place getMetaClassifierPlace();

    public void setMetaClassifierPlace(hub.sam.mof.petrinets.Place value);

}

