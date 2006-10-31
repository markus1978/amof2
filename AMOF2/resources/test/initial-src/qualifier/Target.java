package qualifier;


/**
 * <b>Target</b>, superClass = {qualifier.NamedElement}
 */
public interface Target extends qualifier.NamedElement
{

    /**
     * <b>source</b>, multiplicity=(0,1)
     */
    public qualifier.Source getSource();

    public void setSource(qualifier.Source value);

}

