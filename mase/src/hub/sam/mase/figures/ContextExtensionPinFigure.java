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

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.swt.graphics.Color;

public class ContextExtensionPinFigure extends Figure implements EditableFigure {
    
    private final Label extensionName;
    private final ContextPinFigure contextPin;
    public static final Color COLOR = new Color(null,255,153,0);
    
    public ContextExtensionPinFigure(int num) {

        ToolbarLayout layout = new ToolbarLayout(ToolbarLayout.VERTICAL);
        setLayoutManager(layout);
        
        setOpaque(false);
        if (MaseEditDomain.isDebugMode()) {
            setOpaque(true);
            setBackgroundColor(COLOR);
        }
        
        extensionName = new Label();
        add(extensionName);
        
        contextPin = new ContextPinFigure(num);
        add(contextPin);
    }
    
    public Dimension getPreferredSize(int wHint, int hHint) {
        Dimension preferredSize = new Dimension();
        preferredSize.expand(0, contextPin.getPreferredSize().height);
        preferredSize.expand(0, extensionName.getPreferredSize(wHint, hHint).height);
        int maxWidth = Math.max(contextPin.getPreferredSize().width, extensionName.getPreferredSize().width);
        preferredSize.expand(maxWidth, 0);
        return preferredSize;
    }
    
    public void setText(String str) {
        extensionName.setText(str);
    }
    
    public String getText() {
        return extensionName.getText();
    }
    
    public IFigure getLocatorFigure() {
        return extensionName;
    }
    
    public IFigure getAnchorFigure() {
        return contextPin;
    }
    
}
