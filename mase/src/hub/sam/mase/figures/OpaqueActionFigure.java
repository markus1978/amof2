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

package hub.sam.mase.figures;

import hub.sam.mof.model.mas.ActionKind;

import org.eclipse.draw2d.IFigure;

public class OpaqueActionFigure extends NodeAttachedFigure implements EditableFigure {
    
    private final ActionKind actionKind;

    public OpaqueActionFigure(int margin, ActionKind actionKind) {
        super(margin);
        this.actionKind = actionKind;
    }
    
    protected boolean addBodyFigure() {
        body = new OpaqueActionBodyFigure(margin, actionKind);
        add(body);
        return true;
    }
    
    public IFigure getLocatorFigure() {
        return ((EditableFigure) body).getLocatorFigure();
    }
    
    public void setActionKind(ActionKind actionKind) {
        ((OpaqueActionBodyFigure) body).setActionKind(actionKind);
    }
    
    public void setText(String str) {
        ((EditableFigure) body).setText(str);
    }

    public String getText() {
        return ((EditableFigure) body).getText();
    }
    
    public void setComment(String text) {
        ((OpaqueActionBodyFigure) body).setComment(text);
    }

}