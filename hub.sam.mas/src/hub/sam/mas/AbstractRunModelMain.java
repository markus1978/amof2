/***********************************************************************
 * MASE -- MOF Action Semantics Editor
 * Copyright (C) 2007 Andreas Blunk
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301  USA
 ***********************************************************************/

package hub.sam.mas;

import hub.sam.mas.execution.MasExecutionHelper;
import hub.sam.mas.management.IMasXmiFiles;
import hub.sam.mas.management.MasContext;
import hub.sam.mas.management.MasModelContainer;
import hub.sam.mas.management.MasRepository;
import hub.sam.mas.management.SimpleMasXmiFiles;
import hub.sam.mof.Repository;
import hub.sam.mof.management.MofModel;
import hub.sam.mof.management.MofModelManager;

public abstract class AbstractRunModelMain {
	
	protected MofModelManager manager = null;
	
	private final String contextFile;
	private final String metaModelPackagePath;
	private final String javaPackagePrefix;
	
	public AbstractRunModelMain(final String contextFile, final String metaModelPackagePath, final String javaPackagePrefix) {
		super();
		this.contextFile = contextFile;
		this.metaModelPackagePath = metaModelPackagePath;
		this.javaPackagePrefix = javaPackagePrefix;
	}

	protected void initialise() {
		try {
			Repository repository = Repository.getLocalRepository();
			repository.reset();
			Repository.getConfiguration().setWarnAboutForeignExtentObjectUsage(false);
	        Repository.getConfiguration().setGenerousXMI(true);
	        
	        // load xmi files for syntax and semantic from a mas context file
	        IMasXmiFiles xmiFiles = new SimpleMasXmiFiles(contextFile);
	
	        // create a new mas model container
	        MasModelContainer masModelContainer = new MasModelContainer(repository);
	        
	        // load mas model (semantic)
	        masModelContainer.loadMasModel(xmiFiles.getMasFile());
	        
	        // load state automaton meta-model (syntax)
	        masModelContainer.loadSyntaxModelForExecution(xmiFiles.getSyntaxFile(), metaModelPackagePath);
	        masModelContainer.getSyntaxModel().addJavaPackagePrefix(javaPackagePrefix);	        
	        
	        // now we can create a mas context
	        MasContext masContext = MasRepository.getInstance().createMasContext(masModelContainer);
	        
	        // create a test model:        
	        // here the mof model manager concept can be used to create a test model
	        // as instance of the syntax model.
	        manager = new MofModelManager(repository);
	        manager.setM2Model(masModelContainer.getSyntaxModel());
	        
	        MofModel model = manager.createM1Model("model");        
	        
	        // prepares execution and installs implementations managers for activities,
	        // ocl queries and java code (in order).
	        MasExecutionHelper.prepareRun(repository, masContext, model);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}	
}
