package hub.sam.mof.as;

import static java.lang.System.out;
import hub.sam.mof.Repository;
import hub.sam.mof.xmi.XmiException;
import hub.sam.util.AbstractClusterableException;
import cmof.Package;
import cmof.reflection.Extent;

public class GenerateXmi2ForASXmi1 {
	public static void main(String[] args) throws Exception {
		out.println("initializing."); 	
		Repository repo = Repository.getLocalRepository();
		Extent m2 = repo.createExtent("work");
		Package m3Package = (Package)repo.getExtent(Repository.CMOF_EXTENT_NAME).query("Package:cmof");
		out.println("loading cmof.");		
		repo.loadXmiIntoExtent(m2, m3Package, "resources/models/bootstrap/bootstrap-wo-as.xml");
		
		out.println("loading as, xmi1.");
		try {
			repo.loadEAXmiIntoExtent(m2, m3Package, "resources/models/bootstrap/as-ea.xml");
		} catch (XmiException exceptions) {
			AbstractClusterableException.printReport(exceptions, out);
			System.exit(1);
		}
		
		out.println("writing xmi2.");
		repo.writeExtentToXmi("resources/models/work/bootstrap-as.xml", m3Package, m2);		
	}
}
