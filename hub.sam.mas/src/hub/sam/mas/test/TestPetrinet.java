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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
import hub.sam.mof.Repository;
import hub.sam.mof.javamapping.JavaMapping;
import hub.sam.mof.runtimelayer.M1SemanticModel;
import hub.sam.mof.xmi.Xmi1Reader.XmiKind;
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
		return result;
	}
	
	public void run() throws Exception {
        MasXmiFiles xmiFiles = new SimpleMasXmiFiles("resources/models/", "petrinets.masctx");

        // create a new model manager
        GenericMasMofModelManager modelManager = new GenericMasMofModelManager(repository);
        
        // load mas model and mas meta-model
        modelManager.loadMasMetaModelFromXmi( xmiFiles.getSemanticMetaFile() );
        modelManager.loadMasModelFromXmi( xmiFiles.getSemanticFile() );
        
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
        
        // now the model manager is in a legal state and we can create a mas context
        masContext = MasRepository.getInstance().createMasContext(modelManager);
        
        // create a test model
        Extent testExtent = repository.createExtent("test");
        petrinetsFactory testFactory = (petrinetsFactory) repository.createFactory(testExtent, petrinetMetaModel);
        
		prepareRun(testExtent, testFactory);
		
		Net net = createTestModel(testFactory);
		net.instantiate().run();		
	}
    
    public void cloneXmiModel(String xmiFile, String clonedXmiFile, Collection<String> forPackages) {
        Repository repository = Repository.getLocalRepository();
        Extent m3Extent = repository.getExtent(Repository.CMOF_EXTENT_NAME);
        Package cmofPackage = (Package) m3Extent.query("Package:cmof");

        Extent modelExtent = repository.createExtent(clonedXmiFile);

        cmofFactory modelFactory = (cmofFactory) repository.createFactory(modelExtent, cmofPackage);

        try {
            repository.loadXmiIntoExtent(modelExtent, cmofPackage, xmiFile, getXmiKind(xmiFile));

            boolean prefixSet = false;
            Package petrinetMetaModel = (Package)modelExtent.query("Package:petrinets");
            for(Tag tag: petrinetMetaModel.getTag()) {
                if (tag.getName().equals(JavaMapping.PackagePrefixTagName)) {
                    prefixSet = true;
                    break;
                }
            }
            
            if (!prefixSet) {
                Tag nsPrefixTag = modelFactory.createTag();
                nsPrefixTag.setName(JavaMapping.PackagePrefixTagName);
                nsPrefixTag.setValue("hub.sam.mas.model");
                petrinetMetaModel.getTag().add(nsPrefixTag);
            }
            
            List<Package> implicitElementsForPackages = new ArrayList<Package>();
            for(String packageName: forPackages) {
                implicitElementsForPackages.add( (Package) modelExtent.query("Package:" + packageName) );
            }
            
            // Creates associations for runtime-instance-of-relationships.
            // These associations are used for creating and deleting runtime instances.
            M1SemanticModel semanticModel = new M1SemanticModel(modelFactory);
            semanticModel.createImplicitElements(implicitElementsForPackages);
                        
            repository.writeExtentToXmi(clonedXmiFile, cmofPackage, modelExtent, getXmiKind(clonedXmiFile));
            repository.deleteExtent(clonedXmiFile);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
    
    private XmiKind getXmiKind(String xmiFile) {
        if (xmiFile.endsWith(".xml")) {
            return XmiKind.mof;
        }
        else if (xmiFile.endsWith(".mdxml")) {
            return XmiKind.md;
        }
        return null;
    }

	public static void main(String args[]) throws Exception {
		new TestPetrinet().run();
	}
}