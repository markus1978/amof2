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

import java.util.Collection;
import cmof.*;
import hub.sam.mof.instancemodel.*;

public class MofInstanceModel extends InstanceModel<UmlClass,Property,java.lang.Object> {

    @Override
	public MofClassInstance createAInstance(String id, UmlClass classifier) {
        return new MofClassInstance(classifier, this);        
    }
    
    @Override
	public Collection<ReferenceValue<UmlClass,Property,java.lang.Object>> createReferences(String idString) {
        throw new RuntimeException("assert");
    }
    
    @Override
	public UnspecifiedValue<UmlClass,Property,java.lang.Object> createUnspecifiedValue(Object unspecifiedData) {
        throw new RuntimeException("assert");
    }
        
    @Override
	public InstanceValue<UmlClass,Property,java.lang.Object> createInstanceValue(ClassInstance<UmlClass,Property,java.lang.Object> instance) {
        return super.createInstanceValue(instance);        
    }
    
    @Override
	public MofPrimitiveDataValue createPrimitiveValue(java.lang.Object primitiveData) {
        return new MofPrimitiveDataValue(primitiveData);        
    }      
}
