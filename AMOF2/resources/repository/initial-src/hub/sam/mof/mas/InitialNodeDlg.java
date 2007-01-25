package hub.sam.mof.mas;


public class InitialNodeDlg extends hub.sam.mof.reflection.ObjectDlg implements InitialNode
{
    protected InitialNode self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (InitialNode)self;
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

    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.AttachedNodeList> getList() {
        return (cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.AttachedNodeList>)(java.lang.Object)self.getList();
    }

    public hub.sam.mof.mas.Activity getActivityAsNode() {
        return (hub.sam.mof.mas.Activity)(java.lang.Object)self.getActivityAsNode();
    }

    public void setActivityAsNode(hub.sam.mof.mas.Activity value) {
        self.setActivityAsNode(value);
    }

    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ActivityEdge> getIncoming() {
        return (cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ActivityEdge>)(java.lang.Object)self.getIncoming();
    }

    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ActivityEdge> getOutgoing() {
        return (cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ActivityEdge>)(java.lang.Object)self.getOutgoing();
    }

    public org.eclipse.draw2d.geometry.Rectangle getRectangle() {
        return self.getRectangle();
    }

    public void setRectangle(org.eclipse.draw2d.geometry.Rectangle value) {
        self.setRectangle(value);
    }

    public java.lang.String getName() {
        return self.getName();
    }

    public void setName(java.lang.String value) {
        self.setName(value);
    }

    public cmof.Namespace getNamespace() {
        return (cmof.Namespace)(java.lang.Object)self.getNamespace();
    }

    public void setNamespace(cmof.Namespace value) {
        self.setNamespace(value);
    }

    public void setNamespace(core.abstractions.namespaces.Namespace value) {
        self.setNamespace(value);
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Tag> getTag() {
        return (cmof.common.ReflectiveCollection<? extends cmof.Tag>)(java.lang.Object)self.getTag();
    }

    public java.lang.String getUri() {
        return self.getUri();
    }

    public void setUri(java.lang.String value) {
        self.setUri(value);
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Element> getOwnedElement() {
        return (cmof.common.ReflectiveCollection<? extends cmof.Element>)(java.lang.Object)self.getOwnedElement();
    }

    public cmof.Element getOwner() {
        return (cmof.Element)(java.lang.Object)self.getOwner();
    }

    public void setOwner(cmof.Element value) {
        self.setOwner(value);
    }

    public void setOwner(core.abstractions.ownerships.Element value) {
        self.setOwner(value);
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Comment> getOwnedComment() {
        return (cmof.common.ReflectiveCollection<? extends cmof.Comment>)(java.lang.Object)self.getOwnedComment();
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> allOwnedElements()  {
        return (cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element>)(java.lang.Object)self.allOwnedElements();
    }

    public boolean mustBeOwned()  {
        return self.mustBeOwned();
    }

    public core.abstractions.visibilities.VisibilityKind getVisibility() {
        return (core.abstractions.visibilities.VisibilityKind)(java.lang.Object)self.getVisibility();
    }

    public void setVisibility(core.abstractions.visibilities.VisibilityKind value) {
        self.setVisibility(value);
    }

    public java.lang.String getQualifiedName() {
        return self.getQualifiedName();
    }

    public void setQualifiedName(java.lang.String value) {
        self.setQualifiedName(value);
    }

    public cmof.common.ReflectiveSequence<? extends core.abstractions.namespaces.Namespace> allNamespaces()  {
        return (cmof.common.ReflectiveSequence<? extends core.abstractions.namespaces.Namespace>)(java.lang.Object)self.allNamespaces();
    }

    public boolean isDistinguishableFrom(core.abstractions.namespaces.NamedElement n, core.abstractions.namespaces.Namespace ns)  {
        return self.isDistinguishableFrom(n, ns);
    }

    public java.lang.String separator()  {
        return self.separator();
    }

    public java.lang.String qualifiedNameOperation()  {
        return self.qualifiedNameOperation();
    }

    public java.lang.String getComment() {
        return self.getComment();
    }

    public void setComment(java.lang.String value) {
        self.setComment(value);
    }

}

