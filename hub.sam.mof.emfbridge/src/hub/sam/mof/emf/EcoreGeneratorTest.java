package hub.sam.mof.emf;

import hub.sam.mof.Repository;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import cmof.Package;
import cmof.reflection.Extent;

import org.eclipse.emf.common.util.URI;
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
		URI fileURI = URI.createFileURI(new File("test.ecore")
				.getAbsolutePath());

		// Create a resource for this file.
		Resource resource = resourceSet.createResource(fileURI);		
		EcoreGenerator generator = new EcoreGenerator(resource);
		
		Extent extent = Repository.getLocalRepository().createExtent("myExtent");
		Repository.getLocalRepository().loadMagicDrawXmiIntoExtent(extent, (Package)Repository.getLocalRepository().getExtent(Repository.CMOF_EXTENT_NAME).query("Package:cmof"),
				"models/sdl.mdxml");
		
		List<Package> mofPackages = new Vector<Package>();
		mofPackages.add((Package)extent.query("Package:InfrastructureLibrary"));
		mofPackages.add((Package)extent.query("Package:Pattern"));
		mofPackages.add((Package)extent.query("Package:SDL"));
		
		generator.generateEcorModel(mofPackages);

		// Save the contents of the resource to the file system.
		try {
			resource.save(Collections.EMPTY_MAP);
		} catch (IOException e) {
		}
	}
}
