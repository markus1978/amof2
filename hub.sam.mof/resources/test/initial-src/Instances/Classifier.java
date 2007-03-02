package Instances;


/**
 * <b>Classifier</b>, superClass = {core.abstractions.classifiers.Classifier}
 */
public interface Classifier extends core.abstractions.classifiers.Classifier
{

    /**
     * <b>ownedInstance</b>, multiplicity=(0,*), isComposite, isUnique, subsettedProperty = {core.abstractions.ownerships.Element.ownedElement}
     */
    public cmof.common.ReflectiveCollection<? extends Instances.InstanceDef> getOwnedInstance();

}

