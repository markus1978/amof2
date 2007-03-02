package cmof;


/**
 * <b>Constraint</b>, superClass = {core.abstractions.constraints.Constraint, cmof.PackageableElement}
 */
public interface Constraint extends core.abstractions.constraints.Constraint, cmof.PackageableElement
{

    /**
     * <b>context</b>, multiplicity=(0,1), isDerivedUnion, isDerived, redefinedProperty = {core.abstractions.constraints.Constraint.context}
     */
    public cmof.Namespace getContext();

    public void setContext(cmof.Namespace value);

    public void setContext(core.abstractions.namespaces.Namespace value);

    /**
     * <b>namespace</b>, multiplicity=(0,1), subsettedProperty = {cmof.Constraint.context}, redefinedProperty = {core.abstractions.constraints.Constraint.namespace, cmof.NamedElement.namespace}
     */
    public cmof.Namespace getNamespace();

    public void setNamespace(cmof.Namespace value);

    public void setNamespace(core.abstractions.constraints.Namespace value);

    public void setNamespace(core.abstractions.namespaces.Namespace value);

    /**
     * <b>constrainedElement</b>, multiplicity=(0,*), isUnique, isOrdered, redefinedProperty = {core.abstractions.constraints.Constraint.constrainedElement}
     */
    public cmof.common.ReflectiveSequence<? extends cmof.Element> getConstrainedElement();

    /**
     * <b>specification</b>, multiplicity=(1,1), isComposite, subsettedProperty = {cmof.Element.ownedElement}, redefinedProperty = {core.abstractions.constraints.Constraint.specification}
     */
    public cmof.ValueSpecification getSpecification();

    public void setSpecification(cmof.ValueSpecification value);

    public void setSpecification(core.abstractions.expressions.ValueSpecification value);

}

