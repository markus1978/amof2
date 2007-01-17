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

import hub.sam.mase.editor.MaseEditDomain;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Layer;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.FreeformViewport;
import org.eclipse.draw2d.ScrollPane;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.Label;

public class ExpansionRegionBodyFigure extends RoundedRectangle {
    
    private final BodyPanel bodyPanel;
    private final Label bodyLabel;
    private final int xOffset = 4;
    private final int yOffset = 4;
    
    public ExpansionRegionBodyFigure() {
        XYLayout layout = new XYLayout();
        setLayoutManager(layout);
        setOpaque(true);
        setLineStyle(Graphics.LINE_DASH);
        int cornerSize = MaseEditDomain.getCachedInt("roundedRectangle.cornerDimension.size");
        setCornerDimensions(new Dimension(cornerSize,cornerSize));
                
        bodyLabel = new Label();
        add(bodyLabel, new Rectangle(xOffset, yOffset, -1, -1));
        int labelHeight = 10;

        bodyPanel = new BodyPanel();
        add(bodyPanel, new Rectangle(xOffset, yOffset+labelHeight+6, -1, -1));

        if (MaseEditDomain.isDebugMode()) {
            bodyLabel.setBackgroundColor(ColorConstants.yellow);
            bodyLabel.setOpaque(true);
            setBackgroundColor(ColorConstants.green);
        }
    }
    
    public Dimension getPreferredSize(int wHint, int hHint) {
        Dimension dim = new Dimension();
        int parentWidth = getParent().getSize().width;
        Dimension superDim = super.getPreferredSize(wHint, hHint).getCopy();
        superDim.expand(xOffset, yOffset);
        dim.union(superDim);
        if (parentWidth > dim.width) {
            dim.expand(parentWidth - dim.width, 0);
        }
        return superDim;
    }

    public void setMode(String text) {
        bodyLabel.setText("<<" + text + ">>");
    }
    
    public Label getBodyLabel() {
        return bodyLabel;
    }
    
    public Layer getContentPane() {
        return bodyPanel.getContentPane();
    }
    
    protected class BodyPanel extends Figure {
        private Layer contentPane;

        public BodyPanel() {
            ScrollPane scrollpane = new ScrollPane();
            contentPane = new FreeformLayer();
            contentPane.setLayoutManager(new FreeformLayout());
            setLayoutManager(new StackLayout());
            scrollpane.setViewport(new FreeformViewport());
            scrollpane.setContents(contentPane);
            add(scrollpane);

            setOpaque(true);
            setBackgroundColor(ColorConstants.white);
        }

        public Layer getContentPane() {
            return contentPane;
        }
        
        public Dimension getPreferredSize(int wHint, int hHint) {
            if (contentPane.getChildren().size() > 0) {
                return super.getPreferredSize(wHint, hHint);
            }
            else {
                return new Dimension(120,120);
            }
        }
    }
    
}