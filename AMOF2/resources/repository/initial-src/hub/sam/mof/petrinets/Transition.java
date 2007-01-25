package hub.sam.mof.petrinets;


/**
 * <b>Transition</b>
 */
public interface Transition extends cmof.reflection.Object
{

    /**
     * <b>outputPlaces</b>, multiplicity=(0,*), isUnique
     */
    public cmof.common.ReflectiveCollection<? extends hub.sam.mof.petrinets.Place> getOutputPlaces();

    /**
     * <b>inputPlaces</b>, multiplicity=(0,*), isUnique
     */
    public cmof.common.ReflectiveCollection<? extends hub.sam.mof.petrinets.Place> getInputPlaces();

    /**
     * <b>isEnabled</b>, multiplicity=(1,1)
     */
    public boolean isEnabled(hub.sam.mof.petrinets.NetInstance context) ;

    /**
     * <b>fire</b>, multiplicity=(1,1)
     */
    public void fire(hub.sam.mof.petrinets.NetInstance context) ;

}

