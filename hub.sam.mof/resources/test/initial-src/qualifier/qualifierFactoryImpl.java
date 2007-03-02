package qualifier;


public class qualifierFactoryImpl extends hub.sam.mof.reflection.FactoryImpl implements qualifierFactory {
    public qualifierFactoryImpl(hub.sam.mof.reflection.ExtentImpl extent, cmof.Package path) {
        super(extent, path);
    }

    public qualifier.Source createSource() {
        return (qualifier.Source) create("qualifier.Source");
    }

    public qualifier.Target createTarget() {
        return (qualifier.Target) create("qualifier.Target");
    }

    public qualifier.QualifierType createQualifierType() {
        return (qualifier.QualifierType) create("qualifier.QualifierType");
    }

    public qualifier.ATarget createATarget() {
        return (qualifier.ATarget) create("qualifier.ATarget");
    }

    public qualifier.BTarget createBTarget() {
        return (qualifier.BTarget) create("qualifier.BTarget");
    }

    public qualifier.ASource createASource() {
        return (qualifier.ASource) create("qualifier.ASource");
    }

    public qualifier.AQualifierType createAQualifierType() {
        return (qualifier.AQualifierType) create("qualifier.AQualifierType");
    }

    public qualifier.BQualifierType createBQualifierType() {
        return (qualifier.BQualifierType) create("qualifier.BQualifierType");
    }

    public qualifier.NamedElement createNamedElement() {
        return (qualifier.NamedElement) create("qualifier.NamedElement");
    }

}

