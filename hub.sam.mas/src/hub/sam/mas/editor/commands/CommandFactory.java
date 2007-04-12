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

public interface CommandFactory {
    public ActivityEdgeCreateBendpointCommand createActivityEdgeCreateBendpointCommand(ActivityEdge edge, int bendpointIndex,
            Bendpoint bendpoint);
    public ActivityEdgeCreateCommand createActivityEdgeCreateCommand(Activity activity, ActivityNode sourceNode,
            ActivityEdge edge);
    public ActivityEdgeDeleteBendpointCommand createActivityEdgeDeleteBendpointCommand(ActivityEdge edge, int bendpointIndex);
    public ActivityEdgeDeleteCommand createActivityEdgeDeleteCommand(ActivityNode sourceNode, ActivityNode targetNode,
            ActivityEdge edge);
    public ActivityEdgeMoveBendpointCommand createActivityEdgeMoveBendpointCommand(ActivityEdge edge, int bendpointIndex,
            Bendpoint bendpoint);
    public ActivityEdgeReconnectSourceCommand createActivityEdgeReconnectSourceCommand(ActivityEdge connection,
            ActivityNode newSourceNode);
    public ActivityEdgeReconnectTargetCommand createActivityEdgeReconnectTargetCommand(ActivityEdge edge,
            ActivityNode newTargetNode);
    public ActivityGroupCreateCommand createActivityGroupCreateCommand(Activity parent, ActivityGroup group);
    public ActivityGroupCreateCommand createActivityGroupCreateCommand(Activity parent, ActivityGroup group,
            Rectangle constraint);
    public ActivityGroupDeleteCommand createActivityGroupDeleteCommand(ActivityGroup group);
    public ActivityNodeCreateCommand createActivityNodeCreateCommand(Activity parent, ActivityNode node);
    public ActivityNodeCreateCommand createActivityNodeCreateCommand(Activity parent, ActivityNode node, Rectangle constraint);
    public ActivityNodeDeleteCommand createActivityNodeDeleteCommand(ActivityNode node);
    public AttachedNodeCreateCommand createAttachedNodeCreateCommand(List<AttachedNode> list, AttachedNode node,
            AttachedNode after);
    public AttachedNodeDeleteCommand createAttachedNodeDeleteCommand(List<AttachedNode> list, AttachedNode node);
    public AttachedNodeListMoveChildCommand createAttachedNodeListMoveChildCommand(List<AttachedNode> list,
            AttachedNode child, AttachedNode after);
    public ConstrainedNodeChangeConstraintCommand createConstrainedNodeChangeConstraintCommand(ConstrainedNode node,
            Rectangle constraint);
    public GuardSpecificationCreateCommand createGuardSpecificationCreateCommand(ActivityEdge parent,
            GuardSpecification guard);
    public GuardSpecificationDeleteCommand createGuardSpecificationDeleteCommand(ActivityEdge parent);
    public OpaqueActionDirectEditCommand createOpaqueActionDirectEditCommand(String value, OpaqueAction action);
    public ValueNodeDirectEditCommand createValueNodeDirectEditCommand(String value, ValueNode valueNode);
}