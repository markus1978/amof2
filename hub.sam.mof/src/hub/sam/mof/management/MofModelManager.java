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

package hub.sam.mof.management;

import hub.sam.mof.Repository;
import hub.sam.mof.instancemodel.MetaModelException;
import hub.sam.mof.reflection.ExtentImpl;
import hub.sam.mof.xmi.XmiException;
import hub.sam.mof.xmi.XmiImportExport;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jdom.JDOMException;

import cmof.Package;
import cmof.reflection.Extent;

/**
 * Manages MofModels that are based on the three meta layer architecture where M3 is CMOF,
 * M2 is a meta-model as instance of CMOF and M1 is a model as instance of M2.
 * 
 * The manager allows loading M2 and M1 model from an xmi file or a statically created extent.
 * The M2 model has to be loaded before the M1 model!
 * When you load a particular model, all useful information will be saved in a MofModel object.
 * It can be retrieved by calling the methods getM2Model or getM1Model.
 * 
 * The MofModelManager can be configured to prevent reloading the same model XMI multiple times
 * into different extents. In such a case where an XMI file has already been loaded, the corresponding
 * MofModel is simply returned.
 *
 */
public class MofModelManager {
    
    private final Repository repository;
    private MofModel m2Model;
    private MofModel m1Model;
    private MofModel cmofModel;
    private static int uniqueExtentId = 0;
    
    /**
     * Every model XMI loaded through the manager is mapped to its corresponding MofModel.
     * This prevents loading the same model XMI multiple times into different extents.
     */
    private static boolean allowDuplicateXmiModel = false;
    private static Map<String, MofModel> xmiToMofModel = new HashMap<String, MofModel>();
    
    public static boolean isAllowDuplicateXmiModel() {
        return allowDuplicateXmiModel;
    }

    public static void setAllowDuplicateXmiModel(boolean enableDuplicateXmiModelDetection) {
        MofModelManager.allowDuplicateXmiModel = enableDuplicateXmiModelDetection;
    }

    public MofModelManager(Repository repository) {
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
    
    public void loadM2Model(String xmiFile, String extentName, String packageQuery) throws LoadException {
        m2Model = loadModelFromXmi(getCmofModel(), xmiFile, extentName, packageQuery);
    }

    public void loadM2Model(Extent modelExtent, String packageQuery) throws LoadException {
        m2Model = loadStaticModel(getCmofModel(), modelExtent, packageQuery);
    }
    
    public void loadM1Model(String xmiFile, String extentName) throws LoadException {
        m1Model = loadModelFromXmi(m2Model, xmiFile, extentName, null);
    }

    public void loadM1Model(Extent modelExtent) throws LoadException {
        m1Model = loadStaticModel(m2Model, modelExtent, null);
    }
    
    private Package getPackageFromQuery(Extent extent, String packageQuery) throws LoadException {
        if (packageQuery != null) {
            Package modelPackage = (Package) extent.query(packageQuery);
            if (modelPackage == null) {
                throw new LoadException("package " + packageQuery + " does not exist in model.");
            }
            return modelPackage;
        }
        return null;
    }
    
    private static String getUniqueExtentId() {
        return new Integer(uniqueExtentId++).toString();
    }

    private MofModel loadModelFromXmi(MofModel metaModel, String xmiFile, String extentName, String packageQuery) throws LoadException {
        assert(metaModel != null);
        
        MofModel mofModel = null;
        if (!isAllowDuplicateXmiModel()) {
            mofModel = xmiToMofModel.get(xmiFile);
            if (mofModel != null) {
                if (mofModel.isValid()) {
                    return mofModel;
                }
                else {
                    xmiToMofModel.remove(xmiFile);
                }
            }
        }
        
        // TODO: reuse existing model extent or generate unique extent name ?
        extentName = extentName + " " + getUniqueExtentId();
        Extent modelExtent = repository.createExtent(extentName, metaModel.getExtent());

        try {
            if (xmiFile.endsWith(".xml")) {
                repository.loadXmiIntoExtent(modelExtent, metaModel.getPackage(), new FileInputStream(xmiFile));
                mofModel = new MofModel(repository, metaModel, xmiFile, modelExtent, xmiFile,
                        getPackageFromQuery(modelExtent, packageQuery));
            }
            else if (xmiFile.endsWith(".mdxml")) {
                XmiImportExport diagramInfo = repository.loadMagicDrawXmiIntoExtent(modelExtent,
                        metaModel.getPackage(), new FileInputStream(xmiFile));
                mofModel = new MagicDrawMofModel(repository, metaModel, xmiFile, modelExtent, xmiFile,
                        getPackageFromQuery(modelExtent, packageQuery), diagramInfo);
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
        
        xmiToMofModel.put(xmiFile, mofModel);
        
        return mofModel;
    }
    
    private MofModel loadStaticModel(MofModel metaModel, Extent modelExtent, String packageQuery) throws LoadException {
        assert(metaModel != null);
        ((ExtentImpl) modelExtent).configureExtent(metaModel.getPackage());
        return new MofModel(repository, metaModel, null, modelExtent, null, getPackageFromQuery(modelExtent, packageQuery));
    }
    
    public MofModel getM2Model() {
        return m2Model;
    }
    
    public void setM2Model(MofModel model) {
        this.m2Model = model;
    }
    
    public MofModel getM1Model() {
        return m1Model;
    }

    public void setM1Model(MofModel model) {
        this.m1Model = model;
    }
        
    public MofModel createM2Model(String name, String persistenceXmiFile) {
        Extent m2Extent = repository.createExtent(name, getCmofModel().getExtent());
        m2Model = new MofModel(repository, getM2Model(), persistenceXmiFile, m2Extent, name, null);
        return m2Model;
    }
    
    public MofModel createM2Model(String name) {
        return createM2Model(name, null);
    }
    
    /**
     * creates a m1 model as instance of the m2 model in a new extent.
     * 
     * @param name
     * @param persistenceXmiFile optional if model should be saveable
     * @return
     */
    public MofModel createM1Model(String name, String persistenceXmiFile) {
        Extent m1Extent = repository.createExtent(name, getM2Model().getExtent());
        m1Model = new MofModel(repository, getM2Model(), persistenceXmiFile, m1Extent, name, null);
        return m1Model;
    }
    
    public MofModel createM1Model(String name) {
        return createM1Model(name, null);
    }

}
