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

import hub.sam.mas.editor.figures.ExpansionNodeFigure;
import hub.sam.mas.model.mas.InExpansionNode;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.IFigure;

public class InExpansionNodeEditPart extends ExpansionNodeEditPart {
    
    public InExpansionNode getModel() {
        return (InExpansionNode) super.getModel();
    }
    
    public final IFigure getFigure() {
        return super.getFigure();
    }
    
    @Override
    protected IFigure createFigure() {
        ExpansionNodeFigure nodeFigure = new ExpansionNodeFigure(getModel().getNum(), ExpansionNodeFigure.INPUT_COLOR);
        LabeledFigure figure = new LabeledFigure(nodeFigure, LabeledFigure.POSITION_TOP);
        anchor = new ChopboxAnchor(figure.getAnchorFigure());
        return figure;
    }
    
}
