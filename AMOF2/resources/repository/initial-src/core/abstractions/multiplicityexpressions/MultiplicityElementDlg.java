package core.abstractions.multiplicityexpressions;


public class MultiplicityElementDlg extends hub.sam.mof.reflection.ObjectDlg implements MultiplicityElement
{
    protected MultiplicityElement self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (MultiplicityElement)self;
    }

    public int getLower() {
        return self.getLower();
    }

    public void setLower(int value) {
        self.setLower(value);
    }

    public long getUpper() {
        return self.getUpper();
    }

    public void setUpper(long value) {
        self.setUpper(value);
    }

    public core.abstractions.expressions.ValueSpecification getUpperValue() {
        return (core.abstractions.expressions.ValueSpecification)(java.lang.Object)self.getUpperValue();
    }

    public void setUpperValue(core.abstractions.expressions.ValueSpecification value) {
        self.setUpperValue(value);
    }

    public core.abstractions.expressions.ValueSpecification getLowerValue() {
        return (core.abstractions.expressions.ValueSpecification)(java.lang.Object)self.getLowerValue();
    }

    public void setLowerValue(core.abstractions.expressions.ValueSpecification value) {
        self.setLowerValue(value);
    }

    public int lowerOperation()  {
        return self.lowerOperation();
    }

    public long upperOperation()  {
        return self.upperOperation();
    }

    public int lowerBound()  {
        return self.lowerBound();
    }

    public long upperBound()  {
        return self.upperBound();
    }

    public boolean isOrdered() {
        return self.isOrdered();
    }

    public void setIsOrdered(boolean value) {
        self.setIsOrdered(value);
    }

    public boolean isUnique() {
        return self.isUnique();
    }

    public void setIsUnique(boolean value) {
        self.setIsUnique(value);
    }

    public boolean isMultivalued()  {
        return self.isMultivalued();
    }

    public boolean includesCardinality(int C)  {
        return self.includesCardinality(C);
    }

    public boolean includesMultiplicity(core.abstractions.multiplicities.MultiplicityElement M)  {
        return self.includesMultiplicity(M);
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

