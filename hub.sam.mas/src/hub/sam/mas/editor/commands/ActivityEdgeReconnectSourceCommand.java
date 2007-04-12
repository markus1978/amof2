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

public class ActivityEdgeReconnectSourceCommand extends RootCommand {

    private final ActivityEdge edge;
    private final ActivityNode newSourceNode;
    private ActivityNode oldSourceNode;

    protected ActivityEdgeReconnectSourceCommand(ActivityEdge connection, ActivityNode newSourceNode) {
        this.edge = connection;
        this.newSourceNode = newSourceNode;
    }

    public void execute() {
        oldSourceNode = edge.getSource();
        redo();
    }

    public void redo() {
        edge.setSource(newSourceNode);
        ActivityNodeEditPart.refreshConnections(getEditDomain(), newSourceNode);
        ActivityNodeEditPart.refreshConnections(getEditDomain(), oldSourceNode);
    }

    public void undo() {
        edge.setSource(oldSourceNode);
        ActivityNodeEditPart.refreshConnections(getEditDomain(), newSourceNode);
        ActivityNodeEditPart.refreshConnections(getEditDomain(), oldSourceNode);
    }
    
}
