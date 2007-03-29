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
import cmof.reflection.Extent;

public class MofModelManager {
    
    private final Repository repository;
    private MofModel m2Model;
    private MofModel m1Model;
    private MofModel cmofModel;
    
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
    
    public MofModel getM2Model() {
        return m2Model;
    }
    
    public MofModel getM1Model() {
        return m1Model;
    }

    public void loadM2ModelFromXmi(String xmiFile, String packageQuery) throws LoadException {
        m2Model = loadModelFromXmi(getCmofModel(), xmiFile, packageQuery);
    }
    
    public void loadM1ModelFromXmi(String xmiFile, String packageQuery) throws LoadException {
        m1Model = loadModelFromXmi(m2Model, xmiFile, packageQuery);
    }
    
    private Package getPackageFromQuery(Extent extent, String packageQuery) {
        if (packageQuery != null) {
            return (Package) extent.query(packageQuery);
        }
        return null;
    }
       
    private MofModel loadModelFromXmi(MofModel metaModel, String xmiFile, String modelPackage) throws LoadException {
        assert(metaModel != null);
        Extent modelExtent = repository.createExtent(xmiFile, metaModel.getExtent());
        MofModel mofModel = null;
        
        try {
            if (xmiFile.endsWith(".xml")) {
                repository.loadXmiIntoExtent(modelExtent, metaModel.getPackage(), new FileInputStream(xmiFile));
                mofModel = new MofModel(repository, metaModel, xmiFile, modelExtent, xmiFile,
                        getPackageFromQuery(modelExtent, modelPackage));
            }
            else if (xmiFile.endsWith(".mdxml")) {
                XmiImportExport diagramInfo = repository.loadMagicDrawXmiIntoExtent(modelExtent,
                        metaModel.getPackage(), new FileInputStream(xmiFile));
                mofModel = new MagicDrawMofModel(repository, metaModel, xmiFile, modelExtent, xmiFile,
                        getPackageFromQuery(modelExtent, modelPackage), diagramInfo);
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
    
    public void setM2Model(MofModel model) {
        this.m2Model = model;
    }
    
    public void setM1Model(MofModel model) {
        this.m1Model = model;
    }

}
