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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

public abstract class PropertyAwareGraphicalEditPart extends AbstractGraphicalEditPart implements PropertyChangeListener {

    /**
     * add our editpart as a listener to the model element
     */
    public void activate() {
        if (isActive()) return;
        super.activate(); // <--- very important, never forget this :-)
        // you will not be able to select the editpart without this !
        ((cmof.reflection.Object) getModel()).addListener(this);
    }

    /**
     * remove us
     */
    public void deactivate() {
        if (!isActive()) return;
        super.deactivate(); // <--- very important, never forget this :-)
        ((cmof.reflection.Object) getModel()).removeListener(this);
    }

    /**
     * This method gets called if one of the model elements properties has
     * changed. It should be implemented by any specific editpart to contribute
     * the appropriate changes to the view!! We just use it here in a generic
     * way.
     */
    public void propertyChange(PropertyChangeEvent ev) {
        refreshChildren();
        refreshVisuals();
    }
    
    public AbstractGraphicalEditPart getParent() {
        return (AbstractGraphicalEditPart) super.getParent();
    }

}
