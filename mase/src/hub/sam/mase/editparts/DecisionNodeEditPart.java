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
import java.util.List;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.ui.views.properties.IPropertySource;

import hub.sam.mase.editor.MaseEditDomain;
import hub.sam.mase.editparts.properties.DecisionNodePropertySource;
import hub.sam.mase.editpolicies.DecisionNodeContainerEditPolicy;
import hub.sam.mase.figures.DecisionNodeFigure;
import hub.sam.mase.m2model.DecisionNode;
import hub.sam.mase.m2model.ContextPinList;

// a DecisionNode behaves more like an OpaqueAction which is an ActivityNode
public class DecisionNodeEditPart extends ActivityNodeEditPart {
    
    public DecisionNode getModel() {
        return (DecisionNode) super.getModel();
    }

    public DecisionNodeFigure getFigure() {
        return (DecisionNodeFigure) super.getFigure();
    }
    
    public List getModelChildren() {
        return new hub.sam.mof.util.ListWrapper<ContextPinList>( getModel().getContextList() );
    }

    @Override
    protected IFigure createFigure() {
        DecisionNodeFigure figure = new DecisionNodeFigure();
        
        anchor = new ChopboxAnchor(figure.getAnchorFigure());
        
        if (getModel().getBody() == null) {
            getModel().setBody( MaseEditDomain.getProperties().getProperty("decisionNode.body") );
        }
        
        return figure;
    }

    @Override
    protected void createEditPolicies() {
        super.createEditPolicies();
        installEditPolicy(EditPolicy.CONTAINER_ROLE, new DecisionNodeContainerEditPolicy());
    }
    
    public void propertyChange(PropertyChangeEvent ev) {
        if (ev.getPropertyName().equals("body") || ev.getPropertyName().equals("rectangle")
                || ev.getPropertyName().equals("comment")) {
            refreshVisuals();
        }
        else if (ev.getPropertyName().equals("context")) {
            ((EditPart) getChildren().get(0)).refresh();
            refreshVisuals();
        }
        else {
            super.propertyChange(ev);
        }
    }
    
    protected void refreshVisuals() {
        DecisionNode model = getModel();
        DecisionNodeFigure figure = getFigure();
        
        figure.setText(model.getBody());
        figure.setComment(model.getComment());
        
        Rectangle rectangleCopy = (Rectangle) model.getRectangle().getCopy();
        if (rectangleCopy.width == -1) {
            rectangleCopy.width = MaseEditDomain.getCachedInt("decisionNode.width");
        }
        rectangleCopy.height = -1;
        getParent().setLayoutConstraint(this, figure, rectangleCopy);        
    }
    
    private IPropertySource propertyDescriptor = null;
    
    public Object getAdapter(Class key) {
        if (key == IPropertySource.class) {
            if (propertyDescriptor == null) {
                propertyDescriptor = new DecisionNodePropertySource(getModel());
            }
            return propertyDescriptor;
        }
        
        return super.getAdapter(key);
    }

}
