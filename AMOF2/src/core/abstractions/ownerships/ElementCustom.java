/*
A MOF 2 Java -- The MOF Repository tool for Java
Copyright (C) 2005 Markus Scheidgen

    This library is free software; you can redistribute it and/or modify it
under the terms of the GNU Lesser General Public License as published by the
Free Software Foundation; either version 2.1 of the License, or any later
version.

    This library is distributed in the hope that it will be useful, but WITHOUT
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
details.

    You should have received a copy of the GNU Lesser General Public License
along with this library; if not, write to the Free Software Foundation, Inc.,
59 Temple Place, Suite 330, Boston, MA 02111-1307 USA 
*/

package core.abstractions.ownerships;

import cmof.common.ReflectiveCollection;
import hub.sam.mof.util.SetImpl;

public class ElementCustom extends ElementDlg {
	/**
     * <b>allOwnedElements</b>, multiplicity=(0,*)
     * 
     * The query allOwnedElements() gives all of the direct and indirect owned elements of an element.
     */
	@Override
	public cmof.common.ReflectiveCollection<? extends core.abstractions.ownerships.Element> allOwnedElements() {
    	ReflectiveCollection<Element> allOwnedElements = new SetImpl<Element>();
    	allOwnedElements.addAll(self.getOwnedElement());
    	for (Element element: self.getOwnedElement()) {
    		allOwnedElements.addAll(element.allOwnedElements());
    	}
    	return allOwnedElements;
    }

    /**
     * <b>mustBeOwned</b>, multiplicity=(1,1)
     * 
     * The query mustBeOwned() indicates whether elements of this type must have an owner. Subclasses of Element that do
     * not require an owner must override this operation.
     */
    @Override
	public boolean mustBeOwned() {
    	return true;
    }

}
