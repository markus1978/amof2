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

package hub.sam.mas.editor;

import hub.sam.mas.editor.commands.AbstractCommandFactory;
import hub.sam.mas.editor.commands.CommandFactoryImpl;
import hub.sam.mas.management.MasContext;
import hub.sam.mas.management.MasLink;
import hub.sam.mas.model.mas.ModelGarbageCollector;
import hub.sam.mas.model.mas.masFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.parts.GraphicalEditor;
import org.eclipse.swt.events.MouseEvent;

/**
 * A MaseEditDomain belongs to one graphical editor which may consist of many
 * edit part viewers (usually there is only one).
 * There is only one edit part viewer active at a time.
 *
 */
public class MaseEditDomain extends DefaultEditDomain {
    
    private AbstractCommandFactory commandFactory = null;
    private ModelGarbageCollector modelGarbageCollector = new ModelGarbageCollector();
    private MasLink masLink;
    private static Properties properties = null;
    private EditPartViewer activeViewer;

    public ModelGarbageCollector getModelGarbageCollector() {
        return modelGarbageCollector;
    }

    public MaseEditDomain(GraphicalEditor editor) {
        super(editor);
    }
    
    public AbstractCommandFactory getCommandFactory() {
        if (commandFactory == null) {
            commandFactory = new CommandFactoryImpl(this);
        }
        return commandFactory;
    }
    
    public GraphicalEditor getGraphicalEditor() {
        return (GraphicalEditor) getEditorPart();
    }
    
    @Override
    public void viewerEntered(MouseEvent mouseEvent, EditPartViewer viewer) {
        super.viewerEntered(mouseEvent, viewer);
        this.activeViewer = viewer;
        System.out.println("viewerEntered");
    }
    
    @Override
    public void viewerExited(MouseEvent mouseEvent, EditPartViewer viewer) {
        super.viewerExited(mouseEvent, viewer);
        /* This is a hack. We assume that there is only one viewer that belongs to this editor.
           We always lose the active viewer here when a context menu is opened on the viewer.
           This is prevented by uncommenting the next line. */
        //this.activeViewer = null;
        System.out.println("viewerExited");
    }
    
    public EditPartViewer getActiveEditPartViewer() {
        return activeViewer;
    }

    public void setMasLink(MasLink masLink) {
        this.masLink = masLink;
    }
    
    public MasLink getMasLink() {
        return masLink;
    }
    
    public MasContext getMasContext() {
        return masLink.getMasContext();
    }
    
    public masFactory getFactory() {
        return (masFactory) getMasContext().getMasModel().getFactory();
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
