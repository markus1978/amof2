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

package hub.sam.mof.xmi;

import hub.sam.mof.instancemodel.*;
import hub.sam.mof.instancemodel.conversion.*;

public abstract class AbstractXmiConversion<Ci,Pi,T,D,DataValuei> implements Conversion<XmiClassifier,String,String,Ci,Pi,T,D,DataValuei> {

    protected abstract Ci getClassForTagName(String xmiName, String ns);        
        
    @SuppressWarnings("unchecked")
	public Ci getClassifier(XmiClassifier element) throws MetaModelException {
        if (element.isDefinedByContext()) {
            T result = getPropertyType(getProperty(element.getContextProperty(),getClassifier(element.getContextClass())));
            if (asDataType(result) == null) {
                return (Ci)result;
            } else {
                throw new MetaModelException("Property \"" + element.getContextProperty() + "\" is used with wrong type.");
            }
        } else {
            Ci result = getClassForTagName(element.getName(), element.getNamespacePrefix());
            if (result == null) {
                throw new MetaModelException("Classifier \"" + element + "\" does not exist.");
            } else {
                return result;
            }       
        }
    }

    public boolean doConvert(ValueSpecificationImpl<XmiClassifier, String, String> value, StructureSlot<XmiClassifier, String, String> slot, ClassInstance<XmiClassifier, String, String> instance) {
        return true;
    }              
}
