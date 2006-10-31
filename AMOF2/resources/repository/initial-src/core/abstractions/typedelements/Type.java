package core.abstractions.typedelements;


/**
 * <b>Type</b>, isAbstract, superClass = {core.abstractions.namespaces.NamedElement}
 */
public interface Type extends core.abstractions.namespaces.NamedElement
{

    /**
     * <b>conformsTo</b>, multiplicity=(1,1)
     */
    public boolean conformsTo(core.abstractions.typedelements.Type other) ;

}

