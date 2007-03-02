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

package core.abstractions.behavioralfeatures;

import core.abstractions.behavioralfeatures.BehavioralFeatureDlg;

public class BehavioralFeatureCustom extends BehavioralFeatureDlg {

    /**
     * <b>isDistinguishableFrom</b>
     * 
     * The query isDistinguishableFrom() determines whether two BehavioralFeatures may coexist in the same Namespace. It
     * specifies that they have to have different signatures.
     */
    @Override
	public boolean isDistinguishableFrom(core.abstractions.namespaces.NamedElement n, core.abstractions.namespaces.Namespace ns) {
    	if (n instanceof BehavioralFeature) {
    		if (ns.getNamesOfMember(n).containsAll((ns.getNamesOfMember(self)))) {
    			for (Parameter param1: ((BehavioralFeature)n).getParameter()) {
    				for (Parameter param2: self.getParameter()) {
    					if (!param1.getName().equals(param2.getName()) || !param1.getType().equals(param2.getType())) {
    						return true;
    					}
    				}
    			}
    			return false;
    		}
    	}
    	return true;
    }

}
