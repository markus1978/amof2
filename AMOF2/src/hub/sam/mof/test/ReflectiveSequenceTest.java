package hub.sam.mof.test;

import propertyNotificationTest.*;
import cmof.Package;
import cmof.reflection.Extent;

public class ReflectiveSequenceTest extends AbstractRepository {

	public ReflectiveSequenceTest() {
		super("ReflectiveSequenceTest");
	}
	
    propertyNotificationTestFactory factory = null;
    Container container;
    
    @Override
	public void setUp() throws Exception {
        super.setUp();
        
		Extent metaExtent = repository.createExtent("metaExtent");

		try {
			repository.loadXmiIntoExtent(metaExtent, m3, "resources/models/test/property-notification-test.xml");

	        Package testPackage = (Package)metaExtent.query("Package:propertyNotificationTest");
			
			Extent modelExtent = repository.createExtent("modelExtent");	
			factory = (propertyNotificationTestFactory)repository.createFactory(modelExtent, testPackage);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
        
        container = factory.createContainer();
    }
    
    /**
     * testing add and remove
     *
     */
    public void test() {
        Element e0 = factory.createElement();
        Element e1 = factory.createElement();

        container.getOrderedContent().add(e0); // e0
        container.getOrderedContent().add(e1); // e0, e1
        
        container.getOrderedContent().remove(e1); // e0
        container.getOrderedContent().add(0,e1);  // e1, e0

        // next line results in infinite loop
        container.getOrderedContent().remove(e0); // e1
        container.getOrderedContent().add(0,e0);  // e0, e1
    }

}
