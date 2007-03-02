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

import java.util.List;

public class AttachedNodeDeleteCommand extends MofDeleteCommand {

    private final List<AttachedNode> list;
    private final AttachedNode node;
    private int oldIndex;

    public AttachedNodeDeleteCommand(List<AttachedNode> list, AttachedNode node) {
        super(node);
        this.list = list;
        this.node = node;
    }

    public void execute() {
        oldIndex = list.indexOf(node);
        redo();
    }

    public void redo() {
        super.redo();
        list.remove(node);
    }

    public void undo() {
        super.undo();
        list.add(oldIndex, node);
    }

}