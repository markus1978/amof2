package core.abstractions.constraints;


/**
 * <b>Namespace</b>, isAbstract, superClass = {core.abstractions.namespaces.Namespace}
 */
public interface Namespace extends core.abstractions.namespaces.Namespace
{

    /**
     * <b>ownedRule</b>, multiplicity=(0,*), isComposite, isUnique, subsettedProperty = {core.abstractions.namespaces.Namespace.ownedMember}
     */
    public cmof.common.ReflectiveCollection<? extends core.abstractions.constraints.Constraint> getOwnedRule();

}

