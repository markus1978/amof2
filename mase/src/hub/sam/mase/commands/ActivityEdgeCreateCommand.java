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

package hub.sam.mase.commands;

import org.eclipse.gef.RootEditPart;

import hub.sam.mase.editor.MaseEditDomain;
import hub.sam.mase.editparts.ActivityNodeEditPart;
import hub.sam.mase.m2model.MaseCreationFactory;
import hub.sam.mase.m2model.ObjectFlow;
import hub.sam.mase.m2model.ActivityEdge;
import hub.sam.mase.m2model.ActivityNode;
import hub.sam.mase.m2model.Activity;
import hub.sam.mase.m2model.ModelGarbageCollector;
import hub.sam.mase.m2model.ObjectNode;

public class ActivityEdgeCreateCommand extends MofCreateCommand {

    private final ActivityNode sourceNode;
    private final RootEditPart root;
    private ActivityNode targetNode;
    private ActivityEdge edge;
    private final Activity activity;

    /**
     * 
     * @param root RootEditPart
     * @param activity parent model
     * @param sourceNode source ActivityNode
     * @param edge host model
     */
    public ActivityEdgeCreateCommand(RootEditPart root, Activity activity,
            ActivityNode sourceNode, ActivityEdge edge) {
        super(edge);
        this.sourceNode = sourceNode;
        this.root = root;
        this.activity = activity;
        this.edge = edge;
    }
    
    public void execute() {
        if(sourceNode instanceof ObjectNode) {
            ModelGarbageCollector.getInstance().deleteModel(edge);
            MaseEditDomain editDomain = (MaseEditDomain) root.getViewer().getEditDomain();
            MaseCreationFactory factory = new MaseCreationFactory(editDomain, ObjectFlow.class);
            edge = (ObjectFlow) factory.getNewObject();
            setModel(edge);
        }
        redo();
    }

    public void redo() {
        super.redo();
        edge.setSource(sourceNode);
        edge.setTarget(targetNode);
        activity.getEdge().add(edge);
        ActivityNodeEditPart.refreshConnections(root, sourceNode);
        ActivityNodeEditPart.refreshConnections(root, targetNode);
    }

    public void undo() {
        super.undo();
        edge.setSource(null);
        edge.setTarget(null);
        activity.getEdge().remove(edge);
        ActivityNodeEditPart.refreshConnections(root, sourceNode);
        ActivityNodeEditPart.refreshConnections(root, targetNode);
    }
    
    public ActivityNode getSourceNode() {
        return this.sourceNode;
    }

    public void setTargetNode(ActivityNode targetNode) {
        this.targetNode = targetNode;
    }

}
