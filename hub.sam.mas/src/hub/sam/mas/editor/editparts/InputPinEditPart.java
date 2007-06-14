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

import hub.sam.mas.editor.editparts.properties.InputPinPropertySource;
import hub.sam.mas.editor.figures.PinFigure;
import hub.sam.mas.model.mas.InputPin;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.ui.views.properties.IPropertySource;

public class InputPinEditPart extends PinEditPart {
    
    public InputPin getModel() {
        return (InputPin) super.getModel();
    }
    
    public final IFigure getFigure() {
        return super.getFigure();
    }
    
    @Override
    protected IFigure createFigure() {
        PinFigure pinFigure = new PinFigure(getModel().getNum(), PinFigure.INPUT_COLOR);
        LabeledPinFigure figure = new LabeledPinFigure(pinFigure);
        anchor = new ChopboxAnchor(figure.getAnchorFigure());
        return figure;
    }
    
    public void propertyChange(PropertyChangeEvent event) {
        if ("valueExpression".equals(event.getPropertyName())) {
            refreshVisuals();
        }
    }
    
    protected void refreshVisuals() {
        ((LabeledPinFigure) getFigure()).setText(getModel().getValueExpression());
    }
    
    private IPropertySource propertyDescriptor = null;
    
    public Object getAdapter(Class key) {
        if (key == IPropertySource.class) {
            if (propertyDescriptor == null) {
                propertyDescriptor = new InputPinPropertySource(getModel());
            }
            return propertyDescriptor;
        }
        
        return super.getAdapter(key);
    }
    
}
