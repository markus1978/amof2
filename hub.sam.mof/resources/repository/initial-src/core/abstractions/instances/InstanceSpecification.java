package core.abstractions.instances;


/**
 * <b>InstanceSpecification</b>, superClass = {core.abstractions.namespaces.NamedElement}
 * <br>constraint - slots_are_defined : 
 * <pre>slot->forAll(s | classifier->exists(c | c.allFeatures()->includes(s.definingFeature))</pre>
 * <br>constraint - no_duplicate_slots : 
 * <pre>classifier->forAll(c | (c.allFeatures()->forAll(f | slot->select(s | s.definingFeature = f)->size() <= 1) )</pre>
 */
public interface InstanceSpecification extends core.abstractions.namespaces.NamedElement
{

    /**
     * <b>slot</b>, multiplicity=(0,*), isComposite, isUnique, subsettedProperty = {core.abstractions.ownerships.Element.ownedElement}
     */
    public cmof.common.ReflectiveCollection<? extends core.abstractions.instances.Slot> getSlot();

    /**
     * <b>classifier</b>, multiplicity=(1,*), isUnique
     */
    public cmof.common.ReflectiveCollection<? extends core.abstractions.classifiers.Classifier> getClassifier();

    /**
     * <b>specification</b>, multiplicity=(0,1), isComposite, subsettedProperty = {core.abstractions.ownerships.Element.ownedElement}
     */
    public core.abstractions.expressions.ValueSpecification getSpecification();

    public void setSpecification(core.abstractions.expressions.ValueSpecification value);

}

