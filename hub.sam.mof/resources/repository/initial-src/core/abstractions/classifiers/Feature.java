package core.abstractions.classifiers;


/**
 * <b>Feature</b>, isAbstract, superClass = {core.abstractions.namespaces.NamedElement}
 */
public interface Feature extends core.abstractions.namespaces.NamedElement
{

    /**
     * <b>featuringClassifier</b>, multiplicity=(1,*), isDerivedUnion, isDerived, isUnique
     */
    public cmof.common.ReflectiveCollection<? extends core.abstractions.classifiers.Classifier> getFeaturingClassifier();

}

