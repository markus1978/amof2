package hub.sam.mas.execution;

import java.util.Arrays;
import java.util.Collections;

import hub.sam.mof.Repository;
import hub.sam.mof.javamapping.JavaMapping;
import hub.sam.mof.merge.MergeContext;
import hub.sam.mof.runtimelayer.M1SemanticModel;
import cmof.Package;
import cmof.Tag;
import cmof.cmofFactory;
import cmof.reflection.Extent;

public class GenerateASRepository {

    public static void main(String[] args) {
        Repository repository = Repository.getLocalRepository();
        Extent m3Extent = repository.getExtent(Repository.CMOF_EXTENT_NAME);
        Package cmofPackage = (Package) m3Extent.query("Package:cmof");

        Extent m2Extent = repository.createExtent("m2Extent");

        cmofFactory m3Factory = (cmofFactory) repository.createFactory(m2Extent, cmofPackage);

        try {
            repository.loadMagicDrawXmiIntoExtent(m2Extent, cmofPackage, "resources/models/mase/mas.mdxml");                
            Package asPackage = (Package) m2Extent.query("Package:mas");                   
            MergeContext.mergePackages(asPackage, m3Factory, null);
            
            Tag nsPrefix = m3Factory.createTag();
            nsPrefix.setName(JavaMapping.PackagePrefixTagName);
            nsPrefix.setValue("hub.sam.mof");
            ((Package)m2Extent.query("Package:mas")).getTag().add(nsPrefix);
            ((Package)m2Extent.query("Package:petrinets")).getTag().add(nsPrefix);
            
            M1SemanticModel semanticModel = new M1SemanticModel(m3Factory);
            semanticModel.createImplicitElements(Arrays.asList(new Package[] {
            		asPackage,(Package)m2Extent.query("Package:petrinets")}));            
                        
            repository.generateCode(m2Extent, "resources/repository/generated-src", Arrays.asList(
            		new String[]{"mas", "petrinets"}));           
            repository.writeExtentToXmi("resources/models/mase/bootstrap_merged.xml", cmofPackage, m2Extent);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

}
