package hub.sam.mof.mas;


public class InputPinListDlg extends hub.sam.mof.reflection.ObjectDlg implements InputPinList
{
    protected InputPinList self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (InputPinList)self;
    }

    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.InputPin> getPin() {
        return (cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.InputPin>)(java.lang.Object)self.getPin();
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

