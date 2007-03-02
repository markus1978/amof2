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

import hub.sam.mof.instancemodel.ClassInstance;
import hub.sam.mof.instancemodel.ClassifierSemantics;
import hub.sam.mof.instancemodel.InstanceModel;
import hub.sam.mof.instancemodel.SemanticsFactory;
import hub.sam.mof.instancemodel.ValueSpecification;
import hub.sam.mof.xmi.XmiClassifier;

import java.util.HashMap;
import java.util.Map;

public class InstanciatedXmiModel extends InstanceModel<ClassInstance<XmiClassifier,String,String>,ClassInstance<XmiClassifier,String,String>,java.lang.Object>
        implements SemanticsFactory<ClassInstance<XmiClassifier,String,String>,ClassInstance<XmiClassifier,String,String>,Object,String> {

    private final InstanceModel<XmiClassifier,String,String> sourceModel;
    private final Map<cmof.reflection.Object, ClassInstance<XmiClassifier,String,String>> instances;
    private final Map<ClassInstance<XmiClassifier,String,String>, cmof.reflection.Object> objects;

    public InstanciatedXmiModel(InstanceModel<XmiClassifier,String,String> sourceModel) {
        this.sourceModel = sourceModel;
        this.objects = null;
        this.instances = null;
    }

    public ClassInstance<XmiClassifier,String,String> getInstance(cmof.reflection.Object aObject) {
        return instances.get(aObject);
    }

    public cmof.reflection.Object getObject(ClassInstance<XmiClassifier,String,String> instance) {
        return objects.get(instance);
    }

    private Map<ClassInstance<XmiClassifier,String,String>, ClassifierSemantics<ClassInstance<XmiClassifier,String,String>,Object,String>> cache = new HashMap<ClassInstance<XmiClassifier,String,String>, ClassifierSemantics<ClassInstance<XmiClassifier,String,String>,Object,String>>();
    public ClassifierSemantics<ClassInstance<XmiClassifier, String, String>, Object, String> createClassifierSemantics(ClassInstance<XmiClassifier, String, String> classifier) {
        ClassifierSemantics<ClassInstance<XmiClassifier,String,String>, Object, String> result = cache.get(classifier);
        if (result == null) {
            result = new InstantiatedXmiSemantics(classifier, sourceModel);
            cache.put(classifier, result);
        }
        return result;
    }

    public static String get(ClassInstance<XmiClassifier,String,String> instance, String property) {
        try {
            ValueSpecification<XmiClassifier,String,String> value = instance.get(property).getValues(null).get(0);
            if (value.asUnspecifiedValue() != null) {
                return value.asUnspecifiedValue().getUnspecifiedData().toString();
            } else {
                return value.asDataValue().getValue();
            }
        } catch (Exception e) {
            return "";
        }
    }

    public static String getQualifiedName(ClassInstance<XmiClassifier,String,String> instance) {
        String result = get(instance,"name");
        while(instance.getComposite() != null) {
            instance = instance.getComposite();
            result = get(instance,"name") + "." + result;
        }
        return result;
    }
}
