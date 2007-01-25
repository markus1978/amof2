package hub.sam.mof.mas;


/**
 * <b>ExpansionRegionBody</b>
 */
public interface ExpansionRegionBody extends cmof.reflection.Object
{

    /**
     * <b>region</b>, multiplicity=(0,1)
     */
    public hub.sam.mof.mas.ExpansionRegion getRegion();

    public void setRegion(hub.sam.mof.mas.ExpansionRegion value);

    /**
     * <b>gefChildren</b>, multiplicity=(0,*), isDerived, isUnique, isOrdered
     */
    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ActivityChild> getGefChildren();

}

