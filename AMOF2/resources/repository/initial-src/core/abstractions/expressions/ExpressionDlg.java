package core.abstractions.expressions;


public class ExpressionDlg extends hub.sam.mof.reflection.ObjectDlg implements Expression
{
    protected Expression self = null;
    @Override
    protected void setSelf(cmof.reflection.Object self) {
        super.setSelf(self);
        this.self = (Expression)self;
    }

    public java.lang.String getSymbol() {
        return self.getSymbol();
    }

    public void setSymbol(java.lang.String value) {
        self.setSymbol(value);
    }

    public cmof.common.ReflectiveSequence<? extends core.abstractions.expressions.ValueSpecification> getOperand() {
        return (cmof.common.ReflectiveSequence<? extends core.abstractions.expressions.ValueSpecification>)(java.lang.Object)self.getOperand();
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

