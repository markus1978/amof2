package hub.sam.mof.mas;


public class InExpansionNodeInstanceDlg extends hub.sam.mof.reflection.ObjectDlg implements InExpansionNodeInstance
{
    protected InExpansionNodeInstance self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (InExpansionNodeInstance)self;
    }

    public void metaDelete()  {
        self.metaDelete();
    }

    public hub.sam.mof.mas.InExpansionNode getMetaClassifierInExpansionNode() {
        return (hub.sam.mof.mas.InExpansionNode)(java.lang.Object)self.getMetaClassifierInExpansionNode();
    }

    public void setMetaClassifierInExpansionNode(hub.sam.mof.mas.InExpansionNode value) {
        self.setMetaClassifierInExpansionNode(value);
    }

    public void setMetaClassifierInputPin(hub.sam.mof.mas.InputPin value) {
        self.setMetaClassifierInputPin(value);
    }

    public void setMetaClassifierPlace(hub.sam.mof.petrinets.Place value) {
        self.setMetaClassifierPlace(value);
    }

    public int getTokens() {
        return self.getTokens();
    }

    public void setTokens(int value) {
        self.setTokens(value);
    }

    public hub.sam.mof.petrinets.Place getMetaClassifierPlace() {
        return (hub.sam.mof.petrinets.Place)(java.lang.Object)self.getMetaClassifierPlace();
    }

    public java.lang.Object getValue() {
        return self.getValue();
    }

    public void setValue(java.lang.Object value) {
        self.setValue(value);
    }

    public hub.sam.mof.mas.InputPin getMetaClassifierInputPin() {
        return (hub.sam.mof.mas.InputPin)(java.lang.Object)self.getMetaClassifierInputPin();
    }

}

