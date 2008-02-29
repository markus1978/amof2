package hub.sam.tools.tests;

import hub.sam.mof.Repository;
import cmof.Package;
import cmof.reflection.Extent;

public class ImportAutomaton {

	private static final String fileName = "resources/models/test/automaton-ea.xml";
	
	/**
	 * @param args
	 * @throws Exception 
	 */	
	public static void main(String[] args) throws Exception {
		
		//ClientRepository repo = Repository.connectToRemoteRepository("jnp://localhost:1099");
		//ClientRepository repo = Repository.connectToLocalRepository();
		Repository repo = Repository.getLocalRepository();
		if (repo.getExtent(fileName) != null) {
			System.out.println(repo.getExtent(fileName));
			repo.deleteExtent(fileName);
		}	
		try {
			Extent ext = repo.createExtent(fileName);
			repo.loadXmiIntoExtent(ext, (Package)repo.getExtent(Repository.CMOF_EXTENT_NAME).query("Package:cmof"), 
					"resources/models/user/core.xml");			
			repo.loadEAXmiIntoExtent(ext, (Package)repo.getExtent(Repository.CMOF_EXTENT_NAME).query("Package:cmof"), 
					fileName);
			/*
			for (cmof.reflection.Object o: ext.getObject()) {
				if (o.container() == null) {
					System.out.println("§§§");
					if (o instanceof NamedElement) {
						System.out.println("!!!" + ((NamedElement)o).getQualifiedName());
					}
				}
			}
			*/
			repo.generateCode(ext, "test/automaton/src", false);
			repo.writeExtentToXmi("resources/models/test/automaton.xml",(Package)repo.getExtent(Repository.CMOF_EXTENT_NAME).query("Package:cmof"), ext);
		} catch (Exception e) {
			throw e;
		}
		
		/*
		repo.loadUnisysXmiIntoExtent(automatonM2, (Package)repo.getExtent(Repository.CMOF_EXTENT_NAME).
				query("Package:cmof"),"resources/models/automaton-test.xml");
		repo.writeExtentToXmi("resources/models/automaton.xml", (Package)repo.getExtent(Repository.CMOF_EXTENT_NAME).
				query("Package:cmof"), automatonM2);
        */
	}
}
