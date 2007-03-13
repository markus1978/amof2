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

        Extent masExtent = repository.createExtent("masExtent");

        cmofFactory masFactory = (cmofFactory) repository.createFactory(masExtent, cmofPackage);

        try {
            repository.loadMagicDrawXmiIntoExtent(masExtent, cmofPackage, "resources/models/mas.mdxml");

            Package asPackage = (Package) masExtent.query("Package:mas");                   
            MergeContext.mergePackages(asPackage, masFactory, null);
            
            Tag nsPrefixTag = masFactory.createTag();
            nsPrefixTag.setName(JavaMapping.PackagePrefixTagName);
            nsPrefixTag.setValue("hub.sam.mas.model");
            ((Package)masExtent.query("Package:mas")).getTag().add(nsPrefixTag);
            ((Package)masExtent.query("Package:petrinets")).getTag().add(nsPrefixTag);
            
            M1SemanticModel semanticModel = new M1SemanticModel(masFactory);
            semanticModel.createImplicitElements(Arrays.asList(new Package[] {
            		asPackage,(Package)masExtent.query("Package:petrinets")}));
                        
            repository.generateCode(masExtent, "generated-src", Arrays.asList(
            		new String[]{"mas", "petrinets"}));           
            repository.writeExtentToXmi("resources/models/mas_merged.xml", cmofPackage, masExtent);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

}
