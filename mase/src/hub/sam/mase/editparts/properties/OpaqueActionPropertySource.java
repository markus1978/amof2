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
import hub.sam.mase.m2model.OpaqueAction;
import hub.sam.mase.m2model.ActionKind;

public class OpaqueActionPropertySource implements IPropertySource {

    private final OpaqueAction model;
    private static IPropertyDescriptor[] descriptors;
    private enum PROPERTY_ID {ACTION_BODY, ACTION_KIND};
    
    public OpaqueActionPropertySource(OpaqueAction model) {
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
            descriptors = new IPropertyDescriptor[2];
            descriptors[0] = new TextPropertyDescriptor(PROPERTY_ID.ACTION_BODY, "action body");
            
            String[] cbLabels= new String[ActionKind.values().length+1];
            cbLabels[0] = "<none>";
            for(ActionKind kind: ActionKind.values()) {
                cbLabels[kind.ordinal()+1] = kind.toString();
            }
            descriptors[1] = new ComboBoxPropertyDescriptor(PROPERTY_ID.ACTION_KIND, "action kind", cbLabels);
            
        }
        return descriptors;
    }
    
    public void setPropertyValue(Object id, Object val) throws IllegalArgumentException {
        if (id instanceof PROPERTY_ID) {
            switch((PROPERTY_ID) id) {
            case ACTION_BODY:
                model.setActionBody(val.toString());
                break;
            case ACTION_KIND:
                if(val instanceof Integer) {
                    if ((Integer) val > 0) {
                        model.setActionKind(ActionKind.values()[(Integer) val - 1]);
                    }
                    else {
                        model.setActionKind(null);
                    }
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
            case ACTION_BODY:
                String actionBody = model.getActionBody();
                if (actionBody != null) {
                    return actionBody;
                }
                return new String("");
            case ACTION_KIND:
                if (model.getActionKind() != null) {
                    return model.getActionKind().ordinal()+1;
                }
                else {
                    return 0;
                }
            }
        }
        return null;
    }
    
}
