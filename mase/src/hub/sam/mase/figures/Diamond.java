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

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;

public class Diamond extends Shape {
    
    private Point getTop(Rectangle bounds) {
        return new Point(bounds.x + bounds.width / 2, bounds.y);
    }

    private Point getLeft(Rectangle bounds) {
        return new Point(bounds.x, bounds.y + bounds.height / 2);
    }

    private Point getRight(Rectangle bounds) {
        return new Point(bounds.x + bounds.width - lineWidth, bounds.y + bounds.height / 2);
    }

    private Point getBottom(Rectangle bounds) {
        return new Point(bounds.x + bounds.width / 2, bounds.y + bounds.height - lineWidth);
    }

    @Override
    protected void fillShape(Graphics graphics) {
        Rectangle bounds = getBounds();
        PointList points = new PointList();
        points.addPoint( getLeft(bounds) );
        points.addPoint( getTop(bounds) );
        points.addPoint( getRight(bounds) );
        points.addPoint( getBottom(bounds) );
        
        graphics.fillPolygon(points);
    }

    @Override
    protected void outlineShape(Graphics graphics) {
        Rectangle bounds = getBounds();
        Point left = getLeft(bounds);
        Point top = getTop(bounds);
        Point right = getRight(bounds);
        Point bottom = getBottom(bounds);
        
        graphics.drawLine(left, top);
        graphics.drawLine(top, right);
        graphics.drawLine(right, bottom);
        graphics.drawLine(bottom, left);
    }

}
