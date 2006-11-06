package warehouse;


/**
 * <b>Box</b>, superClass = {warehouse.Container}
 */
public interface Box extends cmof.reflection.Object, warehouse.Container
{



    /**
     * <b>item</b>, multiplicity=(0,*), isComposite, isUnique, subsettedProperty = {warehouse.Container.content}
     */
    public cmof.common.ReflectiveCollection<? extends warehouse.Item> getItem();

    /**
     * <b>weight</b>, multiplicity=(1,1), isDerived
     */
    public int getWeight();

    public void setWeight(int value);

}

