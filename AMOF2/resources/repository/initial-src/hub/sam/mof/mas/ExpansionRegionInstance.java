package hub.sam.mof.mas;


/**
 * <b>ExpansionRegionInstance</b>, superClass = {mas.ActivityInstance}
 */
public interface ExpansionRegionInstance extends cmof.reflection.Object, hub.sam.mof.mas.ActivityInstance
{

    /**
     * <b>expansionRegionInput</b>, multiplicity=(1,1)
     */
    public hub.sam.mof.mas.InExpansionNodeInstance getExpansionRegionInput();

    public void setExpansionRegionInput(hub.sam.mof.mas.InExpansionNodeInstance value);

    /**
     * <b>outerActivity</b>, multiplicity=(1,1)
     */
    public hub.sam.mof.mas.ActivityInstance getOuterActivity();

    public void setOuterActivity(hub.sam.mof.mas.ActivityInstance value);

    /**
     * <b>metaDelete</b>, multiplicity=(1,1)
     */
    public void metaDelete() ;

    /**
     * <b>metaClassifierExpansionRegion</b>, multiplicity=(0,1), redefinedProperty = {mas.ActivityInstance.metaClassifierActivity}
     */
    public hub.sam.mof.mas.ExpansionRegion getMetaClassifierExpansionRegion();

    public void setMetaClassifierExpansionRegion(hub.sam.mof.mas.ExpansionRegion value);

    public void setMetaClassifierActivity(hub.sam.mof.mas.Activity value);

    public void setMetaClassifierNet(hub.sam.mof.petrinets.Net value);

}

