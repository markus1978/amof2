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

package hub.sam.mof.bootstrap;

import java.util.*;
import hub.sam.mof.instancemodel.*;
import hub.sam.mof.xmi.*;

public class InstantiatedXmiSemantics extends CommonClassifierSemantics<ClassInstance<XmiClassifier,String,String>, ClassInstance<XmiClassifier,String,String>,Object,String> {

    private final InstanceModel<XmiClassifier,String,String> model;

    protected InstantiatedXmiSemantics(ClassInstance<XmiClassifier,String,String> classifier, InstanceModel<XmiClassifier,String,String> model) {
        super(classifier);
        this.model = model;
        initialize();
    }

    public String getName(ClassInstance<XmiClassifier, String, String> forProperty) {
        return InstanciatedXmiModel.get(forProperty,"name");
    }

    public boolean isCollectionProperty(ClassInstance<XmiClassifier,String,String> forProperty) {
        return false;
    }

    private Collection<ClassInstance<XmiClassifier,String,String>> get(ClassInstance<XmiClassifier,String,String> instance, String property) {
        StructureSlot<XmiClassifier,String,String> slot = instance.get(property);
        if (slot == null) {
            return new Vector<ClassInstance<XmiClassifier,String,String>>(0);
        }
        List<ValueSpecificationImpl<XmiClassifier,String,String>> values = instance.get(property).getValues(null);        
        Collection<ClassInstance<XmiClassifier,String,String>> result = new Vector<ClassInstance<XmiClassifier,String,String>>(values.size());
        for (ValueSpecification<XmiClassifier,String,String> value: values) {
            if (value.asInstanceValue() == null) {
                String[] valueIds = value.asUnspecifiedValue().getUnspecifiedData().toString().split(" ");
                for (String valueId: valueIds) {
                    result.add(model.getInstance(valueId.trim()));
                }
            } else {
                result.add(value.asInstanceValue().getInstance());
            }
        }
        return result;
    }

    @Override
	protected Collection<? extends ClassInstance<XmiClassifier, String, String>> ownedProperties(ClassInstance<XmiClassifier, String, String> c) {
        return get(c,"ownedAttribute");
    }

    @Override
	protected Collection<? extends ClassInstance<XmiClassifier, String, String>> redefinedProperties(ClassInstance<XmiClassifier, String, String> p) {
        return get(p,"redefinedProperty");
    }

    @Override
	protected Collection<? extends ClassInstance<XmiClassifier, String, String>> subsettedProperties(ClassInstance<XmiClassifier, String, String> p) {
        return new Vector<ClassInstance<XmiClassifier,String,String>>(0);
    }

    @Override
	protected Collection<? extends ClassInstance<XmiClassifier, String, String>> superClasses(ClassInstance<XmiClassifier, String, String> c) {
        return get(c,"superClass");
    }
}
