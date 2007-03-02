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

import org.eclipse.gef.commands.Command;

public abstract class LabelDirectEditCommand extends Command {
	
	private final String newValue;
	private final String oldValue;

	public LabelDirectEditCommand(String value, String oldValue) {
		this.newValue = value;
		this.oldValue = oldValue;
	}

	public void execute() {
		redo();
	}
	
	public void redo() {
        setText(newValue);
	}

	public void undo() {
        setText(oldValue);
	}
    
    protected abstract String getText();
    protected abstract void setText(String str);
}
