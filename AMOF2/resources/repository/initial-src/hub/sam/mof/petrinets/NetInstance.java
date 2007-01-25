package hub.sam.mof.petrinets;


/**
 * <b>NetInstance</b>
 */
public interface NetInstance extends cmof.reflection.Object
{

    /**
     * <b>places</b>, multiplicity=(1,1), isComposite
     */
    public hub.sam.mof.petrinets.PlaceInstance getPlaces(hub.sam.mof.petrinets.Place qualifier);

    public void setPlaces(hub.sam.mof.petrinets.Place qualifier, hub.sam.mof.petrinets.PlaceInstance value);

    /**
     * <b>enabledTransitions</b>, multiplicity=(0,*), isDerived, isUnique
     */
    public cmof.common.ReflectiveCollection<? extends hub.sam.mof.petrinets.Transition> getEnabledTransitions();

    /**
     * <b>run</b>, multiplicity=(1,1)
     */
    public void run() ;

    /**
     * <b>metaDelete</b>, multiplicity=(1,1)
     */
    public void metaDelete() ;

    /**
     * <b>metaClassifierNet</b>, multiplicity=(0,1)
     */
    public hub.sam.mof.petrinets.Net getMetaClassifierNet();

    public void setMetaClassifierNet(hub.sam.mof.petrinets.Net value);

}

