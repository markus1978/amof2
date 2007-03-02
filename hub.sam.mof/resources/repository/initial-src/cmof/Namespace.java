package cmof;


/**
 * <b>Namespace</b>, isAbstract, superClass = {core.abstractions.constraints.Namespace, cmof.NamedElement}
 * <br>constraint - importedMember_derived : 
 * <pre>self.importedMember->includesAll(self.importedMembers(self.elementImport.importedElement.asSet()->union(self.packageImport.importedPackage->collect(p | p.visibleMembers()))))</pre>
 */
public interface Namespace extends core.abstractions.constraints.Namespace, cmof.NamedElement
{

    /**
     * <b>importedMember</b>, multiplicity=(0,*), isDerived, isUnique, subsettedProperty = {cmof.Namespace.member}
     */
    public cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> getImportedMember();

    /**
     * <b>elementImport</b>, multiplicity=(0,*), isComposite, isUnique, subsettedProperty = {cmof.Element.ownedElement}
     */
    public cmof.common.ReflectiveCollection<? extends cmof.ElementImport> getElementImport();

    /**
     * <b>ownedMember</b>, multiplicity=(0,*), isDerivedUnion, isDerived, isComposite, isUnique, subsettedProperty = {cmof.Namespace.member, cmof.Element.ownedElement}, redefinedProperty = {core.abstractions.namespaces.Namespace.ownedMember}
     */
    public cmof.common.ReflectiveCollection<? extends cmof.NamedElement> getOwnedMember();

    /**
     * <b>packageImport</b>, multiplicity=(0,*), isComposite, isUnique, subsettedProperty = {cmof.Element.ownedElement}
     */
    public cmof.common.ReflectiveCollection<? extends cmof.PackageImport> getPackageImport();

    /**
     * <b>ownedRule</b>, multiplicity=(0,*), isComposite, isUnique, subsettedProperty = {cmof.Namespace.ownedMember}, redefinedProperty = {core.abstractions.constraints.Namespace.ownedRule}
     */
    public cmof.common.ReflectiveCollection<? extends cmof.Constraint> getOwnedRule();

    /**
     * <b>member</b>, multiplicity=(0,*), isDerivedUnion, isDerived, isUnique, redefinedProperty = {core.abstractions.namespaces.Namespace.member}
     */
    public cmof.common.ReflectiveCollection<? extends cmof.NamedElement> getMember();

    /**
     * <b>importedMember</b>, multiplicity=(0,*)
     */
    public cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> importedMemberOperation() ;

    /**
     * <b>getNamesOfMember</b>, multiplicity=(0,*)
     */
    public cmof.common.ReflectiveCollection<java.lang.String> getNamesOfMember() ;

    /**
     * <b>getNamesOfMember</b>, multiplicity=(0,*)
     */
    public cmof.common.ReflectiveCollection<java.lang.String> getNamesOfMember(core.abstractions.namespaces.NamedElement element) ;

    /**
     * <b>importMembers</b>, multiplicity=(0,*)
     */
    public cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> importMembers(cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> imps) ;

    /**
     * <b>excludeCollisions</b>, multiplicity=(0,*)
     */
    public cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> excludeCollisions(cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> imps) ;

}

