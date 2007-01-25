package hub.sam.mof.mas;


public class OutputPinListDlg extends hub.sam.mof.reflection.ObjectDlg implements OutputPinList
{
    protected OutputPinList self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (OutputPinList)self;
    }

    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.OutputPin> getPin() {
        return (cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.OutputPin>)(java.lang.Object)self.getPin();
    }

    public hub.sam.mof.mas.Action getAction() {
        return (hub.sam.mof.mas.Action)(java.lang.Object)self.getAction();
    }

    public void setAction(hub.sam.mof.mas.Action value) {
        self.setAction(value);
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

