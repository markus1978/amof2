package hub.sam.mof.mas;

import hub.sam.mof.petrinets.Net;
import hub.sam.mof.petrinets.Place;
import hub.sam.mof.petrinets.Transition;
import hub.sam.mof.petrinets.petrinetsFactory;
import cmof.Package;
import cmof.reflection.Extent;

public class Test extends MAS {
	
	private Net createTestModel(petrinetsFactory factory) {
		Net result = factory.createNet();
		Transition t1 = factory.createTransition();
		Place p1 = factory.createPlace();
		p1.setInitialTokesn(1);
		Place p2 = factory.createPlace();
		t1.getInputPlaces().add(p1);
		t1.getOutputPlaces().add(p2);
		result.getTransitions().add(t1);
		result.getPlaces().add(p1);
		result.getPlaces().add(p2);
		return result;
	}
	
	public void run() throws Exception {
		initialiseRepository();
		
		//Extent m2Extent = repository.createExtent("m2test");		
        Package m2Model = (Package)masExtent.query("Package:petrinets");

		
		Extent testExtent = repository.createExtent("test");
		petrinetsFactory testFactory = (petrinetsFactory)repository.createFactory(testExtent, m2Model);
		prepareRun(testExtent, masExtent, testFactory, m2Model, new String[] {"petrinets.NetInstance.run", 
				"petrinets.Transition.fire"});
		
		Net net = createTestModel(testFactory);
		net.instantiate().run();		
	}

	public static void main(String args[]) throws Exception {
		new Test().run();
	}
}
