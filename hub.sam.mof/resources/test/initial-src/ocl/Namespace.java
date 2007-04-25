package ocl;


/**
 * <b>Namespace</b>, superClass = {ocl.NamedElement}
 */
public interface Namespace extends cmof.reflection.Object, ocl.NamedElement
{

    /**
     * <b>member</b>, multiplicity=(0,*), isComposite, isUnique
     */
    public cmof.common.ReflectiveCollection<? extends ocl.NamedElement> getMember();

}

