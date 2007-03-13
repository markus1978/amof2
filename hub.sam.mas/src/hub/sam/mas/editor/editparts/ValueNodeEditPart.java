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

import hub.sam.mas.editor.MaseEditDomain;
import hub.sam.mas.editor.editparts.properties.ValueNodePropertySource;
import hub.sam.mas.editor.editpolicies.ValueNodeDirectEditPolicy;
import hub.sam.mas.editor.figures.ValueNodeFigure;
import hub.sam.mas.editor.tools.DirectEditManagerImpl;
import hub.sam.mas.editor.tools.FigureCellEditorLocator;
import hub.sam.mas.model.mas.ValueNode;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.draw2d.geometry.Rectangle;

public class ValueNodeEditPart extends ObjectNodeEditPart implements NodeEditPart, EditableEditPart {

    private DirectEditManagerImpl editManager;
    
    public ValueNode getModel() {
        return (ValueNode) super.getModel();
    }

    public ValueNodeFigure getFigure() {
        return (ValueNodeFigure) super.getFigure();
    }

    @Override
    protected IFigure createFigure() {
        IFigure figure = new ValueNodeFigure( MaseEditDomain.getCachedInt("activityNode.innerMargin") );

        anchor = new ChopboxAnchor(figure);

        if (getModel().getName() == null) {
            getModel().setName( MaseEditDomain.getProperties().getProperty("valueNode.body") );
        }

        return figure;
    }

    @Override
    protected void createEditPolicies() {
        super.createEditPolicies();
        installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new ValueNodeDirectEditPolicy());
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
        return getModel().getName();
    }
    
    public void propertyChange(PropertyChangeEvent ev) {
        if (ev.getPropertyName().equals("name") || ev.getPropertyName().equals("rectangle")) {
            refreshVisuals();
        }
        else {
            super.propertyChange(ev);
        }
    }
    
    protected void refreshVisuals() {
        super.refreshVisuals();

        ValueNode model = getModel();
        ValueNodeFigure figure = getFigure();
        figure.setText(model.getName());
        
        // set layout constraint for this ValueNode in Activity
        Rectangle rectangleCopy = (Rectangle) model.getRectangle().getCopy();
        rectangleCopy.height = figure.getPreferredSize(rectangleCopy.width, -1).height;
        getParent().setLayoutConstraint(this, figure, rectangleCopy);
    }
    
    private IPropertySource propertyDescriptor = null;
    
    public Object getAdapter(Class key) {
        if (key == IPropertySource.class) {
            if (propertyDescriptor == null) {
                propertyDescriptor = new ValueNodePropertySource(getModel());
            }
            return propertyDescriptor;
        }
        
        return super.getAdapter(key);
    }
    
}
