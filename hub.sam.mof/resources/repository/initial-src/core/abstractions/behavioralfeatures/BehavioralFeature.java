package core.abstractions.behavioralfeatures;


/**
 * <b>BehavioralFeature</b>, isAbstract, superClass = {core.abstractions.classifiers.Feature, core.abstractions.namespaces.Namespace}
 */
public interface BehavioralFeature extends core.abstractions.classifiers.Feature, core.abstractions.namespaces.Namespace
{

    /**
     * <b>parameter</b>, multiplicity=(0,*), isDerivedUnion, isDerived, isUnique, isOrdered, subsettedProperty = {core.abstractions.namespaces.Namespace.member}
     */
    public cmof.common.ReflectiveSequence<? extends core.abstractions.behavioralfeatures.Parameter> getParameter();

    /**
     * <b>isDistinguishableFrom</b>, multiplicity=(1,1)
     */
    public boolean isDistinguishableFrom(core.abstractions.namespaces.NamedElement n, core.abstractions.namespaces.Namespace ns) ;

}

