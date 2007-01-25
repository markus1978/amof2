package hub.sam.mof.petrinets;


public class NetInstanceDlg extends hub.sam.mof.reflection.ObjectDlg implements NetInstance
{
    protected NetInstance self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (NetInstance)self;
    }

    public hub.sam.mof.petrinets.PlaceInstance getPlaces(hub.sam.mof.petrinets.Place qualifier) {
        return (hub.sam.mof.petrinets.PlaceInstance)(java.lang.Object)self.getPlaces(qualifier);
    }

    public void setPlaces(hub.sam.mof.petrinets.Place qualifier, hub.sam.mof.petrinets.PlaceInstance value) {
        self.setPlaces(qualifier, value);
    }

    public cmof.common.ReflectiveCollection<? extends hub.sam.mof.petrinets.Transition> getEnabledTransitions() {
        return (cmof.common.ReflectiveCollection<? extends hub.sam.mof.petrinets.Transition>)(java.lang.Object)self.getEnabledTransitions();
    }

    public void run()  {
        self.run();
    }

    public void metaDelete()  {
        self.metaDelete();
    }

    public hub.sam.mof.petrinets.Net getMetaClassifierNet() {
        return (hub.sam.mof.petrinets.Net)(java.lang.Object)self.getMetaClassifierNet();
    }

    public void setMetaClassifierNet(hub.sam.mof.petrinets.Net value) {
        self.setMetaClassifierNet(value);
    }

}

