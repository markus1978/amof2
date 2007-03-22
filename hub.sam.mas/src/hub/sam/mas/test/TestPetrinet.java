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

import hub.sam.mas.execution.MASExecution;
import hub.sam.mas.management.GenericMasMofModelManager;
import hub.sam.mas.management.MasRepository;
import hub.sam.mas.management.MasXmiFiles;
import hub.sam.mas.management.MofModel;
import hub.sam.mas.management.SimpleMasXmiFiles;
import hub.sam.mas.model.petrinets.Net;
import hub.sam.mas.model.petrinets.Place;
import hub.sam.mas.model.petrinets.Transition;
import hub.sam.mas.model.petrinets.petrinetsFactory;
import hub.sam.mof.javamapping.JavaMapping;

import java.util.Arrays;

import cmof.Package;
import cmof.Tag;
import cmof.cmofFactory;
import cmof.reflection.Extent;

public class TestPetrinet extends MASExecution {
	
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
        MasXmiFiles xmiFiles = new SimpleMasXmiFiles("resources/models/", "petrinets.masctx");

        // create a new model manager
        GenericMasMofModelManager modelManager = new GenericMasMofModelManager(repository);
        
        // load mas model and mas meta-model
        modelManager.loadMasMetaModelFromXmi( xmiFiles.getMasMetaFile() );
        modelManager.loadMasModelFromXmi( xmiFiles.getMasFile() );
        
        {
        MofModel masMetaModel = modelManager.getMasModel().getMetaModel();
        masMetaModel.setPackage( (Package) masMetaModel.getExtent().query("Package:mas") );
        }
        
        // set syntax meta-model
        modelManager.setSyntaxMetaModel( modelManager.getCmofModel() );
        
        // clone syntax model
        String clonedSyntaxFile = xmiFiles.getSyntaxFile() + "_cloned.xml";
        cloneXmiModel(xmiFiles.getSyntaxFile(), clonedSyntaxFile, Arrays.asList(new String[] {"petrinets"}));
        
        // set syntax model
        modelManager.loadSyntaxModelFromXmi(clonedSyntaxFile);
        Package petrinetMetaModel = (Package) modelManager.getSyntaxModel().getExtent().query("Package:petrinets");
        
        Tag nsPrefixTag = ((cmofFactory) modelManager.getSyntaxModel().getFactory()).createTag();
        nsPrefixTag.setName(JavaMapping.PackagePrefixTagName);
        nsPrefixTag.setValue("hub.sam.mas.model");
        petrinetMetaModel.getTag().add(nsPrefixTag);
        
        // now the model manager is in a legal state and we can create a mas context
        masContext = MasRepository.getInstance().createMasContext(modelManager);
        
        // create a test model
        Extent testExtent = repository.createExtent("test");
        petrinetsFactory testFactory = (petrinetsFactory) repository.createFactory(testExtent, petrinetMetaModel);
        
		prepareRun(testExtent, testFactory);
		
		Net net = createTestModel(testFactory);
		net.instantiate().run();		
	}

	public static void main(String args[]) throws Exception {
		new TestPetrinet().run();
	}
}
