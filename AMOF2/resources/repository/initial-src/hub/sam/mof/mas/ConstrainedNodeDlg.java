package hub.sam.mof.mas;


public class ConstrainedNodeDlg extends hub.sam.mof.reflection.ObjectDlg implements ConstrainedNode
{
    protected ConstrainedNode self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (ConstrainedNode)self;
    }

    public org.eclipse.draw2d.geometry.Rectangle getRectangle() {
        return self.getRectangle();
    }

    public void setRectangle(org.eclipse.draw2d.geometry.Rectangle value) {
        self.setRectangle(value);
    }

}

