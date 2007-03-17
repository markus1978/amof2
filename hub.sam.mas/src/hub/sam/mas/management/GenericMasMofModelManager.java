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

package hub.sam.mas.management;

import hub.sam.mof.Repository;
import hub.sam.mof.instancemodel.MetaModelException;
import hub.sam.mof.xmi.XmiException;
import hub.sam.mof.xmi.XmiImportExport;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.jdom.JDOMException;

import cmof.Package;
import cmof.cmofFactory;
import cmof.reflection.Extent;

/**
 * This MasMofModelManager is generic in the way you can specify the different models
 * (syntax model + syntax meta-model and mas model + mas meta-model).
 * You may either load them from xmi files or explicitly set them by creating your own MofModel objects.
 * 
 * You must specify all four models before using the model manager in a mas context!
 * 
 */
public class GenericMasMofModelManager implements MasMofModelManager {
    
    private final Repository repository;
    private MofModel syntaxModel;
    private MofModel syntaxMetaModel;
    private MofModel masModel;
    private MofModel masMetaModel;
    private MofModel cmofModel;
    private static final String illegalStateMessage = "You must specify a syntax model, a mas model and their meta-models first!";
    
    public GenericMasMofModelManager(Repository repository) {
        this.repository = repository;
    }
    
    public MofModel getCmofModel() {
        if (cmofModel == null) {
            Extent cmofExtent = repository.getExtent(Repository.CMOF_EXTENT_NAME);
            Package cmofPackage = (Package) cmofExtent.query("Package:cmof");
            cmofModel = new MofModel(repository, null, cmofExtent, Repository.CMOF_EXTENT_NAME, cmofPackage);
        }
        return cmofModel;
    }
    
    private boolean isIllegalState() {
        return syntaxModel == null && syntaxMetaModel == null && masModel == null && masMetaModel == null; 
    }
    
    public MofModel getSyntaxModel() {
        if (isIllegalState()) {
            throw new IllegalStateException(illegalStateMessage);
        }
        return syntaxModel;
    }

    /**
     * Loads a syntax model (as instance of the syntax meta-model) from the given xmi file.
     * You must load the syntax meta-model before calling this method!
     * 
     * @param xmiFile
     * @throws LoadException
     */
    public void loadSyntaxModelFromXmi(String xmiFile) throws LoadException {
        syntaxModel = loadModelFromXmi(syntaxMetaModel, xmiFile);
    }
    
    /**
     * Loads a syntax meta-model (as instance of cmof meta-meta-model) from the given xmi file
     * without specifying the model's package.
     * 
     * @param xmiFile
     * @throws LoadException
     */
    public void loadSyntaxMetaModelFromXmi(String xmiFile) throws LoadException {
        syntaxMetaModel = loadModelFromXmi(getCmofModel(), xmiFile);
    }
    
    /**
     * Loads a syntax meta-model (as instance of cmof meta-meta-model) from the given xmi file
     * in the specified package.
     * 
     * @param xmiFile
     * @param packageQuery
     * @throws LoadException
     */
    public void loadSyntaxMetaModelFromXmi(String xmiFile, String packageQuery) throws LoadException {
        syntaxMetaModel = loadModelFromXmi(getCmofModel(), xmiFile);
        syntaxMetaModel.setPackage( (Package) syntaxMetaModel.getExtent().query(packageQuery) );
    }
    
    private MofModel loadModelFromXmi(MofModel metaModel, String xmiFile) throws LoadException {
        assert(metaModel != null);
        Extent modelExtent = repository.createExtent(xmiFile);
        MofModel mofModel = null;
        
        try {
            if (xmiFile.endsWith(".xml")) {
                repository.loadXmiIntoExtent(modelExtent, metaModel.getPackage(), new FileInputStream(xmiFile));
                mofModel = new MofModel(repository, metaModel, xmiFile, modelExtent, xmiFile, null);
            }
            else if (xmiFile.endsWith(".mdxml")) {
                XmiImportExport diagramInfo = repository.loadMagicDrawXmiIntoExtent(modelExtent, metaModel.getPackage(), new FileInputStream(xmiFile));
                mofModel = new MagicDrawMofModel(repository, metaModel, xmiFile, modelExtent, xmiFile, null, diagramInfo);
            }
            else {
                throw new LoadException("unkown extension for xmi file " + xmiFile);
            }
        }
        catch (FileNotFoundException e) {
            throw new LoadException("xmi file " + xmiFile, e);
        }
        catch (IOException e) {
            throw new LoadException("xmi file " + xmiFile, e);
        }
        catch (JDOMException e) {
            throw new LoadException("xmi file " + xmiFile, e);
        }
        catch (XmiException e) {
            throw new LoadException("xmi file " + xmiFile, e);
        }
        catch (MetaModelException e) {
            throw new LoadException("xmi file " + xmiFile, e);
        }
        
        return mofModel;
    }
    
    public void setSyntaxModel(MofModel model) {
        syntaxModel = model;
    }
    
    public void setSyntaxMetaModel(MofModel model) {
        syntaxMetaModel = model;
    }
    
    /**
     * Loads the mas meta-model (as instance of cmof meta-meta-model) from the given xmi file in package mas.
     * 
     * @param xmiFile
     * @throws LoadException
     */
    public void loadMasMetaModelFromXmi(String xmiFile) throws LoadException {
        masMetaModel = loadModelFromXmi(getCmofModel(), xmiFile);
        Package masMetaPackage = (Package) masMetaModel.getExtent().query("Package:mas");
        masMetaModel.setPackage(masMetaPackage);
        
        cmofFactory factory = (cmofFactory) masMetaModel.getFactory(); 
        cmof.Tag nsPrefixTag = factory.createTag();
        nsPrefixTag.setValue("mas");
        nsPrefixTag.setName("org.omg.xmi.nsPrefix");
        masMetaPackage.getTag().add(nsPrefixTag);
    }
    
    /**
     * Loads a mas model (as instance of the mas meta-model) from the given xmi file.
     * You must load the mas meta-model before calling this method!
     * 
     * @param xmiFile
     * @throws LoadException
     */
    public void loadMasModelFromXmi(String xmiFile) throws LoadException {
        masModel = loadModelFromXmi(masMetaModel, xmiFile);
    }
    
    public void setMasModel(MofModel model) {
        this.masModel = model;
    }
    
    public void setMasMetaModel(MofModel model) {
        this.masMetaModel = model;
    }

    /**
     * Returns the currently specified mas model.
     * You must specify a mas model by calling one of the methods setMasModel or loadMasModelFromXmi first!
     * 
     */
    public MofModel getMasModel() {
        if (isIllegalState()) {
            throw new IllegalStateException(illegalStateMessage);
        }
        return masModel;
    }

}
