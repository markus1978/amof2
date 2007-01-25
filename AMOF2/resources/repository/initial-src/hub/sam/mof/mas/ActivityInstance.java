package hub.sam.mof.mas;


/**
 * <b>ActivityInstance</b>, superClass = {petrinets.NetInstance}
 */
public interface ActivityInstance extends cmof.reflection.Object, hub.sam.mof.petrinets.NetInstance
{

    /**
     * <b>oclContext</b>, multiplicity=(1,1)
     */
    public java.lang.Object getOclContext();

    public void setOclContext(java.lang.Object value);

    /**
     * <b>env</b>, multiplicity=(1,1)
     */
    public hub.sam.mof.mas.ExecutionEnvironment getEnv();

    public void setEnv(hub.sam.mof.mas.ExecutionEnvironment value);

    /**
     * <b>innerActivities</b>, multiplicity=(0,*), isComposite, isUnique
     */
    public cmof.common.ReflectiveCollection<? extends hub.sam.mof.mas.ExpansionRegionInstance> getInnerActivities();

    /**
     * <b>metaDelete</b>, multiplicity=(1,1)
     */
    public void metaDelete() ;

    /**
     * <b>metaClassifierActivity</b>, multiplicity=(0,1), redefinedProperty = {petrinets.NetInstance.metaClassifierNet}
     */
    public hub.sam.mof.mas.Activity getMetaClassifierActivity();

    public void setMetaClassifierActivity(hub.sam.mof.mas.Activity value);

    public void setMetaClassifierNet(hub.sam.mof.petrinets.Net value);

}

