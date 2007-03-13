package hub.sam.tools.tests;

import java.io.File;

import hub.sam.mof.ant.MergeModel;
import hub.sam.mof.ant.XmiFile;

public class UmlMergeTest {
	
	public static void main(String[] args) {
		MergeModel task = new MergeModel();
		
		XmiFile f1 = null;
		
		f1 = new XmiFile();
		f1.setKey("Infrastructure.cmof");
		f1.setFile(new File("resources/models/uml/Infrastructure.cmof.xml"));
		task.addXmifile(f1);
		
		f1 = new XmiFile();
		f1.setKey("Superstructure.cmof");
		f1.setFile(new File("resources/models/uml/Superstructure.cmof.xml"));
		task.addXmifile(f1);
		
		f1 = new XmiFile();
		f1.setKey("L0.cmof");
		f1.setFile(new File("resources/examples/uml/resources/models/L0.cmof.xml"));
		task.addXmifile(f1);
		
		f1 = new XmiFile();
		f1.setKey("L1.cmof");
		f1.setFile(new File("resources/examples/uml/resources/models/L1.cmof.xml"));
		task.addXmifile(f1);
		
		f1 = new XmiFile();
		f1.setKey("L2.cmof");
		f1.setFile(new File("resources/examples/uml/resources/models/L2.cmof.xml"));
		task.addXmifile(f1);
		
        //task.setPackageToMerge("Package:UML/Package:AuxiliaryConstructs/Package:Profiles");
		task.setPackageToMerge("Package:L2");
        task.setDest(new File("t.xml"));
        
        task.execute();
	}
}
