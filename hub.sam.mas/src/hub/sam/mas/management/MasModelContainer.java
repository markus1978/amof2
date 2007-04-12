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

import java.util.ArrayList;
import java.util.List;

import cmof.Package;
import cmof.cmofFactory;
import cmof.reflection.Extent;

import hub.sam.mas.model.mas.MasModel;
import hub.sam.mof.Repository;
import hub.sam.mof.runtimelayer.M1SemanticModel;
import hub.sam.mof.xmi.Xmi1Reader.XmiKind;

/**
 * an implementation of a mas model container. it holds a syntax and a semantic model
 * which can be used by a mas context to establish a connection between them.
 * 
 * meta-models:
 * the syntax models meta-model is cmof which is provided by the amof repository.
 * the semantic models meta-model is the mas meta-model which is provided by the mas project.
 * 
 * in order to use the model container in a mas context, you just need to load the appropriate
 * syntax and mas model. when loading the syntax model you must choose between loading it
 * for editing or for execution. if you decide to load it for execution, implicit execution
 * elements will be created. these elements are needed in order to execute models as instance
 * of the syntax model. they should not be saved back to the syntax model!
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
    
    /**
     * Loads a syntax model (as instance of cmof) from the given xmi file and prepares execution.
     * 
     * @param xmiFile
     * @param packageQuery you must specify the models package
     * @throws LoadException
     */
    public void loadSyntaxModelForExecution(String xmiFile, String packageQuery) throws LoadException {
        // clone syntax model - prevents saving runtime-instance-of-association
        String clonedSyntaxFile = xmiFile + "_cloned.xml";
        cloneXmiModel(xmiFile, clonedSyntaxFile);        
        syntaxModelManager.loadM2Model(clonedSyntaxFile, packageQuery);
        
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
     * Instances of the syntax can not be executed!
     * 
     */
    public void loadSyntaxModelForEditing(String xmiFile, String packageQuery) throws LoadException {
        syntaxModelManager.loadM2Model(xmiFile, packageQuery);
    }

    /**
     * Loads a mas model (as instance of the mas meta-model) from the given xmi file.
     * You must load the mas meta-model before calling this method!
     * 
     */
    public void loadMasModel(String xmiFile) throws LoadException {
        masModelManager.loadM1Model(xmiFile);
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
