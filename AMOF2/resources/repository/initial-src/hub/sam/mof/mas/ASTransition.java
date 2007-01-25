package hub.sam.mof.mas;


/**
 * <b>ASTransition</b>, isAbstract, superClass = {petrinets.Transition}
 */
public interface ASTransition extends cmof.reflection.Object, hub.sam.mof.petrinets.Transition
{

    /**
     * <b>outputPlaces</b>, multiplicity=(0,*), isDerived, isUnique, redefinedProperty = {petrinets.Transition.outputPlaces}
     */
    public cmof.common.ReflectiveCollection<? extends hub.sam.mof.mas.ASPlace> getOutputPlaces();

    /**
     * <b>inputPlaces</b>, multiplicity=(0,*), isDerived, isUnique, redefinedProperty = {petrinets.Transition.inputPlaces}
     */
    public cmof.common.ReflectiveCollection<? extends hub.sam.mof.mas.ASPlace> getInputPlaces();

}

