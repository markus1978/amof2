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

package hub.sam.mas.editor.editparts;

import java.util.*;

import hub.sam.mas.model.mas.Action;
import hub.sam.mas.model.mas.OutputPinList;
import hub.sam.mas.model.mas.PinList;

public abstract class ActionEditPart extends ActivityNodeEditPart {
    
    public Action getModel() {
        return (Action) super.getModel();
    }    
    
    public List getModelChildren() {
        List<PinList> modelChildren = new hub.sam.mof.util.ListWrapper<PinList>( getModel().getPinList() );
        
        // enforce order: modelChildren = {inputList, outputList}
        Object firstChild = modelChildren.get(0);
        if (firstChild instanceof OutputPinList) {
            List<PinList> newList = new ArrayList<PinList>();
            newList.add( modelChildren.get(1) );
            newList.add( modelChildren.get(0) );
            return newList;
        }
        
        return modelChildren;
    }

}
