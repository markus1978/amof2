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

import hub.sam.mas.editor.editor.MaseEditDomain;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.ParagraphTextLayout;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;

class CommentFigure extends Figure {
    
    private boolean active = false;
    public final FlowPage commentPage;
    private final TextFlow commentText;
    
    public CommentFigure(boolean installDummyAtTop) {
        ToolbarLayout layout = new ToolbarLayout(ToolbarLayout.VERTICAL);
        setLayoutManager(layout);
        layout.setStretchMinorAxis(false);
        layout.setSpacing(2);
        layout.setMinorAlignment(ToolbarLayout.ALIGN_CENTER);

        if (installDummyAtTop) {
            Figure dummy = new Figure();
            dummy.setPreferredSize(0,0);
            dummy.setOpaque(false);
            add(dummy);
        }
        
        LineFigure line = new LineFigure();
        line.setLinestyle(Graphics.LINE_DOT);
        line.setHeight(1);
        add(line);

        commentPage = new FlowPage();
        add(commentPage);

        commentText = new TextFlow();
        commentText.setFont(new Font(null, MaseEditDomain.getDefaultFontName(),
                MaseEditDomain.getDefaultFontSize(), SWT.ITALIC));
        commentText.setLayoutManager(new ParagraphTextLayout(commentText,
                ParagraphTextLayout.WORD_WRAP_SOFT));
        commentPage.add(commentText);
        
        if (MaseEditDomain.isDebugMode()) {
            commentPage.setOpaque(true);
            commentPage.setBackgroundColor(ColorConstants.yellow);
        }
    }
    
    public void setComment(String text) {
        if (text == null || text.equals("")) {
            active = false;
        }
        else {
            active = true;
        }
        commentText.setText(text);
    }
    
    public Dimension getPreferredSize(int wHint, int hHint) {
        if (active) {
            return super.getPreferredSize(wHint, hHint);
        }
        else {
            return new Dimension(0,0);
        }
    }
    
}
