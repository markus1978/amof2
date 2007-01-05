package hub.sam.mof.as;

import hub.sam.mof.Repository;
import hub.sam.mof.merge.MergeContext;
import cmof.Package;
import cmof.cmofFactory;
import cmof.reflection.Extent;

public class GenerateRepository {

    public static void main(String[] args) {
        Repository repository = Repository.getLocalRepository();
        Extent m3Extent = repository.getExtent(Repository.CMOF_EXTENT_NAME);
        Package cmofPackage = (Package) m3Extent.query("Package:cmof");

        Extent m2Extent = repository.createExtent("m2Extent");

        cmofFactory m3Factory = (cmofFactory) repository.createFactory(m2Extent, cmofPackage);

        try {
            //repository.loadXmiIntoExtent(m2Extent, cmofPackage, "resources/models/bootstrap.xml");
            repository.loadMagicDrawXmiIntoExtent(m2Extent, cmofPackage, "resources/models/bootstrap.mdxml");
            
            repository.loadMagicDrawXmiIntoExtent(m2Extent, cmofPackage, "resources/models/bootstrap_graphics.mdxml");
            Package m2Package = (Package) m2Extent.query("Package:hub/Package:sam/Package:mase/Package:m2model");
            Package asPackage = (Package) m2Extent.query("Package:as");

            // merge as into hub.sam.mase.m2model
            cmof.PackageMerge merge = m3Factory.createPackageMerge();
            merge.setMergingPackage(m2Package);
            merge.setMergedPackage(asPackage);
            MergeContext.mergePackages(m2Package, m3Factory, null);

            ((cmof.reflection.Object)asPackage).delete();
            repository.generateCode(m2Extent, "generated-src/");
            //repository.generateStaticModel(m2Extent, "hub.sam.mase.StaticM2Model", "generated-src/");
            repository.writeExtentToXmi("resources/models/bootstrap_merged.xml", cmofPackage, m2Extent);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

}
