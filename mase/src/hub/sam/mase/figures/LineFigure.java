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

package hub.sam.mase.figures;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;

public class LineFigure extends Figure {
    
    private int linestyle = Graphics.LINE_SOLID;
    private int height = 1;
    
    public LineFigure() {
        setOpaque(true);
        setBackgroundColor(ColorConstants.black);
    }
    
    public Dimension getPreferredSize(int wHint, int hHint) {
        return new Dimension(wHint,height);
    }
    
    @Override
    public void paintFigure(Graphics graphics) {
        Rectangle r = getBounds();
        graphics.setLineStyle(linestyle);
        graphics.drawLine(r.getTopLeft(), r.getTopRight());
    }

    public int getLinestyle() {
        return linestyle;
    }

    public void setLinestyle(int linestyle) {
        this.linestyle = linestyle;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
}
