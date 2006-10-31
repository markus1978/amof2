package core.abstractions.instances;


/**
 * <b>Slot</b>, superClass = {core.abstractions.ownerships.Element}
 */
public interface Slot extends core.abstractions.ownerships.Element
{

    /**
     * <b>owningInstance</b>, multiplicity=(1,1), subsettedProperty = {core.abstractions.ownerships.Element.owner}
     */
    public core.abstractions.instances.InstanceSpecification getOwningInstance();

    public void setOwningInstance(core.abstractions.instances.InstanceSpecification value);

    /**
     * <b>value</b>, multiplicity=(0,*), isComposite, isUnique, isOrdered, subsettedProperty = {core.abstractions.ownerships.Element.ownedElement}
     */
    public cmof.common.ReflectiveSequence<? extends core.abstractions.expressions.ValueSpecification> getValue();

    /**
     * <b>definingFeature</b>, multiplicity=(1,1)
     */
    public core.abstractions.structuralfeatures.StructuralFeature getDefiningFeature();

    public void setDefiningFeature(core.abstractions.structuralfeatures.StructuralFeature value);

}

