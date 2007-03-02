package core.basic;


/**
 * <b>EnumerationLiteral</b>, superClass = {core.basic.NamedElement}
 */
public interface EnumerationLiteral extends core.basic.NamedElement
{

    /**
     * <b>enumeration</b>, multiplicity=(0,1)
     */
    public core.basic.Enumeration getEnumeration();

    public void setEnumeration(core.basic.Enumeration value);

}

