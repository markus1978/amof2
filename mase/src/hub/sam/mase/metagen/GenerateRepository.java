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

import hub.sam.mof.Repository;
import hub.sam.mof.merge.MergeContext;
import cmof.Package;
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
            //repository.loadXmiIntoExtent(m2Extent, cmofPackage, "resources/models/bootstrap.xml");
            repository.loadMagicDrawXmiIntoExtent(m2Extent, cmofPackage, "resources/models/bootstrap_united.mdxml");
            Package m2Package = (Package) m2Extent.query("Package:hub/Package:sam/Package:mase/Package:m2model");
            Package asPackage = (Package) m2Extent.query("Package:as");

            // merge as into hub.sam.mase.m2model
            MergeContext.mergePackages(m2Package, m3Factory, null);

            ((cmof.reflection.Object) asPackage).delete();
            repository.generateCode(m2Extent, "generated-src/");
            //repository.generateStaticModel(m2Extent, "hub.sam.mase.StaticM2Model", "generated-src/");
            repository.writeExtentToXmi("resources/models/bootstrap_merged.xml", cmofPackage, m2Extent);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

}
