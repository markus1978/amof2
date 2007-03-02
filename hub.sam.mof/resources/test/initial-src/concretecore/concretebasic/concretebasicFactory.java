package concretecore.concretebasic;


public interface concretebasicFactory extends cmof.reflection.Factory {

    public concretecore.concretebasic.UmlClass createUmlClass();

    public concretecore.concretebasic.Operation createOperation();

    public concretecore.concretebasic.Parameter createParameter();

    public concretecore.concretebasic.Property createProperty();

    public concretecore.concretebasic.Type createType();

    public concretecore.concretebasic.NamedElement createNamedElement();

    public concretecore.concretebasic.TypedElement createTypedElement();

    public concretecore.concretebasic.Enumeration createEnumeration();

    public concretecore.concretebasic.EnumerationLiteral createEnumerationLiteral();

    public concretecore.concretebasic.PrimitiveType createPrimitiveType();

    public concretecore.concretebasic.Package createPackage();

    public concretecore.concretebasic.DataType createDataType();

}

