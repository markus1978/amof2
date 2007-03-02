package cmof;


/**
 * <b>Association</b>, superClass = {cmof.Classifier, cmof.Relationship}
 */
public interface Association extends cmof.Classifier, cmof.Relationship
{

    /**
     * <b>isDerived</b>, multiplicity=(1,1)
     */
    public boolean isDerived();

    public void setIsDerived(boolean value);

    /**
     * <b>memberEnd</b>, multiplicity=(2,*), isUnique, isOrdered, subsettedProperty = {cmof.Namespace.member}
     */
    public cmof.common.ReflectiveSequence<? extends cmof.Property> getMemberEnd();

    /**
     * <b>ownedEnd</b>, multiplicity=(0,*), isComposite, isUnique, isOrdered, subsettedProperty = {cmof.Association.memberEnd, cmof.Classifier.feature, cmof.Namespace.ownedMember}
     */
    public cmof.common.ReflectiveSequence<? extends cmof.Property> getOwnedEnd();

    /**
     * <b>endType</b>, multiplicity=(1,*), isDerived, isUnique, subsettedProperty = {cmof.Relationship.relatedElement}
     */
    public cmof.common.ReflectiveCollection<? extends cmof.Type> getEndType();

}

