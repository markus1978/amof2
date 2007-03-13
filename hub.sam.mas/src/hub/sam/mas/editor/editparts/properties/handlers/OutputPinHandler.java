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

package hub.sam.mas.editor.editparts.properties.handlers;

import hub.sam.mas.editor.MaseEditDomain;
import hub.sam.mas.model.mas.OutputPin;
import hub.sam.mas.model.mas.TypeString;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.views.properties.*;

public class OutputPinHandler implements PropertyHandler {

    private final OutputPin model;
    private final MaseEditDomain editDomain;
    private enum PROPERTY_ID {TYPE};
    
    public OutputPinHandler(OutputPin model, MaseEditDomain editDomain) {
        this.model = model;
        this.editDomain = editDomain;
    }
    
    public final List<IPropertyDescriptor> getPropertyDescriptors() {
        List<IPropertyDescriptor> descriptors = new ArrayList<IPropertyDescriptor>();
        descriptors.add( new TextPropertyDescriptor(PROPERTY_ID.TYPE, "type") );
        return descriptors;
    }
    
    public final boolean handleSetPropertyValue(Object id, Object value) {
        if (id instanceof PROPERTY_ID) {
            switch((PROPERTY_ID) id) {
            case TYPE:
                hub.sam.mas.model.mas.TypeString type = editDomain.getFactory().createTypeString();
                for(String namePart: value.toString().split("::")) {
                    type.getQualifiedTypeName().add(namePart);
                }
                model.setType(type);
                return true;
            }
        }
        return false;
    }
    
    public final Object handleGetPropertyValue(Object id) {
        if (id instanceof PROPERTY_ID) {
            switch((PROPERTY_ID) id) {
            case TYPE:
                TypeString type = (TypeString)model.getType();
                if (type != null) {
                    StringBuffer qualifiedName = new StringBuffer();
                    boolean first = true;
                    for(String namePart: type.getQualifiedTypeName()) {
                        if (first) {
                            first = false;
                        } else {
                            qualifiedName.append("::");
                        }
                        qualifiedName.append(namePart);                        
                    }
                    return qualifiedName.toString();
                }
                return new String("");
            }
        }
        return null;
    }
    
}
