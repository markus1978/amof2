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

package core.abstractions.generalizations;

import hub.sam.mof.util.SetImpl;
import cmof.common.ReflectiveCollection;

public class ClassifierCustom extends ClassifierDlg {

    /**
     * <b>general</b>, multiplicity=(0,*), isDerived
     */
    @Override
	@SuppressWarnings("unchecked")
	public cmof.common.ReflectiveCollection<? extends core.abstractions.generalizations.Classifier> getGeneral() {
    	return (ReflectiveCollection<? extends Classifier>) self.parents();
    }

    /**
     * <b>parents</b>
     * 
     * The query parents() gives all of the immediate ancestors of a generalized Classifier.
     */
    @Override
	public cmof.common.ReflectiveCollection<? extends core.abstractions.generalizations.Classifier> parents() {
    	ReflectiveCollection<core.abstractions.generalizations.Classifier> parents = new SetImpl<core.abstractions.generalizations.Classifier>();	
    	for (Generalization general : self.getGeneralization()) {
    			parents.add(general.getGeneral());
    		}
    		return parents;
    }

    /**
     * <b>conformsTo</b>
     * 
     * The query conformsTo() gives true for a classifier that defines a type that conforms to another.
     * This is used, for example, in the specification of signature conformance for operations.
     */
    @Override
	public boolean conformsTo(core.abstractions.generalizations.Classifier other) {    
    	if (!self.equals(other)) {
    			return self.allParents().contains(other);
    		}
    	return true;
    }
    
}
