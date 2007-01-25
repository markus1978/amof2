package hub.sam.mof.mas;


public class ASTransitionDlg extends hub.sam.mof.reflection.ObjectDlg implements ASTransition
{
    protected ASTransition self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (ASTransition)self;
    }

    public cmof.common.ReflectiveCollection<? extends hub.sam.mof.mas.ASPlace> getOutputPlaces() {
        return (cmof.common.ReflectiveCollection<? extends hub.sam.mof.mas.ASPlace>)(java.lang.Object)self.getOutputPlaces();
    }

    public cmof.common.ReflectiveCollection<? extends hub.sam.mof.mas.ASPlace> getInputPlaces() {
        return (cmof.common.ReflectiveCollection<? extends hub.sam.mof.mas.ASPlace>)(java.lang.Object)self.getInputPlaces();
    }

    public boolean isEnabled(hub.sam.mof.petrinets.NetInstance context)  {
        return self.isEnabled(context);
    }

    public void fire(hub.sam.mof.petrinets.NetInstance context)  {
        self.fire(context);
    }

}

