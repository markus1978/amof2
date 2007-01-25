package hub.sam.mof.mas;


public class ExpansionRegionInstanceDlg extends hub.sam.mof.reflection.ObjectDlg implements ExpansionRegionInstance
{
    protected ExpansionRegionInstance self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (ExpansionRegionInstance)self;
    }

    public hub.sam.mof.mas.InExpansionNodeInstance getExpansionRegionInput() {
        return (hub.sam.mof.mas.InExpansionNodeInstance)(java.lang.Object)self.getExpansionRegionInput();
    }

    public void setExpansionRegionInput(hub.sam.mof.mas.InExpansionNodeInstance value) {
        self.setExpansionRegionInput(value);
    }

    public hub.sam.mof.mas.ActivityInstance getOuterActivity() {
        return (hub.sam.mof.mas.ActivityInstance)(java.lang.Object)self.getOuterActivity();
    }

    public void setOuterActivity(hub.sam.mof.mas.ActivityInstance value) {
        self.setOuterActivity(value);
    }

    public void metaDelete()  {
        self.metaDelete();
    }

    public hub.sam.mof.mas.ExpansionRegion getMetaClassifierExpansionRegion() {
        return (hub.sam.mof.mas.ExpansionRegion)(java.lang.Object)self.getMetaClassifierExpansionRegion();
    }

    public void setMetaClassifierExpansionRegion(hub.sam.mof.mas.ExpansionRegion value) {
        self.setMetaClassifierExpansionRegion(value);
    }

    public void setMetaClassifierActivity(hub.sam.mof.mas.Activity value) {
        self.setMetaClassifierActivity(value);
    }

    public void setMetaClassifierNet(hub.sam.mof.petrinets.Net value) {
        self.setMetaClassifierNet(value);
    }

    public java.lang.Object getOclContext() {
        return self.getOclContext();
    }

    public void setOclContext(java.lang.Object value) {
        self.setOclContext(value);
    }

    public hub.sam.mof.mas.ExecutionEnvironment getEnv() {
        return self.getEnv();
    }

    public void setEnv(hub.sam.mof.mas.ExecutionEnvironment value) {
        self.setEnv(value);
    }

    public cmof.common.ReflectiveCollection<? extends hub.sam.mof.mas.ExpansionRegionInstance> getInnerActivities() {
        return (cmof.common.ReflectiveCollection<? extends hub.sam.mof.mas.ExpansionRegionInstance>)(java.lang.Object)self.getInnerActivities();
    }

    public hub.sam.mof.mas.Activity getMetaClassifierActivity() {
        return (hub.sam.mof.mas.Activity)(java.lang.Object)self.getMetaClassifierActivity();
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

    public hub.sam.mof.petrinets.Net getMetaClassifierNet() {
        return (hub.sam.mof.petrinets.Net)(java.lang.Object)self.getMetaClassifierNet();
    }

}

