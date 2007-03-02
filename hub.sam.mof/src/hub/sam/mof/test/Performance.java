package hub.sam.mof.test;

import hub.sam.mof.*;
import cmof.reflection.*;

public class Performance {

    public static void main(String[] args) throws Exception {
        Repository repository = Repository.getLocalRepository();
        Extent m3Extent = repository.getExtent(Repository.CMOF_EXTENT_NAME);
        cmof.Package m3 = (cmof.Package)m3Extent.query("Package:cmof");        
        for (String name: new String[] {"1","2","3","4", "5", "6", "7", "8"}) {
            Extent m2Extent = repository.createExtent(name);
            repository.loadXmiIntoExtent(m2Extent, m3, "resources/models/bootstrap/bootstrap.xml");        
            repository.generateCode(m2Extent, "test-out", false);
            repository.deleteExtent(name);
        }
    }
}
