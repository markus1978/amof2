package core.abstractions.instances;


public class InstanceValueDlg extends hub.sam.mof.reflection.ObjectDlg implements InstanceValue
{
    protected InstanceValue self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (InstanceValue)self;
    }

    public core.abstractions.instances.InstanceSpecification getInstance() {
        return (core.abstractions.instances.InstanceSpecification)(java.lang.Object)self.getInstance();
    }

    public void setInstance(core.abstractions.instances.InstanceSpecification value) {
        self.setInstance(value);
    }

    public boolean isComputable()  {
        return self.isComputable();
    }

    public int integerValue()  {
        return self.integerValue();
    }

    public boolean booleanValue()  {
        return self.booleanValue();
    }

    public java.lang.String stringValue()  {
        return self.stringValue();
    }

    public long unlimitedValue()  {
        return self.unlimitedValue();
    }

    public boolean isNull()  {
        return self.isNull();
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

