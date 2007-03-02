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

package hub.sam.mas.editor.editpolicies;

import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;

import hub.sam.mas.editor.commands.AttachedNodeCreateCommand;
import hub.sam.mas.editor.editparts.DecisionNodeEditPart;
import hub.sam.mas.model.mas.AttachedNode;
import hub.sam.mas.model.mas.ContextPin;
import hub.sam.mas.model.mas.DecisionNode;

/**
 * EditPolicy for creating ContextPins on DecisionNodes.
 * 
 * @author Andreas Blunk
 */
public class DecisionNodeContainerEditPolicy extends AttachedNodeContainerEditPolicy {
    
    protected Command getCreateCommand(CreateRequest request) {
        Object newObject = request.getNewObject();
        DecisionNode hostModel = ((DecisionNodeEditPart) getHost()).getModel();
        
        if (newObject instanceof ContextPin) {
            List<AttachedNode> nodeList = new hub.sam.mof.util.ListWrapper<AttachedNode>(hostModel.getContext());
            return new AttachedNodeCreateCommand(nodeList, (AttachedNode) newObject, null);
        }
        return null;
    }

}
