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

/**
 * A Command that supports redo and undo operations for the AMOF-Repository.
 * Subclasses have to recall redo() and undo() from MofCommand.
 * 
 * If an operation should be undone, the specified model object is registered as
 * potential garbage in the ModelGarbageCollector. It is unregistered if the
 * operation is redone or else destroyed by the ModelGarbageCollector.
 * 
 * @author Andreas Blunk
 */
public interface MofCommand {
    public void redo();
    public void undo();
}
