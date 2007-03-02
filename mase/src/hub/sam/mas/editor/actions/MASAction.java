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

package hub.sam.mas.editor.actions;

import hub.sam.mas.management.MASContext;
import hub.sam.mas.management.MASLink;
import hub.sam.mas.management.MASRepository;
import hub.sam.mof.plugin.modelview.tree.TreeObject;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

import cmof.Operation;
import cmof.reflection.Extent;

public abstract class MASAction extends Mof2PluginAction {
    
    protected Operation currentOperation = null;
    protected TreeObject selection = null;
    
    protected MASContext getMASContextFromSelection() {
    	if (selection != null) {
	        Extent syntaxExtent = currentOperation.getExtent();
	        return MASRepository.getInstance().getMASContext(syntaxExtent);
    	} else {
    		return null;
    	}
    }

    protected MASLink getLinkFromSelection() {
        MASContext masContext = getMASContextFromSelection();
        if (masContext == null) {
        	if (selection != null) {
        		MessageDialog.openError(getModelView().getSite().getShell(), "Error", "No MAS Context exists!");
        	}
            return null;
        }
        return masContext.getLink(currentOperation);
    }

    public void selectionChanged(IAction action, ISelection selection) {
        this.selection = (TreeObject) ((IStructuredSelection)selection).getFirstElement();
        if (this.selection != null) {
	        currentOperation = (Operation)this.selection.getElement();
	        action.setEnabled(shouldEnable());
        }
    }
    
    protected abstract boolean shouldEnable();

}
