package cmof;


/**
 * <b>Class</b>, superClass = {cmof.Classifier, core.basic.Class}
 */
public interface UmlClass extends cmof.Classifier, core.basic.UmlClass
{

    /**
     * <b>isAbstract</b>, multiplicity=(1,1), redefinedProperty = {core.abstractions.super.Classifier.isAbstract, core.basic.Class.isAbstract}
     */
    public boolean isAbstract();

    public void setIsAbstract(boolean value);

    /**
     * <b>ownedOperation</b>, multiplicity=(0,*), isComposite, isUnique, isOrdered, subsettedProperty = {cmof.Classifier.feature, cmof.Namespace.ownedMember}, redefinedProperty = {core.basic.Class.ownedOperation}
     */
    public cmof.common.ReflectiveSequence<? extends cmof.Operation> getOwnedOperation();

    /**
     * <b>ownedAttribute</b>, multiplicity=(0,*), isComposite, isUnique, isOrdered, subsettedProperty = {cmof.Classifier.attribute, cmof.Namespace.ownedMember}, redefinedProperty = {core.basic.Class.ownedAttribute}
     */
    public cmof.common.ReflectiveSequence<? extends cmof.Property> getOwnedAttribute();

    /**
     * <b>superClass</b>, multiplicity=(0,*), isUnique, subsettedProperty = {cmof.Classifier.general}, redefinedProperty = {core.basic.Class.superClass}
     */
    public cmof.common.ReflectiveCollection<? extends cmof.UmlClass> getSuperClass();

}

