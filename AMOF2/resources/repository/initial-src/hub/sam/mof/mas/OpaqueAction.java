package hub.sam.mof.mas;


/**
 * <b>OpaqueAction</b>, superClass = {mas.Margin, mas.Action}
 */
public interface OpaqueAction extends cmof.reflection.Object, hub.sam.mof.mas.Margin, hub.sam.mof.mas.Action
{

    /**
     * <b>actionBody</b>, multiplicity=(0,1)
     */
    public java.lang.String getActionBody();

    public void setActionBody(java.lang.String value);

    /**
     * <b>actionKind</b>, multiplicity=(0,1)
     */
    public hub.sam.mof.mas.ActionKind getActionKind();

    public void setActionKind(hub.sam.mof.mas.ActionKind value);

    /**
     * <b>fire</b>, multiplicity=(1,1)
     */
    public void fire(hub.sam.mof.mas.ActivityInstance context) ;

    /**
     * <b>fire</b>, multiplicity=(1,1)
     */
    public void fire(hub.sam.mof.petrinets.NetInstance context) ;

}

