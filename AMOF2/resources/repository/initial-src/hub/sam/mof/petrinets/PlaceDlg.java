package hub.sam.mof.petrinets;


public class PlaceDlg extends hub.sam.mof.reflection.ObjectDlg implements Place
{
    protected Place self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (Place)self;
    }

    public int getInitialTokesn() {
        return self.getInitialTokesn();
    }

    public void setInitialTokesn(int value) {
        self.setInitialTokesn(value);
    }

    public hub.sam.mof.petrinets.PlaceInstance metaCreatePlaceInstance()  {
        return (hub.sam.mof.petrinets.PlaceInstance)(java.lang.Object)self.metaCreatePlaceInstance();
    }

    public hub.sam.mof.petrinets.PlaceInstance metaGCreatePlaceInstance(java.lang.String className)  {
        return (hub.sam.mof.petrinets.PlaceInstance)(java.lang.Object)self.metaGCreatePlaceInstance(className);
    }

    public cmof.common.ReflectiveCollection<? extends hub.sam.mof.petrinets.PlaceInstance> getMetaInstancePlaceInstance() {
        return (cmof.common.ReflectiveCollection<? extends hub.sam.mof.petrinets.PlaceInstance>)(java.lang.Object)self.getMetaInstancePlaceInstance();
    }

}

