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

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.swt.graphics.Color;

import hub.sam.mase.figures.ActivityEdgeFigure;
import hub.sam.mof.model.mas.ObjectFlow;

public class ObjectFlowEditPart extends ActivityEdgeEditPart {
    
    private static final Color color = new Color(null, 204, 0, 153);

    public ObjectFlowEditPart() {
        super(color);
    }
    
    public ObjectFlow getModel() {
        return (ObjectFlow) super.getModel();
    }
    
    public ObjectNodeEditPart getSource() {
        return (ObjectNodeEditPart) super.getSource();
    }

    public ObjectNodeEditPart getTarget() {
        return (ObjectNodeEditPart) super.getTarget();
    }
    
    protected IFigure createFigure() {
        ActivityEdgeFigure figure = new ActivityEdgeFigure(color);
        figure.setLineStyle(Graphics.LINE_DASH);
        return figure;
    }
    
}
