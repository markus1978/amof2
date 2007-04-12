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

import hub.sam.mas.model.mas.AttachedNode;

import org.apache.log4j.Logger;
import java.util.List;

public class AttachedNodeListMoveChildCommand extends RootCommand {
    
    private static Logger logger = Logger.getLogger(AttachedNodeListMoveChildCommand.class.getName());

    private final List<AttachedNode> nodeList;
    private final AttachedNode child;
    private final AttachedNode after;
    private int oldIndex = 0;
    private int newIndex = 0;

    protected AttachedNodeListMoveChildCommand(List<AttachedNode> list, AttachedNode child, AttachedNode after) {
        this.nodeList = list;
        this.child = child;
        this.after = after;
    }

    public void execute() {
        oldIndex = nodeList.indexOf(child);
        if (after != null) {
            newIndex = nodeList.indexOf(after);
        }
        else {
            newIndex = nodeList.size();
        }
        if (newIndex >= 1) newIndex--;
        logger.debug("oldIndex=" + oldIndex);
        logger.debug("newIndex=" + newIndex);
        redo();
    }

    public void redo() {
        nodeList.remove(child);
        nodeList.add(newIndex, child);
    }

    public void undo() {
        nodeList.remove(child);
        nodeList.add(oldIndex, child);
    }

}