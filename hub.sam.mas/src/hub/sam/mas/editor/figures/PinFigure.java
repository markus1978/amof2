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

import hub.sam.mas.editor.MaseEditDomain;

import org.apache.log4j.Logger;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;

public class PinFigure extends RectangleFigure {
    
    private static Logger logger = Logger.getLogger(PinFigure.class.getName());
    
    private final LineBorder border;
    private final Dimension preferredSize;
    private final Label label;
    public static final Color INPUT_COLOR = ColorConstants.white;
    public static final Color OUTPUT_COLOR = ColorConstants.yellow;
    public static final int SIZE = 15;
    
    public PinFigure(int num, Color color) {
        preferredSize = new Dimension(SIZE,SIZE);
        
        border = new LineBorder();
        border.setWidth(1);
        border.setColor(ColorConstants.black);
        setBorder(border);

        XYLayout layout = new XYLayout();
        setLayoutManager(layout);
        
        setOpaque(true);
        if (MaseEditDomain.isDebugMode()) {
            setBackgroundColor(color);
            
            if (num >= 0) {
                label = new Label();
                label.setText(Integer.toString(num));
                add(label);
            }
            else {
                label = null;
            }
        }
        else {
            setBackgroundColor(ColorConstants.white);
            label = null;
        }
        
        logger.debug("created PinFigure");
    }
    
    public Dimension getPreferredSize(int wHint, int hHint) {
        return preferredSize;
    }

    public void validate() {
        if (label != null) {
            LayoutManager layout = getLayoutManager();
            layout.setConstraint(label, new Rectangle(2, 0, -1, -1));
            super.validate();
        }
    }
}
