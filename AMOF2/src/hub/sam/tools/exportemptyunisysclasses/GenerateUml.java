package hub.sam.tools.exportemptyunisysclasses;

import java.util.Collection;
import java.util.Vector;

import cmof.reflection.*;
import hub.sam.mof.Repository;

public class GenerateUml {
    
    public static void main(String[] args) throws Exception {
        Repository repository = Repository.getLocalRepository();
        Extent extent = repository.createExtent("work");
        repository.loadXmiIntoExtent(extent,(cmof.Package)repository.getExtent(Repository.CMOF_EXTENT_NAME).query("Package:cmof"), "resources/models/uml/uml.xml");
     
        Collection<cmof.reflection.Object> elements = new Vector<cmof.reflection.Object>();
        elements.add(extent.query("Package:InfrastructureLibrary"));
        elements.add(extent.query("Package:UML"));
        Rules.writeUnisysXmi(elements, "resources/models/user/uml-unisys.xml");
    }
}
