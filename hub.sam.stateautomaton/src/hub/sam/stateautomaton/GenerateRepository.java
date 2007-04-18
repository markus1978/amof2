/***********************************************************************
 * State Automaton Language
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

package hub.sam.stateautomaton;

import java.util.Arrays;

import hub.sam.mof.Repository;
import hub.sam.mof.javamapping.JavaMapping;
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

        Extent stateAutomatonExtent = repository.createExtent("stateautomaton", m3Extent);
        cmofFactory stateAutomatonFactory = stateAutomatonExtent.getAdaptor(cmofFactory.class);

        try {
            repository.loadMagicDrawXmiIntoExtent(stateAutomatonExtent, cmofPackage, "resources/StateAutomaton.syntax.mdxml");

            Package stateAutomatonModel = (Package) stateAutomatonExtent.query("Package:model");                   
            
            Tag nsPrefixTag = stateAutomatonFactory.createTag();
            nsPrefixTag.setName(JavaMapping.PackagePrefixTagName);
            nsPrefixTag.setValue("hub.sam.stateautomaton");
            stateAutomatonModel.getTag().add(nsPrefixTag);
            
            M1SemanticModel semanticModel = new M1SemanticModel(stateAutomatonFactory);
            semanticModel.createImplicitElements(Arrays.asList(new Package[] {stateAutomatonModel}));
                        
            repository.generateCode(stateAutomatonExtent, "generated-src");
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

}
