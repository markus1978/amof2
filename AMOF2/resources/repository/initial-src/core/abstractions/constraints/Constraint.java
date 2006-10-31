package core.abstractions.constraints;


/**
 * <b>Constraint</b>, superClass = {core.abstractions.namespaces.NamedElement}
 * <br>constraint - not_apply_to_self : 
 * <pre>not constrainedElement->includes(self)</pre>
 */
public interface Constraint extends core.abstractions.namespaces.NamedElement
{

    /**
     * <b>context</b>, multiplicity=(0,1), isDerivedUnion, isDerived
     */
    public core.abstractions.namespaces.Namespace getContext();

    public void setContext(core.abstractions.namespaces.Namespace value);

    /**
     * <b>namespace</b>, multiplicity=(0,1), subsettedProperty = {core.abstractions.constraints.Constraint.context}, redefinedProperty = {core.abstractions.namespaces.NamedElement.namespace}
     */
    public core.abstractions.constraints.Namespace getNamespace();

    public void setNamespace(core.abstractions.constraints.Namespace value);

    public void setNamespace(core.abstractions.namespaces.Namespace value);

    /**
     * <b>specification</b>, multiplicity=(1,1), isComposite, subsettedProperty = {core.abstractions.ownerships.Element.ownedElement}
     */
    public core.abstractions.expressions.ValueSpecification getSpecification();

    public void setSpecification(core.abstractions.expressions.ValueSpecification value);

    /**
     * <b>constrainedElement</b>, multiplicity=(0,*), isUnique, isOrdered
     */
    public cmof.common.ReflectiveSequence<? extends core.abstractions.ownerships.Element> getConstrainedElement();

}

