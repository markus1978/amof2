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

import hub.sam.mase.MasePlugin;
import hub.sam.mof.Repository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;

import cmof.Package;
import cmof.cmofFactory;
import cmof.reflection.Extent;

public class MASEditorMOFModelFactory extends AbstractMOFModelFactory {
    
    private static MOFModel semanticMetaModel;
    private static String semanticMetaExtentName = "hub.sam.mase.metaExtent";
    private static String semanticMetaXmiFile = "resources/models/bootstrap_merged.xml";
    
    public MASEditorMOFModelFactory(String syntaxXmiFile, String semanticXmiFile) {
        super(syntaxXmiFile, null, semanticXmiFile, semanticMetaXmiFile);
    }
    
    public MASEditorMOFModelFactory(boolean createFromContextFile, String pathToContextFile, String contextFile) throws FileNotFoundException, IOException {
        super(pathToContextFile, contextFile);
    }

    @Override
    protected MOFModel createSemanticMetaModel() {
        if (semanticMetaModel == null) {
            org.osgi.framework.Bundle bundle = MasePlugin.getDefault().getBundle();
            InputStream metaInputStream = null;
            try {            
                metaInputStream = FileLocator.openStream(bundle, new Path(semanticMetaXmiFile), false);
            }
            catch(IOException e) {
                System.out.println("IOException while trying to open InputStream to " + semanticMetaXmiFile);
                System.exit(-1);
            }
            
            Repository repository = Repository.getLocalRepository();
            Extent cmofExtent = repository.getExtent(Repository.CMOF_EXTENT_NAME);
            Package cmofPackage = (Package) cmofExtent.query("Package:cmof");
    
            Extent metaExtent = repository.createExtent(semanticMetaExtentName);
            try {
                repository.loadXmiIntoExtent(metaExtent, cmofPackage, metaInputStream);
            }
            catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            Package metaPackage = (Package) metaExtent.query("Package:mas");
            cmofFactory cmofFactory = (cmofFactory) repository.createFactory(metaExtent, cmofPackage);

            cmof.Tag tag = cmofFactory.createTag();
            tag.setValue("Mase");
            tag.setName("org.omg.xmi.nsPrefix");
            metaPackage.getTag().add(tag);
            
            semanticMetaModel = new MOFModel(getSemanticMetaXmiFile(), metaExtent, semanticMetaExtentName, metaPackage);
        }
        return semanticMetaModel;
    }   

}
