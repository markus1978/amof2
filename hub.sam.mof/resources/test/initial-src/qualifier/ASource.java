package qualifier;


/**
 * <b>ASource</b>, superClass = {qualifier.Source}
 */
public interface ASource extends qualifier.Source
{

    /**
     * <b>superAttrB</b>, multiplicity=(0,1), subsettedProperty = {qualifier.Source.superAttr}
     */
    public qualifier.BTarget getSuperAttrB(qualifier.BQualifierType qualifier);

    public void setSuperAttrB(qualifier.BQualifierType qualifier, qualifier.BTarget value);

    /**
     * <b>superAttrA</b>, multiplicity=(0,1), subsettedProperty = {qualifier.Source.superAttr}
     */
    public qualifier.ATarget getSuperAttrA(qualifier.AQualifierType qualifier);

    public void setSuperAttrA(qualifier.AQualifierType qualifier, qualifier.ATarget value);

}

