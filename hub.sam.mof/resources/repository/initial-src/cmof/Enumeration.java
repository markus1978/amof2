package cmof;


/**
 * <b>Enumeration</b>, superClass = {cmof.DataType, core.basic.Enumeration}
 */
public interface Enumeration extends cmof.DataType, core.basic.Enumeration
{

    /**
     * <b>ownedLiteral</b>, multiplicity=(0,*), isComposite, isUnique, isOrdered, subsettedProperty = {cmof.Namespace.ownedMember}, redefinedProperty = {core.basic.Enumeration.ownedLiteral}
     */
    public cmof.common.ReflectiveSequence<? extends cmof.EnumerationLiteral> getOwnedLiteral();

}

