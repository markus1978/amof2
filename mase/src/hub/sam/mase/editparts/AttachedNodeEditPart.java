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

import hub.sam.mas.model.mas.AttachedNode;
import hub.sam.mase.editparts.AttachedNodeListEditPart;
import hub.sam.mase.editpolicies.AttachedNodeComponentEditPolicy;

import org.eclipse.gef.EditPolicy;

public abstract class AttachedNodeEditPart extends ObjectNodeEditPart {
    
    public AttachedNode getModel() {
        return (AttachedNode) super.getModel();
    }
    
    public AttachedNodeListEditPart getParent() {
        return (AttachedNodeListEditPart) super.getParent();
    }
    
    protected void createEditPolicies() {
        super.createEditPolicies();
        installEditPolicy(EditPolicy.COMPONENT_ROLE, new AttachedNodeComponentEditPolicy());
    }
    
}
