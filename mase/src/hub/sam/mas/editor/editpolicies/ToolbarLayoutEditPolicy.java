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

package hub.sam.mas.editor.editpolicies;

import org.eclipse.gef.*;
import org.eclipse.gef.Request;
import org.eclipse.gef.requests.*;
import org.eclipse.gef.editpolicies.*;
import org.eclipse.draw2d.*;
import java.util.*;
import org.eclipse.gef.editparts.*;
import org.eclipse.draw2d.geometry.*;

/**
 * Abstract EditPolicy for showing feedback in Figures that have a ToolbarLayout manager.
 * 
 * @author Andreas Blunk
 */
public abstract class ToolbarLayoutEditPolicy extends OrderedLayoutEditPolicy {
    private IFigure layoutTargetFeedback;
    private static final int FEEDBACK_SIZE = 4;

    protected IFigure createLayoutTargetFeedback() {
        Shape figure = new RectangleFigure();
        figure.setBackgroundColor(ColorConstants.black);
        addFeedback(figure);
        return figure;
    }

    protected IFigure getLayoutTargetFeedback() {
        if (layoutTargetFeedback == null) {
            layoutTargetFeedback = createLayoutTargetFeedback();
        }
        return layoutTargetFeedback;
    }

    /**
     * positions the feedback rectangle depending on the mouse-move-to location
     */
    protected void showLayoutTargetFeedback(Request request) {
        AbstractGraphicalEditPart hostEditPart = (AbstractGraphicalEditPart) getHost();
        Figure contentPane = (Figure) hostEditPart.getContentPane();
        
        ToolbarLayout layout = (ToolbarLayout) contentPane.getLayoutManager();
        boolean verticalLayout = !layout.isHorizontal();
        IFigure feedbackFigure = getLayoutTargetFeedback();
        AbstractGraphicalEditPart after = (AbstractGraphicalEditPart) getInsertionReference(request);

        Point feedbackLocation = null;
        Dimension feedbackSize = null;

        if (after == null) {
            if (getHost().getChildren().size() == 0) {
                // host has no children at all
                int mid = 0;
                if (verticalLayout) {
                    mid = contentPane.getBounds().height / 2 + contentPane.getLocation().y;
                    feedbackLocation = new Point(contentPane.getLocation().x, mid);
                    feedbackSize = new Dimension(contentPane.getSize().width, FEEDBACK_SIZE);
                }
                else {
                    mid = contentPane.getBounds().width / 2 + contentPane.getLocation().x;
                    feedbackLocation = new Point(mid, contentPane.getLocation().y);
                    feedbackSize = new Dimension(FEEDBACK_SIZE, contentPane.getSize().height);
                }
            }
            else {
                // after the last child
                int lastIndex = getHost().getChildren().size() - 1;
                Figure lastFigure = (Figure) ((GraphicalEditPart) getHost().getChildren().get(lastIndex)).getFigure();
                feedbackLocation = lastFigure.getLocation();
                lastFigure.translateToAbsolute(feedbackLocation);
                if (verticalLayout) {
                    feedbackLocation = feedbackLocation.getTranslated(0, lastFigure.getSize().height);
                    feedbackSize = new Dimension(lastFigure.getBounds().width, FEEDBACK_SIZE);
                }
                else {
                    feedbackLocation = feedbackLocation.getTranslated(lastFigure.getSize().width, 0);
                    feedbackSize = new Dimension(FEEDBACK_SIZE, lastFigure.getBounds().height);
                }
            }
        }
        else {
            // place feedback figure just before the figure of "after"
            int indexAfter = getHost().getChildren().indexOf(after);

            if (indexAfter == 0) {
                // place feedback figure at the front/top
                Figure firstFigure = (Figure) ((GraphicalEditPart) getHost().getChildren().get(0)).getFigure();
                feedbackLocation = firstFigure.getLocation();
                firstFigure.translateToAbsolute(feedbackLocation);
                if (verticalLayout) {
                    feedbackLocation = feedbackLocation.getTranslated(0, -FEEDBACK_SIZE);
                    feedbackSize = new Dimension(firstFigure.getBounds().width, FEEDBACK_SIZE);
                }
                else {
                    feedbackLocation = feedbackLocation.getTranslated(-FEEDBACK_SIZE, 0);
                    feedbackSize = new Dimension(FEEDBACK_SIZE, firstFigure.getBounds().height);
                }
            }
            else {
                // place feedback figure somewhere in between
                Figure figureBefore = (Figure) ((GraphicalEditPart) getHost().getChildren().get(indexAfter - 1)).getFigure();
                Figure figureAfter = (Figure) ((GraphicalEditPart) getHost().getChildren().get(indexAfter)).getFigure();
                if (verticalLayout) {
                    int minPrec = figureBefore.getLocation().y + figureBefore.getBounds().height;
                    int maxNext = figureAfter.getLocation().y;
                    int interval = minPrec - maxNext;
                    int midInterval = minPrec + interval / 2;
                    feedbackLocation = new Point(figureBefore.getBounds().x, midInterval);
                    figureBefore.translateToAbsolute(feedbackLocation);
                    feedbackSize = new Dimension(figureBefore.getSize().width, FEEDBACK_SIZE);
                }
                else {
                    int maxPrec = figureBefore.getLocation().x + figureBefore.getBounds().width;
                    int minNext = figureAfter.getLocation().x;
                    int interval = minNext - maxPrec;
                    int midInterval = maxPrec + interval / 2;
                    feedbackLocation = new Point(midInterval, figureBefore.getBounds().y);
                    figureBefore.translateToAbsolute(feedbackLocation);
                    feedbackSize = new Dimension(FEEDBACK_SIZE, figureBefore.getSize().height);
                }
            }
        }

        feedbackFigure.setBounds(new Rectangle(feedbackLocation, feedbackSize));
    }

    protected void eraseLayoutTargetFeedback(Request request) {
        if (layoutTargetFeedback != null) {
            removeFeedback(layoutTargetFeedback);
        }
        layoutTargetFeedback = null;
    }

    protected EditPart getInsertionReference(Request request) {
        EditPart after = null; // the editpart that comes just after the location where we want to move to

        AbstractGraphicalEditPart hostEditPart = (AbstractGraphicalEditPart) getHost();
        Figure contentPane = (Figure) hostEditPart.getContentPane();
        ToolbarLayout layout = (ToolbarLayout) contentPane.getLayoutManager();
        boolean verticalLayout = !layout.isHorizontal();
        int mouseLocation = 0; // where to move the editpart
        
        if (verticalLayout) {
            mouseLocation = ((DropRequest) request).getLocation().y;
        }
        else {
            mouseLocation = ((DropRequest) request).getLocation().x;
        }

        List children = contentPane.getChildren();
        if (children.size() == 0) {
            return null;            
        }

        IFigure childFigure = null;
        int middleOfChildFigure = 0;
        
        for(Object child: children) {
            childFigure = (IFigure) child;
            
            if (verticalLayout) {
                middleOfChildFigure = childFigure.getBounds().y + childFigure.getBounds().height / 2;
            }
            else {
                middleOfChildFigure = childFigure.getBounds().x + childFigure.getBounds().width / 2;
            }

            if (mouseLocation < middleOfChildFigure) {
                // user wants to move just before the current childFigure,
                // so after must be the current figure's editpart
                after = (EditPart) getHost().getViewer().getVisualPartMap().get(childFigure);
                break;
            }
        }
        // after is still null here if the loop did not break !

        // why????
        if (after == null || !(request instanceof GroupRequest)) {
            return after;
        }
        else {
            List groupRequestEditParts = ((GroupRequest) request).getEditParts();
            List hostChildren = getHost().getChildren();
            int indexAfter = hostChildren.indexOf(after);
            
            while (indexAfter < hostChildren.size() && groupRequestEditParts.contains(hostChildren.get(indexAfter))) {
                ++indexAfter;
            }
            
            if (indexAfter == hostChildren.size()) {
                after = null;
            }
            else {
                after = (EditPart) hostChildren.get(indexAfter);
            }
            
            return after;
        }
    }
}
