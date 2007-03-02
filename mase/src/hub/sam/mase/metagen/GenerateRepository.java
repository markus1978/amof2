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

package hub.sam.mase.metagen;

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

        Extent m2Extent = repository.createExtent("m2Extent");

        cmofFactory m3Factory = (cmofFactory) repository.createFactory(m2Extent, cmofPackage);

        try {
            repository.loadMagicDrawXmiIntoExtent(m2Extent, cmofPackage, "resources/models/mas.mdxml");

            Package asPackage = (Package) m2Extent.query("Package:mas");                   
            MergeContext.mergePackages(asPackage, m3Factory, null);
            
            Tag nsPrefix = m3Factory.createTag();
            nsPrefix.setName(JavaMapping.PackagePrefixTagName);
            nsPrefix.setValue("hub.sam.mof.model");
            ((Package)m2Extent.query("Package:mas")).getTag().add(nsPrefix);
            ((Package)m2Extent.query("Package:petrinets")).getTag().add(nsPrefix);
            
            M1SemanticModel semanticModel = new M1SemanticModel(m3Factory);
            semanticModel.createImplicitElements(Arrays.asList(new Package[] {
            		asPackage,(Package)m2Extent.query("Package:petrinets")}));            
                        
            repository.generateCode(m2Extent, "generated-src", Arrays.asList(
            		new String[]{"mas", "petrinets"}));           
            repository.writeExtentToXmi("resources/models/bootstrap_merged.xml", cmofPackage, m2Extent);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

}
