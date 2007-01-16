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
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.ParagraphTextLayout;
import org.eclipse.draw2d.text.TextFlow;

class DecisionNodeBodyFigure extends Figure implements EditableFigure {
    private final DecisionNodeDiamond diamond;
    private final TextFlow bodyFlow;
    private final FlowPage flowPage;
    
    private class DecisionNodeDiamond extends Diamond {
        private final Dimension preferredSize;
        public static final int WIDTH = 50;
        public static final int HEIGHT = 30;
        
        public DecisionNodeDiamond() {
            preferredSize = new Dimension(WIDTH,HEIGHT);
            setOpaque(true);
            if (MaseEditDomain.isDebugMode()) {
                setBackgroundColor(ColorConstants.green);
            }
            else {
                setBackgroundColor(ColorConstants.white);
            }
        }
        
        public Dimension getPreferredSize(int wHint, int hHint) {
            return preferredSize;
        }
    }
    
    public DecisionNodeBodyFigure() {
        ToolbarLayout layout = new ToolbarLayout(ToolbarLayout.VERTICAL);
        setLayoutManager(layout);
        layout.setStretchMinorAxis(false);
        layout.setSpacing(0);
        layout.setMinorAlignment(ToolbarLayout.ALIGN_CENTER);
        setOpaque(false);
        
        diamond = new DecisionNodeDiamond();
        add(diamond);

        flowPage = new FlowPage();
        bodyFlow = new TextFlow();
        bodyFlow.setLayoutManager(new ParagraphTextLayout(bodyFlow,
                ParagraphTextLayout.WORD_WRAP_SOFT));
        flowPage.add(bodyFlow);
        add(flowPage);
        
        if (MaseEditDomain.isDebugMode()) {
            flowPage.setOpaque(true);
            flowPage.setBackgroundColor(ColorConstants.yellow);
        }
    }
    
    public Dimension getPreferredSize(int wHint, int hHint) {
        Dimension dim = new Dimension();
        dim.expand(0,diamond.getPreferredSize(wHint, hHint).height);
        dim.expand(flowPage.getPreferredSize(wHint, hHint));        
        
        int parentWidth = getParent().getClientArea().width;
        if (parentWidth > dim.width) {
            dim.expand(parentWidth - dim.width ,0);            
        }
        
        //int hd = getParent().getClientArea().height - dim.height - PinFigure.SIZE * 2;
        //if (hd > 0) dim.expand(0,hd);
        
        return dim;
    }

    public void setText(String str) {
        bodyFlow.setText(str);
    }

    public String getText() {
        return bodyFlow.getText();
    }
    
    public IFigure getLocatorFigure() {
        return bodyFlow;
    }
}