package core.abstractions.umlsuper;


/**
 * <b>Classifier</b>, isAbstract, superClass = {core.abstractions.classifiers.Classifier}
 * <br>constraint - no_cycles_in_generalization : 
 * <pre>not self.allParents()->includes(self)</pre>
 * <br>constraint - specialize_type : 
 * <pre>self.parents()->forAll(c | self.maySpecializeType(c))</pre>
 * <br>constraint - inherited_member : 
 * <pre>self.inheritedMember->includesAll(self.inherit(self.parents()->collect(p | p.inheritableMembers(self)))</pre>
 */
public interface Classifier extends core.abstractions.classifiers.Classifier
{

    /**
     * <b>isAbstract</b>, multiplicity=(1,1)
     */
    public boolean isAbstract();

    public void setIsAbstract(boolean value);

    /**
     * <b>inheritedMember</b>, multiplicity=(0,*), isDerived, isUnique, subsettedProperty = {core.abstractions.namespaces.Namespace.member}
     */
    public cmof.common.ReflectiveCollection<? extends core.abstractions.namespaces.NamedElement> getInheritedMember();

    /**
     * <b>general</b>, multiplicity=(0,*), isUnique
     */
    public cmof.common.ReflectiveCollection<? extends core.abstractions.umlsuper.Classifier> getGeneral();

    /**
     * <b>inheritedMember</b>, multiplicity=(0,*)
     */
    public cmof.common.ReflectiveCollection<? extends core.abstractions.namespaces.NamedElement> inheritedMemberOperation() ;

    /**
     * <b>parents</b>, multiplicity=(0,*)
     */
    public cmof.common.ReflectiveCollection<? extends core.abstractions.umlsuper.Classifier> parents() ;

    /**
     * <b>allParents</b>, multiplicity=(0,*)
     */
    public cmof.common.ReflectiveCollection<? extends core.abstractions.umlsuper.Classifier> allParents() ;

    /**
     * <b>inheritableMembers</b>, multiplicity=(0,*)
     */
    public cmof.common.ReflectiveCollection<? extends core.abstractions.namespaces.NamedElement> inheritableMembers(core.abstractions.umlsuper.Classifier c) ;

    /**
     * <b>hasVisibilityOf</b>, multiplicity=(1,1)
     */
    public boolean hasVisibilityOf(core.abstractions.namespaces.NamedElement n) ;

    /**
     * <b>maySpecializeType</b>, multiplicity=(1,1)
     */
    public boolean maySpecializeType(core.abstractions.umlsuper.Classifier c) ;

}

