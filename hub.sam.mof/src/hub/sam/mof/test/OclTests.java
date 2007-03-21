package hub.sam.mof.test;

import hub.sam.mof.ocl.OclObjectEnvironment;
import warehouse.WarehouseModel;
import cmof.Package;
import cmof.reflection.Extent;

public class OclTests extends AbstractRepository {

	private Extent m2Extent = null;
	
	public OclTests() {
		super("Ocl tests");	
	}	
	
	@Override
	public void setUp() throws Exception {	
		super.setUp();
		m2Extent = WarehouseModel.createModel();		
		repository.configureExtent(m2Extent, m3Extent);
	}

	public void testSimple() {
		assertEquals(m2Extent.query("Package:warehouse").getAdaptor(OclObjectEnvironment.class).
				execute("self.name"), "warehouse");			
		assertEquals(m2Extent.query("Package:warehouse").getAdaptor(OclObjectEnvironment.class).
					execute("self.ownedType->select(s|s.name = 'Rack')->size()"), 1);		
	}
}
