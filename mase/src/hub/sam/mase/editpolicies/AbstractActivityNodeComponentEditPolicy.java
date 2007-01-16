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

package hub.sam.mase.editpolicies;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

/**
 * Abstract EditPolicy for deleting ActivityNodes. Specialized classes must delete the
 * ActivityNode via an ActivityNodeDeleteCommand.
 * 
 * Delete requests will be forwared to all connected ActivityEdges and to all other
 * children. 
 * 
 * @author Andreas Blunk
 */
public abstract class AbstractActivityNodeComponentEditPolicy extends ComponentEditPolicy {
    
    public AbstractGraphicalEditPart getHost() {
        return (AbstractGraphicalEditPart) super.getHost();
    }

    @Override
    protected Command getDeleteCommand(GroupRequest request) {
        return createDeleteCommand((GroupRequest) request);
    }

    public CompoundCommand createDeleteCommand(GroupRequest r) {
        CompoundCommand compound = new CompoundCommand();

        // delete incoming ActivityEdges
        List sourceConnections = getHost().getSourceConnections();
        for(Object editPart: sourceConnections) {
            GroupRequest request = new GroupRequest();
            request.setType(RequestConstants.REQ_DELETE);
            compound.add( ((EditPart) editPart).getCommand(request) );
        }

        // delete outgoing ActivityEdges
        List targetConnections = getHost().getTargetConnections();
        for(Object editPart: targetConnections) {
            GroupRequest request = new GroupRequest();
            request.setType(RequestConstants.REQ_DELETE);
            compound.add( ((EditPart) editPart).getCommand(request) );
        }
        
        // delete other children (if any)
        for(Object hostChild: getHost().getChildren()) {
            GroupRequest request = new GroupRequest();
            request.setType(RequestConstants.REQ_DELETE);
            compound.add( ((EditPart) hostChild).getCommand(request) );
        }
        
        return compound;
    }

}
