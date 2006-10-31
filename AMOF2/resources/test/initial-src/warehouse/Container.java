package warehouse;


/**
 * <b>Container</b>, isAbstract, superClass = {warehouse.Element}
 */
public interface Container extends warehouse.Element
{

    /**
     * <b>content</b>, multiplicity=(0,*), isComposite, isUnique
     */
    public cmof.common.ReflectiveCollection<? extends warehouse.Element> getContent();

}

