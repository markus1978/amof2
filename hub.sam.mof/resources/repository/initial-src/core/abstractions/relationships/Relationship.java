package core.abstractions.relationships;


/**
 * <b>Relationship</b>, isAbstract, superClass = {core.abstractions.ownerships.Element}
 */
public interface Relationship extends core.abstractions.ownerships.Element
{

    /**
     * <b>relatedElement</b>, multiplicity=(1,*), isDerivedUnion, isDerived, isUnique
     */
    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> getRelatedElement();

}

