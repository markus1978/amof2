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

import hub.sam.mas.MasPlugin;

import org.apache.log4j.Logger;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

public class EditPartFactoryImpl implements EditPartFactory {
    
    private static Logger logger = Logger.getLogger(EditPartFactoryImpl.class.getName());
    
    /**
     * The editpart factory creates an editpart for a given model element and
     * associates the editpart with the model element.
     */
    public EditPart createEditPart(EditPart context, Object model) {
        EditPart part = null;
        
        java.lang.String rawModelId = model.getClass().getSimpleName();
        // erase "Impl" in rawModelId
        java.lang.String modelId = rawModelId.substring(0, rawModelId.length()-4);
        
        try {
            Class editPartClass = Class.forName(MasPlugin.EDITOR_PACKAGE_PREFIX + ".editparts." + modelId + "EditPart");
            part = (EditPart) editPartClass.newInstance();
            part.setModel(model);
            if (logger.isDebugEnabled()) {
                logger.debug("created " + editPartClass.getSimpleName());
            }
        }
        catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return part;
    }

}
