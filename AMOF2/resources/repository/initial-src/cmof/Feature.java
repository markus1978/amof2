package cmof;


/**
 * <b>Feature</b>, isAbstract, superClass = {core.abstractions.classifiers.Feature, cmof.RedefinableElement}
 */
public interface Feature extends core.abstractions.classifiers.Feature, cmof.RedefinableElement
{

    /**
     * <b>featuringClassifier</b>, multiplicity=(1,*), isDerivedUnion, isDerived, isUnique, redefinedProperty = {core.abstractions.classifiers.Feature.featuringClassifier}
     */
    public cmof.common.ReflectiveCollection<? extends cmof.Classifier> getFeaturingClassifier();

}

