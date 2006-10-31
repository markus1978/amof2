package cmof;


/**
 * <b>DirectedRelationship</b>, isAbstract, superClass = {cmof.Relationship, core.abstractions.relationships.DirectedRelationship}
 */
public interface DirectedRelationship extends cmof.Relationship, core.abstractions.relationships.DirectedRelationship
{

    /**
     * <b>source</b>, multiplicity=(1,*), isDerivedUnion, isDerived, isUnique, subsettedProperty = {cmof.Relationship.relatedElement}, redefinedProperty = {core.abstractions.relationships.DirectedRelationship.source}
     */
    public cmof.common.ReflectiveCollection<? extends cmof.Element> getSource();

    /**
     * <b>target</b>, multiplicity=(1,*), isDerivedUnion, isDerived, isUnique, subsettedProperty = {cmof.Relationship.relatedElement}, redefinedProperty = {core.abstractions.relationships.DirectedRelationship.target}
     */
    public cmof.common.ReflectiveCollection<? extends cmof.Element> getTarget();

}

