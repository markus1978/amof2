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

import hub.sam.mas.management.MASContext;
import hub.sam.mas.management.MASLink;
import hub.sam.mase.editor.IMaseEditorInput;
import hub.sam.mase.editor.MaseEditorInput;
import hub.sam.mof.model.mas.Activity;
import hub.sam.mof.model.mas.MaseCreationFactory;
import hub.sam.mof.model.mas.masFactory;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class EditBehaviourAction extends MASAction {

    public void run(IAction action) {
        MASLink link = getLinkFromSelection();
        if (link == null) {
            MASContext masContext = getMASContextFromSelection();
            MaseCreationFactory maseFactory = new MaseCreationFactory((masFactory) masContext.getSemanticModel().getFactory(),
                    Activity.class);
            Activity activity = maseFactory.createActivity();
            link = masContext.createLink(currentOperation, activity);
        }
        
        IMaseEditorInput input = new MaseEditorInput(link);
        try {
            PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().openEditor(input, "hub.sam.mase");
        }
        catch (PartInitException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected boolean shouldEnable() {
        return true;
    }
    
    @Override
    public void selectionChanged(IAction action, ISelection selection) {
        super.selectionChanged(action, selection);
        if (getLinkFromSelection() == null) {
            action.setText("Create Behaviour");
        }
        else {
            action.setText("Edit Behaviour");
        }
    }

    
}
