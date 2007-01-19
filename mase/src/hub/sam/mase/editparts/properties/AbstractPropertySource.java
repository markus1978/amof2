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

import java.util.LinkedHashSet;
import java.util.Set;

import org.eclipse.ui.views.properties.*;

public abstract class AbstractPropertySource implements IPropertySource {

    protected Set<IPropertyDescriptor> rawDescriptors;
    
    public AbstractPropertySource() {
    }

    public IPropertyDescriptor[] getPropertyDescriptors() {
        return getRawPropertyDescriptors().toArray(new IPropertyDescriptor[] {});
    }
    
    protected Set<IPropertyDescriptor> getRawPropertyDescriptors() {
        if (rawDescriptors == null) {
            rawDescriptors = new LinkedHashSet<IPropertyDescriptor>();
        }
        return rawDescriptors;
    }
    
    protected Set<IPropertyDescriptor> getRawDescriptors() {
        return rawDescriptors;
    }
    
    public Object getEditableValue() {
        return null;
    }
    
    public boolean isPropertySet(Object id) {
        return false;
    }
    
    public void resetPropertyValue(Object id) {
        // empty
    }
    
}
