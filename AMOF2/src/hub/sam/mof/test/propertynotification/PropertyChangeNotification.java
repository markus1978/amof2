package hub.sam.mof.test.propertynotification;

import propertyNotificationTest.*;
import cmof.Package;
import cmof.cmofFactory;
import cmof.reflection.Extent;

import hub.sam.mof.test.AbstractRepository;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PropertyChangeNotification extends AbstractRepository
	implements PropertyChangeListener {

	public PropertyChangeNotification() {
		super("PropertyChangeNotification");
	}
	
    String currentPropertyName;
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
        currentPropertyName = "";
    }
    
    private boolean notified = false;
    private Object objectSet;
    private Object objectGot;
    private Object objectHandler;
    
    /**
     * Tests a property with multiplicity 1,1.
     *
     */
    public void testSimpleProperty() {
    	currentPropertyName = "identifier";
        
    	ElementImpl elem = (ElementImpl) factory.createElement();
    	elem.addListener(this);
    	
    	// test setting something
        String test = new String("test");
        objectSet = test;
        objectHandler = elem;
    	elem.setIdentifier(test);
        assertTrue(notified);
        // clean up
        notified = false;    	

        // test setting the same
        elem.setIdentifier("test");
        assertTrue(!notified); // do not notify if nothing changed
        
        ((ElementImpl) elem).delete();
        notified = false;    	
    	currentPropertyName = "";
    }
    
    /**
     * Tests unsetting a property.
     *
     */
    public void xtestUnsetProperty() {
    	currentPropertyName = "container";
        
    	ElementImpl elem = (ElementImpl) factory.createElement();
    	elem.addListener(this);

    	// test
    	elem.setContainer(null);
        assertTrue(!notified); // no notification if never set
    	elem.setContainer(container);
        assertTrue(notified);
    	elem.setContainer(null);
        assertTrue(notified);
        // clean up
        notified = false;
    	currentPropertyName = "";
    }

    /**
     * Runs some tests on an set.
     */
    public void xtestSetProperty() {
    	ElementImpl elem;
    	currentPropertyName = "content";
    	((ContainerImpl) container).addListener(this);
        
    	elem = (ElementImpl) factory.createElement();

    	// adding an element
    	container.getContent().add(elem);
        assertTrue(notified);
        // clean up
        notified = false;

        // adding the same element again
        container.getContent().add(elem);
        assertTrue(!notified); // do not notify!
        // clean up
        notified = false;
        
        // remove the element
        container.getContent().remove(elem);
        assertTrue(notified);
        // clean up
        notified = false;

        // remove the element again
        container.getContent().remove(elem);
        assertTrue(!notified); // do not notify!
        // clean up
        notified = false;

        ((ElementImpl) elem).delete();

        // bring up a list of elements
        java.util.List<Element> l = new java.util.ArrayList<Element>();
        for(int i=0; i<3; i++) {
        	elem = (ElementImpl) factory.createElement();
        	l.add(elem);
        }
        
        // test adding them all
        container.getContent().addAll(l);
        assertTrue(notified);
        // clean up
        notified = false;

        // test adding them all again
        container.getContent().addAll(l);
        assertTrue(!notified); // do not notify!
        // clean up
        notified = false;

        // bring up a subset
        java.util.List<Element> lsub = new java.util.ArrayList<Element>();
       	lsub.add(l.get(0));

        // test adding a subset of them all again
       	container.getContent().addAll(lsub);
        assertTrue(!notified); // do not notify!
        // clean up
        notified = false;

        // test removing them all
        container.getContent().removeAll(l);
        assertTrue(notified);
        // clean up
        notified = false;

        // test removing them all again
        container.getContent().removeAll(l);
        assertTrue(!notified); // do not notify!
        // clean up
        notified = false;

        // delete the elements in the list
        for(int i=2; i>=0; i--) {
        	elem = (ElementImpl) l.get(i);
        	((ElementImpl) elem).delete();
        	l.remove(i);

        }

        // test clear on a collection with one element
    	elem = (ElementImpl) factory.createElement();
    	container.getContent().add(elem);
    	container.getContent().clear();
        assertTrue(notified);
        // clean up
        notified = false;
        
        // test clear on an empty collection
        container.getContent().clear();
        assertTrue(!notified); // do not notify!
        // clean up
        notified = false;
       
    	currentPropertyName = "";
    }

    /**
     * Runs some tests on an list.
     */
    public void xtestListProperty() {
    	ElementImpl elem;
    	currentPropertyName = "orderedContent";
    	((ContainerImpl) container).addListener(this);
        
    	elem = (ElementImpl) factory.createElement();

    	// adding an element
    	container.getOrderedContent().add(0,elem);
        assertTrue(notified);
        // clean up
        notified = false;

        // setting an element at a position
        Element elem2 = factory.createElement();
        container.getOrderedContent().set(0,elem2);
        assertTrue(notified);
        // clean up
        notified = false;
        
        // TODO: not sure if adding the same element at the same position
        //       should be tested

        // remove the element
        container.getOrderedContent().remove(0);
        assertTrue(notified);
        // clean up
        notified = false;

        ((ElementImpl) elem).delete();

        // bring up a list of elements
        java.util.List<Element> l = new java.util.ArrayList<Element>();
        for(int i=0; i<3; i++) {
        	elem = (ElementImpl) factory.createElement();
        	l.add(elem);
        }
        
        // test adding them all
        container.getOrderedContent().addAll(0,l);
        assertTrue(notified);
        // clean up
        notified = false;
        
        // TODO: not sure if adding them all again should be tested
      
    	currentPropertyName = "";
    }
    
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getPropertyName().equals(currentPropertyName)) {
			notified = true;
            if (currentPropertyName.equals("identifier")) {
                objectGot = ((Element) objectHandler).getIdentifier();
                assertSame(objectSet, objectGot);
            }
		}
	}

}
