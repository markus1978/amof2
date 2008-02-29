package hub.sam.mof.test.bugs;

import hub.sam.mof.test.AbstractRepository;
import importmultiplicity.TestClass;
import importmultiplicity.importmultiplicityFactory;
import cmof.Package;
import cmof.reflection.Extent;

public class ImportMultiplicitiesBug extends AbstractRepository {

	private static final String TEST_XML_FILE = "resources/models/work/import-multiplcitiy-test.xml";

	public ImportMultiplicitiesBug() {
		super("ImportMultiplicitiesBug");
	}
	
	private Extent m1Extent = null;
	private Package m2 = null;
	private TestClass myClass = null;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		Extent m2Extent = GenerateImportMultiplicitiesBug.loadMetaModel(repository);
		m2 = (Package)m2Extent.query("Package:importmultiplicity");
		m1Extent = repository.createExtent("extent");
		repository.configureExtent(m1Extent, m2Extent);
		importmultiplicityFactory factory = m1Extent.getAdaptor(importmultiplicityFactory.class);
		myClass = factory.createTestClass();
		
	}
	
	public void test1() throws Exception {
		myClass.getTestProperty().add("eins zwei drei");
		repository.writeExtentToXmi(TEST_XML_FILE,
				m2, m1Extent);
		
		Extent m1TestExtent = repository.createExtent("testExtent");
		repository.loadXmiIntoExtent(m1TestExtent, m2, TEST_XML_FILE);
		TestClass myTestClass = (TestClass)m1TestExtent.getObject().iterator().next();
		assertEquals(myTestClass.getTestProperty().size(), 1);
		assertEquals(myTestClass.getTestProperty().iterator().next(), "eins zwei drei");
	}

	public void test2() throws Exception {
		myClass.getTestProperty().add("eins"); 
		myClass.getTestProperty().add("zwei");
		myClass.getTestProperty().add("drei");
		
		repository.writeExtentToXmi(TEST_XML_FILE,
				m2, m1Extent);
		
		Extent m1TestExtent = repository.createExtent("testExtent");
		repository.loadXmiIntoExtent(m1TestExtent, m2, TEST_XML_FILE);
		TestClass myTestClass = (TestClass)m1TestExtent.getObject().iterator().next();
		assertEquals(myTestClass.getTestProperty().size(), 3);
		assertEquals(myTestClass.getTestProperty().iterator().next(), "eins");
	}
}
