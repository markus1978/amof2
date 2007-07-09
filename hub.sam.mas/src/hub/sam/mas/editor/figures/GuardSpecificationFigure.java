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
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;

public class GuardSpecificationFigure extends Figure implements EditableFigure {
    
    private Label label;
    
    public GuardSpecificationFigure() {
        StackLayout layout = new StackLayout();
        setLayoutManager(layout);
        
        setBackgroundColor(ColorConstants.white);
        setOpaque(true);
        label = new Label();
        label.setFont(new Font(null, MaseEditDomain.getDefaultFontName(),
                    MaseEditDomain.getDefaultFontSize(), SWT.NORMAL));
        add(label);
    }

    public IFigure getLocatorFigure() {
        return label;
    }

    public String getText() {
        return label.getText();
    }

    public void setText(String str) {
        label.setText("[" + str + "]");
    }

}
