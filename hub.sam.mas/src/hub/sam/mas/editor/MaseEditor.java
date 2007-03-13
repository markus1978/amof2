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

import hub.sam.mas.MasPlugin;
import hub.sam.mas.editor.actions.CreateGuardSpecificationAction;
import hub.sam.mas.model.mas.ContextExtensionPin;
import hub.sam.mas.model.mas.ContextPin;
import hub.sam.mas.model.mas.ControlFlow;
import hub.sam.mas.model.mas.DecisionNode;
import hub.sam.mas.model.mas.ExpansionRegion;
import hub.sam.mas.model.mas.FinalNode;
import hub.sam.mas.model.mas.ForkNode;
import hub.sam.mas.model.mas.InExpansionNode;
import hub.sam.mas.model.mas.InitialNode;
import hub.sam.mas.model.mas.InputPin;
import hub.sam.mas.model.mas.JoinNode;
import hub.sam.mas.model.mas.MaseCreationFactory;
import hub.sam.mas.model.mas.ModelGarbageCollector;
import hub.sam.mas.model.mas.OpaqueAction;
import hub.sam.mas.model.mas.OutExpansionNode;
import hub.sam.mas.model.mas.OutputPin;
import hub.sam.mas.model.mas.ValueNode;

import org.apache.log4j.PropertyConfigurator;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.palette.CreationToolEntry;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.SelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.parts.GraphicalEditorWithPalette;
import org.eclipse.gef.ui.parts.GraphicalViewerKeyHandler;
import org.eclipse.gef.commands.CommandStackListener;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.ui.properties.UndoablePropertySheetEntry;
import org.eclipse.gef.EditDomain;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheetPage;
import org.eclipse.core.runtime.IProgressMonitor;
import java.util.EventObject;
import java.util.Properties;

import org.eclipse.core.runtime.Path;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;

import org.eclipse.core.runtime.FileLocator;
import org.osgi.framework.Bundle;

public class MaseEditor extends GraphicalEditorWithPalette {
    
    public MaseEditor() {
        super();
        MaseEditDomain editDomain = new MaseEditDomain(this);
        
        Bundle masBundle = MasPlugin.getDefault().getBundle();
        try {
            PropertyConfigurator.configure(FileLocator.toFileURL(masBundle.getEntry("resources/log4j.properties")));
            
            InputStream inputStream = FileLocator.openStream(masBundle, new Path("resources/mase.properties"), false);
            Properties maseProperties = new Properties();
            maseProperties.load(inputStream);
            MaseEditDomain.setProperties(maseProperties);
        }
        catch(IOException e) {
            System.out.println("IOException while trying to open InputStream to mase.properties");
            System.exit(-1);
        }

        setEditDomain(editDomain);
    }
    
    protected MaseEditDomain getEditDomain() {
        return (MaseEditDomain) super.getEditDomain();
    }

    @Override
    protected PaletteRoot getPaletteRoot() {
        PaletteRoot root = new PaletteRoot();
        PaletteGroup group = new PaletteGroup("Tools");
        root.add(group);

        ToolEntry tool = new SelectionToolEntry();
        group.add(tool);
        root.setDefaultEntry(tool);

        PaletteEntry entry;
        MaseEditDomain editDomain = getEditDomain();
        
        entry = new CreationToolEntry("InitialNode",
                "new InitialNode",
                new MaseCreationFactory(editDomain, InitialNode.class),
                MasPlugin.getImageDescriptor("icons/alt_window_16.gif"),
                MasPlugin.getImageDescriptor("icons/alt_window_16.gif"));
        group.add(entry);

        entry = new CreationToolEntry("FinalNode",
                "new FinalNode",
                new MaseCreationFactory(editDomain, FinalNode.class),
                MasPlugin.getImageDescriptor("icons/alt_window_16.gif"),
                MasPlugin.getImageDescriptor("icons/alt_window_16.gif"));
        group.add(entry);

        entry = new CreationToolEntry("OpaqueAction",
                "new OpaqueAction",
                new MaseCreationFactory(editDomain, OpaqueAction.class),
                MasPlugin.getImageDescriptor("icons/alt_window_16.gif"),
                MasPlugin.getImageDescriptor("icons/alt_window_16.gif"));
        group.add(entry);

        entry = new CreationToolEntry("ValueNode",
                "new ValueNode",
                new MaseCreationFactory(editDomain, ValueNode.class),
                MasPlugin.getImageDescriptor("icons/alt_window_16.gif"),
                MasPlugin.getImageDescriptor("icons/alt_window_16.gif"));
        group.add(entry);

        entry = new CreationToolEntry("DecisionNode",
                "new DecisionNode",
                new MaseCreationFactory(editDomain, DecisionNode.class),
                MasPlugin.getImageDescriptor("icons/alt_window_16.gif"),
                MasPlugin.getImageDescriptor("icons/alt_window_16.gif"));
        group.add(entry);

        entry = new CreationToolEntry("ForkNode",
                "new ForkNode",
                new MaseCreationFactory(editDomain, ForkNode.class),
                MasPlugin.getImageDescriptor("icons/alt_window_16.gif"),
                MasPlugin.getImageDescriptor("icons/alt_window_16.gif"));
        group.add(entry);

        entry = new CreationToolEntry("JoinNode",
                "new JoinNode",
                new MaseCreationFactory(editDomain, JoinNode.class),
                MasPlugin.getImageDescriptor("icons/alt_window_16.gif"),
                MasPlugin.getImageDescriptor("icons/alt_window_16.gif"));
        group.add(entry);

        entry = new CreationToolEntry("ExpansionRegion",
                "new ExpansionRegion",
                new MaseCreationFactory(editDomain, ExpansionRegion.class),
                MasPlugin.getImageDescriptor("icons/alt_window_16.gif"),
                MasPlugin.getImageDescriptor("icons/alt_window_16.gif"));
        group.add(entry);

        entry = new CreationToolEntry("InputPin",
                "new InputPin",
                new MaseCreationFactory(editDomain, InputPin.class),
                MasPlugin.getImageDescriptor("icons/rectangle.gif"),
                MasPlugin.getImageDescriptor("icons/rectangle.gif"));
        group.add(entry);

        entry = new CreationToolEntry("OutputPin",
                "new OutputPin",
                new MaseCreationFactory(editDomain, OutputPin.class),
                MasPlugin.getImageDescriptor("icons/rectangle.gif"),
                MasPlugin.getImageDescriptor("icons/rectangle.gif"));
        group.add(entry);

        entry = new CreationToolEntry("ContextPin",
                "new ContextPin",
                new MaseCreationFactory(editDomain, ContextPin.class),
                MasPlugin.getImageDescriptor("icons/rectangle.gif"),
                MasPlugin.getImageDescriptor("icons/rectangle.gif"));
        group.add(entry);

        entry = new CreationToolEntry("ContextExtensionPin",
                "new ContextExtensionPin",
                new MaseCreationFactory(editDomain, ContextExtensionPin.class),
                MasPlugin.getImageDescriptor("icons/rectangle.gif"),
                MasPlugin.getImageDescriptor("icons/rectangle.gif"));
        group.add(entry);

        entry = new CreationToolEntry("InExpansionNode",
                "new InExpansionNode",
                new MaseCreationFactory(editDomain, InExpansionNode.class),
                MasPlugin.getImageDescriptor("icons/rectangle.gif"),
                MasPlugin.getImageDescriptor("icons/rectangle.gif"));
        group.add(entry);

        entry = new CreationToolEntry("OutExpansionNode",
                "new OutExpansionNode",
                new MaseCreationFactory(editDomain, OutExpansionNode.class),
                MasPlugin.getImageDescriptor("icons/rectangle.gif"),
                MasPlugin.getImageDescriptor("icons/rectangle.gif"));
        group.add(entry);

        // we do not distinguish between ControlFlow and ObjectFlow,
        // for implementation details see the node editpolicies
        entry = new ConnectionCreationToolEntry("ActivityEdge",
                "new ActivityEdge",
                new MaseCreationFactory(editDomain, ControlFlow.class),
                MasPlugin.getImageDescriptor("icons/relationship.gif"),
                MasPlugin.getImageDescriptor("icons/relationship.gif"));
        group.add(entry);

        return root;
    }

    @Override
    protected void initializeGraphicalViewer() {
        EditPartViewer viewer = getGraphicalViewer();
        viewer.setContents( getContent() ); // this is the "root" model element
    }

    protected void configureGraphicalViewer() {
        super.configureGraphicalViewer();
        GraphicalViewer viewer = getGraphicalViewer();

        // context menu
        getGraphicalViewer().setContextMenu(new ContextMenuProviderImpl(getActionRegistry(), getGraphicalViewer()));

        // define root editpart and editpart factory
        ScalableFreeformRootEditPart rootPart = new ScalableFreeformRootEditPart();
        viewer.setRootEditPart(rootPart);
        viewer.setEditPartFactory(new EditPartFactoryImpl());

        // install a key handler
        KeyHandler keyHandler = new KeyHandler();
        keyHandler.put(KeyStroke.getPressed(SWT.DEL, 127, 0), getActionRegistry().getAction(ActionFactory.DELETE.getId()));
        getGraphicalViewer().setKeyHandler(new GraphicalViewerKeyHandler(getGraphicalViewer()).setParent(keyHandler));

        ((FigureCanvas) viewer.getControl()).setScrollBarVisibility(FigureCanvas.ALWAYS);
    }
    
    @SuppressWarnings("unchecked")
    public void createActions() {
        super.createActions();

        ActionRegistry registry = getActionRegistry();
        IAction action;

        action = new CreateGuardSpecificationAction((IWorkbenchPart) this, getEditDomain());
        registry.registerAction(action);
        getSelectionActions().add(action.getId());
    }

    protected hub.sam.mas.model.mas.Activity getContent() {
        IMaseEditorInput editorInput = (IMaseEditorInput) getEditorInput();
        MaseEditDomain editDomain = getEditDomain();
        editDomain.setMASContext( editorInput.getLink().getMASContext() );
        return editorInput.getLink().getActivity();
    }
    
    protected void setInput(IEditorInput input) {
        super.setInput(input);
        editorInputChangeListener = new EditorInputChangeListener(this);
        ((IMaseEditorInput) input).getLink().addListener(editorInputChangeListener);
        String partName = ((IMaseEditorInput) input).getName();
        setPartName(partName);
    }
    
    private EditorInputChangeListener editorInputChangeListener;
    
    class EditorInputChangeListener implements PropertyChangeListener {
        private final IEditorPart editor;
        public EditorInputChangeListener(IEditorPart editor) {
            this.editor = editor;
        }
        public void propertyChange(PropertyChangeEvent event) {
            if (event.getPropertyName().equals("deleted")) {
                PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeEditor(editor, false);
            }
        }
    }

    /*
     * the code below handles updating the dirty state of our editor when the
     * CommandStack changes
     */

    private boolean isDirty;

    // here we listen to the CommandStack and we get informed if it changes
    private CommandStackListener commandStackListener = new CommandStackListener() {
        public void commandStackChanged(EventObject event) {
            setDirty(getCommandStack().isDirty());
        }
    };

    public boolean isDirty() {
        return isDirty;
    }

    protected void setDirty(boolean dirty) {
        if (isDirty != dirty) {
            isDirty = dirty;
            firePropertyChange(IEditorPart.PROP_DIRTY); // notify Eclipse workbench
        }
    }

    public CommandStack getCommandStack() {
        return getEditDomain().getCommandStack();
    }

    /*
     * protected CommandStackListener getCommandStackListener() { return
     * commandStackListener; }
     */

    public void init(IEditorSite site, IEditorInput input) throws PartInitException {
        super.init(site, input);

        // add CommandStackListener when the editor gets its input
        getCommandStack().addCommandStackListener(commandStackListener);
    }

    public void dispose() {
        // remove CommandStackListener
        getCommandStack().removeCommandStackListener(commandStackListener);
        ModelGarbageCollector.getInstance().dispose();
        ((IMaseEditorInput) getEditorInput()).getLink().removeListener(editorInputChangeListener);
        super.dispose();
    }

    public void doSave(IProgressMonitor monitor) {
        ModelGarbageCollector.getInstance().cleanUp();
        try {
            getEditDomain().getMASContext().save();
        }
        catch (Exception e) {
            MessageDialog.openError(
                    getEditorSite().getShell(),
                    "Could not save ...",
                    "Could not save: " + e.getMessage());
            return;
        }
        // update last save in CommandStack when the editor is saved
        getCommandStack().markSaveLocation();
    }

    public boolean isSaveAsAllowed() {
        return false;
    }

    /*
     * provide adaptable behaviour to GEF or Eclipse
     */
    public Object getAdapter(Class adapter) {
        if (adapter == GraphicalViewer.class || adapter == EditPartViewer.class)
            return getGraphicalViewer();
        else if (adapter == CommandStack.class)
            return getCommandStack();
        else if (adapter == EditDomain.class)
            return getEditDomain();
        else if (adapter == IPropertySheetPage.class)
            return getPropertySheetPage();

        // super implementation handles the rest
        return super.getAdapter(adapter);
    }

    public void createPartControl(Composite parent) {
        super.createPartControl(parent);

        getEditorSite().getActionBars().setGlobalActionHandler(ActionFactory.UNDO.getId(),
                getActionRegistry().getAction(ActionFactory.UNDO.getId()));

        getEditorSite().getActionBars().setGlobalActionHandler(ActionFactory.REDO.getId(),
                getActionRegistry().getAction(ActionFactory.REDO.getId()));

        getEditorSite().getActionBars().setGlobalActionHandler(ActionFactory.DELETE.getId(),
                getActionRegistry().getAction(ActionFactory.DELETE.getId()));
    }
    
    private PropertySheetPage propPage;
    
    public PropertySheetPage getPropertySheetPage() {
        if (propPage == null) {
            propPage = new PropertySheetPage();
            UndoablePropertySheetEntry rootEntry = new UndoablePropertySheetEntry(getCommandStack());
            propPage.setRootEntry(rootEntry);
        }
        return propPage;
    }

}
