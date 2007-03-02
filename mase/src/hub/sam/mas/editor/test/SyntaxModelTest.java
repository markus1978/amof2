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

package hub.sam.mas.editor.test;

import hub.sam.mof.Repository;
import cmof.Classifier;
import cmof.Comment;
import cmof.Element;
import cmof.Feature;
import cmof.Operation;
import cmof.Package;
import cmof.cmofFactory;
import cmof.reflection.Extent;

public class SyntaxModelTest {

    public static void main(String[] args) {
        Repository repository = Repository.getLocalRepository();
        Extent m3Extent = repository.getExtent(Repository.CMOF_EXTENT_NAME);
        Package cmofPackage = (Package) m3Extent.query("Package:cmof");

        Extent extent = repository.createExtent("extent");

        cmofFactory cmofFactory = (cmofFactory) repository.createFactory(extent, cmofPackage);

        try {
            repository.loadMagicDrawXmiIntoExtent(extent, cmofPackage, "resources/test/raw-syntax.mdxml");
            for(Object obj: extent.outermostComposites()) {
                if (obj instanceof Package) {
                    System.out.println(obj.toString());
                    for(Element element: ((Package) obj).getOwnedElement()) {
                        System.out.println(element.toString());
                        if (element instanceof Classifier) {
                            for(Feature feature: ((Classifier) element).getFeature()) {
                                System.out.println(feature.toString());
                                if (feature instanceof Operation && feature.getName().equals("run")) {
                                    Operation op = (Operation) feature;
                                    Comment com = cmofFactory.createComment();
                                    com.setBody("mas-id-9000");
                                    op.getOwnedComment().add(com);
                                    System.out.println(com.getOwner());
                                }
                                else if (feature instanceof Operation && feature.getName().equals("run2")) {
                                    Operation op = (Operation) feature;
                                    Comment com = cmofFactory.createComment();
                                    com.setBody("mas-id-9001");
                                    op.getOwnedComment().add(com);
                                    System.out.println(com.getOwner());
                                }
                            }
                        }
                    }
                }
            }
            repository.writeExtentToXmi("resources/test/syntax.xml", cmofPackage, extent);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

}
