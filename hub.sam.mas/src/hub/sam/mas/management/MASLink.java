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

import hub.sam.mas.model.mas.Activity;
import cmof.Operation;

public class MASLink {

    private final String linkId;
    private final MASContext context;
    private final Operation operation;
    private final Activity activity;
    
    protected PropertyChangeSupport listeners = new PropertyChangeSupport(this);
    
    public MASLink(String linkId, MASContext context, Operation operation, Activity activity) {
        this.linkId = linkId;
        this.context = context;
        this.operation = operation;
        this.activity = activity;
    }
    
    public MASContext getMASContext() {
        return context;
    }
    
    public Activity getActivity() {
        return activity;
    }
    
    public Operation getOperation() {
        return operation;
    }
       
    public void delete() {
        context.deleteLink(this);
        listeners.firePropertyChange("deleted", null, null);
    }

    protected String getLinkId() {
        return linkId;
    }
    
    public void addListener(PropertyChangeListener listener) {
        listeners.addPropertyChangeListener(listener);
    }
    
    public void removeListener(PropertyChangeListener listener) {
        listeners.removePropertyChangeListener(listener);
    }
    
}
