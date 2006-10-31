package StateAutomaton.Syntax;


/**
 * <b>Component</b>, isAbstract, superClass = {Instances.InstanceDef}
 */
public interface Component extends Instances.InstanceDef
{

    /**
     * <b>port</b>, multiplicity=(0,*), isComposite, isUnique, subsettedProperty = {Instances.Classifier.ownedInstance}
     */
    public cmof.common.ReflectiveCollection<? extends StateAutomaton.Syntax.Port> getPort();

}

