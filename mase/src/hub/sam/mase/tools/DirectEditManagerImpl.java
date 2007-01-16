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

package hub.sam.mase.tools;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import hub.sam.mase.editparts.EditableEditPart;

public class DirectEditManagerImpl extends DirectEditManager implements ICellEditorValidator {

    public DirectEditManagerImpl(GraphicalEditPart source, Class editorType, FigureCellEditorLocator locator) {
        super(source, editorType, locator);
    }
    
    protected CellEditor createCellEditorOn(Composite composite) {
        return new TextCellEditor(composite, SWT.MULTI | SWT.WRAP);
    }
    
    private String getInitialValue() {
        return ((EditableEditPart) getEditPart()).getDirectEditValue();
    }

    @Override
    protected void initCellEditor() {
        getCellEditor().setValue(getInitialValue());
        getCellEditor().setValidator(this);
    }

    public String isValid(Object value) {
        if (((String) value).equals("")) {
            return "an empty activity is not allowed";
        }
        else {
            return "";
        }
    }

}
