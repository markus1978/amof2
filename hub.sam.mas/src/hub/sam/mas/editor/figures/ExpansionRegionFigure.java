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
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;

public class ExpansionRegionFigure extends NodeAttachedFigure {
    
    private static Logger logger = Logger.getLogger(ExpansionRegionFigure.class.getName());

    public ExpansionRegionFigure(int margin) {
        super(margin);
    }
    
    protected boolean addBodyFigure() {
        return false;
    }
    
    public Dimension getPreferredSize(int wHint, int hHint) {
        return super.getPreferredSize(wHint, hHint);
    }
    
    public int getAttachedNodeListHeight() {
        int height = 0;
        for(Object child: getChildren()) {
            if (child instanceof AttachedNodeListFigure) {
                height += ((AttachedNodeListFigure) child).getHeight(); 
            }
        }
        return height;
    }
    
    public void validate() {
        super.validate();
        logger.debug("validating ...");
        logger.debug("current size: " + getSize());
        
        // get maximum width of children
        int maxWidth=0;
        for(Object child: getChildren()) {
            if (child instanceof AttachedNodeListFigure || child instanceof ExpansionRegionBodyFigure) {
                int width = ((Figure) child).getPreferredSize().width;
                logger.debug("preferred width (child): " + width);
                if (width > maxWidth) maxWidth = width;
            }
        }
        
        if (maxWidth != getSize().width) {
            Rectangle constraint = (Rectangle) getParent().getLayoutManager().getConstraint(this);
            constraint.width = maxWidth;
            //constraint.height = -1;
            getParent().getLayoutManager().setConstraint(this, constraint);
            revalidate();
        }
    }

}