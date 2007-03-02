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

import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;

import hub.sam.mase.commands.AttachedNodeCreateCommand;
import hub.sam.mof.model.mas.AttachedNode;
import hub.sam.mof.model.mas.ExpansionRegion;
import hub.sam.mof.model.mas.InExpansionNode;
import hub.sam.mof.model.mas.OutExpansionNode;

/**
 * EditPolicy for creating things on ExpansionRegions.
 * 
 * @author Andreas Blunk
 */
public class ExpansionRegionContainerEditPolicy extends AttachedNodeContainerEditPolicy {

    /**
     * Override this method to define a different host model.
     * 
     * @return
     */
    protected ExpansionRegion getHostModel() {
        return (ExpansionRegion) getHost().getModel();
    }
    
    @Override
    protected Command getCreateCommand(CreateRequest request) {
        Object newObject = request.getNewObject();
        ExpansionRegion hostModel = getHostModel();

        if (newObject instanceof InExpansionNode) {
            List<AttachedNode> nodeList = new hub.sam.mof.util.ListWrapper<AttachedNode>(hostModel.getInputElement());
            return new AttachedNodeCreateCommand(nodeList, (AttachedNode) newObject, null);
        }
        else if (newObject instanceof OutExpansionNode) {
            List<AttachedNode> nodeList = new hub.sam.mof.util.ListWrapper<AttachedNode>(hostModel.getOutputElement());
            return new AttachedNodeCreateCommand(nodeList, (AttachedNode) newObject, null);
        }
        return null;
    }

}
