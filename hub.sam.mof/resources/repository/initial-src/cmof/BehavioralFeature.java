package cmof;


/**
 * <b>BehavioralFeature</b>, isAbstract, superClass = {core.abstractions.behavioralfeatures.BehavioralFeature, cmof.Feature, cmof.Namespace}
 */
public interface BehavioralFeature extends core.abstractions.behavioralfeatures.BehavioralFeature, cmof.Feature, cmof.Namespace
{

    /**
     * <b>concurrency</b>, multiplicity=(1,1)
     */
    public cmof.CallConcurrencyKind getConcurrency();

    public void setConcurrency(cmof.CallConcurrencyKind value);

    /**
     * <b>parameter</b>, multiplicity=(0,*), isDerivedUnion, isDerived, isUnique, isOrdered, subsettedProperty = {cmof.Namespace.member}, redefinedProperty = {core.abstractions.behavioralfeatures.BehavioralFeature.parameter}
     */
    public cmof.common.ReflectiveSequence<? extends cmof.Parameter> getParameter();

    /**
     * <b>formalParameter</b>, multiplicity=(0,*), isComposite, isUnique, isOrdered, subsettedProperty = {cmof.BehavioralFeature.parameter, cmof.Namespace.ownedMember}
     */
    public cmof.common.ReflectiveSequence<? extends cmof.Parameter> getFormalParameter();

    /**
     * <b>returnResult</b>, multiplicity=(0,*), isComposite, isUnique, isOrdered, subsettedProperty = {cmof.BehavioralFeature.parameter, cmof.Namespace.ownedMember}
     */
    public cmof.common.ReflectiveSequence<? extends cmof.Parameter> getReturnResult();

    /**
     * <b>raisedException</b>, multiplicity=(0,*), isUnique
     */
    public cmof.common.ReflectiveCollection<? extends cmof.Type> getRaisedException();

    /**
     * <b>isDistinguishableFrom</b>, multiplicity=(1,1)
     */
    public boolean isDistinguishableFrom(cmof.NamedElement n, cmof.Namespace ns) ;

    /**
     * <b>isDistinguishableFrom</b>, multiplicity=(1,1)
     */
    public boolean isDistinguishableFrom(core.abstractions.namespaces.NamedElement n, core.abstractions.namespaces.Namespace ns) ;

}

