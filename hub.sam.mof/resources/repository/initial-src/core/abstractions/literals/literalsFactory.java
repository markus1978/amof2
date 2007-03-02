package core.abstractions.literals;


public interface literalsFactory extends cmof.reflection.Factory {

    public core.abstractions.literals.LiteralBoolean createLiteralBoolean();

    public core.abstractions.literals.LiteralString createLiteralString();

    public core.abstractions.literals.LiteralNull createLiteralNull();

    public core.abstractions.literals.LiteralInteger createLiteralInteger();

    public core.abstractions.literals.LiteralUnlimitedNatural createLiteralUnlimitedNatural();

}

