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

import org.eclipse.ui.views.properties.*;
import hub.sam.mase.m2model.ExpansionRegion;
import hub.sam.mase.m2model.ExpansionKind;

public class ExpansionRegionPropertySource implements IPropertySource {

    private final ExpansionRegion model;
    private static IPropertyDescriptor[] descriptors;
    private enum PROPERTY_ID {MODE};
    
    public ExpansionRegionPropertySource(ExpansionRegion model) {
        this.model = model;
    }
    
    public Object getEditableValue() {
        return model;
    }

    public boolean isPropertySet(Object id) {
        return true;
    }
    
    public void resetPropertyValue(Object id) {
    }

    public IPropertyDescriptor[] getPropertyDescriptors() {
        if (descriptors == null) {
            descriptors = new IPropertyDescriptor[1];
            String[] cbLabels= new String[ExpansionKind.values().length];
            for(ExpansionKind kind: ExpansionKind.values()) {
                cbLabels[kind.ordinal()] = kind.toString();
            }
            descriptors[0] = new ComboBoxPropertyDescriptor(PROPERTY_ID.MODE, "mode", cbLabels);
            
        }
        return descriptors;
    }
    
    public void setPropertyValue(Object id, Object val) throws IllegalArgumentException {
        if (id instanceof PROPERTY_ID) {
            switch((PROPERTY_ID) id) {
            case MODE:
                if(val instanceof Integer) {
                    model.setMode(ExpansionKind.values()[(Integer) val]);
                }
                else {
                    throw new java.lang.IllegalArgumentException("val must be an Integer if used in a ComboBox");
                }
                break;
            }
        }
    }

    public Object getPropertyValue(Object id) {
        if (id instanceof PROPERTY_ID) {
            switch((PROPERTY_ID) id) {
            case MODE:
                return model.getMode().ordinal();
            }
        }
        return null;
    }    
    
}
