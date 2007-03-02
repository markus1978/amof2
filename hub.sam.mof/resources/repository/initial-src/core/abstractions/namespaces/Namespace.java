package core.abstractions.namespaces;


/**
 * <b>Namespace</b>, isAbstract, superClass = {core.abstractions.namespaces.NamedElement}
 * <br>constraint - members_are_distinguishable : 
 * <pre>membersAreDistinguishable()</pre>
 */
public interface Namespace extends core.abstractions.namespaces.NamedElement
{

    /**
     * <b>member</b>, multiplicity=(0,*), isDerivedUnion, isDerived, isUnique
     */
    public cmof.common.ReflectiveCollection<? extends core.abstractions.namespaces.NamedElement> getMember();

    /**
     * <b>ownedMember</b>, multiplicity=(0,*), isDerivedUnion, isDerived, isComposite, isUnique, subsettedProperty = {core.abstractions.ownerships.Element.ownedElement, core.abstractions.namespaces.Namespace.member}
     */
    public cmof.common.ReflectiveCollection<? extends core.abstractions.namespaces.NamedElement> getOwnedMember();

    /**
     * <b>getNamesOfMember</b>, multiplicity=(0,*)
     */
    public cmof.common.ReflectiveCollection<java.lang.String> getNamesOfMember(core.abstractions.namespaces.NamedElement element) ;

    /**
     * <b>membersAreDistinguishable</b>, multiplicity=(1,1)
     */
    public boolean membersAreDistinguishable() ;

}

