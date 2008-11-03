package hub.sam.mof.emf;

import hub.sam.mof.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import cmof.Package;
import cmof.Type;
import cmof.reflection.Extent;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

public class EcoreGeneratorTest {

	public static void main(String[] args) throws Exception {
		ResourceSet resourceSet = new ResourceSetImpl();

		// Register the default resource factory -- only needed for stand-alone!
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());

		// Get the URI of the model file.
		URI fileURI = URI.createFileURI(new File("SDL.ecore")
				.getAbsolutePath());

		// Create a resource for this file.
		Resource resource = resourceSet.createResource(fileURI);		
		EcoreGenerator generator = new EcoreGenerator(resource);
		
		Extent extent = Repository.getLocalRepository().createExtent("myExtent");
		Repository.getLocalRepository().loadMagicDrawXmiIntoExtent(extent, (Package)Repository.getLocalRepository().getExtent(Repository.CMOF_EXTENT_NAME).query("Package:cmof"),
				"models/sdl.mdxml");
		
		List<Package> mofPackages = new Vector<Package>();
		Package infraPackage = (Package)extent.query("Package:InfrastructureLibrary");	
		Package patternPackage = (Package)extent.query("Package:Pattern");		
		Package sdlPackage = (Package)extent.query("Package:SDL");
		mofPackages.add(infraPackage);
		mofPackages.add(patternPackage);
		mofPackages.add(sdlPackage);	
		
		generator.generateEcoreModel(mofPackages);
		
		EPackage eSdlPackage = null;
		for(EObject aObject: resource.getContents()) {
			if (aObject instanceof EPackage) {
				Simplificator.simplify((EPackage)aObject);
				if (((EPackage)aObject).getName().equals("SDL")) {
					eSdlPackage = (EPackage)aObject;
				}
			}
		}	
		
		List<EObject> objectsToMove = new ArrayList<EObject>();
		List<EPackage> packagesToRemove = new ArrayList<EPackage>();
		for(EObject aObject: resource.getContents()) {
			if (aObject instanceof EPackage) {		
				if (!((EPackage)aObject).getName().equals("SDL")) {
					objectsToMove.addAll(((EPackage)aObject).eContents());
					packagesToRemove.add((EPackage)aObject);
				}
			}
		}		
		
		for(EObject contents: objectsToMove) {		
			if (contents instanceof EClassifier) {
				eSdlPackage.getEClassifiers().add((EClassifier)contents);
			} else {
				System.out.println("lost: " + contents);
			}
		}
		
		for(EPackage packageToRemove: packagesToRemove) {
			resource.getContents().remove(packageToRemove);
		}
			
		List<EClassifier> classifiers = new ArrayList<EClassifier>();
		classifiers.addAll(eSdlPackage.getEClassifiers());
		Collections.sort(classifiers, new Comparator<EClassifier>() {
			public int compare(EClassifier o1, EClassifier o2) {
				return o1.getName().compareTo(o2.getName());
			}			
		});
		eSdlPackage.getEClassifiers().clear();
		eSdlPackage.getEClassifiers().addAll(classifiers);
		eSdlPackage.setName("sdl");
		

		// Save the contents of the resource to the file system.
		try {
			resource.save(Collections.EMPTY_MAP);
		} catch (IOException e) {
		}
	}
}
