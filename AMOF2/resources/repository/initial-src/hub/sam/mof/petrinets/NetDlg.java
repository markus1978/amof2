package hub.sam.mof.petrinets;


public class NetDlg extends hub.sam.mof.reflection.ObjectDlg implements Net
{
    protected Net self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (Net)self;
    }

    public cmof.common.ReflectiveCollection<? extends hub.sam.mof.petrinets.Place> getPlaces() {
        return (cmof.common.ReflectiveCollection<? extends hub.sam.mof.petrinets.Place>)(java.lang.Object)self.getPlaces();
    }

    public cmof.common.ReflectiveCollection<? extends hub.sam.mof.petrinets.Transition> getTransitions() {
        return (cmof.common.ReflectiveCollection<? extends hub.sam.mof.petrinets.Transition>)(java.lang.Object)self.getTransitions();
    }

    public hub.sam.mof.petrinets.NetInstance instantiate()  {
        return (hub.sam.mof.petrinets.NetInstance)(java.lang.Object)self.instantiate();
    }

    public hub.sam.mof.petrinets.NetInstance metaCreateNetInstance()  {
        return (hub.sam.mof.petrinets.NetInstance)(java.lang.Object)self.metaCreateNetInstance();
    }

    public hub.sam.mof.petrinets.NetInstance metaGCreateNetInstance(java.lang.String className)  {
        return (hub.sam.mof.petrinets.NetInstance)(java.lang.Object)self.metaGCreateNetInstance(className);
    }

    public cmof.common.ReflectiveCollection<? extends hub.sam.mof.petrinets.NetInstance> getMetaInstanceNetInstance() {
        return (cmof.common.ReflectiveCollection<? extends hub.sam.mof.petrinets.NetInstance>)(java.lang.Object)self.getMetaInstanceNetInstance();
    }

}

