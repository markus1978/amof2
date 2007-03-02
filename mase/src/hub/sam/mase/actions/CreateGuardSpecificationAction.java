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

package hub.sam.mase.actions;

import org.eclipse.gef.ui.actions.*;
import org.eclipse.gef.commands.*;
import org.eclipse.gef.*;
import org.eclipse.gef.requests.*;
import org.eclipse.ui.*;

import hub.sam.mas.model.mas.GuardSpecification;
import hub.sam.mas.model.mas.MaseCreationFactory;
import hub.sam.mase.editor.MaseEditDomain;
import hub.sam.mase.editparts.ActivityEdgeEditPart;

import java.util.*;

public class CreateGuardSpecificationAction extends SelectionAction {
    
    public static final String ID = "create guard action";
    private final MaseEditDomain editDomain;

    public CreateGuardSpecificationAction(IWorkbenchPart part, MaseEditDomain editDomain) {
        super(part);
        this.editDomain = editDomain;
    }

    public void init() {
        setId(ID);
        setText("create guard");
    }

    protected boolean calculateEnabled() {
        List list = getSelectedObjects();
        if (list.size() == 1) {
            Object obj = list.get(0);
            if (obj instanceof ActivityEdgeEditPart) {
                ActivityEdgeEditPart part = (ActivityEdgeEditPart) obj;
                if (part.getModel().getGuardSpecification() == null) {
                    return true;
                }
            }
        }
        return false;
    }

    public void run() {
        List list = getSelectedObjects();
        if (list.size() == 1) {
            Object obj = list.get(0);
            if (obj instanceof ActivityEdgeEditPart) {
                ActivityEdgeEditPart part = (ActivityEdgeEditPart) obj;
                CreateRequest req = new CreateRequest();
                req.setType(RequestConstants.REQ_CREATE);
                req.setFactory(new MaseCreationFactory(editDomain, GuardSpecification.class));
                Command command = part.getCommand(req);
                if (command != null && getCommandStack() != null) {
                    getCommandStack().execute(command);
                }
            }
        }
    }

}
