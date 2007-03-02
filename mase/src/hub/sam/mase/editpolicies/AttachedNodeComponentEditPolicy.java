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

import hub.sam.mase.commands.AttachedNodeDeleteCommand;
import hub.sam.mase.editparts.AttachedNodeEditPart;
import hub.sam.mof.model.mas.AttachedNode;
import hub.sam.mof.model.mas.AttachedNodeList;

import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.GroupRequest;
import java.util.List;

/**
 * EditPolicy for deleting one AttachedNode from an ActivityNode.
 * 
 * @author Andreas Blunk
 */
public final class AttachedNodeComponentEditPolicy extends AbstractActivityNodeComponentEditPolicy {
    
    public AttachedNodeEditPart getHost() {
        return (AttachedNodeEditPart) super.getHost();
    }
    
    public CompoundCommand createDeleteCommand(GroupRequest r) {
        CompoundCommand compound = super.createDeleteCommand(r);
        
        // delete the AttachedNode itself
        AttachedNodeList parentModel = getHost().getParent().getModel();
        AttachedNode hostModel = getHost().getModel();
        List<AttachedNode> removeFromList = new hub.sam.mof.util.ListWrapper<AttachedNode>( parentModel.getNode() );
        AttachedNodeDeleteCommand command = new AttachedNodeDeleteCommand(removeFromList, hostModel);
        compound.add(command);

        return compound;
    }

}
