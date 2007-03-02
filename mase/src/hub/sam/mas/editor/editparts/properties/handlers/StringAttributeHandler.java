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

public abstract class StringAttributeHandler implements PropertyHandler {

    private final String displayName;
    private final String id;
    
    public StringAttributeHandler(String id, String displayName) {
        this.displayName = displayName;
        this.id = id;
    }
    
    public final List<IPropertyDescriptor> getPropertyDescriptors() {
        List<IPropertyDescriptor> descriptors = new ArrayList<IPropertyDescriptor>();
        descriptors.add( new TextPropertyDescriptor(id, displayName) );
        return descriptors;
    }
    
    protected abstract void setString(String value);
    
    public final boolean handleSetPropertyValue(Object id, Object value) {
        if (id instanceof String) {
            if (((String) id).equals(this.id)) {
                setString(value.toString());
                return true;
            }
        }
        return false;
    }
    
    protected abstract String getString();
    
    public final Object handleGetPropertyValue(Object id) {
        if (id instanceof String) {
            if (((String) id).equals(this.id)) {
                String str = getString();
                if (str != null) {
                    return str;
                }
                return new String("");
            }
        }
        return null;
    }
    
}
