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

package hub.sam.mas.editor.editparts;

import java.beans.PropertyChangeEvent;

import hub.sam.mas.editor.MaseEditDomain;
import hub.sam.mas.editor.editparts.properties.ContextExtensionPinPropertySource;
import hub.sam.mas.editor.figures.ContextExtensionPinFigure;
import hub.sam.mas.model.mas.ContextExtensionPin;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.ui.views.properties.IPropertySource;

public class ContextExtensionPinEditPart extends ContextPinEditPart {
    
    public ContextExtensionPin getModel() {
        return (ContextExtensionPin) super.getModel();
    }
    
    @Override
    protected IFigure createFigure() {
        ContextExtensionPinFigure figure = new ContextExtensionPinFigure(getModel().getNum());
        if (getModel().getExtensionName() == null) {
            String extensionName = MaseEditDomain.getProperties().getProperty("contextExtensionPin.name");
            getModel().setExtensionName(extensionName);
        }
        anchor = new ChopboxAnchor(figure.getAnchorFigure());
        return figure;
    }
    
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals("extensionName")) {
            refreshVisuals();
        }
    }
    
    protected void refreshVisuals() {
        ((ContextExtensionPinFigure) getFigure()).setText(getModel().getExtensionName());
    }
    
    private IPropertySource propertyDescriptor = null;
    
    public Object getAdapter(Class key) {
        if (key == IPropertySource.class) {
            if (propertyDescriptor == null) {
                propertyDescriptor = new ContextExtensionPinPropertySource(getModel());
            }
            return propertyDescriptor;
        }
        
        return super.getAdapter(key);
    }
    
}
