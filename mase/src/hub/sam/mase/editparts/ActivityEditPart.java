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

package hub.sam.mase.editparts;

import java.beans.PropertyChangeEvent;
import java.util.*;

import org.apache.log4j.Logger;
import org.eclipse.draw2d.BendpointConnectionRouter;
import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.FanRouter;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ManhattanConnectionRouter;
import org.eclipse.draw2d.ShortestPathConnectionRouter;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.LayerConstants;
import org.eclipse.swt.SWT;

import hub.sam.mas.model.mas.Activity;
import hub.sam.mase.editor.MaseEditDomain;
import hub.sam.mase.editpolicies.ActivityXYLayoutEditPolicy;

public class ActivityEditPart extends PropertyAwareGraphicalEditPart {

    private static Logger logger = Logger.getLogger(ActivityEditPart.class.getName());
    private ConnectionRouter router;

    public Activity getModel() {
        return (Activity) super.getModel();
    }

    /**
     * must return a new view for the model element associated with this
     * editpart
     */
    @Override
    protected IFigure createFigure() {
        Figure figure = new FreeformLayer();
        figure.setLayoutManager(new FreeformLayout());
        return figure;
    }

    /**
     * - returns the list of model elements that are children in the content
     *   pane of the view (which model elements will be represented "inside" the
     *   representation of this model element)
     * - this has nothing to do with the
     *   structure of the model
     * - by defining getModelChildren() you define the
     *   tree structure of the view
     */
    public List getModelChildren() {
        if (logger.isDebugEnabled()) {
            java.util.List<hub.sam.mas.model.mas.ActivityChild> list = new hub.sam.mof.util.ListWrapper<hub.sam.mas.model.mas.ActivityChild>( getModel().getGefChildren() );
            int i=0;
            for(Object e: list) {
                logger.debug("getModelChildren(): " + i + "=" + e.getClass().getSimpleName() + "_" + java.lang.Integer.toString(e.hashCode()));
                i++;
            }
        }
        return new hub.sam.mof.util.ListWrapper<hub.sam.mas.model.mas.ActivityChild>( getModel().getGefChildren() );
    }

    /**
     * - fills the view with data extracted from the model
     * - will be called the first time the figure gets created and
     *   on every update of the view which take place when
     *   properyChange(..) is called
     */
    public void refreshVisuals() {
        ConnectionLayer cLayer = (ConnectionLayer) getLayer(LayerConstants.CONNECTION_LAYER);
        cLayer.setConnectionRouter(getConnectionRouter());
        if (MaseEditDomain.getCachedBoolean("activityEdge.aliasOn")) {
            cLayer.setAntialias(SWT.ON);
        }
    }

    @Override
    protected void createEditPolicies() {
        // handle childs on freeform layer
        // installEditPolicy(EditPolicy.COMPONENT_ROLE, new
        // RootComponentEditPolicy());
        installEditPolicy(EditPolicy.LAYOUT_ROLE, new ActivityXYLayoutEditPolicy());
    }

    public void propertyChange(PropertyChangeEvent ev) {
        logger.debug("propertyChange: " + ev.getPropertyName());
        if (ev.getPropertyName().equals("node") || ev.getPropertyName().equals("group")) {
            /*
             * The refreshChildren() method has to be called to update the
             * children EditParts according to the getModelChildren() method.
             * 
             * If there were new model children created by a command then the
             * refreshChildren() method will look at the list of model children
             * (by calling getModelChildren()) and find those model elements
             * that do not have an EditPart associated to them. An EditPart will
             * be created for these model elements.
             * 
             * So the method must be called (by yourself) if structural changes
             * happened in the model element.
             */
            refreshChildren();
        }
    }

    public ConnectionRouter getConnectionRouter() {
        if (router == null) {
            String userdefinedRouter = MaseEditDomain.getCachedString("connectionRouter");
            if (userdefinedRouter.equals("ManhattanConnectionRouter")) {
                router = new ManhattanConnectionRouter();
            }
            else if (userdefinedRouter.equals("ShortestPathConnectionRouter")) {
                router = new ShortestPathConnectionRouter(getFigure());
            }
            else {
                router = new FanRouter();
                ((FanRouter) router).setNextRouter(new BendpointConnectionRouter());
            }
        }
        return router;
    }

}
