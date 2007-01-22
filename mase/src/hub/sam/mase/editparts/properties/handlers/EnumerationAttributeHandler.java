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

package hub.sam.mase.editparts.properties.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.views.properties.*;

public abstract class EnumerationAttributeHandler implements PropertyHandler {

    private final String displayName;
    private final String id;
    private final Class enumeration;
    private final List<String> comboBoxLabels;
    
    public EnumerationAttributeHandler(String id, String displayName, Class enumeration) {
        this.displayName = displayName;
        this.id = id;
        this.enumeration = enumeration;
        this.comboBoxLabels = new ArrayList<String>();
    }
    
    public final List<IPropertyDescriptor> getPropertyDescriptors() {
        List<IPropertyDescriptor> descriptors = new ArrayList<IPropertyDescriptor>();

        comboBoxLabels.add("<unspecified>");
        for(Object enumConstant: enumeration.getEnumConstants()) {
            comboBoxLabels.add( enumConstant.toString() );
        }
        descriptors.add( new ComboBoxPropertyDescriptor(id, displayName, comboBoxLabels.toArray(new String[] {})));

        return descriptors;
    }
    
    protected abstract Object valueOf(String name);
    protected abstract void setEnum(Object value);
    
    public final boolean handleSetPropertyValue(Object id, Object value) {
        if (id instanceof String) {
            if (((String) id).equals(this.id)) {
                if(value instanceof Integer) {
                    Integer intValue = (Integer) value;
                    if (intValue == 0) {
                        setEnum(null);
                    }
                    else {
                        setEnum( valueOf(comboBoxLabels.get(intValue)) );
                    }
                }
                else {
                    throw new java.lang.IllegalArgumentException("val must be an Integer if used in a ComboBox");
                }
                return true;
            }
        }
        return false;
    }
    
    protected abstract Object getEnum();
    
    public final Object handleGetPropertyValue(Object id) {
        if (id instanceof String) {
            if (((String) id).equals(this.id)) {
                Object e = getEnum();
                if (e != null) {
                    return comboBoxLabels.indexOf(e.toString());
                }
                return 0;
            }
        }
        return null;
    }
    
}
