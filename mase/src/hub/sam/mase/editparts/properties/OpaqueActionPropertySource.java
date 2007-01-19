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

import hub.sam.mase.m2model.OpaqueAction;
import hub.sam.mase.m2model.ActionKind;

public class OpaqueActionPropertySource extends CommentedNodePropertySource {

    private final OpaqueAction model;
    private enum PROPERTY_ID {ACTION_BODY, ACTION_KIND};
    
    public OpaqueActionPropertySource(OpaqueAction model) {
        super(model);
        this.model = model;
    }
    
    protected Set<IPropertyDescriptor> getRawPropertyDescriptors() {
        Set<IPropertyDescriptor> rawDescriptors = super.getRawPropertyDescriptors();
        
        rawDescriptors.add( new TextPropertyDescriptor(PROPERTY_ID.ACTION_BODY, "body") );

        String[] comboBoxLabels= new String[ActionKind.values().length+1];
        comboBoxLabels[0] = "<none>";
        for(ActionKind kind: ActionKind.values()) {
            comboBoxLabels[kind.ordinal()+1] = kind.toString();
        }
        rawDescriptors.add( new ComboBoxPropertyDescriptor(PROPERTY_ID.ACTION_KIND, "kind", comboBoxLabels) );
        
        return rawDescriptors;
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
        else {
            super.setPropertyValue(id, val);
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
        else {
            return super.getPropertyValue(id);
        }
        return null;
    }
    
}
