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
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

public class FinalNodeFigure extends Ellipse {
    
    public static final Color COLOR = ColorConstants.white;
    public static final int SIZE = 20;
    private final Dimension preferredSize;
    private final BlackSpot blackSpot;
    
    private class BlackSpot extends Ellipse {
        public static final int SIZE = 12;
        private final Dimension preferredSize;
        
        public BlackSpot() {
            preferredSize = new Dimension(SIZE,SIZE);
            setOpaque(true);
            setBackgroundColor(ColorConstants.black);
        }
        
        public Dimension getPreferredSize(int wHint, int hHint) {
            return preferredSize;
        }
    }
    
    public FinalNodeFigure() {
        preferredSize = new Dimension(SIZE,SIZE);
        setOpaque(true);
        setBackgroundColor(COLOR);

        XYLayout layout = new XYLayout();
        setLayoutManager(layout);
        
        blackSpot = new BlackSpot();
        add(blackSpot);
    }
    
    public Dimension getPreferredSize(int wHint, int hHint) {
        return preferredSize;
    }
    
    public void validate() {
        LayoutManager layout = getLayoutManager();
        layout.setConstraint(blackSpot, new Rectangle(4, 4, -1, -1));        
        super.validate();
    }

}
