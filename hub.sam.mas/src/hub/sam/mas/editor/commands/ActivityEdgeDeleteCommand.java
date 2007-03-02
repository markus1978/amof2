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
import hub.sam.mas.model.mas.Activity;
import hub.sam.mas.model.mas.ActivityEdge;
import hub.sam.mas.model.mas.ActivityNode;

import org.eclipse.gef.RootEditPart;

public class ActivityEdgeDeleteCommand extends MofDeleteCommand {

    private final ActivityEdge edge;
    private final ActivityNode sourceNode;
    private final ActivityNode targetNode;
    private final Activity activity;
    private final RootEditPart root;

    /**
     * 
     * @param root RootEditPart
     * @param sourceNode source ActivityNode model
     * @param targetNode target ActivityNode model
     * @param edge host model
     */
    public ActivityEdgeDeleteCommand(RootEditPart root, ActivityNode sourceNode,
            ActivityNode targetNode, ActivityEdge edge) {
        super(edge);
        this.edge = edge;
        this.sourceNode = sourceNode;
        this.targetNode = targetNode;
        this.activity = edge.getActivity();
        this.root = root;
    }

    public void execute() {
        redo();
    }

    public void redo() {
        super.redo();
        edge.setSource(null);
        edge.setTarget(null);
        edge.setActivity(null);
        ActivityNodeEditPart.refreshConnections(root, sourceNode);
        ActivityNodeEditPart.refreshConnections(root, targetNode);
    }
    
    public void undo() {
        super.undo();
        edge.setSource(sourceNode);
        edge.setTarget(targetNode);
        edge.setActivity(activity);
        ActivityNodeEditPart.refreshConnections(root, sourceNode);
        ActivityNodeEditPart.refreshConnections(root, targetNode);
    }
    
}
