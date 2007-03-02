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

import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.BendpointEditPolicy;
import org.eclipse.gef.requests.BendpointRequest;

import hub.sam.mas.model.mas.ActivityEdgeBendpoint;
import hub.sam.mase.commands.ActivityEdgeCreateBendpointCommand;
import hub.sam.mase.commands.ActivityEdgeDeleteBendpointCommand;
import hub.sam.mase.commands.ActivityEdgeMoveBendpointCommand;
import hub.sam.mase.editparts.ActivityEdgeEditPart;

/**
 * EditPolicy for placing Bendpoints on ActivityEdges.
 * 
 * @author Andreas Blunk
 */
public class ActivityEdgeBendpointEditPolicy extends BendpointEditPolicy {
    
    public ActivityEdgeEditPart getHost() {
        return (ActivityEdgeEditPart) super.getHost();
    }
    
    private Bendpoint getBendpoint(BendpointRequest request) {
        Point location = request.getLocation();
        Connection connection = getConnection();
        
        connection.translateToRelative(location);
        
        Point sourceRef = connection.getSourceAnchor().getReferencePoint();
        Point targetRef = connection.getTargetAnchor().getReferencePoint();
        
        connection.translateToRelative(sourceRef);
        connection.translateToRelative(targetRef);
        
        ActivityEdgeBendpoint bendpoint = new ActivityEdgeBendpoint();
        bendpoint.setRelativeDimensions(location.getDifference(sourceRef), location.getDifference(targetRef));

        return bendpoint;
    }

    @Override
    protected Command getCreateBendpointCommand(BendpointRequest request) {
        ActivityEdgeCreateBendpointCommand create = new ActivityEdgeCreateBendpointCommand(
                getHost().getModel(),
                request.getIndex(),
                getBendpoint(request));
        return create;
    }

    @Override
    protected Command getMoveBendpointCommand(BendpointRequest request) {
        ActivityEdgeMoveBendpointCommand move = new ActivityEdgeMoveBendpointCommand(
                getHost().getModel(),
                request.getIndex(),
                getBendpoint(request));
        return move;
    }

    @Override
    protected Command getDeleteBendpointCommand(BendpointRequest request) {
        ActivityEdgeDeleteBendpointCommand delete = new ActivityEdgeDeleteBendpointCommand(
                getHost().getModel(),
                request.getIndex());
        return delete;
    }

}
