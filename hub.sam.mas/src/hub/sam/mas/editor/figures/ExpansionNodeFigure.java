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

import org.apache.log4j.Logger;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.swt.graphics.Color;

import java.util.*;

/**
 * Four vertical adjustet PinFigures substitute one ExpansionNodeFigure.
 * 
 * @author Andreas Blunk
 */
public class ExpansionNodeFigure extends RectangleFigure {
    
    private static Logger logger = Logger.getLogger(ExpansionNodeFigure.class.getName());
    
    private final List<PinFigure> pinArrangement = new ArrayList<PinFigure>();
    public static final Color INPUT_COLOR = PinFigure.INPUT_COLOR;
    public static final Color OUTPUT_COLOR = PinFigure.OUTPUT_COLOR;
    
    public ExpansionNodeFigure(int num, Color color) {
        ToolbarLayout layout = new ToolbarLayout(ToolbarLayout.HORIZONTAL);
        setLayoutManager(layout);
        layout.setStretchMinorAxis(false);
        layout.setSpacing(0);
        layout.setMinorAlignment(ToolbarLayout.ALIGN_CENTER);

        setOpaque(false);
        pinArrangement.add(new PinFigure(num, color));
        for(int i=0; i<3; i++) {
            pinArrangement.add(new PinFigure(-1, color));
        }
        
        for(PinFigure pin: pinArrangement) {
            add(pin);
        }
        
        logger.debug("created ExpansionNodeFigure");
    }
    
}
