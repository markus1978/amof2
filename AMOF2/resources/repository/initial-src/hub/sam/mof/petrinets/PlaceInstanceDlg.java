package hub.sam.mof.petrinets;


public class PlaceInstanceDlg extends hub.sam.mof.reflection.ObjectDlg implements PlaceInstance
{
    protected PlaceInstance self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (PlaceInstance)self;
    }

    public int getTokens() {
        return self.getTokens();
    }

    public void setTokens(int value) {
        self.setTokens(value);
    }

    public void metaDelete()  {
        self.metaDelete();
    }

    public hub.sam.mof.petrinets.Place getMetaClassifierPlace() {
        return (hub.sam.mof.petrinets.Place)(java.lang.Object)self.getMetaClassifierPlace();
    }

    public void setMetaClassifierPlace(hub.sam.mof.petrinets.Place value) {
        self.setMetaClassifierPlace(value);
    }

}

