package cmof;


/**
 * <b>TypedElement</b>, isAbstract, superClass = {core.basic.TypedElement, core.abstractions.typedelements.TypedElement, cmof.NamedElement}
 */
public interface TypedElement extends core.basic.TypedElement, core.abstractions.typedelements.TypedElement, cmof.NamedElement
{

    /**
     * <b>type</b>, multiplicity=(0,1), redefinedProperty = {core.abstractions.typedelements.TypedElement.type}
     */
    public cmof.Type getType();

    public void setType(cmof.Type value);

    public void setType(core.abstractions.typedelements.Type value);

}

