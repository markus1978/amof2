package cmof;


/**
 * <b>Package</b>, superClass = {cmof.PackageableElement, cmof.Namespace, core.basic.Package}
 * <br>constraint - elements_public_or_private : 
 * <pre>self.ownedElements->forAll(e | e.visibility->notEmpty() implies e.visbility = #public or e.visibility = #private)</pre>
 */
public interface Package extends cmof.PackageableElement, cmof.Namespace, core.basic.Package
{

    /**
     * <b>packagedElement</b>, multiplicity=(0,*), isComposite, isUnique, subsettedProperty = {cmof.Namespace.ownedMember}
     */
    public cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> getPackagedElement();

    /**
     * <b>ownedType</b>, multiplicity=(0,*), isComposite, isUnique, subsettedProperty = {cmof.Package.packagedElement}, redefinedProperty = {core.basic.Package.ownedType}
     */
    public cmof.common.ReflectiveCollection<? extends cmof.Type> getOwnedType();

    /**
     * <b>nestedPackage</b>, multiplicity=(0,*), isComposite, isUnique, subsettedProperty = {cmof.Package.packagedElement}, redefinedProperty = {core.basic.Package.nestedPackage}
     */
    public cmof.common.ReflectiveCollection<? extends cmof.Package> getNestedPackage();

    /**
     * <b>nestingPackage</b>, multiplicity=(0,1), subsettedProperty = {cmof.NamedElement.namespace}, redefinedProperty = {core.basic.Package.nestingPackage}
     */
    public cmof.Package getNestingPackage();

    public void setNestingPackage(cmof.Package value);

    public void setNestingPackage(core.basic.Package value);

    /**
     * <b>packageMerge</b>, multiplicity=(0,*), isComposite, isUnique, subsettedProperty = {cmof.Element.ownedElement}
     */
    public cmof.common.ReflectiveCollection<? extends cmof.PackageMerge> getPackageMerge();

    /**
     * <b>mustBeOwned</b>, multiplicity=(1,1)
     */
    public boolean mustBeOwned() ;

    /**
     * <b>visibleMembers</b>, multiplicity=(0,*)
     */
    public cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> visibleMembers() ;

    /**
     * <b>makesVisible</b>, multiplicity=(1,1)
     */
    public boolean makesVisible(cmof.NamedElement el) ;

}

