package hub.sam.tools.tests;

import hub.sam.mof.Repository;
import hub.sam.mof.Tools;
import hub.sam.mof.xmi.Xmi2Reader;
import hub.sam.mof.xmi.Xmi1Reader.XmiKind;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import cmof.Property;
import cmof.reflection.Extent;

public class GetVisibilityKindOnUmlModels {

	public static void main(String[] args) throws Exception {
		Repository repository;
		cmof.Package m3;

		repository = Repository.getLocalRepository();
		cmof.reflection.Extent m3Extent = repository.getExtent(Repository.CMOF_EXTENT_NAME);
		m3 = (cmof.Package)m3Extent.query("Package:cmof");

		Extent umlExtent = repository.createExtent("UML");
		Map<String, InputStream> xmiMap = new HashMap<String, InputStream>();
		xmiMap.put("Infrastructure.cmof", new FileInputStream(new File("resources/models/uml/Infrastructure.cmof.xml")));
		xmiMap.put("Superstructure.cmof", new FileInputStream(new File("resources/models/uml/Superstructure.cmof.xml")));

		Xmi2Reader.readMofXmi(xmiMap, umlExtent, m3, XmiKind.mof);
		Tools.setOppositeValues(umlExtent);
		Tools.repairBadOMGUmlMultiplicities(umlExtent);
		
		Property qualifiedName = (Property)umlExtent.query(
				"Package:UML/Package:Classes/Package:Kernel/Class:NamedElement/Property:qualifiedName");
		
		System.out.println(qualifiedName.getVisibility());
	}
}
