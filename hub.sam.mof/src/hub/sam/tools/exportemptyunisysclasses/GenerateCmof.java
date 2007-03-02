package hub.sam.tools.exportemptyunisysclasses;

import java.util.Collection;
import java.util.Vector;

import hub.sam.mof.Repository;
import cmof.reflection.Extent;

public class GenerateCmof {

	public static void main(String[] args) throws Exception {
        Repository repository = Repository.getLocalRepository();
        Extent extent = repository.getExtent(Repository.CMOF_EXTENT_NAME);        
     
        Collection<cmof.reflection.Object> elements = new Vector<cmof.reflection.Object>();
        elements.add(extent.query("Package:core"));
        elements.add(extent.query("Package:cmof"));
        Rules.writeUnisysXmi(elements, "resources/models/user/cmof-unisys.xml");        
	}

}
