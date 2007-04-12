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
import hub.sam.mas.model.mas.MaseCreationFactory;
import hub.sam.mas.model.mas.ObjectFlow;
import hub.sam.mas.model.mas.ObjectNode;

public class ActivityEdgeCreateCommand extends MofCreateCommand {

    private final ActivityNode sourceNode;
    private ActivityNode targetNode;
    private ActivityEdge edge;
    private final Activity activity;

    /**
     * 
     * @param activity parent model element
     * @param sourceNode source ActivityNode
     * @param edge model element
     */
    protected ActivityEdgeCreateCommand(Activity activity, ActivityNode sourceNode, ActivityEdge edge) {
        super(edge);
        this.sourceNode = sourceNode;
        this.activity = activity;
        this.edge = edge;
    }
    
    public void execute() {
        if(sourceNode instanceof ObjectNode) {
            getModelGarbageCollector().deleteModel(edge);
            MaseCreationFactory factory = new MaseCreationFactory(getEditDomain(), ObjectFlow.class);
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
        ActivityNodeEditPart.refreshConnections(getEditDomain(), sourceNode);
        ActivityNodeEditPart.refreshConnections(getEditDomain(), targetNode);
    }

    public void undo() {
        super.undo();
        edge.setSource(null);
        edge.setTarget(null);
        activity.getEdge().remove(edge);
        ActivityNodeEditPart.refreshConnections(getEditDomain(), sourceNode);
        ActivityNodeEditPart.refreshConnections(getEditDomain(), targetNode);
    }
    
    public ActivityNode getSourceNode() {
        return this.sourceNode;
    }

    public void setTargetNode(ActivityNode targetNode) {
        this.targetNode = targetNode;
    }

}
