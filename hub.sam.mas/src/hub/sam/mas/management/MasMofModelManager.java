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
import cmof.cmofFactory;

public class MasMofModelManager implements IMasMofModelManager {
    
    private MofModelManager syntaxModelManager;
    private MofModelManager masModelManager;
    
    public MasMofModelManager(Repository repository) {
        this.syntaxModelManager = new MofModelManager(repository);
        this.masModelManager = new MofModelManager(repository);
    }

    public void loadSyntaxModelFromXmi(String xmiFile) throws LoadException {
        loadSyntaxModelFromXmi(xmiFile, null);
    }
    
    /**
     * Loads a syntax model (as instance of cmof) from the given xmi file.
     * You must load the syntax meta-model before calling this method!
     * 
     * @param xmiFile
     * @param packageQuery
     * @throws LoadException
     */
    public void loadSyntaxModelFromXmi(String xmiFile, String packageQuery) throws LoadException {
        syntaxModelManager.loadM2ModelFromXmi(xmiFile, packageQuery);
    }
    
    /**
     * Loads the mas meta-model (as instance of cmof) from the given xmi file in package mas.
     * 
     * @param xmiFile
     * @param packageQuery
     * @throws LoadException
     */
    public void loadMasMetaModelFromXmi(String xmiFile, String packageQuery) throws LoadException {
        masModelManager.loadM2ModelFromXmi(xmiFile, "Package:mas");

        cmofFactory factory = (cmofFactory) masModelManager.getM2Model().getFactory(); 
        cmof.Tag nsPrefixTag = factory.createTag();
        nsPrefixTag.setValue("mas");
        nsPrefixTag.setName("org.omg.xmi.nsPrefix");
        masModelManager.getM2Model().getPackage().getTag().add(nsPrefixTag);
    }
    
    public void loadMasMetaModelFromXmi(String xmiFile) throws LoadException {
        loadMasMetaModelFromXmi(xmiFile, null);
    }
    
    public void loadMasModelFromXmi(String xmiFile) throws LoadException {
        loadMasModelFromXmi(xmiFile, null);
    }
    
    /**
     * Loads a mas model (as instance of the mas meta-model) from the given xmi file.
     * You must load the mas meta-model before calling this method!
     * 
     * @param xmiFile
     * @param packageQuery
     * @throws LoadException
     */
    public void loadMasModelFromXmi(String xmiFile, String packageQuery) throws LoadException {
        masModelManager.loadM1ModelFromXmi(xmiFile, packageQuery);
    }

    public MofModel getMasModel() {
        return masModelManager.getM1Model();
    }

    public MofModel getSyntaxModel() {
        return syntaxModelManager.getM2Model();
    }

}
