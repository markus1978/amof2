package hub.sam.mas.execution;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import hub.sam.mas.management.MasContext;
import hub.sam.mof.Repository;
import hub.sam.mof.reflection.ExtentImpl;
import hub.sam.mof.reflection.ImplementationsManager;
import hub.sam.mof.reflection.ImplementationsManagerContainer;
import hub.sam.mof.reflection.ImplementationsManagerImpl;
import hub.sam.mof.runtimelayer.M1SemanticModel;
import hub.sam.mof.runtimelayer.MultiLevelImplementationsManager;
import hub.sam.mof.xmi.Xmi1Reader.XmiKind;

import cmof.Package;
import cmof.cmofFactory;
import cmof.reflection.Extent;
import cmof.reflection.Factory;

@Deprecated
public class MASExecution {
	
	protected Repository repository;
    protected MasContext masContext;
	
	public MASExecution() {
		repository = Repository.getLocalRepository();
        Repository.getConfiguration().setWarnAboutForeignExtentObjectUsage(false);
        // TODO false before
        Repository.getConfiguration().setGenerousXMI(true);
	}
    
    public static void cloneXmiModel(String xmiFile, String clonedXmiFile, Collection<String> forPackages) {
        Repository repository = Repository.getLocalRepository();
        Extent m3Extent = repository.getExtent(Repository.CMOF_EXTENT_NAME);
        Package cmofPackage = (Package) m3Extent.query("Package:cmof");

        Extent modelExtent = repository.createExtent(clonedXmiFile);

        cmofFactory modelFactory = (cmofFactory) repository.createFactory(modelExtent, cmofPackage);

        try {
            repository.loadXmiIntoExtent(modelExtent, cmofPackage, xmiFile, getXmiKind(xmiFile));

//            boolean prefixSet = false;
//            Package petrinetMetaModel = (Package)modelExtent.query("Package:petrinets");
//            for(Tag tag: petrinetMetaModel.getTag()) {
//                if (tag.getName().equals(JavaMapping.PackagePrefixTagName)) {
//                    prefixSet = true;
//                    break;
//                }
//            }
//            
//            if (!prefixSet) {
//                Tag nsPrefixTag = modelFactory.createTag();
//                nsPrefixTag.setName(JavaMapping.PackagePrefixTagName);
//                nsPrefixTag.setValue("hub.sam.mas.model");
//                petrinetMetaModel.getTag().add(nsPrefixTag);
//            }
            
            List<Package> implicitElementsForPackages = new ArrayList<Package>();
            for(String packageName: forPackages) {
                implicitElementsForPackages.add( (Package) modelExtent.query("Package:" + packageName) );
            }
            
            // Creates associations for runtime-instance-of-relationships.
            // These associations are used for creating and deleting runtime instances.
            M1SemanticModel semanticModel = new M1SemanticModel(modelFactory);
            semanticModel.createImplicitElements(implicitElementsForPackages);
                        
            repository.writeExtentToXmi(clonedXmiFile, cmofPackage, modelExtent, getXmiKind(clonedXmiFile));
            repository.deleteExtent(clonedXmiFile);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
    
    private static XmiKind getXmiKind(String xmiFile) {
        if (xmiFile.endsWith(".xml")) {
            return XmiKind.mof;
        }
        else if (xmiFile.endsWith(".mdxml")) {
            return XmiKind.md;
        }
        return null;
    }
	
	public void prepareRun(Extent m1Extent, Factory m1Factory) throws Exception {
        ExecutionEnvironment env = new ExecutionEnvironment(m1Extent,
                masContext.getMasModel().getExtent(), repository);
        
        Extent semanticExtent = masContext.getMasModel().getExtent();
        ((ExtentImpl) semanticExtent).setCustomImplementationsManager(new ImplementationsManagerContainer(
                new ImplementationsManager[] {
                        new MultiLevelImplementationsManager(masContext.getMasModel().getFactory()),
                        new ImplementationsManagerImpl() }
        ));
        
        // MultiLevelImplementationsManager creates runtime instances
        ((ExtentImpl)m1Extent).setCustomImplementationsManager(new ImplementationsManagerContainer(
                new ImplementationsManager[] {
                        new MASImplementationsManager(masContext, env),
                        new MultiLevelImplementationsManager(m1Factory),
                        new ImplementationsManagerImpl() }
        ));               
	}
    
}
