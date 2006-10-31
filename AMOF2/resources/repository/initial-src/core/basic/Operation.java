package core.basic;


/**
 * <b>Operation</b>, superClass = {core.basic.TypedElement, core.abstractions.multiplicities.MultiplicityElement}
 */
public interface Operation extends core.basic.TypedElement, core.abstractions.multiplicities.MultiplicityElement
{

    /**
     * <b>raisedException</b>, multiplicity=(0,*), isUnique
     */
    public cmof.common.ReflectiveCollection<? extends core.basic.Type> getRaisedException();

    /**
     * <b>ownedParameter</b>, multiplicity=(0,*), isComposite, isUnique, isOrdered
     */
    public cmof.common.ReflectiveSequence<? extends core.basic.Parameter> getOwnedParameter();

    /**
     * <b>class</b>, multiplicity=(0,1)
     */
    public core.basic.UmlClass getUmlClass();

    public void setUmlClass(core.basic.UmlClass value);

}

