package core.basic;


public class ParameterDlg extends hub.sam.mof.reflection.ObjectDlg implements Parameter
{
    protected Parameter self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (Parameter)self;
    }

    public core.basic.Operation getOperation() {
        return (core.basic.Operation)(java.lang.Object)self.getOperation();
    }

    public void setOperation(core.basic.Operation value) {
        self.setOperation(value);
    }

    public java.lang.String getName() {
        return self.getName();
    }

    public void setName(java.lang.String value) {
        self.setName(value);
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

    public int lowerBound()  {
        return self.lowerBound();
    }

    public long upperBound()  {
        return self.upperBound();
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

}

