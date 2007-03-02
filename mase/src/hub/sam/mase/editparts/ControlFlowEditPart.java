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

import org.eclipse.draw2d.ColorConstants;

import hub.sam.mof.model.mas.ControlFlow;

public class ControlFlowEditPart extends ActivityEdgeEditPart {

    public ControlFlowEditPart() {
        super(ColorConstants.black);
    }
    
    public ControlFlow getModel() {
        return (ControlFlow) super.getModel();
    }
    
    public ActivityNodeEditPart getSource() {
        return (ActivityNodeEditPart) super.getSource();
    }

    public ActivityNodeEditPart getTarget() {
        return (ActivityNodeEditPart) super.getTarget();
    }
    
}
