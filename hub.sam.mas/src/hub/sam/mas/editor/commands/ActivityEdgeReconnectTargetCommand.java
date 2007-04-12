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

import hub.sam.mas.editor.editparts.ActivityNodeEditPart;
import hub.sam.mas.model.mas.ActivityEdge;
import hub.sam.mas.model.mas.ActivityNode;

public class ActivityEdgeReconnectTargetCommand extends RootCommand {

    private final ActivityEdge edge;
    private final ActivityNode newTargetNode;
    private ActivityNode oldTargetNode;

    /**
     * 
     * @param edge ActivityEdge
     * @param newTargetNode connect edge to this ActivityNode
     */
    protected ActivityEdgeReconnectTargetCommand(ActivityEdge edge, ActivityNode newTargetNode) {
        this.edge = edge;
        this.newTargetNode = newTargetNode;
    }

    public void execute() {
        this.oldTargetNode = edge.getTarget();
        redo();
    }

    public void redo() {
        edge.setTarget(newTargetNode);
        ActivityNodeEditPart.refreshConnections(getEditDomain(), newTargetNode);
        ActivityNodeEditPart.refreshConnections(getEditDomain(), oldTargetNode);
    }

    public void undo() {
        edge.setTarget(oldTargetNode);
        ActivityNodeEditPart.refreshConnections(getEditDomain(), newTargetNode);
        ActivityNodeEditPart.refreshConnections(getEditDomain(), oldTargetNode);
    }
    
}
