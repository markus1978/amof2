package hub.sam.mof.test.warehouse;
/*
A MOF 2 Java -- The MOF Repository tool for Java
Copyright (C) 2005 Markus Scheidgen

    This library is free software; you can redistribute it and/or modify it
under the terms of the GNU Lesser General Public License as published by the
Free Software Foundation; either version 2.1 of the License, or any later
version.

    This library is distributed in the hope that it will be useful, but WITHOUT
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
details.

    You should have received a copy of the GNU Lesser General Public License
along with this library; if not, write to the Free Software Foundation, Inc.,
59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
*/

import hub.sam.mof.Repository;
import hub.sam.mof.codegeneration.CodeGenerationConfiguration;
import cmof.reflection.*;
import cmof.Package;

public class GenerateRepository {

    public static void main(String[] args) throws Exception {
        CodeGenerationConfiguration.setActualConfig(new CodeGenerationConfiguration(true, false, true));
        Repository repository = Repository.getLocalRepository();
        System.out.println("Generate repository");

        Extent cmofExtent = repository.getExtent(Repository.CMOF_EXTENT_NAME);
        Package cmofPackage = (Package)cmofExtent.query("Package:cmof");

        Extent warehouseMetaExtent = repository.createExtent("warehouseMetaExtent");
        repository.loadXmiIntoExtent(warehouseMetaExtent, cmofPackage, "resources/models/test/warehouse.xml");

        repository.generateCode(warehouseMetaExtent, "resources/test/generated-src/");
        repository.generateStaticModel(warehouseMetaExtent, "warehouse.WarehouseModel", "resources/test/generated-src");
    }
}
