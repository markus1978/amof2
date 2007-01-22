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
import hub.sam.mase.m2model.ActionKind;

import org.apache.log4j.Logger;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.ParagraphTextLayout;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;

import java.util.*;

class OpaqueActionBodyFigure extends RoundedRectangle implements EditableFigure {
    
    private static Logger logger = Logger.getLogger(OpaqueActionBodyFigure.class.getName());
    private final int margin;
    private final TextArrangementFigure textArrangement;
    
    private final static Map<ActionKind, String> actionKindView = new HashMap<ActionKind, String>();
    
    static {
        actionKindView.put(ActionKind.CALL, "call");
        actionKindView.put(ActionKind.CREATE_OBJECT, "create");
        actionKindView.put(ActionKind.EXPRESSION, "eval");
        actionKindView.put(ActionKind.PRINT, "print");
        actionKindView.put(ActionKind.PRINT_EXPRESSION, "print eval");
        actionKindView.put(ActionKind.REMOVE_STRUCTURAL_FEATURE_VALUE, "remove");
        actionKindView.put(ActionKind.WRITE_STRUCTURAL_FEATURE, "set");
        actionKindView.put(ActionKind.WRITE_STRUCTURAL_FEATURE_VALUE, "add");
    }
    
    private class TextArrangementFigure extends Figure {
        
        private final FlowPage mainPage;
        private final TextFlow bodyText;
        private final TextFlow kindText;
        
        private final CommentFigure comment;

        public TextArrangementFigure() {
            ToolbarLayout layout = new ToolbarLayout(ToolbarLayout.VERTICAL);
            setLayoutManager(layout);
            layout.setStretchMinorAxis(false);
            layout.setSpacing(0);
            layout.setMinorAlignment(ToolbarLayout.ALIGN_CENTER);
            
            mainPage = new FlowPage();
            add(mainPage);
            
            kindText = new TextFlow();
            kindText.setFont(new Font(null, MaseEditDomain.getDefaultFontName(),
                    MaseEditDomain.getDefaultFontSize(), SWT.BOLD));
            mainPage.add(kindText);
            
            bodyText = new TextFlow();
            bodyText.setFont(new Font(null, MaseEditDomain.getDefaultFontName(),
                    MaseEditDomain.getDefaultFontSize(), SWT.NORMAL));
            bodyText.setLayoutManager(new ParagraphTextLayout(bodyText,
                    ParagraphTextLayout.WORD_WRAP_SOFT));
            mainPage.add(bodyText);
            
            comment = new CommentFigure(true);
            add(comment);

            setOpaque(true);
            if (MaseEditDomain.isDebugMode()) {
                setBackgroundColor(ColorConstants.red);
                mainPage.setOpaque(true);
                mainPage.setBackgroundColor(ColorConstants.yellow);
            }
        }

        public TextFlow getBodyText() {
            return bodyText;
        }

        public void setComment(String text) {
            comment.setComment(text);
        }

        public TextFlow getKindText() {
            return kindText;
        }
        
    }
    
    public OpaqueActionBodyFigure(int margin, ActionKind actionKind) {
        this.margin = margin;
        
        // layout manager which handles position and size of our children
        XYLayout layout = new XYLayout();
        setLayoutManager(layout);
        setOpaque(true);
        int cornerSize = MaseEditDomain.getCachedInt("roundedRectangle.cornerDimension.size");
        setCornerDimensions(new Dimension(cornerSize,cornerSize));
        
        textArrangement = new TextArrangementFigure();
        add(textArrangement);
        layout.setConstraint(textArrangement, new Rectangle(margin, margin, -1, -1));

        if (MaseEditDomain.isDebugMode()) {
            setBackgroundColor(ColorConstants.green);
        }
        else {
            setBackgroundColor(new Color(null, 255, 255, 130));
        }
    }
    
    public void validate() {
        logger.debug("validating ...");
        LayoutManager layout = getLayoutManager();
        layout.setConstraint(textArrangement, new Rectangle(margin, margin, getSize().width - 2*margin, getSize().height - 2*margin));
        super.validate();
    }
    
    public Dimension getMinimumSize(int wHint, int hHint) {
        return getPreferredSize(wHint, hHint);
    }
    
    public Dimension getPreferredSize(int wHint, int hHint) {
        Dimension dim = new Dimension();
        dim.expand(textArrangement.getPreferredSize(wHint-2*margin, hHint-2*margin));
        dim.expand(2*margin, 2*margin);
        logger.debug("getPreferredSize: " + dim);
        return dim;
    }
    
    public void setActionKind(ActionKind actionKind) {
        if (actionKind != null) {
            textArrangement.getKindText().setText(actionKindView.get(actionKind) + ": ");
        }
        else {
            textArrangement.getKindText().setText("");
        }
    }

    public void setText(String text) {
        if (text == null) {
            text = "";
        }
        textArrangement.getBodyText().setText(text);
    }

    public String getText() {
        return textArrangement.getBodyText().getText();
    }
    
    public void setComment(String text) {
        textArrangement.setComment(text);
    }
    
    public IFigure getLocatorFigure() {
        return textArrangement.getBodyText();
    }
}