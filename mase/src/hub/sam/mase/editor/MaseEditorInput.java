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

import hub.sam.mas.management.MASLink;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;

public class MaseEditorInput implements IMaseEditorInput {
    
    private final MASLink link;
    
    public MaseEditorInput(MASLink link) {
        this.link = link;
    }
    
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IMaseEditorInput)) {
            return false;
        }
        IMaseEditorInput other = (IMaseEditorInput) obj;
        return link.getActivity().equals(other.getLink().getActivity());
    }
    
    public boolean exists() {
        // TODO Auto-generated method stub
        return false;
    }

    public ImageDescriptor getImageDescriptor() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getName() {
        return link.getOperation().getQualifiedName();
    }

    public IPersistableElement getPersistable() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getToolTipText() {
        return link.getMASContext().getSemanticModel().getExtentName();
    }

    public Object getAdapter(Class adapter) {
        // TODO Auto-generated method stub
        return null;
    }

    public MASLink getLink() {
        return link;
    }

}
