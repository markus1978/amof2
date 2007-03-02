package qualifier;


public interface qualifierFactory extends cmof.reflection.Factory {

    public qualifier.Source createSource();

    public qualifier.Target createTarget();

    public qualifier.QualifierType createQualifierType();

    public qualifier.ATarget createATarget();

    public qualifier.BTarget createBTarget();

    public qualifier.ASource createASource();

    public qualifier.AQualifierType createAQualifierType();

    public qualifier.BQualifierType createBQualifierType();

    public qualifier.NamedElement createNamedElement();

}

