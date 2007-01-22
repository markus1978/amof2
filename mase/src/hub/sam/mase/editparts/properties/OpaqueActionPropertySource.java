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

package hub.sam.mase.editparts.properties;

import hub.sam.mase.editparts.properties.handlers.CommentedNodeHandler;
import hub.sam.mase.editparts.properties.handlers.OpaqueActionHandler;
import hub.sam.mase.m2model.OpaqueAction;

public class OpaqueActionPropertySource extends AbstractPropertySource {

    private final OpaqueAction model;
    
    public OpaqueActionPropertySource(OpaqueAction model) {
        this.model = model;
    }

    @Override
    protected void createPropertyHandlers() {
        installPropertyHandler(new CommentedNodeHandler(model));
        installPropertyHandler(new OpaqueActionHandler(model));
    }
    
}
