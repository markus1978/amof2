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

package hub.sam.mase.commands;

import hub.sam.mof.model.mas.Activity;
import hub.sam.mof.model.mas.ActivityNode;
import hub.sam.mof.model.mas.ModelGarbageCollector;

import org.apache.log4j.Logger;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * Command for creating ActivitiyNodes that have a rectangle as constraint.
 * 
 * @author Andreas Blunk
 */
public class ActivityNodeCreateCommand extends MofCreateCommand {

    private static Logger logger = Logger.getLogger(ActivityNodeCreateCommand.class.getName());
    private final Activity parent;
    private final ActivityNode node;
    private final Rectangle constraint;

    public ActivityNodeCreateCommand(Activity parent, ActivityNode node) {
        super(node);
        this.parent = parent;
        this.node = node;
        this.constraint = null;
    }

    public ActivityNodeCreateCommand(Activity parent, ActivityNode node, Rectangle constraint) {
        super(node);
        this.parent = parent;
        this.node = node;
        this.constraint = constraint;
    }
    
    /**
     * The part of this command that will be executed just once
     * without calling method redo.
     */
    protected void executeOnce() {
        node.setRectangle(constraint);
    }

    public void execute() {
        logger.debug("ActivityNodeCreateCommand.execute");
        ModelGarbageCollector.getInstance().unmark(node);
        executeOnce();
        redo();
    }

    public void redo() {
        super.redo();
        logger.debug("ActivityNodeCreateCommand.redo");
        parent.getNode().add(node);
    }

    public void undo() {
        super.undo();
        parent.getNode().remove(node);
    }

}
