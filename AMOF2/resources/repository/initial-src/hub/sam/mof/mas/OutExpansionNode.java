package hub.sam.mof.mas;


/**
 * <b>OutExpansionNode</b>, superClass = {mas.ExpansionNode}
 */
public interface OutExpansionNode extends cmof.reflection.Object, hub.sam.mof.mas.ExpansionNode
{

    /**
     * <b>regionAsOutput</b>, multiplicity=(0,1)
     */
    public hub.sam.mof.mas.ExpansionRegion getRegionAsOutput();

    public void setRegionAsOutput(hub.sam.mof.mas.ExpansionRegion value);

}

