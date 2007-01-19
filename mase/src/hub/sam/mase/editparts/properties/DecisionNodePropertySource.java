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

import hub.sam.mase.m2model.DecisionNode;

public class DecisionNodePropertySource extends CommentedNodePropertySource {

    private final DecisionNode model;
    private enum PROPERTY_ID {BODY};
    
    public DecisionNodePropertySource(DecisionNode model) {
        super(model);
        this.model = model;
    }
    
    protected Set<IPropertyDescriptor> getRawPropertyDescriptors() {
        Set<IPropertyDescriptor> rawDescriptors = super.getRawPropertyDescriptors();
        rawDescriptors.add( new TextPropertyDescriptor(PROPERTY_ID.BODY, "body") );
        return rawDescriptors;
    }
    
    public void setPropertyValue(Object id, Object val) throws IllegalArgumentException {
        if (id instanceof PROPERTY_ID) {
            switch((PROPERTY_ID) id) {
            case BODY:
                model.setBody(val.toString());
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
            case BODY:
                String name = model.getBody();
                if (name != null) {
                    return name;
                }
                return new String("");
            }
        }
        else {
            return super.getPropertyValue(id);
        }
        return null;
    }
    
}
