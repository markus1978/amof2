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

package hub.sam.mase.editor;

import hub.sam.mase.MasePlugin;
import hub.sam.mase.m2model.ContextExtensionPin;
import hub.sam.mase.m2model.ContextPin;
import hub.sam.mase.m2model.ControlFlow;
import hub.sam.mase.m2model.DecisionNode;
import hub.sam.mase.m2model.ExpansionRegion;
import hub.sam.mase.m2model.FinalNode;
import hub.sam.mase.m2model.InExpansionNode;
import hub.sam.mase.m2model.InitialNode;
import hub.sam.mase.m2model.InputPin;
import hub.sam.mase.m2model.MaseRepository;
import hub.sam.mase.m2model.ModelGarbageCollector;
import hub.sam.mase.m2model.MaseCreationFactory;
import hub.sam.mase.m2model.OpaqueAction;
import hub.sam.mase.m2model.OutExpansionNode;
import hub.sam.mase.m2model.OutputPin;
import hub.sam.mase.m2model.ValueNode;
import hub.sam.mase.actions.CreateGuardSpecificationAction;

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
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheetPage;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import java.util.EventObject;
import java.util.Properties;

import org.eclipse.core.runtime.Path;
import java.io.*;

import org.eclipse.core.runtime.FileLocator;

public class MaseEditor extends GraphicalEditorWithPalette {
    
    public MaseEditor() {
        super();
        MaseEditDomain editDomain = new MaseEditDomain(this);
        PropertyConfigurator.configure("resources/log4j.properties");

        org.osgi.framework.Bundle bundle = MasePlugin.getDefault().getBundle();
        try {            
            InputStream propertiesInputStream = FileLocator.openStream(bundle, new Path("resources/mase.properties"), false);
            Properties properties = new Properties();
            properties.load(propertiesInputStream);
            MaseEditDomain.setProperties(properties);
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
    
    private MaseRepository getRepository() {
        return getEditDomain().getRepository();
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
                MasePlugin.getImageDescriptor("icons/alt_window_16.gif"),
                MasePlugin.getImageDescriptor("icons/alt_window_16.gif"));
        group.add(entry);

        entry = new CreationToolEntry("FinalNode",
                "new FinalNode",
                new MaseCreationFactory(editDomain, FinalNode.class),
                MasePlugin.getImageDescriptor("icons/alt_window_16.gif"),
                MasePlugin.getImageDescriptor("icons/alt_window_16.gif"));
        group.add(entry);

        entry = new CreationToolEntry("OpaqueAction",
                "new OpaqueAction",
                new MaseCreationFactory(editDomain, OpaqueAction.class),
                MasePlugin.getImageDescriptor("icons/alt_window_16.gif"),
                MasePlugin.getImageDescriptor("icons/alt_window_16.gif"));
        group.add(entry);

        entry = new CreationToolEntry("ValueNode",
                "new ValueNode",
                new MaseCreationFactory(editDomain, ValueNode.class),
                MasePlugin.getImageDescriptor("icons/alt_window_16.gif"),
                MasePlugin.getImageDescriptor("icons/alt_window_16.gif"));
        group.add(entry);

        entry = new CreationToolEntry("DecisionNode",
                "new DecisionNode",
                new MaseCreationFactory(editDomain, DecisionNode.class),
                MasePlugin.getImageDescriptor("icons/alt_window_16.gif"),
                MasePlugin.getImageDescriptor("icons/alt_window_16.gif"));
        group.add(entry);

        entry = new CreationToolEntry("ExpansionRegion",
                "new ExpansionRegion",
                new MaseCreationFactory(editDomain, ExpansionRegion.class),
                MasePlugin.getImageDescriptor("icons/alt_window_16.gif"),
                MasePlugin.getImageDescriptor("icons/alt_window_16.gif"));
        group.add(entry);

        entry = new CreationToolEntry("InputPin",
                "new InputPin",
                new MaseCreationFactory(editDomain, InputPin.class),
                MasePlugin.getImageDescriptor("icons/rectangle.gif"),
                MasePlugin.getImageDescriptor("icons/rectangle.gif"));
        group.add(entry);

        entry = new CreationToolEntry("OutputPin",
                "new OutputPin",
                new MaseCreationFactory(editDomain, OutputPin.class),
                MasePlugin.getImageDescriptor("icons/rectangle.gif"),
                MasePlugin.getImageDescriptor("icons/rectangle.gif"));
        group.add(entry);

        entry = new CreationToolEntry("ContextPin",
                "new ContextPin",
                new MaseCreationFactory(editDomain, ContextPin.class),
                MasePlugin.getImageDescriptor("icons/rectangle.gif"),
                MasePlugin.getImageDescriptor("icons/rectangle.gif"));
        group.add(entry);

        entry = new CreationToolEntry("ContextExtensionPin",
                "new ContextExtensionPin",
                new MaseCreationFactory(editDomain, ContextExtensionPin.class),
                MasePlugin.getImageDescriptor("icons/rectangle.gif"),
                MasePlugin.getImageDescriptor("icons/rectangle.gif"));
        group.add(entry);

        entry = new CreationToolEntry("InExpansionNode",
                "new InExpansionNode",
                new MaseCreationFactory(editDomain, InExpansionNode.class),
                MasePlugin.getImageDescriptor("icons/rectangle.gif"),
                MasePlugin.getImageDescriptor("icons/rectangle.gif"));
        group.add(entry);

        entry = new CreationToolEntry("OutExpansionNode",
                "new OutExpansionNode",
                new MaseCreationFactory(editDomain, OutExpansionNode.class),
                MasePlugin.getImageDescriptor("icons/rectangle.gif"),
                MasePlugin.getImageDescriptor("icons/rectangle.gif"));
        group.add(entry);

        // we do not distinguish between ControlFlow and ObjectFlow,
        // for implementation details see the node editpolicies
        entry = new ConnectionCreationToolEntry("ActivityEdge",
                "new ActivityEdge",
                new MaseCreationFactory(editDomain, ControlFlow.class),
                MasePlugin.getImageDescriptor("icons/relationship.gif"),
                MasePlugin.getImageDescriptor("icons/relationship.gif"));
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

    private InputStream xmiInputStream = null;
    
    // return root model element (the content editpart)
    protected hub.sam.mase.m2model.Activity getContent() {
        if (!MaseRepository.isInitialized()) {
            org.osgi.framework.Bundle bundle = MasePlugin.getDefault().getBundle();
            try {            
                xmiInputStream = FileLocator.openStream(bundle, new Path(MaseRepository.relativeXmiPath), false);
            }
            catch(IOException e) {
                System.out.println("IOException while trying to open InputStream to " + MaseRepository.relativeXmiPath);
                System.exit(-1);
            }
            MaseRepository.init(xmiInputStream);
        }
        
        IFileEditorInput editorInput = ((IFileEditorInput) getEditorInput());
        getEditDomain().setRepository( new MaseRepository(getEditDomain(), editorInput) );
        return getRepository().loadModel();
    }
    
    protected void setInput(IEditorInput input) {
        super.setInput(input);
        IFile file = ((IFileEditorInput) input).getFile();
        setPartName(file.getName());
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
        
        ModelGarbageCollector.getInstance().cleanUp();
        getRepository().deleteModelExtent();        
        ModelGarbageCollector.getInstance().dispose();
        
        try {
            if (xmiInputStream != null) {
                xmiInputStream.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        super.dispose();
    }

    public void doSave(IProgressMonitor monitor) {
        getRepository().saveModel();
        
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
