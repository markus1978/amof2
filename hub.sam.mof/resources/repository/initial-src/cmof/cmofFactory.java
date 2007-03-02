package cmof;


public interface cmofFactory extends cmof.reflection.Factory {

    public cmof.Comment createComment();

    public cmof.Tag createTag();

    public cmof.ElementImport createElementImport();

    public cmof.PackageMerge createPackageMerge();

    public cmof.Package createPackage();

    public cmof.PackageImport createPackageImport();

    public cmof.Constraint createConstraint();

    public cmof.Association createAssociation();

    public cmof.Parameter createParameter();

    public cmof.Operation createOperation();

    public cmof.DataType createDataType();

    public cmof.Property createProperty();

    public cmof.UmlClass createUmlClass();

    public cmof.EnumerationLiteral createEnumerationLiteral();

    public cmof.Enumeration createEnumeration();

    public cmof.Expression createExpression();

    public cmof.PrimitiveType createPrimitiveType();

    public cmof.OpaqueExpression createOpaqueExpression();

}

