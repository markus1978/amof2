package hub.sam.mof.mas;


public class GraphicalElementDlg extends hub.sam.mof.reflection.ObjectDlg implements GraphicalElement
{
    protected GraphicalElement self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (GraphicalElement)self;
    }

    public org.eclipse.draw2d.geometry.Rectangle getRectangle() {
        return self.getRectangle();
    }

    public void setRectangle(org.eclipse.draw2d.geometry.Rectangle value) {
        self.setRectangle(value);
    }

}

