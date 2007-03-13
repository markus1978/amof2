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
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * A Figure that has an AttachedNodeList at the top and bottom of its body figure.
 * 
 * @author Andreas Blunk
 */
public abstract class NodeAttachedFigure extends Figure {

    private static Logger logger = Logger.getLogger(NodeAttachedFigure.class.getName());
    
    protected Figure body;
    protected final int margin;
    
    /**
     * staticBody = true means that the body figure is added via method addBodyFigure
     * and false means that it is added via createFigure of an editpart (dynamic)
     */
    private boolean staticBody = true;

    public NodeAttachedFigure(int margin) {
        this.margin = margin;
        
        ToolbarLayout layout = new ToolbarLayout(ToolbarLayout.VERTICAL);
        setLayoutManager(layout);
        layout.setStretchMinorAxis(false);
        layout.setSpacing(0);
        layout.setMinorAlignment(ToolbarLayout.ALIGN_CENTER);
        
        setOpaque(false);
        if (MaseEditDomain.isDebugMode()) {
            setBackgroundColor(ColorConstants.white);
            setOpaque(true);
        }

        staticBody = addBodyFigure();
    }
    
    /**
     * Adds a body figure.
     * 
     * @return true if a figure was added, else false
     */
    protected abstract boolean addBodyFigure();
    
    public void validate() {
        super.validate();
        logger.debug("validating ...");
        
        logger.debug("current size: " + getSize());
        // get maximum width of children
        int maxWidth=0;
        for(Object child: getChildren()) {
            if (child instanceof AttachedNodeListFigure) {
                //logger.debug("current size: " + ((Figure) child).getSize());
                logger.debug("preferred size (child): " + ((Figure) child).getPreferredSize());
                int width = ((Figure) child).getPreferredSize().width;
                if (width > maxWidth) maxWidth = width;
            }
        }
        // check if maximum child width is wider than this figures current width
        if (maxWidth > getSize().width) {
            Rectangle constraint = new Rectangle();
            constraint.setLocation( getLocation() );
            constraint.width = maxWidth;
            constraint.height = -1;
            getParent().getLayoutManager().setConstraint(this, constraint);
            revalidate();
        }
    }
    
    /**
     * overridden to redefine the order in which children get added to the view
     */
    public void add(IFigure figure, Object constraint, int index) {
        if (staticBody) {
            if (figure instanceof AttachedNodeListFigure
                    && ((AttachedNodeListFigure) figure).isPositionTop() ) {
                index = 0;
            }
            else if (figure instanceof AttachedNodeListFigure
                    && ((AttachedNodeListFigure) figure).isPositionBottom() ) {
                index = getChildren().size();
            }
        }
        super.add(figure,constraint,index);
    }
    
    public IFigure getAnchorFigure() {
        return body;
    }
   
}