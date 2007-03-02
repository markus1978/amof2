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

import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import hub.sam.mase.editpolicies.AttachedNodeListComponentEditPolicy;
import hub.sam.mase.figures.AttachedNodeListFigure;
import hub.sam.mof.model.mas.AttachedNode;
import hub.sam.mof.model.mas.AttachedNodeList;

public abstract class AttachedNodeListEditPart extends AbstractGraphicalEditPart {
   
    public AttachedNodeList getModel() {
        return (AttachedNodeList) super.getModel();
    }
    
    public ActivityNodeEditPart getParent() {
        return (ActivityNodeEditPart) super.getParent();
    }
    
    public AttachedNodeListFigure getFigure() {
        return (AttachedNodeListFigure) super.getFigure();
    }
    
    protected List getModelChildren() {
        return new hub.sam.mof.util.ListWrapper<AttachedNode>( getModel().getNode() );
    }
    
    public IFigure getContentPane() {
        return getFigure().getContentPane();
    }

    @Override
    protected void createEditPolicies() {
        installEditPolicy(EditPolicy.COMPONENT_ROLE, new AttachedNodeListComponentEditPolicy());
    }
    
    protected void refreshVisuals() {
        AttachedNodeList model = getModel();
        AttachedNodeListFigure figure = getFigure();
        
        if (model.getNode().size() > 0) {
            figure.expandFigure();
        }
        else {
            figure.shrinkFigure();
        }
    }

    public boolean isSelectable() {
        return false;
    }
    
}
