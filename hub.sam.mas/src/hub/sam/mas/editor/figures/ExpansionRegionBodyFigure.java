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
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;

public class ExpansionRegionBodyFigure extends RoundedRectangle {
    
    private final int xOffset = 4;
    private final int yOffset = 4;
    private final ContentPane contentPane;
    
    private class ContentPane extends Figure {

        private final Label modeLabel;
        private final BodyPanel bodyPanel;
        private final CommentFigure comment;
        
        public ContentPane() {
            ToolbarLayout layout = new ToolbarLayout(ToolbarLayout.VERTICAL);
            setLayoutManager(layout);
            layout.setStretchMinorAxis(false);
            layout.setSpacing(2);
            layout.setMinorAlignment(ToolbarLayout.ALIGN_TOPLEFT);

            modeLabel = new Label();
            modeLabel.setFont(new Font(null, MaseEditDomain.getDefaultFontName(),
                        MaseEditDomain.getDefaultFontSize(), SWT.NORMAL));
            add(modeLabel);

            bodyPanel = new BodyPanel();
            add(bodyPanel);
            
            comment = new CommentFigure(true);
            add(comment);

            if (MaseEditDomain.isDebugMode()) {
                modeLabel.setBackgroundColor(ColorConstants.yellow);
                modeLabel.setOpaque(true);
                setBackgroundColor(ColorConstants.red);
            }
        }
        
        public Dimension getPreferredSize(int wHint, int hHint) {
            int limitWidth = bodyPanel.getPreferredSize(wHint, hHint).width;
            return super.getPreferredSize(limitWidth, hHint);
        }
        
        public void setMode(String text) {
            modeLabel.setText("<<" + text + ">>");
        }
        
        public void setComment(String text) {
            comment.setComment(text);
        }

        public BodyPanel getBodyPanel() {
            return bodyPanel;
        }
        
    }

    public ExpansionRegionBodyFigure() {
        XYLayout layout = new XYLayout();
        setLayoutManager(layout);
        setOpaque(true);
        setLineStyle(Graphics.LINE_DASH);
        int cornerSize = MaseEditDomain.getDefaultCornerSize();
        setCornerDimensions(new Dimension(cornerSize,cornerSize));
        
        contentPane = new ContentPane();
        add(contentPane, new Rectangle(xOffset, yOffset, -1, -1));

        if (MaseEditDomain.isDebugMode()) {
            setBackgroundColor(ColorConstants.green);
        }
    }
    
    public Dimension getPreferredSize(int wHint, int hHint) {
        /*Dimension dim = new Dimension();
        int parentWidth = getParent().getSize().width;*/
        Dimension superDim = super.getPreferredSize(wHint, hHint).getCopy();
        superDim.expand(xOffset, yOffset);
        /*dim.union(superDim);
        if (parentWidth > dim.width) {
            dim.expand(parentWidth - dim.width, 0);
        }*/
        return superDim;
    }

    public void setMode(String text) {
        contentPane.setMode(text);
    }
    
    public void setComment(String text) {
        contentPane.setComment(text);
    }
    
    public Layer getContentPane() {
        return contentPane.getBodyPanel().getContentPane();
    }
    
    private class BodyPanel extends Figure {
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