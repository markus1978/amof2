package hub.sam.mof.mas;


/**
 * <b>AttachedNode</b>, isAbstract, superClass = {mas.ObjectNode}
 */
public interface AttachedNode extends cmof.reflection.Object, hub.sam.mof.mas.ObjectNode
{

    /**
     * <b>num</b>, multiplicity=(1,1)
     */
    public int getNum();

    public void setNum(int value);

}

