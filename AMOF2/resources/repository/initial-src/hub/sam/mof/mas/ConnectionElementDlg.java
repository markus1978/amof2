package hub.sam.mof.mas;


public class ConnectionElementDlg extends hub.sam.mof.reflection.ObjectDlg implements ConnectionElement
{
    protected ConnectionElement self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (ConnectionElement)self;
    }

    public cmof.common.ReflectiveSequence<hub.sam.mase.m2model.ActivityEdgeBendpoint> getBendpoints() {
        return self.getBendpoints();
    }

}

