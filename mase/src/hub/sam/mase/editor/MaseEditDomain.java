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

package hub.sam.mase.editor;

import java.util.*;

import hub.sam.mas.management.MASContext;
import hub.sam.mase.m2model.m2modelFactory;

import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.ui.IEditorPart;

public class MaseEditDomain extends DefaultEditDomain {
    
    private MASContext masContext;
    private static Properties properties = null;

    public MaseEditDomain(IEditorPart editorPart) {
        super(editorPart);
    }
    
    public void setMASContext(MASContext masContext) {
        this.masContext = masContext;
    }
    
    public MASContext getMASContext() {
        return masContext;
    }
    
    public m2modelFactory getFactory() {
        return (m2modelFactory) getMASContext().getSemanticModel().getFactory();
    }
    
    // editor properties

    public static Properties getProperties() {
        return properties;
    }

    public static void setProperties(Properties properties) {
        MaseEditDomain.properties = properties;
    }
    
    private static Map<String, Object> propertyCache = new HashMap<String, Object>();
    private static enum CachedType {INT, BOOLEAN, STRING};
    
    public static int getCachedInt(String property) {
        return (Integer) getCachedProperty(CachedType.INT, property);
    }

    public static boolean getCachedBoolean(String property) {
        return (Boolean) getCachedProperty(CachedType.BOOLEAN, property);
    }

    public static String getCachedString(String property) {
        return (String) getCachedProperty(CachedType.STRING, property);
    }

    private static Object getCachedProperty(CachedType cachedType, String property) {
        if (!propertyCache.containsKey(property)) {
            Object value = null;
            switch(cachedType) {
            case INT:
                value = Integer.parseInt( getProperties().getProperty(property) );
                break;
            case BOOLEAN:
                value = Boolean.parseBoolean( getProperties().getProperty(property) );
                break;
            case STRING:
                value = getProperties().getProperty(property);
            }
            propertyCache.put(property, value);
        }
        return propertyCache.get(property);
    }

    public static boolean isDebugMode() {
        return (Boolean) getCachedProperty(CachedType.BOOLEAN, "debugMode");
    }
    
    public static String getDefaultFontName() {
        return getCachedString("font.name");
    }

    public static int getDefaultFontSize() {
        return getCachedInt("font.size");
    }
    
    public static int getDefaultCornerSize() {
        return getCachedInt("roundedRectangle.cornerDimension.size");
    }

}
