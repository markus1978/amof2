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

package hub.sam.mas;

import java.util.Arrays;

import hub.sam.mof.Repository;
import hub.sam.mof.javamapping.JavaMapping;
import hub.sam.mof.merge.MergeContext;
import hub.sam.mof.runtimelayer.M1SemanticModel;
import cmof.Package;
import cmof.Tag;
import cmof.cmofFactory;
import cmof.reflection.Extent;

public class GenerateRepository {

    public static void main(String[] args) {
        Repository repository = Repository.getLocalRepository();
        Extent m3Extent = repository.getExtent(Repository.CMOF_EXTENT_NAME);
        Package cmofPackage = (Package) m3Extent.query("Package:cmof");

        Extent masExtent = repository.createExtent("masExtent", m3Extent);
        cmofFactory cmofFactory = masExtent.getAdaptor(cmofFactory.class);

        try {
            repository.loadMagicDrawXmiIntoExtent(masExtent, cmofPackage, "resources/models/mas.mdxml");

            Package asPackage = (Package) masExtent.query("Package:mas");                   
            MergeContext.mergePackages(asPackage, cmofFactory, null);
            
            cmof.Tag nsPrefixTag = cmofFactory.createTag();
            nsPrefixTag.setName("org.omg.xmi.nsPrefix");
            nsPrefixTag.setValue("mas");
            ((Package) masExtent.query("Package:mas")).getTag().add(nsPrefixTag);
            
            Tag packagePrefixTag = cmofFactory.createTag();
            packagePrefixTag.setName(JavaMapping.PackagePrefixTagName);
            packagePrefixTag.setValue("hub.sam.mas.model");
            ((Package) masExtent.query("Package:mas")).getTag().add(packagePrefixTag);
            ((Package) masExtent.query("Package:petrinets")).getTag().add(packagePrefixTag);
            
            M1SemanticModel semanticModel = new M1SemanticModel(cmofFactory);
            semanticModel.createImplicitElements(Arrays.asList(new Package[] {
            		asPackage,(Package)masExtent.query("Package:petrinets")}));
                        
            repository.generateCode(masExtent, "generated-src", Arrays.asList(
            		new String[]{"mas", "petrinets"}));           
            repository.writeExtentToXmi("resources/models/mas_merged.xml", cmofPackage, masExtent);
            repository.generateStaticModel(masExtent, "hub.sam.mas.model.mas.MasModel", "generated-src");
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

}
