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

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.ui.views.properties.IPropertySource;

import hub.sam.mas.editor.MaseEditDomain;
import hub.sam.mas.editor.commands.GuardSpecificationDeleteCommand;
import hub.sam.mas.editor.editparts.properties.GuardSpecificationPropertySource;
import hub.sam.mas.editor.figures.GuardSpecificationFigure;
import hub.sam.mas.model.mas.GuardSpecification;

public class GuardSpecificationEditPart extends PropertyAwareGraphicalEditPart {
    
    public GuardSpecification getModel() {
        return (GuardSpecification) super.getModel();
    }
    
    public ActivityEdgeEditPart getParent() {
        return (ActivityEdgeEditPart) super.getParent();
    }

    public GuardSpecificationFigure getFigure() {
        return (GuardSpecificationFigure) super.getFigure();
    }

    @Override
    protected IFigure createFigure() {
        GuardSpecificationFigure figure = new GuardSpecificationFigure();
        if (getModel().getBody() == null) {
            getModel().setBody( MaseEditDomain.getProperties().getProperty("guardSpecification.body") );
        }
        return figure;
    }

    @Override
    protected void createEditPolicies() {
        installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, new NonResizableEditPolicy());
        
        installEditPolicy(EditPolicy.COMPONENT_ROLE, new ComponentEditPolicy() {
            protected Command createDeleteCommand(GroupRequest deleteRequest) {
                List list = deleteRequest.getEditParts();
                if (list.size() > 0) {
                    Object obj = list.get(0);
                    if (obj instanceof GuardSpecificationEditPart) {
                        return new GuardSpecificationDeleteCommand(getParent().getModel());
                    }
                }
                return null;
            }
        });        
    }
    
    protected void refreshVisuals() {
        GuardSpecificationFigure figure = getFigure();
        ActivityEdgeEditPart parent = getParent();
        figure.setText(getModel().getBody());
        getParent().setLayoutConstraint(this, figure, parent.getFigure().getLocator());
    }

    public void propertyChange(PropertyChangeEvent ev) {
        if (ev.getPropertyName() != null) {
            if (ev.getPropertyName().equals("body")) {
                refreshVisuals();
            }
        }
    }
    
    private IPropertySource propertyDescriptor = null;
    
    public Object getAdapter(Class key) {
        if (key == IPropertySource.class) {
            if (propertyDescriptor == null) {
                propertyDescriptor = new GuardSpecificationPropertySource(getModel());
            }
            return propertyDescriptor;
        }
        
        return super.getAdapter(key);
    }

}
