package hub.sam.mof.petrinets;


public class TransitionDlg extends hub.sam.mof.reflection.ObjectDlg implements Transition
{
    protected Transition self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (Transition)self;
    }

    public cmof.common.ReflectiveCollection<? extends hub.sam.mof.petrinets.Place> getOutputPlaces() {
        return (cmof.common.ReflectiveCollection<? extends hub.sam.mof.petrinets.Place>)(java.lang.Object)self.getOutputPlaces();
    }

    public cmof.common.ReflectiveCollection<? extends hub.sam.mof.petrinets.Place> getInputPlaces() {
        return (cmof.common.ReflectiveCollection<? extends hub.sam.mof.petrinets.Place>)(java.lang.Object)self.getInputPlaces();
    }

    public boolean isEnabled(hub.sam.mof.petrinets.NetInstance context)  {
        return self.isEnabled(context);
    }

    public void fire(hub.sam.mof.petrinets.NetInstance context)  {
        self.fire(context);
    }

}

