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

import hub.sam.mas.management.MasContext;
import hub.sam.mas.management.MasLink;
import hub.sam.mas.management.MasRepository;
import hub.sam.mof.plugin.modelview.tree.TreeObject;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

import cmof.Operation;

public abstract class MasAction extends Mof2PluginAction {
    
    private Operation selectedOperation = null;
    
    protected MasContext getMASContextFromSelection() {
    	if (selectedOperation != null) {
	        return MasRepository.getInstance().getMasContext(selectedOperation.getExtent());
    	} else {
    		return null;
    	}
    }
    
    protected Operation getSelectedOperation() {
        return selectedOperation;
    }

    protected MasLink getLinkFromSelection() {
        MasContext masContext = getMASContextFromSelection();
        if (masContext != null) {
            return getMASContextFromSelection().getLink(selectedOperation);
        }
        return null;
    }

    public void selectionChanged(IAction action, ISelection selection) {
        if (selection != null) {
            TreeObject structuredSelection = (TreeObject) ((IStructuredSelection) selection).getFirstElement();
	        selectedOperation = (Operation) structuredSelection.getElement();
	        action.setEnabled(isEnabled());
        }
    }
    
    protected abstract boolean isEnabled();
}
