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

import hub.sam.mas.model.mas.ControlNode;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.NodeEditPart;

public abstract class ControlNodeEditPart extends ActivityNodeEditPart implements NodeEditPart {
    
    public ControlNode getModel() {
        return (ControlNode) super.getModel();
    }
    
    public void propertyChange(PropertyChangeEvent ev) {
        if (ev.getPropertyName().equals("rectangle")) {
            refreshVisuals();
        }
        else {
            super.propertyChange(ev);
        }
    }
    
    protected void refreshVisuals() {
        Rectangle rectangleCopy = (Rectangle) getModel().getRectangle().getCopy();
        getParent().setLayoutConstraint(this, getFigure(), rectangleCopy);        
    }
    
}
