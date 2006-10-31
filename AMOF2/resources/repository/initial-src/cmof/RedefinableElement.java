package cmof;


/**
 * <b>RedefinableElement</b>, isAbstract, superClass = {core.abstractions.redefinitions.RedefinableElement, cmof.NamedElement}
 */
public interface RedefinableElement extends core.abstractions.redefinitions.RedefinableElement, cmof.NamedElement
{

    /**
     * <b>redefinitionContext</b>, multiplicity=(0,*), isDerivedUnion, isDerived, isUnique, redefinedProperty = {core.abstractions.redefinitions.RedefinableElement.redefinitionContext}
     */
    public cmof.common.ReflectiveCollection<? extends cmof.Classifier> getRedefinitionContext();

    /**
     * <b>redefinedElement</b>, multiplicity=(0,*), isDerivedUnion, isDerived, isUnique, redefinedProperty = {core.abstractions.redefinitions.RedefinableElement.redefinedElement}
     */
    public cmof.common.ReflectiveCollection<? extends cmof.RedefinableElement> getRedefinedElement();

}

