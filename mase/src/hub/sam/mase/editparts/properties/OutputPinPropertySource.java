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

package hub.sam.mase.editparts.properties;

import java.util.Set;

import org.eclipse.ui.views.properties.*;

import hub.sam.mase.m2model.OutputPin;
import hub.sam.mase.m2model.MaseRepository;
import hub.sam.mase.m2model.TypeString;

/**
 * 
 * @author Andreas Blunk
 * @deprecated
 */
public class OutputPinPropertySource extends AbstractPropertySource {

    private final OutputPin model;
    private final MaseRepository repository;
    private enum PROPERTY_ID {TYPE};
    
    public OutputPinPropertySource(OutputPin model, MaseRepository repository) {
        this.model = model;
        this.repository = repository;
    }

    protected Set<IPropertyDescriptor> getRawPropertyDescriptors() {
        Set<IPropertyDescriptor> rawDescriptors = super.getRawPropertyDescriptors();
        rawDescriptors.add( new TextPropertyDescriptor(PROPERTY_ID.TYPE, "type") );
        return rawDescriptors;
    }
    
    public void setPropertyValue(Object id, Object val) throws IllegalArgumentException {
        if (id instanceof PROPERTY_ID) {
            switch((PROPERTY_ID) id) {
            case TYPE:
                hub.sam.mase.m2model.TypeString type = repository.getFactory().createTypeString();
                for(String namePart: val.toString().split("::")) {
                    type.getQualifiedTypeName().add(namePart);
                }
                model.setType(type);
                break;
            }
        }
    }

    public Object getPropertyValue(Object id) {
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
