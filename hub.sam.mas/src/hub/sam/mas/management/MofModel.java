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

import java.io.IOException;

import org.jdom.JDOMException;

import hub.sam.mof.Repository;
import hub.sam.mof.instancemodel.MetaModelException;
import hub.sam.mof.xmi.XmiException;
import cmof.Package;
import cmof.reflection.Extent;
import cmof.reflection.Factory;

public class MofModel {

    protected final Repository repository;
    private final String xmiFile;
    private final Extent extent;
    private final String extentName;
    private Factory factory;
    private Package cmofPackage;
    private MofModel metaModel;
    
    /**
     * Role: M2-Model or lower
     * 
     * @param metaModel
     * @param xmiFile
     * @param extent
     * @param extentName
     * @param cmofPackage
     */
    public MofModel(Repository repository, MofModel metaModel, String xmiFile, Extent extent, String extentName, Package cmofPackage) {
        this.repository = repository;
        this.metaModel = metaModel;
        this.xmiFile = xmiFile;
        this.extent = extent;
        this.extentName = extentName;
        this.cmofPackage = cmofPackage;
    }

    /**
     * Role: M3-Model
     * 
     * @param xmiFile
     * @param extent
     * @param extentName
     * @param cmofPackage
     */
    public MofModel(Repository repository, String xmiFile, Extent extent, String extentName, Package cmofPackage) {
        this.repository = repository;
        this.xmiFile = xmiFile;
        this.extent = extent;
        this.extentName = extentName;
        this.cmofPackage = cmofPackage;
    }

    public Extent getExtent() {
        return extent;
    }
    
    public String getExtentName() {
        return extentName;
    }
    
    public Factory getFactory() {
        if (factory == null) {
            factory = repository.createFactory(extent, getMetaModel().getPackage());
        }
        return factory;
    }
    
    public String getXmiFile() {
        return xmiFile;
    }

    public Package getPackage() {
        return cmofPackage;
    }
    
    public void setPackage(Package cmofPackage) {
        this.cmofPackage = cmofPackage;
    }

    public MofModel getMetaModel() {
        return metaModel;
    }
    
    public void setMetaModel(MofModel metaModel) {
        this.metaModel = metaModel;
    }
    
    protected void save() throws SaveException {
        String xmiFile = getXmiFile();
        if (xmiFile == null) {
            throw new SaveException("xmi file not specified");
        }
        
        try {
            repository.writeExtentToXmi(xmiFile, getMetaModel().getPackage(), getExtent());
        }
        catch (IOException e) {
            throw new SaveException("xmi file " + xmiFile, e);
        }
        catch (MetaModelException e) {
            throw new SaveException("xmi file " + xmiFile, e);
        }
        catch (XmiException e) {
            throw new SaveException("xmi file " + xmiFile, e);
        }
        catch (JDOMException e) {
            throw new SaveException("xmi file " + xmiFile, e);
        }
    }
    
    protected void close() {
        if (getMetaModel() != null) {
            getMetaModel().close();
        }
        repository.deleteExtent(getExtentName());
    }
    
}
