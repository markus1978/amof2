package hub.sam.tools.tests;

import cmof.Package;
import cmof.reflection.Extent;
import hub.sam.mof.Repository;

public class Rebuild {

	public static void main(String args[]) throws Exception {
		Repository repo = Repository.getLocalRepository();
		Extent extent = repo.createExtent("work");
		Package cmofPackage = (Package)repo.getExtent(Repository.CMOF_EXTENT_NAME).query("Package:cmof");
		repo.loadXmiIntoExtent(extent, cmofPackage, "resources/models/test/merge-test.xml");
		repo.writeExtentToXmi("resources/models/work/merge-test-new.xml",cmofPackage, extent);
	}
}
