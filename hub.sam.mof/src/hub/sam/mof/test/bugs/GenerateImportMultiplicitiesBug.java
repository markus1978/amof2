package hub.sam.mof.test.bugs;

import hub.sam.mof.Repository;
import hub.sam.mof.xmi.XmiToCMOF;
import cmof.Package;
import cmof.Tag;
import cmof.cmofFactory;
import cmof.reflection.Extent;

public class GenerateImportMultiplicitiesBug {
	
	public static Extent loadMetaModel(Repository repository) throws Exception {	                  
        Extent cmofExtent = repository.getExtent(Repository.CMOF_EXTENT_NAME);
        Package cmofPackage = (Package)cmofExtent.query("Package:cmof"); 

        Extent metaExtent = repository.createExtent("importMultiplicitiesBug");        
        repository.loadMagicDrawXmiIntoExtent(metaExtent, cmofPackage, "resources/models/test/import-multiplicity-bug.mdxml");        
        addXmiNs(repository, metaExtent);
        return metaExtent;        
	}
	
	public static void addXmiNs(Repository repository, Extent m2Extent) {
		Extent cmofExtent = repository.getExtent(Repository.CMOF_EXTENT_NAME);		
		repository.configureExtent(m2Extent, cmofExtent);
		cmofFactory factory = m2Extent.getAdaptor(cmofFactory.class);
		Tag tag = factory.createTag();
		tag.setName(XmiToCMOF.NsPrefixTagName);
		tag.setValue("im");
		((Package)m2Extent.query("Package:importmultiplicity")).getTag().add(tag);
	}
	
    public static void main(String[] args) throws Exception {                                     
        System.out.println("Generate repository");
        
        Repository repository = Repository.getLocalRepository();
		Extent metaExtent = loadMetaModel(repository);        
        repository.generateCode(metaExtent, "resources/test/generated-src/");        
    }
}
