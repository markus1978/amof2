package core.abstractions.generalizations;


/**
 * <b>Generalization</b>, superClass = {core.abstractions.relationships.DirectedRelationship}
 */
public interface Generalization extends core.abstractions.relationships.DirectedRelationship
{

    /**
     * <b>specific</b>, multiplicity=(1,1), subsettedProperty = {core.abstractions.relationships.DirectedRelationship.source, core.abstractions.ownerships.Element.owner}
     */
    public core.abstractions.generalizations.Classifier getSpecific();

    public void setSpecific(core.abstractions.generalizations.Classifier value);

    /**
     * <b>general</b>, multiplicity=(1,1), subsettedProperty = {core.abstractions.relationships.DirectedRelationship.target}
     */
    public core.abstractions.generalizations.Classifier getGeneral();

    public void setGeneral(core.abstractions.generalizations.Classifier value);

}

