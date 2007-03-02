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

package core.abstractions.namespaces;

import hub.sam.mof.util.SetImpl;
import cmof.common.ReflectiveCollection;

public class NamespaceCustom extends NamespaceDlg {

    /**
     * <b>getNamesOfMember</b>
     * 
     * The query getNamesOfMember() gives a set of all of the names that a member would have in a Namespace. In general a
     * member can have multiple names in a Namespace if it is imported more than once with different aliases. Those semantics
     * are specified by overriding the getNamesOfMember operation. The specification here simply returns a set containing a
     * single name, or the empty set if no name.
     */
	@Override
	public cmof.common.ReflectiveCollection<String> getNamesOfMember(core.abstractions.namespaces.NamedElement element) {
    	ReflectiveCollection<String> namesOfMembers = new SetImpl<String>(1);
    	if (self.getMember().contains(element)) {
    		namesOfMembers.add(element.getName());
    	}
    	return namesOfMembers;
    }

    /**
     * <b>membersAreDistinguishable</b>
     * The Boolean query membersAreDistinguishable() determines whether all of the namespace’s members are distinguisha-
     * ble within it.
     */
    @Override
	public boolean membersAreDistinguishable() {
    	for (NamedElement n: self.getMember()) {
    		for (NamedElement m: self.getMember()) {
    			if (!n.isDistinguishableFrom(m, self) && !n.equals(m)) {
    				return false;
    			}
    		}
    	}
    	return true;
    }

}
