package cmof;


public class cmofFactoryImpl extends hub.sam.mof.reflection.FactoryImpl implements cmofFactory {
    public cmofFactoryImpl(hub.sam.mof.reflection.ExtentImpl extent, cmof.Package path) {
        super(extent, path);
    }

    public cmof.Comment createComment() {
        return (cmof.Comment) create("cmof.Comment");
    }

    public cmof.Tag createTag() {
        return (cmof.Tag) create("cmof.Tag");
    }

    public cmof.ElementImport createElementImport() {
        return (cmof.ElementImport) create("cmof.ElementImport");
    }

    public cmof.PackageMerge createPackageMerge() {
        return (cmof.PackageMerge) create("cmof.PackageMerge");
    }

    public cmof.Package createPackage() {
        return (cmof.Package) create("cmof.Package");
    }

    public cmof.PackageImport createPackageImport() {
        return (cmof.PackageImport) create("cmof.PackageImport");
    }

    public cmof.Constraint createConstraint() {
        return (cmof.Constraint) create("cmof.Constraint");
    }

    public cmof.Association createAssociation() {
        return (cmof.Association) create("cmof.Association");
    }

    public cmof.Parameter createParameter() {
        return (cmof.Parameter) create("cmof.Parameter");
    }

    public cmof.Operation createOperation() {
        return (cmof.Operation) create("cmof.Operation");
    }

    public cmof.DataType createDataType() {
        return (cmof.DataType) create("cmof.DataType");
    }

    public cmof.Property createProperty() {
        return (cmof.Property) create("cmof.Property");
    }

    public cmof.UmlClass createUmlClass() {
        return (cmof.UmlClass) create("cmof.Class");
    }

    public cmof.EnumerationLiteral createEnumerationLiteral() {
        return (cmof.EnumerationLiteral) create("cmof.EnumerationLiteral");
    }

    public cmof.Enumeration createEnumeration() {
        return (cmof.Enumeration) create("cmof.Enumeration");
    }

    public cmof.Expression createExpression() {
        return (cmof.Expression) create("cmof.Expression");
    }

    public cmof.PrimitiveType createPrimitiveType() {
        return (cmof.PrimitiveType) create("cmof.PrimitiveType");
    }

    public cmof.OpaqueExpression createOpaqueExpression() {
        return (cmof.OpaqueExpression) create("cmof.OpaqueExpression");
    }

}

