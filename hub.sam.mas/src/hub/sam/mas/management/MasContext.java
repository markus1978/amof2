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

package hub.sam.mas.management;

import hub.sam.mas.MasPlugin;
import hub.sam.mas.editor.MaseEditor;
import hub.sam.mas.model.mas.Activity;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import cmof.Classifier;
import cmof.Comment;
import cmof.Element;
import cmof.Feature;
import cmof.Operation;
import cmof.Package;
import cmof.cmofFactory;
import cmof.reflection.Extent;

/**
 * the glue between a syntax and a semantic model. the context manages
 * the connection and offers functionality for:
 * - saving and closing models
 * - preserving integrity betweens models
 * - creating, deleting and retrieving links for operations and activities
 * 
 * a mas context can only be created via the {@link MasRepository}.
 * 
 */
public class MasContext {
    
    private static Logger logger = Logger.getLogger(MasContext.class.getName());
    private Extent contextId;
    private Map<String, MasLink> links = new TreeMap<String, MasLink>();
    private MofModel syntaxModel;
    private MofModel masModel;
    private static String linkIdPrefix = "mas-id-";
    private Map<String, Operation> operations  = new TreeMap<String, Operation>();
    private Map<String, Activity> activities = new TreeMap<String, Activity>();
    private boolean syntaxModelNeedsSaving = false;
    
    protected MasContext(IMasModelContainer modelContainer) {
        MasPlugin.configureLog4j();
        
        syntaxModel = modelContainer.getSyntaxModel();
        masModel = modelContainer.getMasModel();
        checkMasModel();
        
        contextId = syntaxModel.getExtent();

        operations = getOperations(syntaxModel.getExtent());
        activities = getActivities(masModel.getExtent());
        
        preserveIntegrity(operations, activities);
    }
    
    private void checkMasModel() {
        if (logger.isEnabledFor(Level.WARN)) {
            for(cmof.reflection.Object obj: getMasModel().getExtent().outermostComposites()) {
                if (!(obj instanceof Activity)) {
                    logger.warn("found object outside of the activity");
                }
            }
        }
    }
    
    /**
     * creates a physical connection and returns a virtual mas link.
     * 
     * @param operation
     * @param activity
     * @return
     */
    public MasLink createLink(Operation operation, Activity activity) {
        // TODO may not be unique in all cases
        String linkId = "mas-id-" + operation.toString().hashCode();
        
        cmofFactory syntaxFactory = (cmofFactory) getSyntaxModel().getFactory();
        Comment com = syntaxFactory.createComment();
        com.setBody(linkId);
        operation.getOwnedComment().add(com);
        
        activity.setLinkId(linkId);
        
        MasLink link = new MasLink(linkId, this, operation, activity);
        links.put(linkId, link);
        
        setSyntaxModelNeedsSaving(true);
        
        return link;
    }
    
    /**
     * returns a virtual mas link or null if no virtual link or physical connection exists.
     * 
     * @param operation
     * @return
     */
    public MasLink getLink(Operation operation) {
        String linkId = getLinkId(operation);
        if (linkId == null) {
            return null;
        }
        if (links.containsKey(linkId)) {
            return links.get(linkId);
        }
        Activity activity = getActivity(linkId);
        if (activity == null) {
            return null;
        }
        MasLink link = new MasLink(linkId, this, operation, activity);
        links.put(linkId, link);
        return link;
    }
    
    /**
     * returns a virtual mas link or null if no virtual link or physical connection exists.
     * 
     * @param activity
     * @return
     */
    public MasLink getLink(Activity activity) {
        String linkId = getLinkId(activity);
        if (linkId == null) {
            return null;
        }
        if (links.containsKey(linkId)) {
            return links.get(linkId);
        }
        Operation operation = getOperation(linkId);
        if (operation == null) {
            return null;
        }
        MasLink link = new MasLink(linkId, this, operation, activity);
        links.put(linkId, link);
        return link;
    }
    
    private String getLinkId(Operation operation) {
        String id = null;
        for(Comment com: operation.getOwnedComment()) {
            if (com.getBody() != null && com.getBody().startsWith(linkIdPrefix)) {
                return com.getBody();
            }
        }
        return id;
    }
    
    private String getLinkId(Activity activity) {
        return activity.getLinkId();
    }
    
    private Operation getOperation(String linkId) {
        return operations.get(linkId);
    }
    
    private Activity getActivity(String linkId) {
        return activities.get(linkId);
    }

    public MofModel getMasModel() {
        return masModel;
    }

    public MofModel getSyntaxModel() {
        return syntaxModel;
    }
    
    private void warnUser(String title, String message) {
        if (PlatformUI.isWorkbenchRunning() && PlatformUI.getWorkbench() != null) {
            Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart().getSite().getShell();
            MessageDialog.openWarning(shell, title, message);
        }
        else {
            System.out.println("Warning: " + message);
        }
    }
    
    /**
     * preserves reference integrity between a set of operations and activities
     * 
     * @param syntaxExtent
     * @param semanticExtent
     */
    private void preserveIntegrity(Map<String, Operation> operations, Map<String, Activity> activities) {
        for(String id: operations.keySet()) {
            if (activities.containsKey(id)) {
                continue;
            }
            // no activity was found, delete id in operation
            deleteLinkId(operations.get(id));
            warnUser("Preserved reference integrity",
                    "Syntax extent: deleted " + id + " in operation \"" + operations.get(id).getQualifiedName() + "\".");
        }

        int deleted = 0;
        for(String id: activities.keySet()) {
            if (operations.containsKey(id)) {
                continue;
            }
            // no operation was found, delete activity
            activities.get(id).delete();
            deleted++;
        }
        if (deleted > 0) {
            warnUser("Preserved reference integrity", "Semantic extent: deleted " + deleted + " unreferenced activities.");
        }
    }
    
    /**
     * destroys a physical connection at the syntax side.
     * 
     * @param operation
     */
    private void deleteLinkId(Operation operation) {
        for(Comment com: operation.getOwnedComment()) {
            if (com.getBody().startsWith(linkIdPrefix)) {
                operation.getOwnedComment().remove(com);
                com.delete();
                return;
            }
        }
    }
    
    /**
     * destroys a physical connection at the semantic side.
     * 
     * @param activity
     */
    private void deleteLinkId(Activity activity) {
        activity.setLinkId(null);
        activity.delete();
    }

    /**
     * destroys a physical connection on both sides (syntax and semantic model).
     * 
     * @param link
     */
    protected void deleteLink(MasLink link) {
        deleteLinkId(link.getOperation());
        deleteLinkId(link.getActivity());
        getLinks().remove(link.getLinkId());
        setSyntaxModelNeedsSaving(true);
        try {
            save();
        }
        catch (SaveException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * returns a TreeMap that maps ids to operations
     * 
     * @param syntaxExtent
     * @return
     */
    private Map<String, Operation> getOperations(Extent syntaxExtent) {
        Map<String, Operation> operations  = new TreeMap<String, Operation>();
        for(Object obj: syntaxExtent.outermostComposites()) {
            if (obj instanceof Package) {
                for(Element element: ((Package) obj).getOwnedElement()) {
                    if (element instanceof Classifier) {
                        for(Feature feature: ((Classifier) element).getFeature()) {
                            if (feature instanceof Operation) {
                                Operation op = (Operation) feature;
                                String id = getLinkId(op);
                                if (id != null) {
                                    operations.put(id, op);
                                }
                            }
                        }
                    }
                }
            }
        }
        return operations;
    }

    /**
     * returns a TreeMap that maps ids to activities
     * 
     * @param semanticExtent
     * @return
     */
    private Map<String, Activity> getActivities(Extent semanticExtent) {
        Map<String, Activity> activities = new TreeMap<String, Activity>();
        for(Object obj: semanticExtent.outermostComposites()) {
            if (obj instanceof Activity) {
                Activity act = (Activity) obj;
                if (act.getLinkId() != null) {
                    activities.put(act.getLinkId(), act);
                }
            }
        }
        return activities;
    }

    protected Extent getContextId() {
        return contextId;
    }
    
    public void close() {
        getSyntaxModel().close();
        getMasModel().close();
    }
    
    /**
     * Always saves the semantic model, but only saves the syntax model if needed.
     * 
     * @throws SaveException 
     */
    public void save() throws SaveException {
        checkMasModel();
        getMasModel().save();
        
        if(syntaxModelNeedsSaving) {
            getSyntaxModel().save();
        }
        
        // mark associated mas editors as saved for all mas links
        for(MasLink link: getLinks().values()) {
            MaseEditor editor = link.getAssociatedEditor();
            if (editor != null) {
                editor.setDirty(false);
            }
        }
    }

    private Map<String, MasLink> getLinks() {
        return links;
    }

    protected void setSyntaxModelNeedsSaving(boolean syntaxModelNeedsSaving) {
        this.syntaxModelNeedsSaving = syntaxModelNeedsSaving;
    }
    
}
