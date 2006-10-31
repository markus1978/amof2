package concretecore.concreteabstractions.concreteliterals;


public interface concreteliteralsFactory extends cmof.reflection.Factory {

    public concretecore.concreteabstractions.concreteliterals.LiteralBoolean createLiteralBoolean();

    public concretecore.concreteabstractions.concreteliterals.LiteralString createLiteralString();

    public concretecore.concreteabstractions.concreteliterals.LiteralSpecification createLiteralSpecification();

    public concretecore.concreteabstractions.concreteliterals.LiteralNull createLiteralNull();

    public concretecore.concreteabstractions.concreteliterals.LiteralInteger createLiteralInteger();

    public concretecore.concreteabstractions.concreteliterals.LiteralUnlimitedNatural createLiteralUnlimitedNatural();

}

