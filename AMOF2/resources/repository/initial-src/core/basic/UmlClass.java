package core.basic;


/**
 * <b>Class</b>, superClass = {core.basic.Type}
 */
public interface UmlClass extends core.basic.Type
{

    /**
     * <b>isAbstract</b>, multiplicity=(1,1)
     */
    public boolean isAbstract();

    public void setIsAbstract(boolean value);

    /**
     * <b>ownedAttribute</b>, multiplicity=(0,*), isComposite, isUnique, isOrdered
     */
    public cmof.common.ReflectiveSequence<? extends core.basic.Property> getOwnedAttribute();

    /**
     * <b>ownedOperation</b>, multiplicity=(0,*), isComposite, isUnique, isOrdered
     */
    public cmof.common.ReflectiveSequence<? extends core.basic.Operation> getOwnedOperation();

    /**
     * <b>superClass</b>, multiplicity=(0,*), isUnique
     */
    public cmof.common.ReflectiveCollection<? extends core.basic.UmlClass> getSuperClass();

}

