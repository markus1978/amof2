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

package hub.sam.mas.editor.editparts;

import java.beans.PropertyChangeEvent;
import java.util.List;

import hub.sam.mas.editor.editpolicies.ActivityNodeComponentEditPolicy;
import hub.sam.mas.editor.editpolicies.ActivityNodeGraphicalNodeEditPolicy;
import hub.sam.mas.model.mas.ActivityEdge;
import hub.sam.mas.model.mas.ActivityNode;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RootEditPart;

public abstract class ActivityNodeEditPart extends PropertyAwareGraphicalEditPart
        implements org.eclipse.gef.NodeEditPart {
    
    protected ConnectionAnchor anchor;
    protected ActivityNodeGraphicalNodeEditPolicy graphicalNodeEditPolicy;
    
    public ActivityNode getModel() {
        return (ActivityNode) super.getModel();
    }
    
    protected ConnectionAnchor getConnectionAnchor() {
        return new ChopboxAnchor(getFigure());
    }
    
    public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
        if (anchor == null) {
            anchor = getConnectionAnchor();
        }
        return anchor;
    }

    public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
        if (anchor == null) {
            anchor = getConnectionAnchor();
        }
        return anchor;
    }

    public ConnectionAnchor getSourceConnectionAnchor(Request request) {
        if (anchor == null) {
            anchor = getConnectionAnchor();
        }
        return anchor;
    }

    public ConnectionAnchor getTargetConnectionAnchor(Request request) {
        if (anchor == null) {
            anchor = getConnectionAnchor();
        }
        return anchor;
    }

    public List getModelSourceConnections() {
        return new hub.sam.mof.util.ListWrapper<ActivityEdge>( getModel().getOutgoing() );
    }

    public List getModelTargetConnections() {
        return new hub.sam.mof.util.ListWrapper<ActivityEdge>( getModel().getIncoming() );
    }

    @Override
    protected void createEditPolicies() {
        graphicalNodeEditPolicy = new ActivityNodeGraphicalNodeEditPolicy();
        installEditPolicy(EditPolicy.NODE_ROLE, graphicalNodeEditPolicy);
        installEditPolicy(EditPolicy.COMPONENT_ROLE, new ActivityNodeComponentEditPolicy());
    }
    
    /**
     * Limits the number of ActivityEdges that can be connected to this ActivityNode,
     * if the ActivityNode is the source of the connection.
     * @param upper
     */
    protected void setSourceUpperLimit(int upper) {
        if (graphicalNodeEditPolicy != null) {
            graphicalNodeEditPolicy.setSourceUpperLimit(upper);
        }
    }
    
    /**
     * Limits the number of ActivityEdges that can be connected to this ActivityNode,
     * if the ActivityNode is the target of the connection.
     * @param upper
     */
    protected void setTargetUpperLimit(int upper) {
        if (graphicalNodeEditPolicy != null) {
            graphicalNodeEditPolicy.setTargetUpperLimit(upper);
        }
    }
    
    /**
     * Forwards refresh of connections for a particual ActivityNode model
     * via the RootEditPart to the corresponding ActivityNodeEditPart.
     * 
     * This method is just a workaround because AMOF does not fire a property change
     * on the other side of an association end if one end changes.
     * See {@link ActivityEdgeCreateCommand} for an example on how it is used.
     * 
     * @param root RootEditPart
     * @param node ActivityNode model
     * @deprecated
     */
    public static void refreshConnections(RootEditPart root, ActivityNode node) {
        ActivityNodeEditPart nodeEditPart = (ActivityNodeEditPart) root.getViewer().getEditPartRegistry().get(node);
        if (nodeEditPart != null) {
            nodeEditPart.refreshSourceConnections();
            nodeEditPart.refreshTargetConnections();
        }
    }
    
    public void propertyChange(PropertyChangeEvent ev) {
        if (ev.getPropertyName().equals("outgoing")) {
            refreshSourceConnections();
        }
        else if (ev.getPropertyName().equals("incoming")) {
            refreshTargetConnections();
        }
    }

}
