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

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;

import hub.sam.mase.commands.ActivityEdgeCreateCommand;
import hub.sam.mase.commands.ActivityEdgeReconnectSourceCommand;
import hub.sam.mase.commands.ActivityEdgeReconnectTargetCommand;
import hub.sam.mase.editparts.ActivityEditPart;
import hub.sam.mase.editparts.ActivityNodeEditPart;
import hub.sam.mase.editparts.ControlFlowEditPart;
import hub.sam.mase.editparts.ObjectFlowEditPart;
import hub.sam.mase.editparts.ObjectNodeEditPart;
import hub.sam.mase.m2model.ActivityNode;
import hub.sam.mase.m2model.ActivityEdge;
import hub.sam.mase.m2model.ObjectNode;

/**
 * EditPolicy for connecting Nodes with ControlFlows or ObjectFlows
 * depending on the concrete Node.
 * 
 * @author Andreas Blunk
 */
public class ActivityNodeGraphicalNodeEditPolicy extends GraphicalNodeEditPolicy {
    
    protected ActivityEditPart getActivityEditPart() {
        return (ActivityEditPart) getHost().getRoot().getContents();
    }
    
    protected ActivityNodeEditPart getHostEditPart() {
        return (ActivityNodeEditPart) getHost();
    }
    
    protected ActivityNode getHostModel() {
        return getHostEditPart().getModel();
    }

    @Override
    protected Command getConnectionCompleteCommand(CreateConnectionRequest request) {
        Command command = request.getStartCommand();
        if (command instanceof ActivityEdgeCreateCommand) {
            ActivityNode sourceNode = ((ActivityEdgeCreateCommand) command).getSourceNode();
            ActivityNode targetNode = getHostEditPart().getModel();

            if (sourceNode instanceof ObjectNode && targetNode instanceof ObjectNode
                    || !(sourceNode instanceof ObjectNode) && !(targetNode instanceof ObjectNode) ) {
                ((ActivityEdgeCreateCommand) command).setTargetNode(targetNode);
                return command;
            }
        }
        return null;
    }

    @Override
    protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
        Object newObject = request.getNewObject();
        if (newObject != null && newObject instanceof ActivityEdge) {
            Command command = new ActivityEdgeCreateCommand(
                    getHostEditPart().getRoot(),
                    getActivityEditPart().getModel(),
                    getHostEditPart().getModel(),
                    (ActivityEdge) newObject);
            request.setStartCommand(command);
            return command;
        }
        return null;
    }
    
    private boolean legalReconnect(ConnectionEditPart connection, EditPart target) {
        return target instanceof ActivityNodeEditPart && (
                (connection instanceof ControlFlowEditPart && !(target instanceof ObjectNodeEditPart)) 
                        || (connection instanceof ObjectFlowEditPart && target instanceof ObjectNodeEditPart) );
    }

    @Override
    protected Command getReconnectSourceCommand(ReconnectRequest request) {
        ConnectionEditPart connection = request.getConnectionEditPart();
        EditPart targetEditPart = request.getTarget();
        if (legalReconnect(connection, targetEditPart)) {
            return new ActivityEdgeReconnectSourceCommand(
                    getHost().getRoot(),
                    (ActivityEdge) connection.getModel(),
                    (ActivityNode) targetEditPart.getModel());
        }
        return null;
    }

    @Override
    protected Command getReconnectTargetCommand(ReconnectRequest request) {
        ConnectionEditPart connection = request.getConnectionEditPart();
        EditPart targetEditPart = request.getTarget();
        if (legalReconnect(connection, targetEditPart)) {
            return new ActivityEdgeReconnectTargetCommand(
                    getHost().getRoot(),
                    (ActivityEdge) connection.getModel(),
                    (ActivityNode) targetEditPart.getModel());
        }
        return null;
    }

}
