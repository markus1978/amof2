package core.abstractions.generalizations;


/**
 * <b>Classifier</b>, isAbstract, superClass = {core.abstractions.super.Classifier, core.abstractions.typedelements.Type}
 * <br>constraint - general_equals_parents : 
 * <pre>general = self.parents()</pre>
 */
public interface Classifier extends core.abstractions.umlsuper.Classifier, core.abstractions.typedelements.Type
{

    /**
     * <b>generalization</b>, multiplicity=(0,*), isComposite, isUnique, subsettedProperty = {core.abstractions.ownerships.Element.ownedElement}
     */
    public cmof.common.ReflectiveCollection<? extends core.abstractions.generalizations.Generalization> getGeneralization();

    /**
     * <b>general</b>, multiplicity=(0,*), isDerived, isUnique, redefinedProperty = {core.abstractions.super.Classifier.general}
     */
    public cmof.common.ReflectiveCollection<? extends core.abstractions.generalizations.Classifier> getGeneral();

    /**
     * <b>general</b>, multiplicity=(0,*)
     */
    public cmof.common.ReflectiveCollection<? extends core.abstractions.generalizations.Classifier> generalOperation() ;

    /**
     * <b>parents</b>, multiplicity=(0,*)
     */
    public cmof.common.ReflectiveCollection<? extends core.abstractions.generalizations.Classifier> parents() ;

    /**
     * <b>conformsTo</b>, multiplicity=(1,1)
     */
    public boolean conformsTo(core.abstractions.generalizations.Classifier other) ;

    /**
     * <b>conformsTo</b>, multiplicity=(1,1)
     */
    public boolean conformsTo(core.abstractions.typedelements.Type other) ;

}

