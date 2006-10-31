package core.abstractions.instances;


public class SlotDlg extends hub.sam.mof.reflection.ObjectDlg implements Slot
{
    protected Slot self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (Slot)self;
    }

    public core.abstractions.instances.InstanceSpecification getOwningInstance() {
        return (core.abstractions.instances.InstanceSpecification)(java.lang.Object)self.getOwningInstance();
    }

    public void setOwningInstance(core.abstractions.instances.InstanceSpecification value) {
        self.setOwningInstance(value);
    }

    public cmof.common.ReflectiveSequence<? extends core.abstractions.expressions.ValueSpecification> getValue() {
        return (cmof.common.ReflectiveSequence<? extends core.abstractions.expressions.ValueSpecification>)(java.lang.Object)self.getValue();
    }

    public core.abstractions.structuralfeatures.StructuralFeature getDefiningFeature() {
        return (core.abstractions.structuralfeatures.StructuralFeature)(java.lang.Object)self.getDefiningFeature();
    }

    public void setDefiningFeature(core.abstractions.structuralfeatures.StructuralFeature value) {
        self.setDefiningFeature(value);
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> getOwnedElement() {
        return (cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element>)(java.lang.Object)self.getOwnedElement();
    }

    public core.abstractions.ownerships.Element getOwner() {
        return (core.abstractions.ownerships.Element)(java.lang.Object)self.getOwner();
    }

    public void setOwner(core.abstractions.ownerships.Element value) {
        self.setOwner(value);
    }

    public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> allOwnedElements()  {
        return (cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element>)(java.lang.Object)self.allOwnedElements();
    }

    public boolean mustBeOwned()  {
        return self.mustBeOwned();
    }

}

