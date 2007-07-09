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

package hub.sam.mas.editor.editparts;

import hub.sam.mas.editor.MaseEditDomain;
import hub.sam.mas.editor.figures.EditableFigure;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;

/**
 * Wraps a given figure by adding a label at the top.
 *
 */
public class LabeledFigure extends Figure implements EditableFigure {

    private final Label label;
    private final IFigure wrappedFigure;
    private final static String labelPrefix = "=";
    public static final Color COLOR = new Color(null,255,153,0);
    public static final int POSITION_TOP = 1;
    public static final int POSITION_BOTTOM = 2;
    
    public LabeledFigure(IFigure wrappedFigure, int position) {
        ToolbarLayout layout = new ToolbarLayout(ToolbarLayout.VERTICAL);
        setLayoutManager(layout);
        
        setOpaque(false);
        if (MaseEditDomain.isDebugMode()) {
            setOpaque(true);
            setBackgroundColor(COLOR);
        }
        
        label = new Label();
        label.setFont(new Font(null, MaseEditDomain.getDefaultFontName(),
                MaseEditDomain.getDefaultFontSize(), SWT.NORMAL));
        this.wrappedFigure = wrappedFigure;
        
        if (position == POSITION_TOP) {
            add(label);
            add(wrappedFigure);
        }
        else if (position == POSITION_BOTTOM) {
            add(wrappedFigure);
            add(label);
        }
    }
    
    public Dimension getPreferredSize(int wHint, int hHint) {
        Dimension preferredSize = new Dimension();
        preferredSize.expand(0, wrappedFigure.getPreferredSize().height);
        preferredSize.expand(0, label.getPreferredSize(wHint, hHint).height);
        int maxWidth = Math.max(wrappedFigure.getPreferredSize().width, label.getPreferredSize().width);
        preferredSize.expand(maxWidth, 0);
        return preferredSize;
    }
    
    public void setText(String str) {
        if (str != null && str.length() > 0) {
            label.setText(labelPrefix + str);
        }
        else {
            label.setText(str);
        }
    }
    
    public String getText() {
        return label.getText();
    }
    
    public IFigure getLocatorFigure() {
        return label;
    }
    
    public IFigure getAnchorFigure() {
        return wrappedFigure;
    }
    
}
