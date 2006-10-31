package core.basic;


/**
 * <b>Enumeration</b>, superClass = {core.basic.DataType}
 */
public interface Enumeration extends core.basic.DataType
{

    /**
     * <b>ownedLiteral</b>, multiplicity=(0,*), isComposite, isUnique, isOrdered
     */
    public cmof.common.ReflectiveSequence<? extends core.basic.EnumerationLiteral> getOwnedLiteral();

}

