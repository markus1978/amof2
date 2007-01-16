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

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;

import hub.sam.mase.commands.ActivityNodeCreateCommand;
import hub.sam.mase.commands.ActivityGroupCreateCommand;
import hub.sam.mase.commands.ConstrainedNodeChangeConstraintCommand;
import hub.sam.mase.m2model.Activity;
import hub.sam.mase.m2model.ActivityNode;
import hub.sam.mase.m2model.ActivityGroup;
import hub.sam.mase.m2model.ConstrainedNode;
import hub.sam.mase.m2model.OpaqueAction;
import hub.sam.mase.m2model.ValueNode;
import hub.sam.mase.m2model.ExpansionRegion;
import hub.sam.mase.m2model.ControlNode;
import hub.sam.mase.editparts.InitialNodeEditPart;
import hub.sam.mase.editparts.FinalNodeEditPart;

/**
 * EditPolicy for creating concrete ActivityNodes and ActivityGroups and moving ConstrainedNodes.
 * 
 * @author Andreas Blunk
 */
public class ActivityXYLayoutEditPolicy extends XYLayoutEditPolicy {
    
    protected Command createAddCommand(EditPart child, Object constraint) {
        return null;
    }

    @Override
    protected Command createChangeConstraintCommand(EditPart child, Object constraint) {
        return new ConstrainedNodeChangeConstraintCommand(
                (ConstrainedNode) child.getModel(),
                (Rectangle) constraint);
    }
    
    protected EditPolicy createChildEditPolicy(EditPart child) {
        if (child.getClass().equals(InitialNodeEditPart.class) ||
                child.getClass().equals(FinalNodeEditPart.class)) {
            // these parts are not resizable
            return new NonResizableEditPolicy();
        }
        else {
            return super.createChildEditPolicy(child);
        }
    }
    
    /**
     * Override this method to define a different host model.
     * 
     * @return
     */
    protected Activity getHostModel() {
        return (Activity) getHost().getModel();
    }

    @Override
    protected Command getCreateCommand(CreateRequest request) {
        Object newObject = request.getNewObject();
        Rectangle rectangle = (Rectangle) getConstraintFor(request);
        if (newObject instanceof OpaqueAction || newObject instanceof ValueNode
                || newObject instanceof ControlNode) {
            return new ActivityNodeCreateCommand(
                    getHostModel(),
                    (ActivityNode) newObject,
                    rectangle);
        }
        else if (newObject instanceof ExpansionRegion) {
            return new ActivityGroupCreateCommand(
                    getHostModel(),
                    (ActivityGroup) newObject,
                    rectangle);
        }
        return null;
    }

}
