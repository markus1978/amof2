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

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;

import hub.sam.mas.editor.MaseEditDomain;
import hub.sam.mas.editor.commands.ActivityEdgeCreateCommand;
import hub.sam.mas.editor.editparts.ActivityEditPart;
import hub.sam.mas.editor.editparts.ActivityNodeEditPart;
import hub.sam.mas.editor.editparts.ControlFlowEditPart;
import hub.sam.mas.editor.editparts.ObjectFlowEditPart;
import hub.sam.mas.editor.editparts.ObjectNodeEditPart;
import hub.sam.mas.model.mas.ActivityEdge;
import hub.sam.mas.model.mas.ActivityNode;
import hub.sam.mas.model.mas.ObjectNode;

/**
 * EditPolicy for connecting Nodes with ControlFlows or ObjectFlows
 * depending on the concrete Node.
 * 
 * @author Andreas Blunk
 */
public class ActivityNodeGraphicalNodeEditPolicy extends GraphicalNodeEditPolicy {
    
    private int sourceUpperLimit = -1;
    private int targetUpperLimit = -1;
    
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
            
            if (targetUpperLimit == -1 || targetNode.getIncoming().size() < targetUpperLimit) {
                if (sourceNode instanceof ObjectNode && targetNode instanceof ObjectNode
                        || !(sourceNode instanceof ObjectNode) && !(targetNode instanceof ObjectNode) ) {
                    ((ActivityEdgeCreateCommand) command).setTargetNode(targetNode);
                    return command;
                }
            }
        }
        return null;
    }

    @Override
    protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
        Object newObject = request.getNewObject();
        if (newObject != null && newObject instanceof ActivityEdge) {
            ActivityNode sourceNode = getHostEditPart().getModel();
            if (sourceUpperLimit == -1 || sourceNode.getOutgoing().size() < sourceUpperLimit) {
                MaseEditDomain editDomain = (MaseEditDomain) getHostEditPart().getRoot().getViewer().getEditDomain();
                Command command = editDomain.getCommandFactory().createActivityEdgeCreateCommand(
                        getActivityEditPart().getModel(), sourceNode, (ActivityEdge) newObject);
                request.setStartCommand(command);
                return command;
            }
        }
        return null;
    }
    
    private boolean legalReconnect(ConnectionEditPart connection, EditPart target) {
        return target instanceof ActivityNodeEditPart && (
                (connection instanceof ControlFlowEditPart && !(target instanceof ObjectNodeEditPart)) 
                        || (connection instanceof ObjectFlowEditPart && target instanceof ObjectNodeEditPart) );
    }
    
    protected MaseEditDomain getEditDomain() {
        return (MaseEditDomain) getHost().getRoot().getViewer().getEditDomain();
    }

    @Override
    protected Command getReconnectSourceCommand(ReconnectRequest request) {
        ConnectionEditPart connection = request.getConnectionEditPart();
        EditPart targetEditPart = request.getTarget();
        if (legalReconnect(connection, targetEditPart)) {
            return getEditDomain().getCommandFactory().createActivityEdgeReconnectSourceCommand(
                    (ActivityEdge) connection.getModel(), (ActivityNode) targetEditPart.getModel());
        }
        return null;
    }

    @Override
    protected Command getReconnectTargetCommand(ReconnectRequest request) {
        ConnectionEditPart connection = request.getConnectionEditPart();
        EditPart targetEditPart = request.getTarget();
        if (legalReconnect(connection, targetEditPart)) {
            return getEditDomain().getCommandFactory().createActivityEdgeReconnectTargetCommand(
                    (ActivityEdge) connection.getModel(), (ActivityNode) targetEditPart.getModel());
        }
        return null;
    }

    public int getSourceUpperLimit() {
        return sourceUpperLimit;
    }

    public void setSourceUpperLimit(int sourceUpperLimit) {
        this.sourceUpperLimit = sourceUpperLimit;
    }

    public int getTargetUpperLimit() {
        return targetUpperLimit;
    }

    public void setTargetUpperLimit(int targetUpperLimit) {
        this.targetUpperLimit = targetUpperLimit;
    }

}
