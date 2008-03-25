package hub.sam.mof.test.bugs;

import hub.sam.mof.Repository;
import hub.sam.mof.Tools;
import hub.sam.mof.xmi.Xmi2Reader;
import hub.sam.mof.xmi.Xmi1Reader.XmiKind;
import cmof.Package;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import cmof.reflection.Extent;

public class LoadUml {
	public static void main(String[] args) throws Exception {
		Repository repository = Repository.getLocalRepository();

		Extent cmofExtent = repository.getExtent(Repository.CMOF_EXTENT_NAME);
		Package cmofPackage = (Package)cmofExtent.query("Package:cmof");

		Map<String, InputStream> xmis = new HashMap<String, InputStream>();

		xmis.put("Infrastructure.cmof", new FileInputStream("resources/models/uml/Infrastructure.cmof.xml"));
		xmis.put("Superstructure.cmof", new FileInputStream("resources/models/uml/Superstructure.cmof.xml"));

		Extent umlMetaExtent = repository.createExtent("UML2");

		System.out.println("reading cmof's ...");
		Xmi2Reader.readMofXmi(xmis, umlMetaExtent, cmofPackage, XmiKind.mof);

		Tools.setOppositeValues(umlMetaExtent);		
		
		System.out.println("fin");
	}
}
