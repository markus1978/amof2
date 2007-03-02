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

import hub.sam.mas.editor.editpolicies.AttachedNodeListToolbarLayoutEditPolicy;
import hub.sam.mas.editor.figures.AttachedNodeListFigure;
import hub.sam.mas.model.mas.InputPin;
import hub.sam.mas.model.mas.InputPinList;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;

public class InputPinListEditPart extends PinListEditPart {
    
    public InputPinList getModel() {
        return (InputPinList) super.getModel();
    }
    
    @Override
    protected IFigure createFigure() {
        return new AttachedNodeListFigure(ColorConstants.blue);
    }
    
    protected void createEditPolicies() {
        super.createEditPolicies();
        installEditPolicy(EditPolicy.LAYOUT_ROLE, new AttachedNodeListToolbarLayoutEditPolicy() {
            @Override
            protected boolean isOwnerOfModel(Object model) {
                return model instanceof InputPin;
            }
            
            @Override
            protected boolean isOwnerOfEditPart(Object editPart) {
                return editPart instanceof InputPinEditPart;
            }
        });
    }

}
