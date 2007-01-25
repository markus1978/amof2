package hub.sam.mof.mas;


/**
 * <b>ActivityGroup</b>, isAbstract, superClass = {mas.ConstrainedNode, cmof.Element, mas.ActivityChild, mas.Activity}
 */
public interface ActivityGroup extends cmof.reflection.Object, hub.sam.mof.mas.ConstrainedNode, cmof.Element, hub.sam.mof.mas.ActivityChild, hub.sam.mof.mas.Activity
{

    /**
     * <b>activityAsGroup</b>, multiplicity=(0,1)
     */
    public hub.sam.mof.mas.Activity getActivityAsGroup();

    public void setActivityAsGroup(hub.sam.mof.mas.Activity value);

}

