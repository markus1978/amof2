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

import org.eclipse.gef.commands.Command;

/**
 * A create command that supports undo/redo operations for AMOF model objects.
 * 
 * @author Andreas Blunk
 */
public abstract class MofCreateCommand extends Command implements MofCommand {
    
    private boolean undoneBefore = false;
    private cmof.reflection.Object model;
    
    /**
     * 
     * @param model The model object that should be undoable.
     */
    public MofCreateCommand(cmof.reflection.Object model) {
        this.model = model;
    }
    
    protected void setModel(cmof.reflection.Object model) {
        this.model = model;
    }
    
    /**
     * Subclasses have to call this method.
     */
    public void redo() {
        if (undoneBefore) {
            ModelGarbageCollector.getInstance().unmark(model);
        }
    }
    
    /**
     * Subclasses have to call this method.
     */
    public void undo() {
        ModelGarbageCollector.getInstance().mark(model);
        undoneBefore = true;
    }

}
