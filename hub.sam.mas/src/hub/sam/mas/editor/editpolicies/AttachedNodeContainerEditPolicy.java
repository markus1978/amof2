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

package hub.sam.mas.editor.editpolicies;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editpolicies.ContainerEditPolicy;

/**
 * EditPolicy for creating AttachedNodes on ActivityNodes.
 * 
 * @author Andreas Blunk
 */
public abstract class AttachedNodeContainerEditPolicy extends ContainerEditPolicy {
    
    public EditPart getTargetEditPart(Request request) {
        if (REQ_ADD.equals(request.getType())
            || REQ_MOVE.equals(request.getType())
            || REQ_CREATE.equals(request.getType())
            || REQ_CLONE.equals(request.getType()))
            return getHost();
        return null;
    }

}
