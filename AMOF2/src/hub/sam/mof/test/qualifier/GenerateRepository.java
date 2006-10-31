package hub.sam.mof.test.qualifier;

import cmof.Package;
import cmof.reflection.Extent;
import hub.sam.mof.Repository;

public class GenerateRepository {

    public static void main(String[] args) throws Exception {
        Repository repository = Repository.getLocalRepository();
        System.out.println("Generate repository");

        Extent cmofExtent = repository.getExtent(Repository.CMOF_EXTENT_NAME);
        cmof.Package cmofPackage = (Package)cmofExtent.query("Package:cmof");

        Extent metaExtent = repository.createExtent("m2");
        repository.loadMagicDrawXmiIntoExtent(metaExtent, cmofPackage, "resources/models/test/qualifier-test.mdxml");

        repository.generateCode(metaExtent, "resources/test/generated-src/");
        repository.generateStaticModel(metaExtent, "qualifier.QualifierModel", "resources/test/generated-src");
    }
}
