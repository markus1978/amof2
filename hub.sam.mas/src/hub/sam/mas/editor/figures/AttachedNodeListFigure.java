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

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.swt.graphics.Color;
import org.eclipse.draw2d.geometry.Dimension;

import hub.sam.mas.editor.MaseEditDomain;

public class AttachedNodeListFigure extends Figure {
    
    private int height = 0;
    private enum Position {TOP, BOTTOM};
    private Position position = Position.TOP;
    
    private PlaceHolderFigure placeHolderLeft;
    private PlaceHolderFigure placeHolderRight;
    private ContentPane contentPane;

    public AttachedNodeListFigure(Color color) {
        ToolbarLayout layout = new ToolbarLayout(ToolbarLayout.HORIZONTAL);
        setLayoutManager(layout);
        layout.setStretchMinorAxis(false);
        layout.setSpacing(0);
        layout.setMinorAlignment(ToolbarLayout.ALIGN_CENTER);
        
        setOpaque(false);
        if (MaseEditDomain.isDebugMode()) {
            setBackgroundColor(ColorConstants.red);
            setOpaque(true);
        }

        placeHolderLeft = new PlaceHolderFigure();
        add(placeHolderLeft);
        
        contentPane = new ContentPane(color);
        add(contentPane);
        
        placeHolderRight = new PlaceHolderFigure();
        add(placeHolderRight);
    }
    
    public int getHeight() {
        return height;
    }
    
    public void expandFigure() {
        height = PinFigure.SIZE;
    }

    public void shrinkFigure() {
        height = 0;
    }
    
    public void setPositionTop() {
        position = Position.TOP;
    }
    
    public boolean isPositionTop() {
        return position == Position.TOP;
    }

    public void setPositionBottom() {
        position = Position.BOTTOM;
    }
    
    public boolean isPositionBottom() {
        return position == Position.BOTTOM;
    }
    
    public IFigure getContentPane() {
        return contentPane;
    }
    
    public Dimension getMinimumSize(int wHint, int hHint) {
        return getPreferredSize(wHint, hHint);
    }

    public Dimension getPreferredSize(int wHint, int hHint) {
        int numChildren = getContentPane().getChildren().size();
        int prefWidth = 0;
        int maxHeight = 0;
        for(Object childFigure: getContentPane().getChildren()) {
            Dimension prefChildSize = ((IFigure) childFigure).getPreferredSize(wHint, hHint);
            prefWidth += prefChildSize.width;
            if (prefChildSize.height > maxHeight) {
                maxHeight = prefChildSize.height;
            }
        }
        prefWidth += (numChildren-1) * contentPane.SPACING + 2 * placeHolderLeft.WIDTH;
        return new Dimension(prefWidth, maxHeight);
    }

    private class PlaceHolderFigure extends Figure {
        
        public final int WIDTH = 10;
        private final Dimension dimension = new Dimension(WIDTH, PinFigure.SIZE);
        
        public PlaceHolderFigure() {
            setOpaque(false);
        }

        public Dimension getPreferredSize(int wHint, int hHint) {
            return dimension;
        }

        public Dimension getMinSize() {
            return dimension;
        }

        public Dimension getMaxSize() {
            return dimension;
        }

    }
    
    private class ContentPane extends Figure {
        
        public final int SPACING = 10;
        
        public ContentPane(Color color) {
            ToolbarLayout layout = new ToolbarLayout(ToolbarLayout.HORIZONTAL);
            setLayoutManager(layout);
            layout.setStretchMinorAxis(false);
            layout.setMinorAlignment(ToolbarLayout.ALIGN_BOTTOMRIGHT);
            layout.setSpacing(SPACING);

            setOpaque(false);
            if (MaseEditDomain.isDebugMode()) {
                setBackgroundColor(color);
                setOpaque(true);
            }
        }

    }
    
}