/***********************************************************************
 * MAS -- MOF Action Semantics
 * Copyright (C) 2007 Markus Scheidgen, Andreas Blunk
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

package hub.sam.mas.management;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cmof.Package;
import cmof.cmofFactory;
import cmof.reflection.Extent;

import hub.sam.mas.model.mas.MasModel;
import hub.sam.mof.Repository;
import hub.sam.mof.management.LoadException;
import hub.sam.mof.management.MofModel;
import hub.sam.mof.management.MofModelManager;
import hub.sam.mof.runtimelayer.M1SemanticModel;
import hub.sam.mof.xmi.Xmi1Reader.XmiKind;

/**
 * An implementation of a MasModelContainer. It's purpose is to hold a syntax and
 * a semantic model, that can be used by a MasContext to establish the connection between them.
 * 
 * Meta-models:
 * The syntax model's meta-model is cmof, which is provided by the AMOF repository.
 * The semantic model's meta-model is the MAS meta-model, which is provided by the MAS project.
 * 
 * Before you can use the MasModelContainer in a MasContext, you have to load the appropriate
 * syntax and semantic model. When you load the syntax model, you will have to choose if you want
 * to load it for editing or for execution. If you decide to load it for execution, implicit execution
 * elements will be created. These elements are used internally in order to execute models as instance
 * of the syntax model. They should not be saved back to the syntax model!
 * 
 * Just to make confusion perfect:
 * The syntax model is actually a meta-model (the abstract syntax of the language) whoms instances are
 * concrete models for that syntax (like a program based on that language). The semantic model
 * describes the behaviour of operations from the syntax model. It's meta-model is the MAS meta-model. 
 * 
 */
public class MasModelContainer implements IMasModelContainer {
    
    private MofModelManager syntaxModelManager;
    private MofModelManager masModelManager;
    
    public MasModelContainer(Repository repository) {
        this.syntaxModelManager = new MofModelManager(repository);
        this.masModelManager = new MofModelManager(repository);
        // load static mas meta-model
        this.masModelManager.loadM2Model(MasModel.createModel(), "Package:mas");
    }
    
    private String getFilename(String path) {
        File file = new File(path);
        return file.getName();
    }
       
    /**
     * Loads a syntax model (as instance of cmof) from the given xmi file and prepares it for execution.
     * 
     * @param xmiFile
     * @param packageQuery you must specify the models package
     * @throws LoadException
     */
    public void loadSyntaxModelForExecution(String xmiFile, String packageQuery) throws LoadException {
        // clone syntax model - prevents saving runtime-instance-of-association
        String clonedSyntaxFile = xmiFile + "_cloned.xml";
        cloneXmiModel(xmiFile, clonedSyntaxFile);
        syntaxModelManager.loadM2Model(clonedSyntaxFile, "Syntax: " + getFilename(xmiFile),packageQuery);
        
        // create implicit elements in models package
        if (getSyntaxModel().getPackage() != null) {
            M1SemanticModel semanticModel = new M1SemanticModel((cmofFactory) getSyntaxModel().getFactory());
            List<Package> implicitElementsForPackages = new ArrayList<Package>();
            implicitElementsForPackages.add(getSyntaxModel().getPackage());
            semanticModel.createImplicitElements(implicitElementsForPackages);
        }
    }
    
    /**
     * Loads a syntax model (as instance of cmof) from the given xmi file.
     * Instances of the syntax model cannot be executed!
     * 
     */
    public void loadSyntaxModelForEditing(String xmiFile, String packageQuery) throws LoadException {
        syntaxModelManager.loadM2Model(xmiFile, "Syntax: " + getFilename(xmiFile), packageQuery);
    }

    /**
     * Loads a MAS model (as instance of the MAS meta-model) from the given xmi file.
     * 
     */
    public void loadMasModel(String xmiFile) throws LoadException {
        masModelManager.loadM1Model(xmiFile, "Semantic: " + getFilename(xmiFile));
    }

    public MofModel getMasModel() {
        return masModelManager.getM1Model();
    }

    public MofModel getSyntaxModel() {
        return syntaxModelManager.getM2Model();
    }
    
    private static void cloneXmiModel(String xmiFile, String clonedXmiFile) {
        Repository repository = Repository.getLocalRepository();
        Extent m3Extent = repository.getExtent(Repository.CMOF_EXTENT_NAME);
        Package cmofPackage = (Package) m3Extent.query("Package:cmof");
        Extent modelExtent = repository.createExtent(clonedXmiFile);

        try {
            repository.loadXmiIntoExtent(modelExtent, cmofPackage, xmiFile, getXmiKind(xmiFile));
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

}
