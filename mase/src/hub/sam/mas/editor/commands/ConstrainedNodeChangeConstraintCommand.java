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

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;

import hub.sam.mas.model.mas.ConstrainedNode;

public class ConstrainedNodeChangeConstraintCommand extends Command {

    private final Rectangle oldConstraint;
    private final Rectangle newConstraint;
    private final ConstrainedNode node;

    public ConstrainedNodeChangeConstraintCommand(ConstrainedNode node, Rectangle constraint) {
        this.newConstraint = constraint;
        this.oldConstraint = node.getRectangle();
        this.node = node;
    }

    public void execute() {
        redo();
    }

    public void redo() {
        node.setRectangle(newConstraint);
    }

    public void undo() {
        node.setRectangle(oldConstraint);
    }

}
