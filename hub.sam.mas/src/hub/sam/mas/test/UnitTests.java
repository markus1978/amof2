/***********************************************************************
 * MASE -- MOF Action Semantics Editor
 * Copyright (C) 2007 Andreas Blunk
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
import hub.sam.mas.management.IMasContextFile;
import hub.sam.mas.management.MasContext;
import hub.sam.mas.management.MasModelContainer;
import hub.sam.mas.management.MasRepository;
import hub.sam.mas.management.SimpleMasContextFile;
import hub.sam.mas.model.tests.flow.descisionobjectflow.descisionobjectflowFactory;
import hub.sam.mas.model.tests.flow.iterative.TestObject;
import hub.sam.mas.model.tests.flow.iterative.iterativeFactory;
import hub.sam.mas.model.tests.flow.startactionend.startactionendFactory;
import hub.sam.mas.model.tests.flow.startend.startendFactory;
import hub.sam.mof.Repository;
import hub.sam.mof.management.MofModel;
import hub.sam.mof.management.MofModelManager;
import junit.framework.TestCase;

public class UnitTests extends TestCase {

		
	public UnitTests() {
		super("MAS flow tests"); 
	}
	
	private static MofModelManager testManager = null;
	private static Exception initialisationException = null;
	static {
		try {
			Repository repository = Repository.getLocalRepository();
			repository.reset();
			Repository.getConfiguration().setWarnAboutForeignExtentObjectUsage(false);
	        Repository.getConfiguration().setGenerousXMI(true);
	        
	        // load xmi files for syntax and semantic from a mas context file
	        IMasContextFile contextFile = new SimpleMasContextFile("resources/models/MASTest.masctx");
	
	        // create a new mas model container
	        MasModelContainer masModelContainer = new MasModelContainer(repository);
	        
	        // load mas model (semantic)
	        masModelContainer.loadMasModel(contextFile.getMasFile());
	        
	        // load state automaton meta-model (syntax)
	        masModelContainer.loadSyntaxModelForExecution(contextFile.getSyntaxFile(), "Package:tests");
	        masModelContainer.getSyntaxModel().addJavaPackagePrefix("hub.sam.mas.model");
	        masModelContainer.getSyntaxModel().addNsPrefix("mastests");
	        
	        // now we can create a mas context
	        MasContext masContext = MasRepository.getInstance().createMasContext(masModelContainer, contextFile);
	        
	        // create a test model:        
	        // here the mof model manager concept can be used to create a test model
	        // as instance of the syntax model.
	        testManager = new MofModelManager(repository);
	        testManager.setM2Model(masModelContainer.getSyntaxModel());
	        
	        MofModel testModel = testManager.createM1Model("testModel");        
	        
	        // prepares execution and installs implementations managers for activities,
	        // ocl queries and java code (in order).
	        MasExecutionHelper.prepareRun(repository, masContext, testModel);
		} catch (Exception ex) {
			initialisationException = ex;
		}
	}
	
	private MofModel testModel = null;
	
	@Override
	protected void setUp() throws Exception {
		if (initialisationException != null) {
			throw initialisationException;
		}
		testModel = testManager.getM1Model();
		for(cmof.reflection.Object obj: testModel.getExtent().getObject()) {
			obj.delete();
		}
	}

	public void testStartEnd() {
		startendFactory factory = testModel.getExtent().getAdaptor(startendFactory.class);
		hub.sam.mas.model.tests.flow.startend.Main main = factory.createMain();
		try {
			main.testOperation();
		} catch (Exception ex) {
			ex.printStackTrace();
			assertFalse(true);
		}
	}
	
	public void testStartActionEnd() {
		startactionendFactory factory = testModel.getExtent().getAdaptor(startactionendFactory.class);
		hub.sam.mas.model.tests.flow.startactionend.Main main = factory.createMain();
		try {
			main.testOperation();
		} catch (Exception ex) {
			ex.printStackTrace();
			assertFalse(true);
		}
	}
	
	public void testDecisionObjectFlow() {
		descisionobjectflowFactory factory = testModel.getExtent().getAdaptor(descisionobjectflowFactory.class);
		hub.sam.mas.model.tests.flow.descisionobjectflow.Main main = factory.createMain();
		try {
			main.testOperation();
		} catch (Exception ex) {
			ex.printStackTrace();
			assertFalse(true);
		}
	}
	
	public void testIterative() {
		iterativeFactory factory = testModel.getExtent().getAdaptor(iterativeFactory.class);
		hub.sam.mas.model.tests.flow.iterative.Main main = factory.createMain();
		for (int i = 0; i < 10; i++) {
			main.getCollection().add(factory.createTestObject());
		}
		try {
			main.testOperation();			
		} catch (Exception ex) {
			ex.printStackTrace();
			assertFalse(true);
		}
		int count = 0;
		for(TestObject obj: main.getCollection()) {
			assertEquals(1, obj.getValue());
			count++;
		}
		assertEquals(10, count);
	}
}
