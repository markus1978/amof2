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

package hub.sam.mas.editor.commands;

import hub.sam.mas.model.mas.Activity;
import hub.sam.mas.model.mas.ActivityGroup;
import hub.sam.mas.model.mas.ModelGarbageCollector;

import org.apache.log4j.Logger;
import org.eclipse.draw2d.geometry.Rectangle;

/**
 * Command for creating ActivitiyGroups that have a rectangle as constraint.
 * 
 * @author Andreas Blunk
 */
public class ActivityGroupCreateCommand extends MofCreateCommand {

    private static Logger logger = Logger.getLogger(ActivityGroupCreateCommand.class.getName());
    private final Activity parent;
    private final ActivityGroup group;
    private final Rectangle constraint;

    public ActivityGroupCreateCommand(Activity parent, ActivityGroup group) {
        super(group);
        this.parent = parent;
        this.group = group;
        this.constraint = null;
    }

    public ActivityGroupCreateCommand(Activity parent, ActivityGroup group, Rectangle constraint) {
        super(group);
        this.parent = parent;
        this.group = group;
        this.constraint = constraint;
    }
    
    /**
     * The part of this command that will be executed just once
     * without calling method redo.
     */
    protected void executeOnce() {
        group.setRectangle(constraint);
    }

    public void execute() {
        logger.debug("ActivityGroupCreateCommand.execute");
        ModelGarbageCollector.getInstance().unmark(group);
        executeOnce();
        redo();
    }

    public void redo() {
        super.redo();
        parent.getGroup().add(group);
    }

    public void undo() {
        super.undo();
        parent.getGroup().remove(group);
    }

}
