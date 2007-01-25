package hub.sam.mof.mas;


public class ExpansionNodeListDlg extends hub.sam.mof.reflection.ObjectDlg implements ExpansionNodeList
{
    protected ExpansionNodeList self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (ExpansionNodeList)self;
    }

    public hub.sam.mof.mas.ExpansionRegion getRegion() {
        return (hub.sam.mof.mas.ExpansionRegion)(java.lang.Object)self.getRegion();
    }

    public void setRegion(hub.sam.mof.mas.ExpansionRegion value) {
        self.setRegion(value);
    }

    public void setAttachedTo(hub.sam.mof.mas.ActivityNode value) {
        self.setAttachedTo(value);
    }

    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.AttachedNode> getNode() {
        return (cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.AttachedNode>)(java.lang.Object)self.getNode();
    }

    public hub.sam.mof.mas.ActivityNode getAttachedTo() {
        return (hub.sam.mof.mas.ActivityNode)(java.lang.Object)self.getAttachedTo();
    }

}

