package core.abstractions.relationships;


/**
 * <b>DirectedRelationship</b>, isAbstract, superClass = {core.abstractions.relationships.Relationship}
 */
public interface DirectedRelationship extends core.abstractions.relationships.Relationship
{

    /**
     * <b>source</b>, multiplicity=(1,*), isDerivedUnion, isDerived, isUnique, subsettedProperty = {core.abstractions.relationships.Relationship.relatedElement}
     */
    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> getSource();

    /**
     * <b>target</b>, multiplicity=(1,*), isDerivedUnion, isDerived, isUnique, subsettedProperty = {core.abstractions.relationships.Relationship.relatedElement}
     */
    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> getTarget();

}

