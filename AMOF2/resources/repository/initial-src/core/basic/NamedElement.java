package core.basic;


/**
 * <b>NamedElement</b>, isAbstract, superClass = {core.abstractions.elements.Element}
 */
public interface NamedElement extends core.abstractions.elements.Element
{

    /**
     * <b>name</b>, multiplicity=(0,1)
     */
    public java.lang.String getName();

    public void setName(java.lang.String value);

}

