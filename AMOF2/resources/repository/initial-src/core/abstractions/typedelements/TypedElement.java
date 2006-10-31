package core.abstractions.typedelements;


/**
 * <b>TypedElement</b>, isAbstract, superClass = {core.abstractions.namespaces.NamedElement}
 */
public interface TypedElement extends core.abstractions.namespaces.NamedElement
{

    /**
     * <b>type</b>, multiplicity=(0,1)
     */
    public core.abstractions.typedelements.Type getType();

    public void setType(core.abstractions.typedelements.Type value);

}

