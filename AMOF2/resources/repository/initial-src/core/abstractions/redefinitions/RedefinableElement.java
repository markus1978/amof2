package core.abstractions.redefinitions;


/**
 * <b>RedefinableElement</b>, isAbstract, superClass = {core.abstractions.namespaces.NamedElement}
 * <br>constraint - redefinition_context_valid : 
 * <pre>self.redefinedElement->forAll(e | self.isRedefinitionContextValid(e))</pre>
 * <br>constraint - redefinition_consistent : 
 * <pre>self.redefinedElement->forAll(re | re.isConsistentWith(self))</pre>
 */
public interface RedefinableElement extends core.abstractions.namespaces.NamedElement
{

    /**
     * <b>redefinedElement</b>, multiplicity=(0,*), isDerivedUnion, isDerived, isUnique
     */
    public cmof.common.ReflectiveCollection<? extends core.abstractions.redefinitions.RedefinableElement> getRedefinedElement();

    /**
     * <b>redefinitionContext</b>, multiplicity=(0,*), isDerivedUnion, isDerived, isUnique
     */
    public cmof.common.ReflectiveCollection<? extends core.abstractions.umlsuper.Classifier> getRedefinitionContext();

    /**
     * <b>isConsistentWith</b>, multiplicity=(1,1)
     */
    public boolean isConsistentWith(core.abstractions.redefinitions.RedefinableElement redefinee) ;

    /**
     * <b>isRedefinitionContextValid</b>, multiplicity=(1,1)
     */
    public boolean isRedefinitionContextValid(core.abstractions.redefinitions.RedefinableElement redefinable) ;

}

