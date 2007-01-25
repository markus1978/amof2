package hub.sam.mof.mas;


/**
 * <b>PinList</b>, isAbstract, superClass = {mas.AttachedNodeList}
 */
public interface PinList extends cmof.reflection.Object, hub.sam.mof.mas.AttachedNodeList
{

    /**
     * <b>action</b>, multiplicity=(0,1), redefinedProperty = {mas.AttachedNodeList.attachedTo}
     */
    public hub.sam.mof.mas.Action getAction();

    public void setAction(hub.sam.mof.mas.Action value);

    public void setAttachedTo(hub.sam.mof.mas.ActivityNode value);

}

