package umltest;

import hub.sam.mof.Repository;
import cmof.reflection.Extent;
import junit.framework.TestCase;

public class ReadASampleModel extends TestCase {

    public ReadASampleModel() {
        super("UML");
    }

    public void testUML() throws Exception {
        main(new String[] {});
    }

    public static void main(String[] args) throws Exception {
        Repository repo = Repository.getLocalRepository();
        Extent cmofExtent = repo.getExtent(Repository.CMOF_EXTENT_NAME);
        cmof.Package cmofPackage = (cmof.Package)cmofExtent.query("Package:cmof");

        Extent umlExtent = repo.createExtent("uml");
        repo.loadXmiIntoExtent(umlExtent, cmofPackage, "resources/models/CD.merged.cmof.xml");
        cmof.Package umlPackage = (cmof.Package)umlExtent.query("Package:uml");

        Extent modelExtent = repo.createExtent("model");
        repo.loadXmiIntoExtent(modelExtent, umlPackage, "resources/models/TestModel.mdxml");
    }
}
