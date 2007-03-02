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

import hub.sam.mas.editor.editor.MaseEditDomain;
import hub.sam.mas.editor.editparts.properties.OutputPinPropertySource;
import hub.sam.mas.editor.figures.PinFigure;
import hub.sam.mas.model.mas.OutputPin;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.ui.views.properties.IPropertySource;

public class OutputPinEditPart extends PinEditPart {
    
    public OutputPin getModel() {
        return (OutputPin) super.getModel();
    }

    public PinFigure getFigure() {
        return (PinFigure) super.getFigure();
    }

    @Override
    protected IFigure createFigure() {
        PinFigure figure = new PinFigure(getModel().getNum(), PinFigure.OUTPUT_COLOR);
        anchor = new ChopboxAnchor(figure);
        return figure;
    }

    @Override
    protected void createEditPolicies() {
        super.createEditPolicies();
    }

    protected void refreshVisuals() {
        super.refreshVisuals();
    }
    
    private IPropertySource propertyDescriptor = null;
    
    public Object getAdapter(Class key) {
        if (key == IPropertySource.class) {
            if (propertyDescriptor == null) {
                propertyDescriptor = new OutputPinPropertySource(getModel(),
                        (MaseEditDomain) getViewer().getEditDomain());
            }
            return propertyDescriptor;
        }
        
        return super.getAdapter(key);
    }
    
}
