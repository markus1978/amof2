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

import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.GroupRequest;

import hub.sam.mas.editor.commands.ActivityGroupDeleteCommand;
import hub.sam.mas.editor.editpolicies.AbstractActivityNodeComponentEditPolicy;
import hub.sam.mas.model.mas.ActivityGroup;

/**
 * EditPolicy for deleting ExpansionRegions.
 * 
 * @author Andreas Blunk
 */
public final class ExpansionRegionComponentEditPolicy extends AbstractActivityNodeComponentEditPolicy {
    
    public CompoundCommand createDeleteCommand(GroupRequest r) {
        CompoundCommand compound = super.createDeleteCommand(r);
        
        // delete the ExpansionRegion, which is an ActivityGroup
        ActivityGroup model = (ActivityGroup) getHost().getModel();
        ActivityGroupDeleteCommand delete = new ActivityGroupDeleteCommand(model);
        compound.add(delete);

        return compound;
    }

}
