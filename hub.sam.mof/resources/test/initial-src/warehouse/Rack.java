package warehouse;


/**
 * <b>Rack</b>, superClass = {warehouse.Container}
 */
public interface Rack extends cmof.reflection.Object, warehouse.Container
{



    /**
     * <b>box</b>, multiplicity=(0,*), isComposite, isUnique, subsettedProperty = {warehouse.Container.content}
     */
    public cmof.common.ReflectiveCollection<? extends warehouse.Box> getBox();

}

