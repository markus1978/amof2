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

package hub.sam.mas.editor.tools;

import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.widgets.Text;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;

public class FigureCellEditorLocator implements CellEditorLocator {

    private IFigure figure;

    public FigureCellEditorLocator(IFigure figure) {
        this.figure = figure;
    }
    
    public void relocate(CellEditor celleditor) {
        Text text = (Text)celleditor.getControl();

        Rectangle rect = figure.getClientArea(Rectangle.SINGLETON);
        if (figure instanceof Label) {
            rect = ((Label) figure).getTextBounds().intersect(rect);
        }
        figure.translateToAbsolute(rect);

        org.eclipse.swt.graphics.Rectangle trim = text.computeTrim(0, 0, 0, 0);
        rect.translate(trim.x, trim.y);
        rect.width += trim.width;
        rect.height += trim.height;
        
        text.setBounds(rect.x, rect.y, rect.width, rect.height);
    }

}
