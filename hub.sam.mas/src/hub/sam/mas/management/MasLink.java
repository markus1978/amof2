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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import hub.sam.mas.editor.MaseEditor;
import hub.sam.mas.model.mas.Activity;
import cmof.Operation;

/**
 * a virtual link between an operation (syntax) and an activity (semantic).
 * the link is always based on a physical connection (reference-ids used in both models).
 * 
 */
public class MasLink {

    private final String linkId;
    private final MasContext context;
    private final Operation operation;
    private final Activity activity;
    private MaseEditor associatedEditor;
    
    protected PropertyChangeSupport listeners = new PropertyChangeSupport(this);
    
    public MasLink(String linkId, MasContext context, Operation operation, Activity activity) {
        this.linkId = linkId;
        this.context = context;
        this.operation = operation;
        this.activity = activity;
    }
    
    public MasContext getMASContext() {
        return context;
    }
    
    public Activity getActivity() {
        return activity;
    }
    
    public Operation getOperation() {
        return operation;
    }
    
    /**
     * destroys the physical connection.
     *
     */
    public void delete() {
        context.deleteLink(this);
        listeners.firePropertyChange("deleted", null, null);
    }

    /**
     * returns the reference-id of the physical connection.
     * 
     */
    protected String getLinkId() {
        return linkId;
    }
    
    public void addListener(PropertyChangeListener listener) {
        listeners.addPropertyChangeListener(listener);
    }
    
    public void removeListener(PropertyChangeListener listener) {
        listeners.removePropertyChangeListener(listener);
    }

    public MaseEditor getAssociatedEditor() {
        return associatedEditor;
    }

    /**
     * mas editor that can be associated with a single mas link.
     * 
     * @param associatedEditor
     */
    public void setAssociatedEditor(MaseEditor associatedEditor) {
        this.associatedEditor = associatedEditor;
    }
    
}
