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

import java.lang.reflect.InvocationTargetException;

import hub.sam.mas.management.IMasContextFileResource;
import hub.sam.mas.management.MasContextFile;
import hub.sam.mas.management.MasModelContainer;
import hub.sam.mas.management.MasRepository;
import hub.sam.mof.Repository;
import hub.sam.mof.management.LoadException;
import hub.sam.mof.plugin.modelview.tree.RepositoryTreeObject;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.views.navigator.ResourceSorter;

public class AddMASContextAction extends Mof2PluginAction implements IRunnableWithProgress {

    private IMasContextFileResource contextFile;
    private Display display;
    private MasModelContainer modelManager;

    public void run(IAction action) {
        display = Display.getCurrent();
        Shell shell = new Shell(display);
        
        ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(shell, new WorkbenchLabelProvider(),
                new WorkbenchContentProvider());
        dialog.setTitle("MAS Context File"); 
        dialog.setMessage("Select a MAS Context File:"); 
        dialog.setInput(ResourcesPlugin.getWorkspace().getRoot()); 
        dialog.setSorter(new ResourceSorter(ResourceSorter.NAME));
        // TODO filter *.masctx
        
        if (dialog.open() == IDialogConstants.OK_ID) {
            IResource resource = (IResource) dialog.getFirstResult();
            
            try {
                contextFile = new MasContextFile(resource);
                ProgressMonitorDialog progressMonitor = new ProgressMonitorDialog(shell);
                progressMonitor.run(true, false, this);
            }
            catch (Exception e) {
                MessageDialog.openError(
                        shell,
                        "Error",
                        "Failed to create a MAS Context: " + e.getMessage());
                return;
            }
            
            IStructuredSelection selection = (IStructuredSelection) getModelView().getViewer().getSelection();
            ((RepositoryTreeObject) selection.getFirstElement()).refresh();

            getModelView().getViewer().refresh();
            shell.dispose();
        }
    }

    public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
        monitor.beginTask("Loading MAS Meta-Model ...", 4);
        display.syncExec(new Runnable() {            
            public void run() {
                try {
                    Repository repository = Repository.getLocalRepository();
                    modelManager = new MasModelContainer(repository);
                }
                catch (LoadException e) {
                    MessageDialog.openError(display.getActiveShell(), "Error",
                            "Failed to create a MAS Context. Could not load MAS Meta-Model: " + e.getMessage());
                }
            }
        });
        monitor.worked(1);
        
        monitor.setTaskName("Loading MAS Model ...");        
        display.syncExec(new Runnable() {            
            public void run() {
                try {
                    modelManager.loadMasModel(contextFile.getMasFile());
                }
                catch (LoadException e) {
                    MessageDialog.openError(display.getActiveShell(), "Error",
                            "Failed to create a MAS Context. Could not load MAS Model: " + e.getMessage());
                }
            }
        });
        monitor.worked(2);

        monitor.setTaskName("Loading Syntax Model ...");        
        display.syncExec(new Runnable() {            
            public void run() {
                try {
                    modelManager.loadSyntaxModelForEditing(contextFile.getSyntaxFile(), null);
                }
                catch (LoadException e) {
                    MessageDialog.openError(display.getActiveShell(), "Error",
                            "Failed to create a MAS Context. Could not load Syntax Model: " + e.getMessage());
                }
            }
        });
        monitor.worked(3);

        monitor.setTaskName("Creating a MAS Context ...");        
        MasRepository.getInstance().createMasContext(modelManager, contextFile);
        monitor.worked(4);
        
        monitor.done();
    }

    public void selectionChanged(IAction action, ISelection selection) {
        // empty
    }

}
