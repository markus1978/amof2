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

import hub.sam.mof.util.ListImpl;
import cmof.common.ReflectiveSequence;

public class NamedElementCustom extends NamedElementDlg {
    /**
     * <b>qualifiedName</b>, multiplicity=(0,1), isDerived
     */
    @Override
	public String getQualifiedName() {
    	StringBuffer qualifiedName = new StringBuffer(30);
    	Namespace nameSpace = self.getNamespace();
    	qualifiedName.append(self.getName());
    	while (nameSpace != null) {
    		qualifiedName.insert(0, nameSpace.getName() + self.separator());
    		nameSpace = nameSpace.getNamespace();
    	}
    	return qualifiedName.toString();
    }


    /**
     * <b>allNamespaces</b>
     * 
     * The query allNamespaces() gives the sequence of namespaces in which the NamedElement is nested, working outwards.
     */
    @Override
	public ReflectiveSequence<? extends core.abstractions.namespaces.Namespace> allNamespaces() {
    	ReflectiveSequence<Namespace> allNamespaces = new ListImpl<Namespace>();
    	Namespace nameSpace = self.getNamespace();
    	while (nameSpace != null) {
    		allNamespaces.add(nameSpace);
    		nameSpace = nameSpace.getNamespace();
    	}
    	return allNamespaces;
	}

    /**
     * <b>isDistinguishableFrom</b>
     * 
     * The query isDistinguishableFrom() determines whether two NamedElements may logically co-exist within a Namespace.
     * By default, two named elements are distinguishable if (a) they have unrelated types or (b) they have related types but dif-
     * ferent names. 
     */
    @Override
	public boolean isDistinguishableFrom(core.abstractions.namespaces.NamedElement n, core.abstractions.namespaces.Namespace ns) 
    {
    	if (n.getClass().isAssignableFrom(self.getClass()) || self.getClass().isAssignableFrom(n.getClass())) {
    		if (ns.getNamesOfMember(self).containsAll(ns.getNamesOfMember(n)) &&
    				ns.getNamesOfMember(n).containsAll(ns.getNamesOfMember(self))) {
    			return false;
    		}
   		}
    	return true;
    }

    /**
     * <b>separator</b>
     * 
     * The query separator() gives the string that is used to separate names when constructing a qualified name.
     */
    @Override
	public String separator() {
    	return "::";
    }

}
