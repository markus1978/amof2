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

import hub.sam.mase.editparts.properties.handlers.PropertyHandler;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;

public abstract class AbstractPropertySource implements IPropertySource {
    
    private List<PropertyHandler> handlers;
    private List<IPropertyDescriptor> propertyDescriptors;
    
    public IPropertyDescriptor[] getPropertyDescriptors() {
        if (propertyDescriptors == null) {
            
            createPropertyHandlers();
            createPropertyDescriptors();
        }
        return propertyDescriptors.toArray(new IPropertyDescriptor[] {});
    }
   
    protected abstract void createPropertyHandlers();
    
    protected void installPropertyHandler(PropertyHandler handler) {
        if (handlers == null) {
            handlers = new ArrayList<PropertyHandler>();
        }
        handlers.add(handler);
    }
    
    private void createPropertyDescriptors() {
        propertyDescriptors = new ArrayList<IPropertyDescriptor>();
        for(PropertyHandler handler: getHandlers()) {
            propertyDescriptors.addAll( handler.getPropertyDescriptors() );
        }
    }

    public final void setPropertyValue(Object id, Object value) {
        for(PropertyHandler handler: getHandlers()) {
            if (handler.handleSetPropertyValue(id, value)) {
                break;
            }
        }
    }

    public final Object getPropertyValue(Object id) {
        Object value = null;
        for(PropertyHandler handler: getHandlers()) {
            value = handler.handleGetPropertyValue(id);
            if (value != null) {
                return value;
            }
        }
        return value;
    }

    protected List<PropertyHandler> getHandlers() {
        return handlers;
    }
    
    public boolean isPropertySet(Object id) {
        return false;
    }

    public void resetPropertyValue(Object id) {
        // empty
    }

    public Object getEditableValue() {
        return null;
    }

}
