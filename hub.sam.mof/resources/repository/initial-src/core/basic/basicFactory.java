package core.basic;


public interface basicFactory extends cmof.reflection.Factory {

    public core.basic.Property createProperty();

    public core.basic.Package createPackage();

    public core.basic.Parameter createParameter();

    public core.basic.Operation createOperation();

    public core.basic.UmlClass createUmlClass();

    public core.basic.EnumerationLiteral createEnumerationLiteral();

    public core.basic.Enumeration createEnumeration();

    public core.basic.PrimitiveType createPrimitiveType();

}

