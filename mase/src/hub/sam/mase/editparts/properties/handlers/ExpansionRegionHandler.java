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

package hub.sam.mase.editparts.properties.handlers;

import hub.sam.mase.m2model.ExpansionKind;
import hub.sam.mase.m2model.ExpansionRegion;

public class ExpansionRegionHandler extends EnumerationAttributeHandler {

    private final ExpansionRegion model;
    
    public ExpansionRegionHandler(ExpansionRegion model) {
        super("EXPANSION_REGION_MODE", "mode", ExpansionKind.class);
        this.model = model;
    }

    @Override
    protected Object getEnum() {
        return model.getMode();
    }

    @Override
    protected void setEnum(Object value) {
        model.setMode((ExpansionKind) value);
    }

    @Override
    protected Object valueOf(String name) {
        return ExpansionKind.valueOf(name);
    }  
    
}
