package hub.sam.mof.mas;


public class ContextPinListDlg extends hub.sam.mof.reflection.ObjectDlg implements ContextPinList
{
    protected ContextPinList self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (ContextPinList)self;
    }

    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ContextPin> getPin() {
        return (cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ContextPin>)(java.lang.Object)self.getPin();
    }

    public hub.sam.mof.mas.DecisionNode getDecisionNode() {
        return (hub.sam.mof.mas.DecisionNode)(java.lang.Object)self.getDecisionNode();
    }

    public void setDecisionNode(hub.sam.mof.mas.DecisionNode value) {
        self.setDecisionNode(value);
    }

    public void setAction(hub.sam.mof.mas.Action value) {
        self.setAction(value);
    }

    public void setAttachedTo(hub.sam.mof.mas.ActivityNode value) {
        self.setAttachedTo(value);
    }

    public hub.sam.mof.mas.Action getAction() {
        return (hub.sam.mof.mas.Action)(java.lang.Object)self.getAction();
    }

    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.AttachedNode> getNode() {
        return (cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.AttachedNode>)(java.lang.Object)self.getNode();
    }

    public hub.sam.mof.mas.ActivityNode getAttachedTo() {
        return (hub.sam.mof.mas.ActivityNode)(java.lang.Object)self.getAttachedTo();
    }

}

