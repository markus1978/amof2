package cmof;


/**
 * <b>Relationship</b>, isAbstract, superClass = {cmof.Element, core.abstractions.relationships.Relationship}
 */
public interface Relationship extends cmof.Element, core.abstractions.relationships.Relationship
{

    /**
     * <b>relatedElement</b>, multiplicity=(1,*), isDerivedUnion, isDerived, isUnique, redefinedProperty = {core.abstractions.relationships.Relationship.relatedElement}
     */
    public cmof.common.ReflectiveCollection<? extends cmof.Element> getRelatedElement();

}

