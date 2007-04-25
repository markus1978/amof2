package hub.sam.mof.test;

import hub.sam.mof.ocl.OclImplementationsManager;
import hub.sam.mof.reflection.ExtentImpl;
import ocl.NamedElement;
import ocl.Namespace;
import ocl.oclFactory;
import qualifier.QualifierModel;
import cmof.reflection.Extent;

public class OclImplementations extends AbstractRepository {

    public OclImplementations() {
        super("ocl implementations test");
    }

    private oclFactory oclFactory = null;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        Extent m2Extent = QualifierModel.createModel();
        Extent m1Extent = repository.createExtent("m1Extent", m2Extent);
        oclFactory = m1Extent.getAdaptor(oclFactory.class);                
        ((ExtentImpl)m1Extent).setCustomImplementationsManager(new OclImplementationsManager());
    }

    public void testDerivedAttribute() throws Exception {
    	Namespace oclNamespace = oclFactory.createNamespace();
    	oclNamespace.setName("ns");    	
        NamedElement oclNamedElement = oclFactory.createNamedElement();
        oclNamedElement.setName("test");
        oclNamespace.getMember().add(oclNamedElement);

        assertEquals("ns", oclNamespace.getQualifiedName());
        assertEquals("ns::test", oclNamedElement.getQualifiedName());
    }
    
    public void testOperation() throws Exception {
    	Namespace oclNamespace = oclFactory.createNamespace();
    	oclNamespace.setName("ns");    	
        NamedElement oclNamedElement = oclFactory.createNamedElement();
        oclNamedElement.setName("test");
        oclNamespace.getMember().add(oclNamedElement);

        assertEquals("ns", oclNamespace.calculateFullName());
        assertEquals("ns::test", oclNamedElement.calculateFullName());
    }
    
    public void testOperationWithSeparator() throws Exception {
    	Namespace oclNamespace = oclFactory.createNamespace();
    	oclNamespace.setName("ns");    	
        NamedElement oclNamedElement = oclFactory.createNamedElement();
        oclNamedElement.setName("test");
        oclNamespace.getMember().add(oclNamedElement);

        assertEquals("ns", oclNamespace.calculateFullName("::"));
        assertEquals("ns::test", oclNamedElement.calculateFullName("::"));
    }
}
