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

package hub.sam.mas.editor.editparts.properties.handlers;

import hub.sam.mas.model.mas.ActionKind;
import hub.sam.mas.model.mas.OpaqueAction;

public class OpaqueActionHandler extends AbstractPropertyHandler {

    private OpaqueAction model;
    
    public OpaqueActionHandler(OpaqueAction model) {
        this.model = model;
        getHandlers().add(new BodyHandler());
        getHandlers().add(new KindHandler());
    }
    
    private class BodyHandler extends StringAttributeHandler {
        
        public BodyHandler() {
            super("OPAQUE_ACTION_BODY", "body");
        }

        @Override
        protected String getString() {
            return model.getActionBody();
        }

        @Override
        protected void setString(String value) {
            model.setActionBody(value);
        }
        
    }
    
    private class KindHandler extends EnumerationAttributeHandler {
        
        public KindHandler() {
            super("OPAQUE_ACTION_KIND", "kind", ActionKind.class);
        }
        
        protected ActionKind valueOf(String name) {
            return ActionKind.valueOf(name);
        }

        @Override
        protected void setEnum(Object value) {
            model.setActionKind((ActionKind) value);
        }

        @Override
        protected Object getEnum() {
            return model.getActionKind();
        }

    }
    
}
