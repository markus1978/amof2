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

package hub.sam.mas.model.mas;

import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;

public class ActivityEdgeBendpoint implements java.io.Serializable, Bendpoint {

    static final long serialVersionUID = 1;
    private float weight = 0.5f;
    private Dimension d1, d2;

    public ActivityEdgeBendpoint() {
    }

    public Dimension getFirstRelativeDimension() {
        return d1;
    }

    public Point getLocation() {
        return null;
    }

    public Dimension getSecondRelativeDimension() {
        return d2;
    }

    public float getWeight() {
        return weight;
    }

    public void setRelativeDimensions(Dimension dim1, Dimension dim2) {
        d1 = dim1;
        d2 = dim2;
    }

    public void setWeight(float w) {
        weight = w;
    }

}
