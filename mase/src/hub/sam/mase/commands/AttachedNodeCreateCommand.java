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

import hub.sam.mase.m2model.ModelGarbageCollector;
import hub.sam.mase.m2model.AttachedNode;
import java.util.List;

public class AttachedNodeCreateCommand extends MofCreateCommand {
    
    private final List<AttachedNode> list;
    private final AttachedNode node;
    private final AttachedNode after;

    public AttachedNodeCreateCommand(List<AttachedNode> list, AttachedNode node, AttachedNode after) {
        super(node);
        this.list = list;
        this.node = node;
        this.after = after;
    }

    public void execute() {
        ModelGarbageCollector.getInstance().unmark(node);
        redo();
    }

    public void redo() {
        super.redo();
        node.setNum(list.size());
        if (after != null) {
            int index = list.indexOf(after);
            if (index != -1) {
                list.add(index, node);
                return;
            }
        }
        list.add(node);
    }

    public void undo() {
        super.undo();
        list.remove(node);
    }

}
