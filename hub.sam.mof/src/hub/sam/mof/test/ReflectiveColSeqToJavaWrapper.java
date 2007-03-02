package hub.sam.mof.test;

import propertyNotificationTest.*;
import cmof.Package;
import cmof.reflection.Extent;
import java.util.*;

public class ReflectiveColSeqToJavaWrapper extends AbstractRepository {

	public ReflectiveColSeqToJavaWrapper() {
		super("ReflectiveColSeqToJavaWrapper");
	}
	
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
    public void testCollectionWrapper() {
        Container container = factory.createContainer();
        Element e = factory.createElement();
        hub.sam.mof.util.CollectionWrapper wrapper = new hub.sam.mof.util.CollectionWrapper(container.getContent());

        assertTrue( wrapper.add(e) );
        assertFalse( wrapper.isEmpty() );
        
        Collection<Element> c = new HashSet<Element>();
        c.add(e);
        wrapper.clear();
        assertTrue( wrapper.addAll(c) );
    }

    @SuppressWarnings("unchecked")
    public void testListWrapper() {
        Container container = factory.createContainer();
        Element e = factory.createElement();
        hub.sam.mof.util.ListWrapper wrapper = new hub.sam.mof.util.ListWrapper(container.getOrderedContent());

        wrapper.add(0,e);
        assertFalse( wrapper.isEmpty() );
        
        /*Collection<Element> c = new HashSet<Element>();
        c.add(e);
        wrapper.clear();
        assertTrue( wrapper.addAll(c) );*/
    }

}
