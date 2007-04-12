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

package hub.sam.mas.editor.commands;

import java.util.List;

import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.geometry.Rectangle;

import hub.sam.mas.model.mas.Activity;
import hub.sam.mas.model.mas.ActivityEdge;
import hub.sam.mas.model.mas.ActivityGroup;
import hub.sam.mas.model.mas.ActivityNode;
import hub.sam.mas.model.mas.AttachedNode;
import hub.sam.mas.model.mas.ConstrainedNode;
import hub.sam.mas.model.mas.GuardSpecification;
import hub.sam.mas.model.mas.OpaqueAction;
import hub.sam.mas.model.mas.ValueNode;

public abstract class AbstractCommandFactory implements CommandFactory {
    
    protected abstract void initialiseCommand(RootCommand command);

    public ActivityEdgeCreateBendpointCommand createActivityEdgeCreateBendpointCommand(ActivityEdge edge, int bendpointIndex,
            Bendpoint bendpoint) {
        ActivityEdgeCreateBendpointCommand command = new ActivityEdgeCreateBendpointCommand(edge, bendpointIndex, bendpoint);
        initialiseCommand(command);
        return command;
    }

    public ActivityEdgeCreateCommand createActivityEdgeCreateCommand(Activity activity, ActivityNode sourceNode,
            ActivityEdge edge) {
        ActivityEdgeCreateCommand command = new ActivityEdgeCreateCommand(activity, sourceNode, edge);
        initialiseCommand(command);
        return command;
    }

    public ActivityEdgeDeleteBendpointCommand createActivityEdgeDeleteBendpointCommand(ActivityEdge edge,
            int bendpointIndex) {
        ActivityEdgeDeleteBendpointCommand command = new ActivityEdgeDeleteBendpointCommand(edge, bendpointIndex);
        initialiseCommand(command);
        return command;
    }

    public ActivityEdgeDeleteCommand createActivityEdgeDeleteCommand(ActivityNode sourceNode, ActivityNode targetNode,
            ActivityEdge edge) {
        ActivityEdgeDeleteCommand command = new ActivityEdgeDeleteCommand(sourceNode, targetNode, edge);
        initialiseCommand(command);
        return command;
    }

    public ActivityEdgeMoveBendpointCommand createActivityEdgeMoveBendpointCommand(ActivityEdge edge, int bendpointIndex,
            Bendpoint bendpoint) {
        ActivityEdgeMoveBendpointCommand command = new ActivityEdgeMoveBendpointCommand(edge, bendpointIndex, bendpoint);
        initialiseCommand(command);
        return command;
    }

    public ActivityEdgeReconnectSourceCommand createActivityEdgeReconnectSourceCommand(ActivityEdge connection,
            ActivityNode newSourceNode) {
        ActivityEdgeReconnectSourceCommand command = new ActivityEdgeReconnectSourceCommand(connection, newSourceNode);
        initialiseCommand(command);
        return command;
    }

    public ActivityEdgeReconnectTargetCommand createActivityEdgeReconnectTargetCommand(ActivityEdge edge,
            ActivityNode newTargetNode) {
        ActivityEdgeReconnectTargetCommand command = new ActivityEdgeReconnectTargetCommand(edge, newTargetNode);
        initialiseCommand(command);
        return command;
    }

    public ActivityGroupCreateCommand createActivityGroupCreateCommand(Activity parent, ActivityGroup group) {
        ActivityGroupCreateCommand command = new ActivityGroupCreateCommand(parent, group);
        initialiseCommand(command);
        return command;
    }

    public ActivityGroupCreateCommand createActivityGroupCreateCommand(Activity parent, ActivityGroup group,
            Rectangle constraint) {
        ActivityGroupCreateCommand command = new ActivityGroupCreateCommand(parent, group, constraint);
        initialiseCommand(command);
        return command;
    }

    public ActivityGroupDeleteCommand createActivityGroupDeleteCommand(ActivityGroup group) {
        ActivityGroupDeleteCommand command = new ActivityGroupDeleteCommand(group);
        initialiseCommand(command);
        return command;
    }

    public ActivityNodeCreateCommand createActivityNodeCreateCommand(Activity parent, ActivityNode node) {
        ActivityNodeCreateCommand command = new ActivityNodeCreateCommand(parent, node);
        initialiseCommand(command);
        return command;
    }

    public ActivityNodeCreateCommand createActivityNodeCreateCommand(Activity parent, ActivityNode node,
            Rectangle constraint) {
        ActivityNodeCreateCommand command = new ActivityNodeCreateCommand(parent, node, constraint);
        initialiseCommand(command);
        return command;
    }

    public ActivityNodeDeleteCommand createActivityNodeDeleteCommand(ActivityNode node) {
        ActivityNodeDeleteCommand command = new ActivityNodeDeleteCommand(node);
        initialiseCommand(command);
        return command;
    }

    public AttachedNodeCreateCommand createAttachedNodeCreateCommand(List<AttachedNode> list, AttachedNode node,
            AttachedNode after) {
        AttachedNodeCreateCommand command = new AttachedNodeCreateCommand(list, node, after);
        initialiseCommand(command);
        return command;
    }

    public AttachedNodeDeleteCommand createAttachedNodeDeleteCommand(List<AttachedNode> list, AttachedNode node) {
        AttachedNodeDeleteCommand command = new AttachedNodeDeleteCommand(list, node);
        initialiseCommand(command);
        return command;
    }

    public AttachedNodeListMoveChildCommand createAttachedNodeListMoveChildCommand(List<AttachedNode> list,
            AttachedNode child, AttachedNode after) {
        AttachedNodeListMoveChildCommand command = new AttachedNodeListMoveChildCommand(list, child, after);
        initialiseCommand(command);
        return command;
    }

    public ConstrainedNodeChangeConstraintCommand createConstrainedNodeChangeConstraintCommand(ConstrainedNode node,
            Rectangle constraint) {
        ConstrainedNodeChangeConstraintCommand command = new ConstrainedNodeChangeConstraintCommand(node, constraint);
        initialiseCommand(command);
        return command;
    }

    public GuardSpecificationCreateCommand createGuardSpecificationCreateCommand(ActivityEdge parent,
            GuardSpecification guard) {
        GuardSpecificationCreateCommand command = new GuardSpecificationCreateCommand(parent, guard);
        initialiseCommand(command);
        return command;
    }

    public GuardSpecificationDeleteCommand createGuardSpecificationDeleteCommand(ActivityEdge parent) {
        GuardSpecificationDeleteCommand command = new GuardSpecificationDeleteCommand(parent);
        initialiseCommand(command);
        return command;
    }

    public OpaqueActionDirectEditCommand createOpaqueActionDirectEditCommand(String value, OpaqueAction action) {
        OpaqueActionDirectEditCommand command = new OpaqueActionDirectEditCommand(value, action);
        initialiseCommand(command);
        return command;
    }

    public ValueNodeDirectEditCommand createValueNodeDirectEditCommand(String value, ValueNode valueNode) {
        ValueNodeDirectEditCommand command = new ValueNodeDirectEditCommand(value, valueNode);
        initialiseCommand(command);
        return command;
    }

}
