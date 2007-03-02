package hub.sam.tools.tests;

import cmof.Package;
import cmof.reflection.Extent;

import hub.sam.mof.Repository;
import hub.sam.mof.codegeneration.GenerationException;
import hub.sam.mof.xmi.XmiException;
import hub.sam.util.AbstractClusterableException;

import static java.lang.System.out;

public class GenerateCodeForSdl {

	public static void main(String[] args) throws Exception {
		out.println("initializing.");	
		Repository repo = Repository.getLocalRepository();
		Extent m2 = repo.createExtent("sdl");
		Package m3Package = (Package)repo.getExtent(Repository.CMOF_EXTENT_NAME).query("Package:cmof");
		out.println("loading sdl.");		
		repo.loadXmiIntoExtent(m2, m3Package, "resources/models/other/sdl.xml");
						
		out.println("generating:");
		try {
			repo.generateCode(m2, "resources/sdl-src/");
		} catch (GenerationException ex) {
			AbstractClusterableException.printReport(ex, System.out);
		}
	}
}
