package hub.sam.mof.mas;

import hub.sam.mof.Repository;
import hub.sam.mof.mas.layers.M1SemanticModel;
import hub.sam.mof.mas.layers.MultiLevelImplementationsManager;
import hub.sam.mof.reflection.ExtentImpl;
import hub.sam.mof.reflection.ImplementationsManager;
import hub.sam.mof.reflection.ImplementationsManagerContainer;
import hub.sam.mof.reflection.ImplementationsManagerImpl;
import hub.sam.mof.xmi.XmiToCMOF;
import hub.sam.util.AbstractClusterableException;
import hub.sam.util.Clusterable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import cmof.Package;
import cmof.Tag;
import cmof.cmofFactory;
import cmof.reflection.Extent;
import cmof.reflection.Factory;

public class MAS {
	
	protected Repository repository;
	protected Package m3model;
	protected Extent masExtent;
	private Package masModel = null;		
	
	public void initialiseRepository() throws Exception {
		try {
			// initialise repository
			repository = Repository.getLocalRepository();
			repository.getConfiguration().setGenerousXMI(false);
			Extent m3Extent = repository.getExtent(repository.CMOF_EXTENT_NAME);
			m3model = (Package)m3Extent.query("Package:cmof");
			masExtent = repository.createExtent("maseM3");
			repository.loadXmiIntoExtent(masExtent, m3model, "resources/models/mase/bootstrap_merged.xml");
			masModel = (Package)masExtent.query("Package:mas");
			cmofFactory masfactory = (cmofFactory)repository.createFactory(masExtent, m3model);
			Tag nsPrefixTag = masfactory.createTag();
			nsPrefixTag.setName(XmiToCMOF.NsPrefixTagName);
			nsPrefixTag.setValue("Mase");
			masModel.getTag().add(nsPrefixTag);			                    	            
        } catch (Exception e) {
            if (e instanceof Clusterable) {
                AbstractClusterableException.printReport((Clusterable)e, System.err);
                System.exit(1);
            } else {
                throw e;
            }
        }
	}
	
	public void prepareRun(Extent m1Extent, Extent m2Extent, Factory m1Factory, Package m2Model, String[] operationNames) throws Exception {
        ExecutionEnvironment env = new ExecutionEnvironment(m1Extent, m2Extent, repository);
        Map<String, Activity> activities = new HashMap<String,Activity>();
        Repository repository = Repository.getLocalRepository();
        for(String operationName: operationNames) {
        	Extent extent = repository.createExtent(operationName);
        	System.out.println("Loading activity for " + operationName);
   		 	((ExtentImpl)extent).setCustomImplementationsManager(new ImplementationsManagerContainer(
	                new ImplementationsManager[] {
	                                               new MultiLevelImplementationsManager(repository.createFactory(extent, masModel)),
	                                               new ImplementationsManagerImpl() }
	        ));	              
	
        	repository.loadXmiIntoExtent(extent, masModel, operationName + ".asxml");
        	for (Object omc: extent.outermostComposites()) {
        		if (omc instanceof Activity) {        	
        			activities.put(operationName, (Activity)omc);        			
        		}
        	}
        }
        
        ImplementationsManagerContainer implementationsManagerContainer = new ImplementationsManagerContainer(
                new ImplementationsManager[] {new MASImplementationsManager(activities, env),
                                               new MultiLevelImplementationsManager(m1Factory),
                                               new ImplementationsManagerImpl() }
        );
        ((ExtentImpl)m1Extent).setCustomImplementationsManager(implementationsManagerContainer);               
	}
}
