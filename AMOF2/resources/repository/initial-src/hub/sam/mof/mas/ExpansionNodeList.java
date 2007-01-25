package hub.sam.mof.mas;


/**
 * <b>ExpansionNodeList</b>, isAbstract, superClass = {mas.AttachedNodeList}
 */
public interface ExpansionNodeList extends cmof.reflection.Object, hub.sam.mof.mas.AttachedNodeList
{

    /**
     * <b>region</b>, multiplicity=(0,1), redefinedProperty = {mas.AttachedNodeList.attachedTo}
     */
    public hub.sam.mof.mas.ExpansionRegion getRegion();

    public void setRegion(hub.sam.mof.mas.ExpansionRegion value);

    public void setAttachedTo(hub.sam.mof.mas.ActivityNode value);

}

