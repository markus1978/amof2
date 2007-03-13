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

import hub.sam.mas.editor.MaseEditDomain;
import hub.sam.mas.editor.editparts.properties.ExpansionRegionPropertySource;
import hub.sam.mas.editor.editpolicies.ExpansionRegionComponentEditPolicy;
import hub.sam.mas.editor.editpolicies.ExpansionRegionContainerEditPolicy;
import hub.sam.mas.editor.figures.ExpansionRegionFigure;
import hub.sam.mas.model.mas.ExpansionKind;
import hub.sam.mas.model.mas.ExpansionNodeList;
import hub.sam.mas.model.mas.ExpansionRegion;
import hub.sam.mas.model.mas.OutExpansionNodeList;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.draw2d.geometry.Rectangle;

public class ExpansionRegionEditPart extends ActionEditPart implements org.eclipse.gef.NodeEditPart {

    private static Logger logger = Logger.getLogger(ExpansionRegionEditPart.class.getName());
    
    public ExpansionRegion getModel() {
        return (ExpansionRegion) super.getModel();
    }

    public ExpansionRegionFigure getFigure() {
        return (ExpansionRegionFigure) super.getFigure();
    }
    
    public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
        return getBodyEditPart().getSourceConnectionAnchor(connection);
    }

    public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
        return getBodyEditPart().getTargetConnectionAnchor(connection);
    }

    public ConnectionAnchor getSourceConnectionAnchor(Request request) {
        return getBodyEditPart().getSourceConnectionAnchor(request);
    }

    public ConnectionAnchor getTargetConnectionAnchor(Request request) {
        return getBodyEditPart().getTargetConnectionAnchor(request);
    }
    
    public List getModelChildren() {
        List<ExpansionNodeList> modelChildren = new hub.sam.mof.util.ListWrapper<ExpansionNodeList>( getModel().getList() );
        List<Object> newList = new ArrayList<Object>();
        
        // enforce order: modelChildren = {inList, outList}
        Object firstChild = modelChildren.get(0);
        if (firstChild instanceof OutExpansionNodeList) {
            newList.add( modelChildren.get(1) );
            newList.add( modelChildren.get(0) );
        }
        else {
            newList.addAll(modelChildren);
        }
        // enforce order: modelChildren = {inList, body, outList}
        newList.add(1, getModel().getBody());
        
        if (logger.isDebugEnabled()) {
            int i=0;
            for(Object e: newList) {
                logger.debug("getModelChildren(): " + i + "=" + e.getClass().getSimpleName() + "_" + java.lang.Integer.toString(e.hashCode()));
                i++;
            }
        }
        
        return newList;
    }
    
    @Override
    protected IFigure createFigure() {
        ExpansionRegionFigure figure = new ExpansionRegionFigure( MaseEditDomain.getCachedInt("expansionRegion.innerMargin") );

        anchor = new ChopboxAnchor(figure.getAnchorFigure());

        if (getModel().getMode() == null) {
            getModel().setMode(ExpansionKind.ITERATIVE);
        }

        return figure;
    }
    
    @Override
    protected void createEditPolicies() {
        super.createEditPolicies();
        // handles delete
        installEditPolicy(EditPolicy.COMPONENT_ROLE, new ExpansionRegionComponentEditPolicy());
        // handles ExpansionNodes
        installEditPolicy(EditPolicy.CONTAINER_ROLE, new ExpansionRegionContainerEditPolicy());
    }

    public void propertyChange(PropertyChangeEvent ev) {
        if (ev.getPropertyName().equals("mode") || ev.getPropertyName().equals("rectangle")
                || ev.getPropertyName().equals("comment")) {
            logger.debug("property changed: mode or rectangle");
            refreshVisuals();
        }
        else if (ev.getPropertyName().equals("node") || ev.getPropertyName().equals("group")) {
            logger.debug("property changed: node");
            getBodyEditPart().refresh();
            refreshVisuals();
        }
        else if (ev.getPropertyName().equals("inputElement")) {
            logger.debug("property changed: inputElement");
            getInListEditPart().refresh();
            refreshVisuals();
        }
        else if (ev.getPropertyName().equals("outputElement")) {
            logger.debug("property changed: outputElement");
            getOutListEditPart().refresh();
            refreshVisuals();
        }
        else {
            super.propertyChange(ev);
        }
    }
    
    private ExpansionRegionBodyEditPart getBodyEditPart() {
        return (ExpansionRegionBodyEditPart) getChildren().get(1);
    }
    
    private AttachedNodeListEditPart getInListEditPart() {
        return (AttachedNodeListEditPart) getChildren().get(0);
    }

    private AttachedNodeListEditPart getOutListEditPart() {
        return (AttachedNodeListEditPart) getChildren().get(2);
    }
    
    private boolean childConstructionComplete() {
        return getChildren().size() == 3;
    }

    public String getPresentableModeFromModel() {
        ExpansionRegion model = getModel();
        if (model.getMode() != null) {
            return model.getMode().toString().toLowerCase();
        }
        return "";
    }
    
    protected void refreshVisuals() {
        ExpansionRegion model = getModel();
        ExpansionRegionFigure figure = getFigure();
        
        if (childConstructionComplete()) {
            getBodyEditPart().getFigure().setMode( getPresentableModeFromModel() );
            getBodyEditPart().getFigure().setComment( getModel().getComment() );
        }
        
        Rectangle rectangleCopy = (Rectangle) model.getRectangle().getCopy();
        logger.debug("saved rectangle: " + rectangleCopy);
        if (rectangleCopy.width == -1) rectangleCopy.width = 120;
        //if (rectangleCopy.height == -1) rectangleCopy.height = 120;
        //rectangleCopy.width = -1;
        rectangleCopy.height = -1;
        getParent().setLayoutConstraint(this, figure, rectangleCopy);
    }
    
    private IPropertySource propertyDescriptor = null;
    
    public Object getAdapter(Class key) {
        if (key == IPropertySource.class) {
            if (propertyDescriptor == null) {
                propertyDescriptor = new ExpansionRegionPropertySource(getModel());
            }
            return propertyDescriptor;
        }
        
        return super.getAdapter(key);
    }
}
