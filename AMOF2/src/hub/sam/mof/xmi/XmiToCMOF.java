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

import cmof.Classifier;
import cmof.DataType;
import cmof.Element;
import cmof.PackageImport;
import cmof.Property;
import cmof.Tag;
import cmof.Type;
import cmof.UmlClass;
import cmof.reflection.Extent;
import cmof.reflection.Factory;
import hub.sam.mof.Repository;
import hub.sam.mof.instancemodel.ClassInstance;
import hub.sam.mof.instancemodel.MetaModelException;
import hub.sam.mof.instancemodel.StructureSlot;
import hub.sam.mof.instancemodel.ValueSpecificationImpl;
import hub.sam.mof.instancemodel.conversion.Conversion;
import hub.sam.mof.mofinstancemodel.MofClassifierSemantics;
import hub.sam.mof.util.SetImpl;
import org.jdom.Namespace;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class XmiToCMOF
        implements Conversion<XmiClassifier, String, String, UmlClass, Property, Type, DataType, java.lang.Object> {

    public static final String NsPrefixTagName = "org.omg.xmi.nsPrefix";

    private Factory factory = null;
    private Map<UmlClass, Map<String, Property>> properties = new HashMap<UmlClass, Map<String, Property>>();
    private Map<String, Map<String, UmlClass>> namespaces = new HashMap<String, Map<String, UmlClass>>();
    private Map<Element, Namespace> xmiNamespaces = new HashMap<Element, Namespace>();
    private cmof.common.ReflectiveCollection<? extends cmof.reflection.Object> allElements =
            new SetImpl<cmof.reflection.Object>();
    private final PrimitiveValueSerializeConfiguration valueSerializeConfiguration =
            new StandardPrimitiveValueSerializeConfiguration();

    /**
     * The parameter extent is only used to retrieve a factory, which is only used to create primitive data from strings.
     */
    public XmiToCMOF(Extent extent, cmof.Package model) {
        super();
        factory = hub.sam.mof.reflection.FactoryImpl.createFactory(extent, model);
        collectAllElements(model);
        initialize();
    }

    private void collectAllElements(cmof.Package thePackage) {
        for (Tag tag : thePackage.getTag()) {
            allElements.add(tag);
        }
        for (Element e : thePackage.getOwnedElement()) {
            allElements.add(e);
            for (Tag tag : e.getTag()) {
                allElements.add(tag);
            }
            if (e instanceof PackageImport) {
                PackageImport packageImport = (PackageImport)e;
                collectAllElements(packageImport.getImportedPackage());
            }
        }
    }

    private void initialize() {
        for (cmof.reflection.Object o : allElements) {
            if (o instanceof Tag) {
                Tag tag = (Tag)o;
                if (tag.getName().equals(NsPrefixTagName)) {
                    String prefix = tag.getValue();
                    for (Element taggedElement : tag.getElement()) {
                        xmiNamespaces.put(taggedElement, getNamespace(prefix));
                    }
                }
            }
        }

        for (cmof.reflection.Object o : allElements) {
            if (o instanceof UmlClass) {
                UmlClass umlClass = (UmlClass)o;
                if (!umlClass.isAbstract()) {
                    // find nsPrefix for class
                    Namespace xmiNamespace = null;
                    String prefix;
                    for (cmof.Namespace ns = umlClass; ns != null && xmiNamespace == null;
                         ns = (cmof.Namespace)ns.getNamespace()) {
                        xmiNamespace = xmiNamespaces.get(ns);
                    }

                    if (xmiNamespace == null) {
                        prefix = "";
                    } else {
                        prefix = xmiNamespace.getPrefix();
                    }
                    Map<String, UmlClass> classForNames = namespaces.get(prefix);
                    if (classForNames == null) {
                        classForNames = new HashMap<String, UmlClass>();
                        namespaces.put(prefix, classForNames);
                    }
                    classForNames.put(umlClass.getName(), umlClass);
                    classForNames.put(umlClass.getQualifiedName(), umlClass);
                }
            }
        }
    }

    private UmlClass getClassForTagName(String xmiName, String ns) {
        Map<String, UmlClass> classForName = namespaces.get(ns);
        if (classForName == null) {
            return null;
        } else {
            if (classForName.get(xmiName) == null) {
                if (ns.equals("")) {
                    // look in all namespaces if ns not specified
                    int foundCount = 0;
                    UmlClass result = null;
                    for (String otherNs : namespaces.keySet()) {
                        classForName = namespaces.get(otherNs);
                        if (classForName.get(xmiName) != null) {
                            foundCount++;
                            result = classForName.get(xmiName);
                        }
                    }
                    if (foundCount != 1) {
                        return null;
                    } else {
                        return result;
                    }
                } else {
                    return null;
                }
            } else {
                return classForName.get(xmiName);
            }
        }
    }

    private org.jdom.Namespace getNamespace(String prefix) {
        return org.jdom.Namespace.getNamespace(prefix, "http://www.abcdefguuh.de/URL");
    }

    public UmlClass getClassifier(XmiClassifier element) throws MetaModelException {
        if (element.isDefinedByContext()) {
            Type result = getPropertyType(
                    getProperty(element.getContextProperty(), getClassifier(element.getContextClass())));
            if (result instanceof UmlClass) {
                return (UmlClass)result;
            } else {
                throw new MetaModelException(
                        "Property \"" + element.getContextProperty() + "\" is used with wrong type.");
            }
        } else {
            UmlClass result = getClassForTagName(element.getName(), element.getNamespacePrefix());
            if (result == null) {
                throw new MetaModelException("Classifier \"" + element + "\" does not exist.");
            } else {
                return result;
            }
        }
    }

    public Property getProperty(String property, UmlClass classifier) throws MetaModelException {
        Map<String, Property> propertiesOfClassifier = properties.get(classifier);
        if (propertiesOfClassifier == null) {
            propertiesOfClassifier = new HashMap<String, Property>();
            for (Property member : MofClassifierSemantics.createClassClassifierForUmlClass(classifier)
                    .getFinalPropertysByName()) {
                String name = member.getName();
                if (name != null) {
                    propertiesOfClassifier.put(name, member);
                }
            }
            properties.put(classifier, propertiesOfClassifier);
        }
        Property result = propertiesOfClassifier.get(property);
        if (result == null) {
            throw new MetaModelException(
                    "Property \"" + property + "\" of classifier \"" + classifier.getQualifiedName() +
                            "\" does not exist");
        } else {
            return result;
        }
    }

    public Property getProperty(
            String property, UmlClass classifier, ValueSpecificationImpl<UmlClass, Property, Object> value)
            throws MetaModelException {
        Property result = getProperty(property, classifier);
        Property closestProperty = result;        
        if (Repository.getConfiguration().allowsMutuableDerivedUnions() && value.asInstanceValue() != null && result.isDerivedUnion()) {
            Type type = value.asInstanceValue().getInstance().getClassifier();
            for (Property supersettedProperty : MofClassifierSemantics.createClassClassifierForUmlClass(
                    classifier).getSupersettedProperties(result)) {
                if (!supersettedProperty.isDerived() && type.conformsTo(supersettedProperty.getType())) {
                    if (((Classifier)supersettedProperty.getType()).allParents().contains(closestProperty.getType())) {
                        closestProperty = supersettedProperty;
                    }
                }
            }
            return closestProperty;
        } else {
            return result;
        }
    }

    public Type getPropertyType(Property property) throws MetaModelException {
        return (Type)property.getType();
    }

    public java.lang.Object createFromString(DataType type, String stringRepresentation) throws MetaModelException {
        if (valueSerializeConfiguration.needsSerialization(type)) {
            return valueSerializeConfiguration.deSerialize(type, stringRepresentation);
        } else {
            return factory.createFromString(type, stringRepresentation);
        }
    }

    public DataType asDataType(Type type) {
        if (type instanceof DataType) {
            return (DataType)type;
        } else {
            return null;
        }
    }

    public boolean doConvert(
            ValueSpecificationImpl<XmiClassifier, String, String> value,
            StructureSlot<XmiClassifier, String, String> slot, ClassInstance<XmiClassifier, String, String> instance) {
        return true;
    }

	public Collection<String> splitDataValue(DataType type, String multipleValues) throws MetaModelException {
		Collection<String> result = new Vector<String>();
		if (!"String".equals(type.getName())) {
			for (String singleValue: multipleValues.split(" ")) {
				result.add(singleValue);
			}
		} else {
			result.add(multipleValues);
		}
		return result;
	}
      
}
