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

    @Override
    protected void fillShape(Graphics graphics) {
        Rectangle r = getBounds();
        PointList points = new PointList();
        points.addPoint(r.x, r.y + r.height / 2);
        points.addPoint(r.x + r.width / 2, r.y);
        points.addPoint(r.x + r.width, r.y + r.height / 2);
        points.addPoint(r.x + r.width / 2, r.y + r.height);
        graphics.fillPolygon(points);
    }

    @Override
    protected void outlineShape(Graphics graphics) {
        Rectangle r = getBounds();
        Point left = new Point(r.x, r.y + r.height / 2);
        Point top = new Point(r.x + r.width / 2, r.y);
        Point right = new Point(r.x + r.width, r.y + r.height / 2);
        Point bottom = new Point(r.x + r.width / 2, r.y + r.height);
        graphics.drawLine(left, top);
        graphics.drawLine(top, right);
        graphics.drawLine(right, bottom);
        graphics.drawLine(bottom, left);
    }

}
