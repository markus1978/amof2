package cmof;


/**
 * <b>Operation</b>, superClass = {core.basic.Operation, cmof.BehavioralFeature}
 * <br>constraint - type_of_result : 
 * <pre>if returnResult->size() = 1 then type = returnResult.type else type = nil endif </pre>
 * <br>constraint - only_body_for_query : 
 * <pre>bodyCondition->notEmpty() implies isQuery</pre>
 */
public interface Operation extends core.basic.Operation, cmof.BehavioralFeature
{

    /**
     * <b>ownedBehavior</b>, multiplicity=(0,1), subsettedProperty = {cmof.Element.ownedElement}
     */
    public cmof.Element getOwnedBehavior();

    public void setOwnedBehavior(cmof.Element value);

    /**
     * <b>isQuery</b>, multiplicity=(1,1)
     */
    public boolean isQuery();

    public void setIsQuery(boolean value);

    /**
     * <b>isOrdered</b>, multiplicity=(1,1), redefinedProperty = {core.abstractions.multiplicities.MultiplicityElement.isOrdered}
     */
    public boolean isOrdered();

    public void setIsOrdered(boolean value);

    /**
     * <b>isUnique</b>, multiplicity=(1,1), redefinedProperty = {core.abstractions.multiplicities.MultiplicityElement.isUnique}
     */
    public boolean isUnique();

    public void setIsUnique(boolean value);

    /**
     * <b>lower</b>, multiplicity=(0,1), redefinedProperty = {core.abstractions.multiplicities.MultiplicityElement.lower}
     */
    public int getLower();

    public void setLower(int value);

    /**
     * <b>upper</b>, multiplicity=(0,1), redefinedProperty = {core.abstractions.multiplicities.MultiplicityElement.upper}
     */
    public long getUpper();

    public void setUpper(long value);

    /**
     * <b>class</b>, multiplicity=(0,1), subsettedProperty = {cmof.RedefinableElement.redefinitionContext, cmof.NamedElement.namespace, cmof.Feature.featuringClassifier}, redefinedProperty = {core.basic.Operation.class}
     */
    public cmof.UmlClass getUmlClass();

    public void setUmlClass(cmof.UmlClass value);

    public void setUmlClass(core.basic.UmlClass value);

    /**
     * <b>datatype</b>, multiplicity=(0,1), subsettedProperty = {cmof.RedefinableElement.redefinitionContext, cmof.NamedElement.namespace, cmof.Feature.featuringClassifier}
     */
    public cmof.DataType getDatatype();

    public void setDatatype(cmof.DataType value);

    /**
     * <b>formalParameter</b>, multiplicity=(0,*), isComposite, isUnique, isOrdered, redefinedProperty = {cmof.BehavioralFeature.formalParameter, core.basic.Operation.ownedParameter}
     */
    public cmof.common.ReflectiveSequence<? extends cmof.Parameter> getFormalParameter();

    /**
     * <b>raisedException</b>, multiplicity=(0,*), isUnique, redefinedProperty = {core.basic.Operation.raisedException, cmof.BehavioralFeature.raisedException}
     */
    public cmof.common.ReflectiveCollection<? extends cmof.Type> getRaisedException();

    /**
     * <b>precondition</b>, multiplicity=(0,*), isComposite, isUnique, subsettedProperty = {cmof.Namespace.ownedRule}
     */
    public cmof.common.ReflectiveCollection<? extends cmof.Constraint> getPrecondition();

    /**
     * <b>postcondition</b>, multiplicity=(0,*), isComposite, isUnique, subsettedProperty = {cmof.Namespace.ownedRule}
     */
    public cmof.common.ReflectiveCollection<? extends cmof.Constraint> getPostcondition();

    /**
     * <b>redefinedOperation</b>, multiplicity=(0,*), isUnique, subsettedProperty = {cmof.RedefinableElement.redefinedElement}
     */
    public cmof.common.ReflectiveCollection<? extends cmof.Operation> getRedefinedOperation();

    /**
     * <b>type</b>, multiplicity=(0,1)
     */
    public cmof.Type getType();

    public void setType(cmof.Type value);

    /**
     * <b>bodyCondition</b>, multiplicity=(0,1), isComposite, subsettedProperty = {cmof.Namespace.ownedRule}
     */
    public cmof.Constraint getBodyCondition();

    public void setBodyCondition(cmof.Constraint value);

    /**
     * <b>isOrdered</b>, multiplicity=(1,1)
     */
    public boolean isOrderedOperation() ;

    /**
     * <b>isUnique</b>, multiplicity=(1,1)
     */
    public boolean isUniqueOperation() ;

    /**
     * <b>lower</b>, multiplicity=(0,1)
     */
    public int lowerOperation() ;

    /**
     * <b>upper</b>, multiplicity=(0,1)
     */
    public long upperOperation() ;

    /**
     * <b>type</b>, multiplicity=(0,1)
     */
    public cmof.Classifier typeOperation() ;

    /**
     * <b>isConsistentWith</b>, multiplicity=(1,1)
     */
    public boolean isConsistentWith(cmof.RedefinableElement redefinee) ;

    /**
     * <b>isConsistentWith</b>, multiplicity=(1,1)
     */
    public boolean isConsistentWith(core.abstractions.redefinitions.RedefinableElement redefinee) ;

}

