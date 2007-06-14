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

import hub.sam.mas.management.MasModelContainer;
import hub.sam.mas.management.MasRepository;
import hub.sam.mas.management.PluginMasXmiFiles;
import hub.sam.mof.Repository;
import hub.sam.mof.management.LoadException;
import hub.sam.mof.plugin.modelview.tree.RepositoryTreeObject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class AddMASContextAction extends Mof2PluginAction implements IRunnableWithProgress {

    private PluginMasXmiFiles xmiFiles;
    private Display display;
    private MasModelContainer modelManager;

    public void run(IAction action) {
        display = Display.getCurrent();
        Shell shell = new Shell(display);
        
        FileDialog dialog = new FileDialog(shell, SWT.SINGLE);
        dialog.setFilterExtensions(new String[] { "*.masctx" }); //$NON-NLS-1$;               

        String result = dialog.open();
        if (result == null) {
            return;
        }
        
        try {
            xmiFiles = new PluginMasXmiFiles(new Path(dialog.getFilterPath()), dialog.getFileName());
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

    public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
        monitor.beginTask("Loading Models: MAS Meta-Model", 4);
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
        
        monitor.setTaskName("Loading Models: MAS Model");        
        display.syncExec(new Runnable() {            
            public void run() {
                try {
                    modelManager.loadMasModel(xmiFiles.getMasFile());
                }
                catch (LoadException e) {
                    MessageDialog.openError(display.getActiveShell(), "Error",
                            "Failed to create a MAS Context. Could not load MAS Model: " + e.getMessage());
                }
            }
        });
        monitor.worked(2);

        monitor.setTaskName("Loading Models: Syntax Model");        
        display.syncExec(new Runnable() {            
            public void run() {
                try {
                    modelManager.loadSyntaxModelForEditing(xmiFiles.getSyntaxFile(), null);
                }
                catch (LoadException e) {
                    MessageDialog.openError(display.getActiveShell(), "Error",
                            "Failed to create a MAS Context. Could not load Syntax Model: " + e.getMessage());
                }
            }
        });
        monitor.worked(3);

        monitor.setTaskName("Creating a MAS Context.");        
        MasRepository.getInstance().createMasContext(modelManager);
        monitor.worked(4);
        
        monitor.done();
    }

    public void selectionChanged(IAction action, ISelection selection) {
        // empty
    }

}
