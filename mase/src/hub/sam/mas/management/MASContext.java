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

import hub.sam.mase.m2model.Activity;
import hub.sam.mof.instancemodel.MetaModelException;
import hub.sam.mof.xmi.XmiException;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.jdom.JDOMException;

import cmof.Classifier;
import cmof.Comment;
import cmof.Element;
import cmof.Feature;
import cmof.Operation;
import cmof.Package;
import cmof.cmofFactory;
import cmof.reflection.Extent;

public class MASContext {
    
    private Extent contextId;
    private Map<String, MASLink> links = new TreeMap<String, MASLink>();
    private MOFModel syntaxModel;
    private MOFModel semanticModel;
    private static String linkIdPrefix = "mas-id-";
    private Map<String, Operation> operations  = new TreeMap<String, Operation>();
    private Map<String, Activity> activities = new TreeMap<String, Activity>();
    private boolean syntaxModelNeedsSaving = false;
    
    MASContext(MOFModelFactory factory) throws MOFModelException {
        syntaxModel = factory.createSyntaxModel();
        semanticModel = factory.createSemanticModel();
        
        contextId = syntaxModel.getExtent();

        operations = getOperations(syntaxModel.getExtent());
        activities = getActivities(semanticModel.getExtent());
        
        preserveIntegrity(operations, activities);
        
        // TODO create all MASLinks ?
    }
    
    /**
     * Creates a physical link and then returns a virtual MASLink.
     * 
     * @param operation
     * @param activity
     * @return
     */
    public MASLink createLink(Operation operation, Activity activity) {
        // TODO may not be unique in all cases
        String linkId = "mas-id-" + operation.toString().hashCode();
        
        cmofFactory syntaxFactory = (cmofFactory) getSyntaxModel().getFactory();
        Comment com = syntaxFactory.createComment();
        com.setBody(linkId);
        operation.getOwnedComment().add(com);
        
        activity.setLinkId(linkId);
        
        MASLink link = new MASLink(linkId, this, operation, activity);
        links.put(linkId, link);
        
        setSyntaxModelNeedsSaving(true);
        
        return link;
    }
    
    /**
     * Returns a virtual MASLink or null if no virtual or physical link exists.
     * 
     * @param operation
     * @return
     */
    public MASLink getLink(Operation operation) {
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
        MASLink link = new MASLink(linkId, this, operation, activity);
        links.put(linkId, link);
        return link;
    }
    
    /**
     * Returns a virtual MASLink or null if no virtual or physical link exists.
     * 
     * @param activity
     * @return
     */
    public MASLink getLink(Activity activity) {
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
        MASLink link = new MASLink(linkId, this, operation, activity);
        links.put(linkId, link);
        return link;
    }
    
    private String getLinkId(Operation operation) {
        String id = null;
        for(Comment com: operation.getOwnedComment()) {
            if (com.getBody().startsWith(linkIdPrefix)) {
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

    public MOFModel getSemanticModel() {
        return semanticModel;
    }

    public MOFModel getSyntaxModel() {
        return syntaxModel;
    }
    
    /**
     * preserves reference integrity between a set of operations and activities
     * 
     * @param syntaxExtent
     * @param semanticExtent
     */
    private void preserveIntegrity(Map<String, Operation> operations, Map<String, Activity> activities) {
        Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart().getSite().getShell();

        for(String id: operations.keySet()) {
            if (activities.containsKey(id)) {
                continue;
            }
            // no activity was found, delete id in operation
            deleteLinkId(operations.get(id));
            MessageDialog.openWarning(shell, "Preserved reference integrity",
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
            MessageDialog.openWarning(shell, "Preserved reference integrity",
                    "Semantic extent: deleted " + deleted + " unreferenced activities.");
        }
    }
    
    /**
     * Deletes physical link in operation.
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
     * Deletes physical link in activity.
     * 
     * @param activity
     */
    private void deleteLinkId(Activity activity) {
        activity.setLinkId(null);
        activity.delete();
    }
    
    protected void deleteLink(MASLink link) {
        deleteLinkId(link.getOperation());
        deleteLinkId(link.getActivity());
        getLinks().remove(link.getLinkId());
        setSyntaxModelNeedsSaving(true);
        try {
            save();
        }
        catch (MOFModelException e) {
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
    
    protected void close() {
        getSyntaxModel().close();
        getSemanticModel().close();
    }
    
    /**
     * Always saves the semantic model, but only saves the syntax model if needed.
     * @throws MOFModelException 
     * 
     * @throws IOException
     * @throws MetaModelException
     * @throws XmiException
     * @throws JDOMException
     */
    public void save() throws MOFModelException {
        getSemanticModel().save();
        
        if(syntaxModelNeedsSaving) {
            getSyntaxModel().save();
        }
    }

    private Map<String, MASLink> getLinks() {
        return links;
    }

    protected void setSyntaxModelNeedsSaving(boolean syntaxModelNeedsSaving) {
        this.syntaxModelNeedsSaving = syntaxModelNeedsSaving;
    }
    
}
