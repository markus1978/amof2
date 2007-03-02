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

package hub.sam.mase.editparts;

import hub.sam.mas.model.mas.OutExpansionNode;
import hub.sam.mas.model.mas.OutExpansionNodeList;
import hub.sam.mase.editpolicies.AttachedNodeListToolbarLayoutEditPolicy;
import hub.sam.mase.figures.AttachedNodeListFigure;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.swt.graphics.Color;

public class OutExpansionNodeListEditPart extends AttachedNodeListEditPart {
    
    public OutExpansionNodeList getModel() {
        return (OutExpansionNodeList) super.getModel();
    }
    
    @Override
    protected IFigure createFigure() {
        AttachedNodeListFigure figure = new AttachedNodeListFigure(new Color(null, 204, 0, 153));
        figure.setPositionBottom();
        return figure;
    }
    
    protected void createEditPolicies() {
        super.createEditPolicies();
        installEditPolicy(EditPolicy.LAYOUT_ROLE, new AttachedNodeListToolbarLayoutEditPolicy() {
            @Override
            protected boolean isOwnerOfModel(Object model) {
                return model instanceof OutExpansionNode;
            }
            
            @Override
            protected boolean isOwnerOfEditPart(Object editPart) {
                return editPart instanceof OutExpansionNodeEditPart;
            }
        });
    }

}
