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

import hub.sam.mas.management.MASEditorMOFModelFactory;
import hub.sam.mas.management.MASRepository;
import hub.sam.mof.plugin.modelview.tree.RepositoryTreeObject;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class AddMASContextAction extends Mof2PluginAction {

    public void run(IAction action) {
        Display display = Display.getCurrent();
        Shell shell = new Shell(display);
        
        FileDialog dialog = new FileDialog(shell, SWT.SINGLE);
        dialog.setFilterExtensions(new String[] { "*.masctx" }); //$NON-NLS-1$;               

        String result = dialog.open();
        if (result == null) {
            return;
        }
        
        try {
            MASEditorMOFModelFactory masFactory = new MASEditorMOFModelFactory(true, dialog.getFilterPath(), dialog.getFileName());
            MASRepository.getInstance().createMASContext(masFactory);
        }
        catch (Exception e) {
            MessageDialog.openError(
                    shell,
                    "Could not add ...",
                    "Could not add Semantic Context: " + e.getMessage());
            return;
        }
        
        IStructuredSelection selection = (IStructuredSelection) getModelView().getViewer().getSelection();
        ((RepositoryTreeObject) selection.getFirstElement()).refresh();

        getModelView().getViewer().refresh();
        shell.dispose();
    }

    public void selectionChanged(IAction action, ISelection selection) {
        // empty
    }

}
