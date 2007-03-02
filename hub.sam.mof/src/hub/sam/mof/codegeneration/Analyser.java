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

package hub.sam.mof.codegeneration;

import cmof.reflection.*;
import cmof.*;
import java.util.*;

public class Analyser {

    public void analyse(Extent extent) {
        List<List<Property>> potentialDeletes= new Vector<List<Property>>();        
        for (cmof.reflection.Object element: extent.objectsOfType(null, true)) {
            if (element instanceof UmlClass) {
                UmlClass umlClass = (UmlClass)element;
                for(Property property: umlClass.getOwnedAttribute()) {
                    // properties that redefine at least 2
                    if (property.getRedefinedProperty().size() >= 2) {
                        // with equal name and different type
                        String name = property.getName();
                        Type type = property.getType();
                        List<Property> withThatNameAndThatType = new Vector<Property>();
                        for (Property redefinedProperty: property.getRedefinedProperty()) {                                                                                   
                            if (redefinedProperty.getName().equals(name) && !redefinedProperty.getType().equals(type)) {
                                withThatNameAndThatType.add(redefinedProperty);
                            }                            
                        }   
                        if (withThatNameAndThatType.size() > 1) {
                            potentialDeletes.add(withThatNameAndThatType);
                        }
                    }
                }
            }
        }
        Set<Property> toDeletes = new HashSet<Property>();
        for (List<Property> potentialDelete: potentialDeletes) {
            for (Property toDelete: potentialDelete) {                
                toDeletes.add(toDelete);                
            }
        }
        for (Property toDelete: toDeletes) {
            System.out.println("WARNING A property was removed: " + toDelete.getName() + " in " + toDelete.getOwner());
            ((cmof.reflection.Object)toDelete).delete();            
        }
        
    }
}
