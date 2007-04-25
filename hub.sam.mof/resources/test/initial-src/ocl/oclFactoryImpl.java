package ocl;


public class oclFactoryImpl extends hub.sam.mof.reflection.FactoryImpl implements oclFactory {
    public oclFactoryImpl(hub.sam.mof.reflection.ExtentImpl extent, cmof.Package path) {
        super(extent, path);
    }

    public ocl.Namespace createNamespace() {
        return (ocl.Namespace) create("ocl.Namespace");
    }

    public ocl.NamedElement createNamedElement() {
        return (ocl.NamedElement) create("ocl.NamedElement");
    }

}

