package cmof;


/**
 * <b>EnumerationLiteral</b>, superClass = {core.basic.EnumerationLiteral, cmof.NamedElement}
 */
public interface EnumerationLiteral extends core.basic.EnumerationLiteral, cmof.NamedElement
{

    /**
     * <b>enumeration</b>, multiplicity=(0,1), subsettedProperty = {cmof.NamedElement.namespace}, redefinedProperty = {core.basic.EnumerationLiteral.enumeration}
     */
    public cmof.Enumeration getEnumeration();

    public void setEnumeration(cmof.Enumeration value);

    public void setEnumeration(core.basic.Enumeration value);

}

