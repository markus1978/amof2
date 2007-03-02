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

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.DirectEditRequest;

import hub.sam.mas.model.mas.OpaqueAction;
import hub.sam.mase.commands.OpaqueActionDirectEditCommand;
import hub.sam.mase.figures.OpaqueActionFigure;

import org.eclipse.gef.editpolicies.DirectEditPolicy;

public class OpaqueActionDirectEditPolicy extends DirectEditPolicy {
    
    @Override
    protected Command getDirectEditCommand(DirectEditRequest request) {
        String newValue = (String) request.getCellEditor().getValue();

        if (newValue == null)
            return null;

        return new OpaqueActionDirectEditCommand(newValue, (OpaqueAction) getHost().getModel());
    }

    @Override
    protected void showCurrentEditValue(DirectEditRequest request) {
        String currentValue = (String) request.getCellEditor().getValue();
        ((OpaqueActionFigure) getHostFigure()).setText(currentValue);

        // this code is recommended by a GEF comment:
        // hack to prevent async layout from placing the cell editor twice.
        getHostFigure().getUpdateManager().performUpdate();
    }

}
