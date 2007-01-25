package hub.sam.mof.petrinets;


/**
 * <b>Net</b>
 */
public interface Net extends cmof.reflection.Object
{

    /**
     * <b>places</b>, multiplicity=(0,*), isComposite, isUnique
     */
    public cmof.common.ReflectiveCollection<? extends hub.sam.mof.petrinets.Place> getPlaces();

    /**
     * <b>transitions</b>, multiplicity=(0,*), isComposite, isUnique
     */
    public cmof.common.ReflectiveCollection<? extends hub.sam.mof.petrinets.Transition> getTransitions();

    /**
     * <b>instantiate</b>, multiplicity=(1,1)
     */
    public hub.sam.mof.petrinets.NetInstance instantiate() ;

    /**
     * <b>metaCreateNetInstance</b>, multiplicity=(1,1)
     */
    public hub.sam.mof.petrinets.NetInstance metaCreateNetInstance() ;

    /**
     * <b>metaGCreateNetInstance</b>, multiplicity=(1,1)
     */
    public hub.sam.mof.petrinets.NetInstance metaGCreateNetInstance(java.lang.String className) ;

    /**
     * <b>metaInstanceNetInstance</b>, multiplicity=(0,*), isUnique
     */
    public cmof.common.ReflectiveCollection<? extends hub.sam.mof.petrinets.NetInstance> getMetaInstanceNetInstance();

}

