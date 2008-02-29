package importmultiplicity;


public class importmultiplicityFactoryImpl extends hub.sam.mof.reflection.FactoryImpl implements importmultiplicityFactory {
    public importmultiplicityFactoryImpl(hub.sam.mof.reflection.ExtentImpl extent, cmof.Package path) {
        super(extent, path);
    }

    public importmultiplicity.TestClass createTestClass() {
        return (importmultiplicity.TestClass) create("importmultiplicity.TestClass");
    }

}

