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

package hub.sam.mof.codegeneration.wrapper;

import cmof.NamedElement;
import cmof.RedefinableElement;
import cmof.common.ReflectiveCollection;

public abstract class AbstractWrapper {
    
	protected hub.sam.mof.javamapping.JavaMapping javaMapping = hub.sam.mof.javamapping.JavaMapping.mapping;
	
	protected String getJavaIdentifier(cmof.NamedElement element) {
        return javaMapping.getJavaIdentifier(element);
    }
	
    protected String getFullQualifiedJavaIdentifier(cmof.NamedElement element) {
        return javaMapping.getFullQualifiedJavaIdentifier(element);
    }
    
    protected String docStringForElementList(ReflectiveCollection<? extends NamedElement> elements) {
        StringBuffer result = new StringBuffer();
        boolean first = true;
        for (NamedElement element: elements) {                
            if (first) {
                first = false;
            } else {
                result.append(", ");
            }
            result.append(element.getQualifiedName());
        }
        return result.toString();
    }
    
    @SuppressWarnings("unchecked")
	protected <E extends RedefinableElement> void collectAllRedefined(ReflectiveCollection<? extends E> redefinedProperties, E property) {
        redefinedProperties.addAll(property.getRedefinedElement());
        for (E redefinedProperty: (ReflectiveCollection<E>)property.getRedefinedElement()) {
            collectAllRedefined(redefinedProperties, redefinedProperty);
        }
    }
}
