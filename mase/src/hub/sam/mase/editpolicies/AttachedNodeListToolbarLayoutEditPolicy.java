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

package hub.sam.mase.editpolicies;

import hub.sam.mase.commands.AttachedNodeListMoveChildCommand;
import hub.sam.mase.commands.AttachedNodeCreateCommand;
import hub.sam.mase.editparts.AttachedNodeEditPart;
import hub.sam.mase.editparts.AttachedNodeListEditPart;
import hub.sam.mof.model.mas.AttachedNode;
import hub.sam.mof.model.mas.AttachedNodeList;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.GroupRequest;
import java.util.List;

/**
 * EditPolicy for creating and moving AttachedNodes in AttachedNodeLists and for showing feedback.
 * 
 * @author Andreas Blunk
 */
public abstract class AttachedNodeListToolbarLayoutEditPolicy extends ToolbarLayoutEditPolicy {
    
    public AttachedNodeListEditPart getHost() {
        return (AttachedNodeListEditPart) super.getHost();
    }
    
    protected abstract boolean isOwnerOfModel(Object model);
    protected abstract boolean isOwnerOfEditPart(Object editPart);
    
    protected void showLayoutTargetFeedback(Request request) {
        if (request instanceof CreateRequest) {
            Object newObject = ((CreateRequest) request).getNewObject();
            if (isOwnerOfModel(newObject)) {
                super.showLayoutTargetFeedback(request);
            }
        }
        else if (request instanceof GroupRequest) {
            if (((GroupRequest) request).getEditParts().size() > 0) {
                Object editPart = ((GroupRequest) request).getEditParts().get(0);
                if (isOwnerOfEditPart(editPart)) {
                    super.showLayoutTargetFeedback(request);
                }
            }
        }
    }
    
    /**
     * gets called if the editpart has already been created but
     * is added somewhere else
     */
    @Override
    protected Command createAddCommand(EditPart child, EditPart after) {
        return null;
    }

    protected Command getOrphanChildrenCommand(Request request) {
        return null;
    }
    
    @Override
    protected Command createMoveChildCommand(EditPart child, EditPart after) {
        if (child instanceof AttachedNodeEditPart) {
            AttachedNode childModel = (AttachedNode) child.getModel();
            AttachedNodeList parentModel = getHost().getModel();
            List<AttachedNode> nodeList = new hub.sam.mof.util.ListWrapper<AttachedNode>( parentModel.getNode() );
            
            AttachedNode afterModel = null;
            if (after != null) {
                afterModel = (AttachedNode) after.getModel();
            }            

            return new AttachedNodeListMoveChildCommand(nodeList, childModel, afterModel);
        }
        return null;
    }
    
    @Override
    protected Command getCreateCommand(CreateRequest request) {
        AttachedNodeEditPart after = (AttachedNodeEditPart) getInsertionReference(request);
        AttachedNode afterModel = null;
        if (after != null) {
            afterModel = after.getModel();
        }
        
        Object newObject = request.getNewObject();
        AttachedNodeList hostModel = getHost().getModel();
        List<AttachedNode> nodeList = new hub.sam.mof.util.ListWrapper<AttachedNode>( hostModel.getNode() );
        
        if (isOwnerOfModel(newObject)) {
            return new AttachedNodeCreateCommand(nodeList, (AttachedNode) newObject, afterModel);
        }
        return null;
    }

}
