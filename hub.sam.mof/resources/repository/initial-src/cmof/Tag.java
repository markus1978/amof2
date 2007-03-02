package cmof;


/**
 * <b>Tag</b>, superClass = {cmof.Element}
 */
public interface Tag extends cmof.Element
{

    /**
     * <b>name</b>, multiplicity=(1,1)
     */
    public java.lang.String getName();

    public void setName(java.lang.String value);

    /**
     * <b>value</b>, multiplicity=(1,1)
     */
    public java.lang.String getValue();

    public void setValue(java.lang.String value);

    /**
     * <b>element</b>, multiplicity=(0,*), isUnique
     */
    public cmof.common.ReflectiveCollection<? extends cmof.Element> getElement();

}

