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

import hub.sam.mas.model.mas.ActionKind;
import hub.sam.mas.model.mas.OpaqueAction;
import hub.sam.mase.editor.MaseEditDomain;
import hub.sam.mase.editparts.properties.OpaqueActionPropertySource;
import hub.sam.mase.editpolicies.OpaqueActionDirectEditPolicy;
import hub.sam.mase.editpolicies.ActionContainerEditPolicy;
import hub.sam.mase.figures.OpaqueActionFigure;
import hub.sam.mase.tools.FigureCellEditorLocator;
import hub.sam.mase.tools.DirectEditManagerImpl;

import java.beans.PropertyChangeEvent;

import org.apache.log4j.Logger;
import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;

public class OpaqueActionEditPart extends ActionEditPart
        implements org.eclipse.gef.NodeEditPart, EditableEditPart {

    private static Logger logger = Logger.getLogger(OpaqueActionEditPart.class.getName());
    
    private DirectEditManagerImpl editManager;

    public OpaqueAction getModel() {
        return (OpaqueAction) super.getModel();
    }

    public OpaqueActionFigure getFigure() {
        return (OpaqueActionFigure) super.getFigure();
    }
    
    @Override
    protected IFigure createFigure() {
        OpaqueActionFigure figure = new OpaqueActionFigure( MaseEditDomain.getCachedInt("activityNode.innerMargin"),
                ActionKind.EXPRESSION );

        if (getModel().getActionBody() == null) {
            getModel().setActionBody( MaseEditDomain.getProperties().getProperty("opaqueAction.body") );
        }
        
        /*
         * Anchor points represent the ends of a connection. They specify the
         * location of a connection's endpoints. A ChopboxAnchor is located at
         * the border of the figure where the connection would intersect the
         * figure if it continued to the figures center point.
         */
        anchor = new ChopboxAnchor(figure.getAnchorFigure());

        return figure;
    }
    
    @Override
    protected void createEditPolicies() {
        super.createEditPolicies();
        installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new OpaqueActionDirectEditPolicy());
        installEditPolicy(EditPolicy.CONTAINER_ROLE, new ActionContainerEditPolicy());
    }

    // catch DIRECT_EDIT request and handle it specially
    public void performRequest(Request request) {
        if (request.getType() == RequestConstants.REQ_DIRECT_EDIT) {
            if (editManager == null) {
                FigureCellEditorLocator locator = new FigureCellEditorLocator( getFigure().getLocatorFigure() );
                editManager = new DirectEditManagerImpl(this, TextCellEditor.class, locator);
            }
            editManager.show();
        }
    }
    
    public String getDirectEditValue() {
        return getModel().getActionBody();
    }
    
    protected void addChild(EditPart child, int index) {
        super.addChild(child, index);
    }
    
    public void propertyChange(PropertyChangeEvent ev) {
        if (ev.getPropertyName().equals("actionBody") || ev.getPropertyName().equals("actionKind")
                || ev.getPropertyName().equals("rectangle") || ev.getPropertyName().equals("comment")) {
            refreshVisuals();
        }
        else if (ev.getPropertyName().equals("input")) {
            ((EditPart) getChildren().get(0)).refresh();
            refreshVisuals();
            logger.debug("property input changed");
        }
        else if (ev.getPropertyName().equals("output")) {
            ((EditPart) getChildren().get(1)).refresh();
            refreshVisuals();
            logger.debug("property output changed");
        }
        else {
            super.propertyChange(ev);
        }
    }
    
    /**
     * This method refreshes the view of the current editpart according to the
     * new state of the model. The view gets filled with data extracted from the
     * model.
     */
    protected void refreshVisuals() {
        OpaqueAction model = getModel();
        OpaqueActionFigure figure = getFigure();
        
        figure.setActionKind( model.getActionKind() );
        figure.setText( getModel().getActionBody() );
        figure.setComment( getModel().getComment() );
        
        Rectangle rectangleCopy = (Rectangle) model.getRectangle().getCopy();
        logger.debug("refreshVisuals: saved rectangle = " + rectangleCopy);
        if (rectangleCopy.width == -1) {
            rectangleCopy.width = MaseEditDomain.getCachedInt("opaqueAction.width");
        }
        rectangleCopy.height = -1;
        
        logger.debug("refreshVisuals: transformed rectangle = " + rectangleCopy);
        getParent().setLayoutConstraint(this, figure, rectangleCopy);
    }
    
    private IPropertySource propertyDescriptor = null;
    
    /**
     * By default GEF checks if the corresponding model element implements
     * the IPropertySource interface. If it does, it can be viewed in a
     * property sheet.
     * 
     * In my opinion, things like property descriptors should not be part of the
     * model. It just helps to make the model editable via a property sheet and
     * therefore it should be part of the editpart.
     * Another reason for this decision is that because we are using AMOF as the
     * model, we cannot make model elements implement some eclipse interfaces
     * without messing things up in the AMOF framework. 
     * 
     * So we have to change GEF's default behaviour and delegate all requests for
     * an IPropertySource object to an ActivityPropertyDescriptor object which
     * implements the IPropertySource interface.
     */
    public Object getAdapter(Class key) {
        if (key == IPropertySource.class) {
            if (propertyDescriptor == null) {
                propertyDescriptor = new OpaqueActionPropertySource(getModel());
            }
            return propertyDescriptor;
        }
        
        return super.getAdapter(key);
    }
}
