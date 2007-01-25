package hub.sam.mof.mas;


public class AttachedNodeListDlg extends hub.sam.mof.reflection.ObjectDlg implements AttachedNodeList
{
    protected AttachedNodeList self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (AttachedNodeList)self;
    }

    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.AttachedNode> getNode() {
        return (cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.AttachedNode>)(java.lang.Object)self.getNode();
    }

    public hub.sam.mof.mas.ActivityNode getAttachedTo() {
        return (hub.sam.mof.mas.ActivityNode)(java.lang.Object)self.getAttachedTo();
    }

    public void setAttachedTo(hub.sam.mof.mas.ActivityNode value) {
        self.setAttachedTo(value);
    }

}

