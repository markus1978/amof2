package hub.sam.mof.test.bugs;

import propertyNotificationTest.*;
import cmof.Package;
import cmof.reflection.Extent;
import hub.sam.mof.test.AbstractRepository;

import java.util.*;

public class ReflectiveSequenceExtension extends AbstractRepository {

	public ReflectiveSequenceExtension() {
		super("ReflectiveExtension");
	}
	
    String currentPropertyName;
    propertyNotificationTestFactory factory = null;
    
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
    }
    
    @SuppressWarnings("unchecked")
    public void testExtensions() {
        Container container = factory.createContainer();

        Element e = factory.createElement();
    	container.getOrderedContent().add(0,e);
        Element re = container.getOrderedContent().remove(0);
        assertTrue(re == re);
        
        List l = new ArrayList();
        l.add(e);
        assertTrue( container.getOrderedContent().addAll(0, l) );
        l.clear();
        assertTrue( !container.getOrderedContent().addAll(0, l) );
        
        container.getOrderedContent().add(0,e);
        Element e2 = factory.createElement();
        e2.setIdentifier("test2");
        Element e1 = container.getOrderedContent().set(0, e2);
        assertTrue(e == e1);
    }

}
