package hub.sam.tools.tests;

import cmof.Package;
import cmof.reflection.Extent;
import hub.sam.mof.Repository;

public class ImportUMLModel {
	public static void main(String[] args) throws Exception {
		Repository repo = Repository.getLocalRepository();
		Package cmofPackage = (Package)repo.getExtent(Repository.CMOF_EXTENT_NAME).query("Package:cmof");
		Extent m2 = repo.createExtent("m2");
		repo.loadMagicDrawXmiIntoExtent(m2, cmofPackage, "resources/models/test/merge-tests.mdxml");
		repo.writeExtentToXmi("resources/models/work/test.xml", cmofPackage, m2);
		
		//Package umlPackage = (Package)m2.query("Package:UML/Package:AuxiliaryConstructs/Package:Models");
		//Extent m1 = repo.createExtent("m1");
		//repo.loadXmiIntoExtent(m1, umlPackage, "resources/models/uml-model-test.xml");		
	}
}
