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

import hub.sam.mase.editpolicies.AbstractActivityNodeComponentEditPolicy;
import hub.sam.mase.editpolicies.ActivityNodeGraphicalNodeEditPolicy;
import hub.sam.mase.editpolicies.ActivityXYLayoutEditPolicy;
import hub.sam.mase.editpolicies.ExpansionRegionContainerEditPolicy;
import hub.sam.mase.figures.ExpansionRegionBodyFigure;
import hub.sam.mof.model.mas.Activity;
import hub.sam.mof.model.mas.ActivityChild;
import hub.sam.mof.model.mas.ExpansionRegion;
import hub.sam.mof.model.mas.ExpansionRegionBody;

import java.util.*;

import org.apache.log4j.Logger;
import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.requests.GroupRequest;

public class ExpansionRegionBodyEditPart extends AbstractGraphicalEditPart implements org.eclipse.gef.NodeEditPart {
    
    private static Logger logger = Logger.getLogger(ExpansionRegionBodyEditPart.class.getName());

    public ExpansionRegionBody getModel() {
        return (ExpansionRegionBody) super.getModel();
    }

    public ExpansionRegionBodyFigure getFigure() {
        return (ExpansionRegionBodyFigure) super.getFigure();
    }
    
    public ExpansionRegionEditPart getParent() {
        return (ExpansionRegionEditPart) super.getParent();
    }
    
    protected ChopboxAnchor anchor;
    
    public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
        if (anchor == null) {
            anchor = new ChopboxAnchor(getFigure());
        }
        return anchor;
    }

    public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
        if (anchor == null) {
            anchor = new ChopboxAnchor(getFigure());
        }
        return anchor;
    }

    public ConnectionAnchor getSourceConnectionAnchor(Request request) {
        if (anchor == null) {
            anchor = new ChopboxAnchor(getFigure());
        }
        return anchor;
    }

    public ConnectionAnchor getTargetConnectionAnchor(Request request) {
        if (anchor == null) {
            anchor = new ChopboxAnchor(getFigure());
        }
        return anchor;
    }
    
    public List getModelChildren() {
        List<ActivityChild> modelChildren = new hub.sam.mof.util.ListWrapper<ActivityChild>( getModel().getGefChildren() );
        
        if (logger.isDebugEnabled()) {
            int i=0;
            for(Object e: modelChildren) {
                logger.debug("getModelChildren(): " + i + "=" + e.getClass().getSimpleName() + "_" + java.lang.Integer.toString(e.hashCode()));
                i++;
            }
        }
        
        return modelChildren;
    }
    
    @Override
    protected IFigure createFigure() {
        ExpansionRegionBodyFigure figure = new ExpansionRegionBodyFigure();
        figure.setMode( getParent().getPresentableModeFromModel() );
        figure.setComment( getModel().getRegion().getComment() );
        return figure;
    }
    
    public IFigure getContentPane() {
        return getFigure().getContentPane();
    }
    
    @Override
    protected void createEditPolicies() {
        installEditPolicy(EditPolicy.COMPONENT_ROLE, new AbstractActivityNodeComponentEditPolicy() {
            public CompoundCommand createDeleteCommand(GroupRequest r) {
                return super.createDeleteCommand(r);
            }
        });
        
        installEditPolicy(EditPolicy.NODE_ROLE, new ActivityNodeGraphicalNodeEditPolicy() {
            @Override
            protected ActivityNodeEditPart getHostEditPart() {
                return (ActivityNodeEditPart) getHost().getParent();
            }
        });
        
        installEditPolicy(EditPolicy.LAYOUT_ROLE, new ActivityXYLayoutEditPolicy() {
            @Override
            protected Activity getHostModel() {
                return ((ExpansionRegionBody) getHost().getModel()).getRegion();
            }
        });
        
        installEditPolicy(EditPolicy.CONTAINER_ROLE, new ExpansionRegionContainerEditPolicy() {
            protected ExpansionRegion getHostModel() {
                return ((ExpansionRegionBody) getHost().getModel()).getRegion();
            }
        });
    }
    
    public boolean isSelectable() {
        return false;
    }
    
}
