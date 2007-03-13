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

package hub.sam.mas.editor.figures;

//import org.eclipse.draw2d.PolygonDecoration;
import hub.sam.mas.editor.MaseEditDomain;

import org.eclipse.draw2d.PolylineDecoration;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.MidpointLocator;
import org.eclipse.draw2d.Locator;
import org.eclipse.swt.graphics.Color;

public class ActivityEdgeFigure extends PolylineConnection {
    
    private MidpointLocator midpoint;
    
    public ActivityEdgeFigure(Color color) {
        setLineWidth(1);
        
        if (MaseEditDomain.isDebugMode()) {
            setBackgroundColor(color);
            setForegroundColor(color);
        }
        
        PolylineDecoration dec = new PolylineDecoration();
        //PolygonDecoration dec = new PolygonDecoration();
        //dec.setScale(8, 3);
        setTargetDecoration(dec);
        
        midpoint = new MidpointLocator(this,0);
    }
    
    public Locator getLocator() {
        return midpoint;
    }

}
