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

package hub.sam.mof.bootstrap;

import java.util.*;
import cmof.*;
import hub.sam.mof.instancemodel.*;

public class ClassifierSemanticsWrapper implements ClassifierSemantics<Property, Operation, String> {

    private final BootstrapSemantics semantics;
    private final BootstrapModel model;
    private Collection<Property> properties = null;
    private Collection<Property> finalProperties = null;
    
    public ClassifierSemanticsWrapper(BootstrapSemantics semantics, BootstrapModel model) {
        this.semantics = semantics;
        this.model = model;
    }
            
    public Collection<Property> getProperties() {        
        if (properties == null) {
            Collection<ClassInstance<ClassInstance,ClassInstance,Object>> wrappedProperties = semantics.getProperties();
            properties = new Vector<Property>(wrappedProperties.size());
            for (ClassInstance<ClassInstance,ClassInstance,Object> property: wrappedProperties) {
                properties.add((Property)model.getObjectImpl(property));
            }
        }
        return properties;
    }

    public Collection<Property> getFinalProperties() {
        if (finalProperties == null) {
            Collection<ClassInstance<ClassInstance,ClassInstance,Object>> wrappedProperties = semantics.getFinalProperties();
            finalProperties = new Vector<Property>(wrappedProperties.size());
            for (ClassInstance<ClassInstance,ClassInstance,Object> property: wrappedProperties) {
                finalProperties.add((Property)model.getObjectImpl(property));
            }
        }
        return finalProperties;
    }

    public Property getFinalProperty(Property forProperty) {
        return (Property)model.getObjectImpl(semantics.getFinalProperty(model.getBootstrapElement(forProperty)));
    }

    public Collection<Property> getSupersettedProperties(Property forProperty) {
        return new Vector<Property>();
    }

    public Collection<Property> getSubsettedProperties(Property forProperty) {
        return new Vector<Property>();
    }

    public String getName(Property forProperty) {
        return semantics.getName(model.getBootstrapElement(forProperty));
    }

    public Property getProperty(String forName) {
        return (Property)model.getObjectImpl(semantics.getFinalProperty(semantics.getProperty(forName)));
    }

    public boolean isCollectionProperty(Property forProperty) {
        return semantics.isCollectionProperty(model.getBootstrapElement(forProperty));
    }

    public String getJavaGetMethodNameForProperty(Property forProperty) {
        return semantics.getJavaGetMethodNameForProperty(model.getBootstrapElement(forProperty));        
    }
    
    protected BootstrapSemantics getSemantics() {
        return semantics;
    }

    public Operation getFinalOperation(String name) {
        return null;
    }
}
