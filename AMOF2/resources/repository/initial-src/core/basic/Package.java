package core.basic;


/**
 * <b>Package</b>, superClass = {core.basic.NamedElement}
 */
public interface Package extends core.basic.NamedElement
{

    /**
     * <b>nestedPackage</b>, multiplicity=(0,*), isComposite, isUnique
     */
    public cmof.common.ReflectiveCollection<? extends core.basic.Package> getNestedPackage();

    /**
     * <b>nestingPackage</b>, multiplicity=(0,1)
     */
    public core.basic.Package getNestingPackage();

    public void setNestingPackage(core.basic.Package value);

    /**
     * <b>ownedType</b>, multiplicity=(0,*), isComposite, isUnique
     */
    public cmof.common.ReflectiveCollection<? extends core.basic.Type> getOwnedType();

}

