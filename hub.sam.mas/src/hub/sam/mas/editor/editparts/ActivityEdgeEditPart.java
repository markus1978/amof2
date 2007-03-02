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
import java.util.*;

import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.ManhattanConnectionRouter;
import org.eclipse.draw2d.RelativeBendpoint;
import org.eclipse.swt.graphics.Color;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.editpolicies.ConnectionEndpointEditPolicy;

import hub.sam.mas.editor.editparts.ActivityNodeEditPart;
import hub.sam.mas.editor.editpolicies.ActivityEdgeBendpointEditPolicy;
import hub.sam.mas.editor.editpolicies.ActivityEdgeConnectionEditPolicy;
import hub.sam.mas.editor.editpolicies.ActivityEdgeContainerEditPolicy;
import hub.sam.mas.editor.figures.ActivityEdgeFigure;
import hub.sam.mas.model.mas.ActivityEdge;
import hub.sam.mas.model.mas.ActivityEdgeBendpoint;
import hub.sam.mas.model.mas.GuardSpecification;

public abstract class ActivityEdgeEditPart extends PropertyAwareConnectionEditPart {
    
    private final Color color;

    public ActivityEdgeEditPart(Color color) {
        this.color = color;
    }

    public ActivityEdge getModel() {
        return (ActivityEdge) super.getModel();
    }
    
    public ActivityNodeEditPart getSource() {
        return (ActivityNodeEditPart) super.getSource();
    }

    public ActivityNodeEditPart getTarget() {
        return (ActivityNodeEditPart) super.getTarget();
    }
    
    public ActivityEdgeFigure getFigure() {
        return (ActivityEdgeFigure) super.getFigure();
    }
    
    protected IFigure createFigure() {
        return new ActivityEdgeFigure(color);
    }

    /*protected IFigure createFigure() {
        PolylineConnection figure = new PolylineConnection();
        figure.setLineWidth(1);
        figure.setBackgroundColor(color);
        figure.setForegroundColor(color);
        
        PolygonDecoration dec = new PolygonDecoration();
        dec.setScale(8, 3);
        figure.setTargetDecoration(dec);
        
        return figure;
    }*/
    
    public List getModelChildren() {
        if (getModel().getGuardSpecification() != null) {
            List<GuardSpecification> list = new ArrayList<GuardSpecification>();
            list.add( getModel().getGuardSpecification() );
            return list;
        }
        return Collections.EMPTY_LIST;
    }

    protected void refreshVisuals() {
        refreshBendpoints();
    }
    
    private void refreshBendpoints() {
        if (getConnectionFigure().getConnectionRouter() instanceof ManhattanConnectionRouter) {
            return;
        }
        List<Bendpoint> constraint = new ArrayList<Bendpoint>();
        for (ActivityEdgeBendpoint bendpoint: getModel().getBendpoints()) {
            RelativeBendpoint relativeBendpoint = new RelativeBendpoint(getConnectionFigure());
            relativeBendpoint.setRelativeDimensions(bendpoint.getFirstRelativeDimension(), bendpoint.getSecondRelativeDimension());
            constraint.add(relativeBendpoint);
        }
        getConnectionFigure().setRoutingConstraint(constraint);
    }

    public void propertyChange(PropertyChangeEvent ev) {
        if (ev.getPropertyName().equals("bendpoints")) {
            refreshVisuals();
        }
        else if (ev.getPropertyName().equals("guardSpecification")) {
            refreshVisuals();
            refreshChildren();
        }
    }

    @Override
    protected void createEditPolicies() {
        // policy for placing handles at the two ends of a ConnectionEditPart,
        // this is also the primary SelectionEditPolicy for showing focus
        installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new ConnectionEndpointEditPolicy());

        // model-based editpolicy for connections, it understands only DELETE
        installEditPolicy(EditPolicy.CONNECTION_ROLE, new ActivityEdgeConnectionEditPolicy());

        // this adds bendpoint handles to a connection
        installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE, new ActivityEdgeBendpointEditPolicy());
        
        installEditPolicy(EditPolicy.CONTAINER_ROLE, new ActivityEdgeContainerEditPolicy());
    }

}
