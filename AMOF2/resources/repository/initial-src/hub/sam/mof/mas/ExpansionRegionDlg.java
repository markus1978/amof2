package hub.sam.mof.mas;


public class ExpansionRegionDlg extends hub.sam.mof.reflection.ObjectDlg implements ExpansionRegion
{
    protected ExpansionRegion self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (ExpansionRegion)self;
    }

    public hub.sam.mof.mas.InExpansionNodeList getInList() {
        return (hub.sam.mof.mas.InExpansionNodeList)(java.lang.Object)self.getInList();
    }

    public void setInList(hub.sam.mof.mas.InExpansionNodeList value) {
        self.setInList(value);
    }

    public hub.sam.mof.mas.OutExpansionNodeList getOutList() {
        return (hub.sam.mof.mas.OutExpansionNodeList)(java.lang.Object)self.getOutList();
    }

    public void setOutList(hub.sam.mof.mas.OutExpansionNodeList value) {
        self.setOutList(value);
    }

    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ExpansionNodeList> getList() {
        return (cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ExpansionNodeList>)(java.lang.Object)self.getList();
    }

    public hub.sam.mof.mas.ExpansionRegionBody getBody() {
        return (hub.sam.mof.mas.ExpansionRegionBody)(java.lang.Object)self.getBody();
    }

    public void setBody(hub.sam.mof.mas.ExpansionRegionBody value) {
        self.setBody(value);
    }

    public hub.sam.mof.mas.ExpansionKind getMode() {
        return (hub.sam.mof.mas.ExpansionKind)(java.lang.Object)self.getMode();
    }

    public void setMode(hub.sam.mof.mas.ExpansionKind value) {
        self.setMode(value);
    }

    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.InExpansionNode> getInputElement() {
        return (cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.InExpansionNode>)(java.lang.Object)self.getInputElement();
    }

    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.OutExpansionNode> getOutputElement() {
        return (cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.OutExpansionNode>)(java.lang.Object)self.getOutputElement();
    }

    public hub.sam.mof.mas.ExpansionRegionInstance metaCreateExpansionRegionInstance()  {
        return (hub.sam.mof.mas.ExpansionRegionInstance)(java.lang.Object)self.metaCreateExpansionRegionInstance();
    }

    public hub.sam.mof.mas.ActivityInstance metaCreateActivityInstance()  {
        return (hub.sam.mof.mas.ActivityInstance)(java.lang.Object)self.metaCreateActivityInstance();
    }

    public hub.sam.mof.petrinets.NetInstance metaCreateNetInstance()  {
        return (hub.sam.mof.petrinets.NetInstance)(java.lang.Object)self.metaCreateNetInstance();
    }

    public hub.sam.mof.mas.ExpansionRegionInstance metaGCreateExpansionRegionInstance(java.lang.String className)  {
        return (hub.sam.mof.mas.ExpansionRegionInstance)(java.lang.Object)self.metaGCreateExpansionRegionInstance(className);
    }

    public hub.sam.mof.mas.ActivityInstance metaGCreateActivityInstance(java.lang.String className)  {
        return (hub.sam.mof.mas.ActivityInstance)(java.lang.Object)self.metaGCreateActivityInstance(className);
    }

    public hub.sam.mof.petrinets.NetInstance metaGCreateNetInstance(java.lang.String className)  {
        return (hub.sam.mof.petrinets.NetInstance)(java.lang.Object)self.metaGCreateNetInstance(className);
    }

    public cmof.common.ReflectiveCollection<? extends hub.sam.mof.mas.ExpansionRegionInstance> getMetaInstanceExpansionRegionInstance() {
        return (cmof.common.ReflectiveCollection<? extends hub.sam.mof.mas.ExpansionRegionInstance>)(java.lang.Object)self.getMetaInstanceExpansionRegionInstance();
    }

    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.PinList> getPinList() {
        return (cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.PinList>)(java.lang.Object)self.getPinList();
    }

    public hub.sam.mof.mas.OutputPinList getOutputList() {
        return (hub.sam.mof.mas.OutputPinList)(java.lang.Object)self.getOutputList();
    }

    public void setOutputList(hub.sam.mof.mas.OutputPinList value) {
        self.setOutputList(value);
    }

    public hub.sam.mof.mas.InputPinList getInputList() {
        return (hub.sam.mof.mas.InputPinList)(java.lang.Object)self.getInputList();
    }

    public void setInputList(hub.sam.mof.mas.InputPinList value) {
        self.setInputList(value);
    }

    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.OutputPin> getOutput() {
        return (cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.OutputPin>)(java.lang.Object)self.getOutput();
    }

    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.InputPin> getInput() {
        return (cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.InputPin>)(java.lang.Object)self.getInput();
    }

    public cmof.Classifier getContext() {
        return (cmof.Classifier)(java.lang.Object)self.getContext();
    }

    public void setContext(cmof.Classifier value) {
        self.setContext(value);
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

    public hub.sam.mof.mas.Activity getActivityAsGroup() {
        return (hub.sam.mof.mas.Activity)(java.lang.Object)self.getActivityAsGroup();
    }

    public void setActivityAsGroup(hub.sam.mof.mas.Activity value) {
        self.setActivityAsGroup(value);
    }

    public cmof.common.ReflectiveCollection<? extends hub.sam.mof.mas.ASTransition> getTransitions() {
        return (cmof.common.ReflectiveCollection<? extends hub.sam.mof.mas.ASTransition>)(java.lang.Object)self.getTransitions();
    }

    public cmof.common.ReflectiveCollection<? extends hub.sam.mof.mas.ASPlace> getPlaces() {
        return (cmof.common.ReflectiveCollection<? extends hub.sam.mof.mas.ASPlace>)(java.lang.Object)self.getPlaces();
    }

    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ValueNode> getArgument() {
        return (cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ValueNode>)(java.lang.Object)self.getArgument();
    }

    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ActivityEdge> getEdge() {
        return (cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ActivityEdge>)(java.lang.Object)self.getEdge();
    }

    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ActivityNode> getNode() {
        return (cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ActivityNode>)(java.lang.Object)self.getNode();
    }

    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ActivityGroup> getGroup() {
        return (cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ActivityGroup>)(java.lang.Object)self.getGroup();
    }

    public cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ActivityChild> getGefChildren() {
        return (cmof.common.ReflectiveSequence<? extends hub.sam.mof.mas.ActivityChild>)(java.lang.Object)self.getGefChildren();
    }

    public hub.sam.mof.mas.ActivityInstance instantiate()  {
        return (hub.sam.mof.mas.ActivityInstance)(java.lang.Object)self.instantiate();
    }

    public cmof.common.ReflectiveCollection<? extends hub.sam.mof.mas.ActivityInstance> getMetaInstanceActivityInstance() {
        return (cmof.common.ReflectiveCollection<? extends hub.sam.mof.mas.ActivityInstance>)(java.lang.Object)self.getMetaInstanceActivityInstance();
    }

    public cmof.common.ReflectiveCollection<? extends hub.sam.mof.petrinets.NetInstance> getMetaInstanceNetInstance() {
        return (cmof.common.ReflectiveCollection<? extends hub.sam.mof.petrinets.NetInstance>)(java.lang.Object)self.getMetaInstanceNetInstance();
    }

    public cmof.common.ReflectiveCollection<? extends cmof.NamedElement> getMember() {
        return (cmof.common.ReflectiveCollection<? extends cmof.NamedElement>)(java.lang.Object)self.getMember();
    }

    public cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> getImportedMember() {
        return (cmof.common.ReflectiveCollection<? extends cmof.PackageableElement>)(java.lang.Object)self.getImportedMember();
    }

    public cmof.common.ReflectiveCollection<? extends cmof.ElementImport> getElementImport() {
        return (cmof.common.ReflectiveCollection<? extends cmof.ElementImport>)(java.lang.Object)self.getElementImport();
    }

    public cmof.common.ReflectiveCollection<? extends cmof.NamedElement> getOwnedMember() {
        return (cmof.common.ReflectiveCollection<? extends cmof.NamedElement>)(java.lang.Object)self.getOwnedMember();
    }

    public cmof.common.ReflectiveCollection<? extends cmof.PackageImport> getPackageImport() {
        return (cmof.common.ReflectiveCollection<? extends cmof.PackageImport>)(java.lang.Object)self.getPackageImport();
    }

    public cmof.common.ReflectiveCollection<? extends cmof.Constraint> getOwnedRule() {
        return (cmof.common.ReflectiveCollection<? extends cmof.Constraint>)(java.lang.Object)self.getOwnedRule();
    }

    public cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> importedMemberOperation()  {
        return (cmof.common.ReflectiveCollection<? extends cmof.PackageableElement>)(java.lang.Object)self.importedMemberOperation();
    }

    public cmof.common.ReflectiveCollection<java.lang.String> getNamesOfMember()  {
        return self.getNamesOfMember();
    }

    public cmof.common.ReflectiveCollection<java.lang.String> getNamesOfMember(core.abstractions.namespaces.NamedElement element)  {
        return self.getNamesOfMember(element);
    }

    public cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> importMembers(cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> imps)  {
        return (cmof.common.ReflectiveCollection<? extends cmof.PackageableElement>)(java.lang.Object)self.importMembers(imps);
    }

    public cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> excludeCollisions(cmof.common.ReflectiveCollection<? extends cmof.PackageableElement> imps)  {
        return (cmof.common.ReflectiveCollection<? extends cmof.PackageableElement>)(java.lang.Object)self.excludeCollisions(imps);
    }

    public boolean membersAreDistinguishable()  {
        return self.membersAreDistinguishable();
    }

    public int getMargin() {
        return self.getMargin();
    }

    public void setMargin(int value) {
        self.setMargin(value);
    }

}

