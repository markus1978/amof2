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

package hub.sam.mof.mofinstancemodel;

import java.io.Serializable;

import cmof.*;
import hub.sam.mof.instancemodel.ValueSpecification;

public class MofPrimitiveDataValue extends hub.sam.mof.instancemodel.PrimitiveDataValue<UmlClass,Property,java.lang.Object> implements ValueSpecification<UmlClass,Property,java.lang.Object>, Serializable {
          
    private final java.lang.Object value;
    
    /**
     * Only for serialization
     */
    protected MofPrimitiveDataValue() {
    	super();
    	value = null;
    } 

    protected MofPrimitiveDataValue(java.lang.Object value) {
        super(value);
        this.value = value;
    }  
          
    public DataType getClassifier() {
        return null;
    }
    
    @Override
	public String getValueRepresentation() {
        return getValue().toString();
    }
    
    @Override
	public java.lang.Object getValue() {
        return value;
    }
               
    @Override
	public int hashCode() {
        return value.hashCode();
    }
    @Override
	public boolean equals(Object theOther) {
        if (theOther instanceof MofPrimitiveDataValue) {
            return getValue().equals(((MofPrimitiveDataValue)theOther).value);
        } else {
            return false;
        }
    } 
    
    @Override
    public String toString() {
    	return value.toString();
    }
}
