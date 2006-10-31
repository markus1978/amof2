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

import cmof.Property;
import cmof.UmlClass;
import hub.sam.mof.instancemodel.StructureSlot;
import hub.sam.mof.instancemodel.ValueSpecification;
import hub.sam.mof.instancemodel.ValueSpecificationImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class MofStructureSlot extends StructureSlot<UmlClass,Property,java.lang.Object> {

    private MofValueSpecificationList values;
    private final Map<Object, MofValueSpecificationList> qualifiedValues;
    private final Property qualifierProperty;
    private final MofClassInstance owner;

    protected MofStructureSlot(MofClassInstance owner, Property feature) {
        super(feature, owner);
        this.owner = owner;
        this.values = new MofValueSpecificationList(owner, this, null);
        qualifierProperty = feature.getQualifier();
        if (qualifierProperty == null) {
            qualifiedValues = null;
        } else {
            qualifiedValues = new HashMap<Object, MofValueSpecificationList>();
        }
    }

    @Override
    public MofValueSpecificationList getValuesAsList(ValueSpecification<UmlClass,Property,Object> qualifier) {
        if (hasQualifier()) {
            if (qualifier == null) {
                return values;
            } else {
                MofValueSpecificationList result = qualifiedValues.get(qualifier);
                if (result == null) {
                    result = new MofValueSpecificationList(owner, this, qualifier);
                    qualifiedValues.put(qualifier, result);
                }
                return result;
            }
        } else {
            //if (qualifier != null) {
            //    throw new cmof.exception.IllegalArgumentException("unallowed qualifier");
            //}
            // this happends when for secondary adds based on associations. The other end must not have
            // any qualifiers. This is not checked.
            return values;
        }
    }

    @Override
    protected List<ValueSpecificationImpl<UmlClass,Property,java.lang.Object>> createValues() {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ValueSpecificationImpl<UmlClass,Property,java.lang.Object>> getValues(
            ValueSpecification<UmlClass,Property,Object> qualifier) {
        List result = new Vector();
        for (Object o: getValuesAsList(qualifier)) {
            result.add(o);
        }
        return result;
    }

    public Property getDefiningFeature() {
        return getProperty();
    }

    @Override
    protected void myFinalize() {
        super.myFinalize();
        values.myFinalize();
        for(MofValueSpecificationList values: qualifiedValues.values()) {
            values.myFinalize();
        }
    }

    private boolean hasQualifier() {
        return qualifierProperty != null;
    }
}
