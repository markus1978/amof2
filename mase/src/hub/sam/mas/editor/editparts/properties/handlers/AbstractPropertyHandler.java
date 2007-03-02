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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.views.properties.*;

public abstract class AbstractPropertyHandler implements PropertyHandler {

    private final List<PropertyHandler> handlers;
    
    public AbstractPropertyHandler() {
        handlers = new ArrayList<PropertyHandler>();
    }
    
    protected List<PropertyHandler> getHandlers() {
        return handlers;
    }
    
    public final List<IPropertyDescriptor> getPropertyDescriptors() {
        List<IPropertyDescriptor> descriptors = new ArrayList<IPropertyDescriptor>();
        for(PropertyHandler h: getHandlers()) {
            descriptors.addAll( h.getPropertyDescriptors() );
        }
        return descriptors;
    }
    
    public final boolean handleSetPropertyValue(Object id, Object value) {
        for(PropertyHandler h: getHandlers()) {
            if (h.handleSetPropertyValue(id, value)) {
                return true;
            }
        }
        return false;
    }
    
    public final Object handleGetPropertyValue(Object id) {
        Object value = null;
        for(PropertyHandler h: getHandlers()) {
            value = h.handleGetPropertyValue(id);
            if (value != null) {
                return value;
            }
        }
        return value;
    }
    
}
