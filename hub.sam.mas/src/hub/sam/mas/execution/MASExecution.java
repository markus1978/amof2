package hub.sam.mas.execution;

import hub.sam.mas.management.MasContext;
import hub.sam.mof.Repository;
import hub.sam.mof.reflection.ExtentImpl;
import hub.sam.mof.reflection.ImplementationsManager;
import hub.sam.mof.reflection.ImplementationsManagerContainer;
import hub.sam.mof.reflection.ImplementationsManagerImpl;
import hub.sam.mof.runtimelayer.MultiLevelImplementationsManager;

import cmof.reflection.Extent;
import cmof.reflection.Factory;

public class MASExecution {
	
	protected Repository repository;
    protected MasContext masContext;
	
	public MASExecution() {
		repository = Repository.getLocalRepository();
        Repository.getConfiguration().setWarnAboutForeignExtentObjectUsage(false);
        // TODO false before
        Repository.getConfiguration().setGenerousXMI(true);
	}
	
	public void prepareRun(Extent m1Extent, Factory m1Factory) throws Exception {
        ExecutionEnvironment env = new ExecutionEnvironment(m1Extent,
                masContext.getMasModel().getMetaModel().getExtent(), repository);
        
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
