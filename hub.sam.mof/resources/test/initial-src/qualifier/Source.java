package qualifier;


/**
 * <b>Source</b>, superClass = {qualifier.NamedElement}
 */
public interface Source extends qualifier.NamedElement
{

    /**
     * <b>superAttr</b>, multiplicity=(0,1)
     */
    public qualifier.Target getSuperAttr(qualifier.QualifierType qualifier);

    public void setSuperAttr(qualifier.QualifierType qualifier, qualifier.Target value);

}

