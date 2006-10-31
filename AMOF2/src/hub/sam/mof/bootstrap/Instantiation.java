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

import cmof.ParameterDirectionKind;
import hub.sam.mof.instancemodel.ClassInstance;
import hub.sam.mof.instancemodel.InstanceModel;
import hub.sam.mof.instancemodel.MetaModelException;
import hub.sam.mof.instancemodel.StructureSlot;
import hub.sam.mof.instancemodel.ValueSpecification;
import hub.sam.mof.instancemodel.ValueSpecificationImpl;
import hub.sam.mof.xmi.AbstractXmiConversion;
import hub.sam.mof.xmi.XmiClassifier;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Be aware, this hole thing will only work for the bootstrap m3 model. It is not capable of
 * importing package, nesting with overloading names, correct property redefinition or inheritence
 * in general. This is only for bootstrapping the m3 model.
 */
public class Instantiation extends AbstractXmiConversion<ClassInstance<XmiClassifier,String,String>,ClassInstance<XmiClassifier,String,String>,ClassInstance<XmiClassifier,String,String>,ClassInstance<XmiClassifier,String,String>,java.lang.Object> {

    private final InstanceModel<XmiClassifier,String,String> original;
    private final InstanciatedXmiModel image;
    private final Map<String, ClassInstance<XmiClassifier,String,String>> classifiers;
    private static final String M3_PACKAGE = "cmof";
    private static String[] M3_CLASSIFIERS = new String[]{"Class", "Enumeration", "PrimitiveType", "DataType"};
    static {
        Arrays.sort(M3_CLASSIFIERS);
    }

    public Instantiation(InstanceModel<XmiClassifier,String,String> original, InstanciatedXmiModel image) {
        super();
        this.original = original;
        this.image = image;
        classifiers = new HashMap<String, ClassInstance<XmiClassifier, String, String>>();
        for (ValueSpecification<XmiClassifier, String, String> aPackage : original.getOutermostComposites()) {
            if (InstanciatedXmiModel.get(aPackage.asInstanceValue().getInstance(), "name").equals(M3_PACKAGE)) {
                collectClassifiersFromPackage(aPackage.asInstanceValue().getInstance());
            }
        }
    }

    private void collectClassifiersFromPackage(ClassInstance<XmiClassifier,String,String> aPackage) {
        for (ValueSpecification<XmiClassifier,String,String> value: aPackage.get("ownedType").getValues(null)) {
            ClassInstance<XmiClassifier,String,String> instance = value.asInstanceValue().getInstance();
            if (!instance.getClassifier().isDefinedByContext()) {
                if (Arrays.binarySearch(M3_CLASSIFIERS, instance.getClassifier().getName()) != -1) {
                    classifiers.put(InstanciatedXmiModel.get(instance,"name"),instance);
                    classifiers.put(InstanciatedXmiModel.getQualifiedName(instance), instance);
                }
            }
        }
        StructureSlot<XmiClassifier,String,String> nestedPackageSlot = aPackage.get("nestedPackage");
        if (nestedPackageSlot != null) {
            for (ValueSpecification<XmiClassifier,String,String> value: nestedPackageSlot.getValues(null)) {
                collectClassifiersFromPackage(value.asInstanceValue().getInstance());
            }
        }
    }

    @Override
	protected ClassInstance<XmiClassifier,String,String> getClassForTagName(String tagName, String nsPrefix) {
        return classifiers.get(tagName);
    }

    public ClassInstance<XmiClassifier, String, String> getProperty(String property, ClassInstance<XmiClassifier, String, String> classifier) throws MetaModelException {
        ClassInstance<XmiClassifier, String, String> result = image.createClassifierSemantics(classifier).getProperty(property);
        if (result == null) {
            throw new MetaModelException("property \"" + property + "\" does not exist in \"" + InstanciatedXmiModel.get(classifier,"name") + "\"");
        } else {
            return result;
        }
    }

	public ClassInstance<XmiClassifier,String,String> getPropertyType(ClassInstance<XmiClassifier, String, String> property) throws MetaModelException {
        return original.getInstance(InstanciatedXmiModel.get(property,"type"));
    }

    public java.lang.Object createFromString(ClassInstance<XmiClassifier,String,String> type, String stringRepresentation) throws MetaModelException {
        if (type.getClassifier().getName().equals(cmof.PrimitiveType.class.getSimpleName())) {
        	String name = InstanciatedXmiModel.get(type, "name");
            if (name.equals(core.primitivetypes.String.class.getSimpleName())) {
                return stringRepresentation;
            } else if (name.equals(core.primitivetypes.Object.class.getSimpleName())) {
                return null;
            } else if (name.equals(core.primitivetypes.Boolean.class.getSimpleName())) {
                return new Boolean(stringRepresentation);
            } else if (name.equals(core.primitivetypes.Integer.class.getSimpleName())) {
                return new Integer(stringRepresentation);
            } else if (name.equals(core.primitivetypes.UnlimitedNatural.class.getSimpleName())) {
                if (stringRepresentation.equals("*")) {
                    return new Long(-1);
                } else {
                    return new Long(stringRepresentation);
                }
            } else {
                throw new MetaModelException("data type not supported");
            }
        } else if (type.getClassifier().getName().equals(cmof.Enumeration.class.getSimpleName()) &&
        		InstanciatedXmiModel.get(type, "name").equals(ParameterDirectionKind.class.getSimpleName())) {
        	if (stringRepresentation.equals("in")) {
        		return ParameterDirectionKind.IN;
        	} else if (stringRepresentation.equals("out")) {
        		return ParameterDirectionKind.OUT;
        	} else {
        		throw new RuntimeException("assert, unrecognised value for ParameterDirectionKind.");
        	}
        } else {
            throw new MetaModelException("data type not supported");
        }
    }

    public ClassInstance<XmiClassifier,String,String> asDataType(ClassInstance<XmiClassifier,String,String> type) {
        if (type.getClassifier().getName().equals(cmof.PrimitiveType.class.getSimpleName()) || type.getClassifier().getName().equals(cmof.Enumeration.class.getSimpleName())) {
            return type;
        } else {
            return null;
        }
    }

	public ClassInstance<XmiClassifier, String, String> getProperty(String property, ClassInstance<XmiClassifier, String, String> classifier, ValueSpecificationImpl<ClassInstance<XmiClassifier, String, String>, ClassInstance<XmiClassifier, String, String>, Object> value) throws MetaModelException {
		return getProperty(property, classifier);
	}

}
