package hub.sam.tools.exportemptyunisysclasses;

import cmof.reflection.*;
import hub.sam.mof.Repository;

public class GenerateInfrastructure {
    
    public static void main(String[] args) throws Exception {
        Repository repository = Repository.getLocalRepository();
        Extent extent = repository.getExtent(Repository.CMOF_EXTENT_NAME);
     
        Rules.writeUnisysXmi(extent.query("Package:core"), "resources/models/user/infrastructure-unisys.xml");
    }
}
