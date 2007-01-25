package hub.sam.mof.mas;


public class ExpansionRegionBodyDlg extends hub.sam.mof.reflection.ObjectDlg implements ExpansionRegionBody
{
    protected ExpansionRegionBody self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (ExpansionRegionBody)self;
    }

    public hub.sam.mof.mas.ExpansionRegion getRegion() {
        return (hub.sam.mof.mas.ExpansionRegion)(java.lang.Object)self.getRegion();
    }

    public void setRegion(hub.sam.mof.mas.ExpansionRegion value) {
        self.setRegion(value);
    }

    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ActivityChild> getGefChildren() {
        return (cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ActivityChild>)(java.lang.Object)self.getGefChildren();
    }

}

