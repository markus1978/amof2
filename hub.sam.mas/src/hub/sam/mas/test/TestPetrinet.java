/***********************************************************************
 * MAS -- MOF Action Semantics
 * Copyright (C) 2007 Markus Scheidgen, Andreas Blunk
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301  USA
 ***********************************************************************/

package hub.sam.mas.test;

import hub.sam.mas.execution.MasExecutionHelper;
import hub.sam.mas.management.MasContext;
import hub.sam.mas.management.MasModelContainer;
import hub.sam.mas.management.MasRepository;
import hub.sam.mas.management.IMasXmiFiles;
import hub.sam.mas.management.SimpleMasXmiFiles;
import hub.sam.mas.model.petrinets.Net;
import hub.sam.mas.model.petrinets.Place;
import hub.sam.mas.model.petrinets.Transition;
import hub.sam.mas.model.petrinets.petrinetsFactory;
import hub.sam.mof.Repository;
import hub.sam.mof.management.MofModel;
import hub.sam.mof.management.MofModelManager;

public class TestPetrinet {
	
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
		
		Transition t2 = factory.createTransition();
		t2.getInputPlaces().add(p2);
		t2.getOutputPlaces().add(p1);
		result.getTransitions().add(t2);
		return result;
	}
	
	public void run() throws Exception {
        Repository repository = Repository.getLocalRepository();
        Repository.getConfiguration().setWarnAboutForeignExtentObjectUsage(false);
        Repository.getConfiguration().setGenerousXMI(true);
        
        // load xmi files for syntax and semantic from mas context file
        IMasXmiFiles xmiFiles = new SimpleMasXmiFiles("resources/models/", "petrinets.masctx");

        // create a new mas model container
        MasModelContainer masModelContainer = new MasModelContainer(repository);
        
        // load mas model
        masModelContainer.loadMasModel(xmiFiles.getMasFile());
        
        // load syntax model
        masModelContainer.loadSyntaxModelForExecution(xmiFiles.getSyntaxFile(), "Package:petrinets");
        masModelContainer.getSyntaxModel().addJavaPackagePrefix("hub.sam.mas.model");
        
        // now we can create a mas context
        MasContext masContext = MasRepository.getInstance().createMasContext(masModelContainer);
        
        // create a test model:        
        // here the mof model manager concept can be used to easily create a test model
        // as instance of the syntax model.
        MofModelManager testManager = new MofModelManager(repository);
        testManager.setM2Model(masModelContainer.getSyntaxModel());
        MofModel testModel = testManager.createM1Model("test");
        petrinetsFactory testFactory = (petrinetsFactory) testModel.getFactory();
        
        MasExecutionHelper.prepareRun(repository, masContext, testModel);
		
		Net net = createTestModel(testFactory);
		net.instantiate().run();		
	}

	public static void main(String args[]) throws Exception {
		new TestPetrinet().run();
	}
}
