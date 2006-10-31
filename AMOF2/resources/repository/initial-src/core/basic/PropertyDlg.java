package core.basic;


public class PropertyDlg extends hub.sam.mof.reflection.ObjectDlg implements Property
{
    protected Property self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (Property)self;
    }

    public boolean isReadOnly() {
        return self.isReadOnly();
    }

    public void setIsReadOnly(boolean value) {
        self.setIsReadOnly(value);
    }

    public java.lang.String getDefault() {
        return self.getDefault();
    }

    public void setDefault(java.lang.String value) {
        self.setDefault(value);
    }

    public boolean isComposite() {
        return self.isComposite();
    }

    public void setIsComposite(boolean value) {
        self.setIsComposite(value);
    }

    public boolean isDerived() {
        return self.isDerived();
    }

    public void setIsDerived(boolean value) {
        self.setIsDerived(value);
    }

    public core.basic.UmlClass getUmlClass() {
        return (core.basic.UmlClass)(java.lang.Object)self.getUmlClass();
    }

    public void setUmlClass(core.basic.UmlClass value) {
        self.setUmlClass(value);
    }

    public core.basic.Property getOpposite() {
        return (core.basic.Property)(java.lang.Object)self.getOpposite();
    }

    public void setOpposite(core.basic.Property value) {
        self.setOpposite(value);
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

