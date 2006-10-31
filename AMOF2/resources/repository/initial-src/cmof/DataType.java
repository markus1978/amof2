package cmof;


/**
 * <b>DataType</b>, superClass = {core.basic.DataType, cmof.Classifier}
 */
public interface DataType extends core.basic.DataType, cmof.Classifier
{

    /**
     * <b>ownedAttribute</b>, multiplicity=(0,*), isComposite, isUnique, isOrdered, subsettedProperty = {cmof.Classifier.attribute, cmof.Namespace.ownedMember}
     */
    public cmof.common.ReflectiveSequence<? extends cmof.Property> getOwnedAttribute();

    /**
     * <b>ownedOperation</b>, multiplicity=(0,*), isComposite, isUnique, isOrdered, subsettedProperty = {cmof.Classifier.feature, cmof.Namespace.ownedMember}
     */
    public cmof.common.ReflectiveSequence<? extends cmof.Operation> getOwnedOperation();

}

