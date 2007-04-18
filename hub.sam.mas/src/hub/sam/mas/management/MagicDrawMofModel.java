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
import hub.sam.mof.xmi.XmiImportExport;
import cmof.Package;
import cmof.reflection.Extent;

/**
 * A special MofModel for MagicDraw where the model's diagram information
 * is preserved on saving.
 *
 */
public class MagicDrawMofModel extends MofModel {
    
    private final XmiImportExport diagramInfo;

    public MagicDrawMofModel(Repository repository, MofModel metaModel, String xmiFile, Extent extent,
            String extentName, Package cmofPackage, XmiImportExport diagramInfo) {
        super(repository, metaModel, xmiFile, extent, extentName, cmofPackage);
        this.diagramInfo = diagramInfo;
    }

    public MagicDrawMofModel(Repository repository, String xmiFile, Extent extent, String extentName,
            Package cmofPackage, XmiImportExport diagramInfo) {
        super(repository, xmiFile, extent, extentName, cmofPackage);
        this.diagramInfo = diagramInfo;
    }
    
    @Override
    public void save() throws SaveException {
        String xmiFile = getXmiFile();
        if (xmiFile == null) {
            throw new SaveException("xmi file not specified");
        }
        
        try {
            repository.writeExtentToMagicDrawXmi(xmiFile, getMetaModel().getPackage(), getExtent(), diagramInfo);
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
    
}
