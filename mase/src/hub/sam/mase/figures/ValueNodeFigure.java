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

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.ParagraphTextLayout;
import org.eclipse.draw2d.text.TextFlow;
import hub.sam.mase.editor.MaseEditDomain;

public class ValueNodeFigure extends RectangleFigure implements EditableFigure {

    private final int margin;
    private final TextFlow nameFlow;
    private final FlowPage flowPage;

    public ValueNodeFigure(int margin) {
        this.margin = margin;
        
        flowPage = new FlowPage();
        nameFlow = new TextFlow();
        nameFlow.setLayoutManager(new ParagraphTextLayout(nameFlow,
                ParagraphTextLayout.WORD_WRAP_SOFT));
        flowPage.add(nameFlow);
        if (MaseEditDomain.isDebugMode()) {
            flowPage.setOpaque(true);
            flowPage.setBackgroundColor(ColorConstants.yellow);
        }
        add(flowPage);

        // layout manager which handles position and size of our children
        XYLayout layout = new XYLayout();
        setLayoutManager(layout);
        setBackgroundColor(ColorConstants.white);
        setOpaque(true);        
    }
    
    public Dimension getPreferredSize(int wHint, int hHint) {
        if (wHint <= 0) {
            wHint = MaseEditDomain.getCachedInt("valueNode.width");
        }
        Dimension dim = new Dimension();
        dim.expand(margin*2,margin*2);
        dim.expand(flowPage.getPreferredSize(wHint-margin*2, hHint-margin*2));        
        return dim;
    }
    
    public Dimension getMinimumSize(int w, int h) {
        return getPreferredSize(w, h);
    }

    public void validate() {
        LayoutManager layout = getLayoutManager();
        layout.setConstraint(flowPage, new Rectangle(margin,margin, getSize().width - 2*margin, getSize().height - 2*margin ));
        
        super.validate();
    }
    
    public void setText(String str) {
        nameFlow.setText(str);
    }

    public String getText() {
        return nameFlow.getText();
    }

    public IFigure getLocatorFigure() {
        return nameFlow;
    }
    
}
