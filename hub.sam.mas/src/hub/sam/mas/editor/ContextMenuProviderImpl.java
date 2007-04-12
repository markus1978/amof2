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

package hub.sam.mas.editor;

import org.eclipse.jface.action.*;
import org.eclipse.gef.*;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.ui.actions.ActionFactory;

import hub.sam.mas.editor.actions.CreateGuardSpecificationAction;

public class ContextMenuProviderImpl extends ContextMenuProvider {

    private ActionRegistry actionRegistry;

    public ContextMenuProviderImpl(ActionRegistry ar, GraphicalViewer gv) {
        super(gv);
        actionRegistry = ar;
    }

    public void buildContextMenu(IMenuManager manager) {
        GEFActionConstants.addStandardActionGroups(manager);

        IAction action;
        
        action = actionRegistry.getAction(CreateGuardSpecificationAction.ID);
        if (action.isEnabled()) {
            manager.appendToGroup(GEFActionConstants.GROUP_REST, action);
        }

        action = actionRegistry.getAction(ActionFactory.DELETE.getId());
        if (action.isEnabled()) {
            manager.appendToGroup(GEFActionConstants.GROUP_EDIT, action);
        }

        action = actionRegistry.getAction(ActionFactory.UNDO.getId());
        manager.appendToGroup(GEFActionConstants.GROUP_UNDO, action);

        action = actionRegistry.getAction(ActionFactory.REDO.getId());
        manager.appendToGroup(GEFActionConstants.GROUP_UNDO, action);
    }

}