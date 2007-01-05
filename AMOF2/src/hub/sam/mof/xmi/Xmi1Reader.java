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

import cmof.Association;
import cmof.Classifier;
import cmof.DataType;
import cmof.NamedElement;
import cmof.Operation;
import cmof.Property;
import cmof.Type;
import cmof.TypedElement;
import cmof.UmlClass;
import cmof.reflection.Extent;
import core.primitivetypes.Integer;
import hub.sam.mof.instancemodel.ClassInstance;
import hub.sam.mof.instancemodel.InstanceModel;
import hub.sam.mof.instancemodel.InstanceValue;
import hub.sam.mof.instancemodel.MetaModelException;
import hub.sam.mof.instancemodel.ValueSpecification;
import hub.sam.mof.instancemodel.conversion.Converter;
import hub.sam.mof.mofinstancemodel.MofClassSemantics;
import hub.sam.mof.mofinstancemodel.MofClassifierSemantics;
import hub.sam.mof.reflection.ExtentImpl;
import hub.sam.mof.reflection.FactoryImpl;
import hub.sam.mof.util.AssertionException;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class Xmi1Reader {

	public enum XmiKind {
		unisys,
		ea,
		md,
		mof
	}

    private final InstanceModel<XmiClassifier,String,String> model;
    private static final String XMI_ROOT_ELEMENT = "XMI";
    private String actualNamespacePrefix = null;

    public Xmi1Reader(InstanceModel<XmiClassifier,String,String> model) {
        super();
        this.model = model;
    }

    public void read(InputStream in) throws JDOMException, java.io.IOException, XmiException, MetaModelException {
        Document document = new SAXBuilder().build(in);
        Element xmi = document.getRootElement();
        if (!xmi.getName().equals(XMI_ROOT_ELEMENT)) {
            throw new XmiException("Unexpected root element \"" + xmi.getName() + "\"");
        }

        for (Object oChild: xmi.getChildren()) {
            if (oChild instanceof Element) {
                if (((Element)oChild).getName().equals("XMI.content")) {
                    for (Object ooChild: ((Element)oChild).getChildren()) {
                        if (ooChild instanceof Element) {
                            Element child = (Element)ooChild;
                            readInstance(child);
                        }
                    }
                }
            }
        }
    }

    public void readInstance(Element child) throws XmiException, MetaModelException {
        String id = child.getAttributeValue("xmi.id");
        actualNamespacePrefix = child.getNamespacePrefix();
        ClassInstance<XmiClassifier,String,String> instance = model.createInstance(id,
                new XmiClassifier(child.getName(), actualNamespacePrefix));
        readContentForInstance(child, instance);
    }

    public void readContentForInstance(Element element, ClassInstance<XmiClassifier,String,String> instance) throws XmiException, MetaModelException {
        for(Object oAttr: element.getAttributes()) {
            Attribute attr = (Attribute)oAttr;
            if (!attr.getName().equals("xmi.id")) {
                String name = attr.getName();
                String value = attr.getValue();
                instance.addValue(name, model.createUnspecifiedValue(value), null);
            }
        }
        for (Object attrChild: element.getChildren()) {
            if (attrChild instanceof Element) {
                Element attrElement = (Element)attrChild;
                String[] fullAttrName = attrElement.getName().split("\\.");
                String attrName = fullAttrName[fullAttrName.length - 1];
                for (Object typeChild: attrElement.getChildren()) {
                    if (typeChild instanceof Element) {
                        Element typeElement = (Element)typeChild;
                        String namespace = attrElement.getNamespacePrefix();
                        if (!namespace.equals(actualNamespacePrefix)) {
                            throw new XmiException("Unexpected namespace prefix \"" + attrElement.getNamespacePrefix() +
                                    "\" for element \"" + attrName + "\"");
                        }
                        String id = typeElement.getAttributeValue("xmi.id");
                        String[] fullType = typeElement.getName().split("\\.");
                        String type = fullType[fullType.length - 1];
                        if (typeElement.getAttributes().size() + typeElement.getChildren().size() == 0) {
                            instance.addValue(attrName, model.createUnspecifiedValue(typeElement.getText()), null);
                        } else {
                            XmiClassifier elementForChild = new XmiClassifier(type, actualNamespacePrefix);
                            InstanceValue<XmiClassifier,String,String> value =
                                    model.createInstanceValue(model.createInstance(id, elementForChild));
                            readContentForInstance(typeElement, value.getInstance());
                            instance.addValue(attrName, value, null);
                            value.getInstance().setComposite(instance);
                        }
                    }
                }
            }
        }
    }

    /**
     * Reads XMI with a m1 model of the given m2 in an extent. The extent must be based on a MofInstanceModel.
     */
    public static void readMofXmi(java.io.File file, Extent extent, cmof.Package m2, XmiKind xmiKind) throws JDOMException, java.io.IOException, XmiException, MetaModelException {
        readMofXmi(new FileInputStream(file), extent, m2, xmiKind);
    }

    public static void readMofXmi(InputStream in, Extent extent, cmof.Package m2, XmiKind xmiKind) throws JDOMException, java.io.IOException, XmiException, MetaModelException {
        // remember the original contents
        Map<String, Classifier> classifiers = new HashMap<String, Classifier>();
        Map<String, Type> types = new HashMap<String, Type>();
        Map<String, cmof.NamedElement> elements = new HashMap<String, cmof.NamedElement>();
        Collection<cmof.NamedElement> elementsToRemove = new HashSet<cmof.NamedElement>();
        for (cmof.reflection.Object obj: extent.objectsOfType(null, true)) {
            if ( obj instanceof cmof.Package || obj instanceof cmof.UmlClass) {
                elements.put(((cmof.NamedElement)obj).getQualifiedName(), (cmof.NamedElement)obj);
            }
            if (obj instanceof Classifier) {
                classifiers.put(((Classifier)obj).getQualifiedName(), (Classifier)obj);
            }
            if (obj instanceof Type) {
                types.put(((Type)obj).getQualifiedName(), (Type)obj);
            }
        }

        // read xmi 1
        InstanceModel<XmiClassifier,String,String> xmiModel = new InstanceModel<XmiClassifier,String,String>();
        new Xmi1Reader(xmiModel).read(in);

        // transform UnisysUml to MOF 2
        XmiTransformator transformator = null;
        if (xmiKind == XmiKind.unisys) {
        	transformator = new XmiUnisysUML1ToMOF2(xmiModel);
        } else if (xmiKind == XmiKind.ea) {
        	transformator = new XmiEAUML1ToMOF2(xmiModel);
        } else {
            throw new cmof.exception.IllegalArgumentException("unknown xmi kind");
        }

        Collection<ClassInstance> outermostComposites = new Vector<ClassInstance>();
        for (ValueSpecification<XmiClassifier,String,String> instance: xmiModel.getOutermostComposites()) {
        	outermostComposites.add(instance.asInstanceValue().getInstance());
        }
        for (ClassInstance<XmiClassifier,String,String> instance: outermostComposites) {
            if (instance.getClassifier().getName().equals("Model")) {
                transformator.transform(hub.sam.mof.xmi.mopa.XmiMopaTreeNode.createNode(instance));
            }
        }
        for (ClassInstance<XmiClassifier,String,String> topLevelElement: transformator.getTopLevelElements()) {
            topLevelElement.setComposite(null);
        }
        List<ClassInstance<XmiClassifier,String,String>> toDelete = new Vector<ClassInstance<XmiClassifier,String,String>>();
        for (ValueSpecification<XmiClassifier,String,String> outermostComposite: xmiModel.getOutermostComposites()) {
        	if (!transformator.getTopLevelElements().contains(outermostComposite.asInstanceValue().getInstance())) {
                toDelete.add(outermostComposite.asInstanceValue().getInstance());
            }
        }
        for (ClassInstance<XmiClassifier,String,String> instance: toDelete) {
            instance.delete();
        }

        // convert MOF 2 XMI to MOF 2 InstanceModel
        XmiToCMOF conversion = new XmiToCMOF(extent, m2);
        InstanceModel<UmlClass,Property,Object> mofModel = ((ExtentImpl)extent).getModel();
        new Converter<XmiClassifier,String,String, UmlClass, Property, Type, DataType, java.lang.Object>(conversion).
                convert(xmiModel, mofModel);
        FactoryImpl factory = FactoryImpl.createFactory(extent, m2);
        for (ClassInstance<UmlClass,Property,Object> instance: mofModel.getInstances()) {
            factory.create(instance);
        }

        // resolve library skeletons and replace them by the real ones based on the knowledge gathered before
        loop: for (cmof.reflection.Object obj: extent.objectsOfType(null, true)) {
            if (obj instanceof cmof.Package || obj instanceof cmof.UmlClass) {
                cmof.NamedElement element = (cmof.NamedElement)obj;
                String qualifiedName = element.getQualifiedName();
                if (elements.get(qualifiedName) != null) {
                    if (!elements.get(qualifiedName).equals(element)) {
                        elementsToRemove.add(element); // scedule reread element for removal
                    }
                    continue loop; // ignore all prior existing or reread elements
                }
            }
            if (obj instanceof Classifier) {
                Classifier classifier = (Classifier)obj;
                for (Classifier superClassifier: new hub.sam.mof.util.SetImpl<Classifier>(classifier.getGeneral())) {
                    String qualifiedName = superClassifier.getQualifiedName();
                    if (classifiers.get(qualifiedName) != null) {
                        if (classifier instanceof UmlClass) {
                            ((UmlClass)classifier).getSuperClass().remove(superClassifier);
                            ((UmlClass)classifier).getSuperClass().add(classifiers.get(qualifiedName));
                        } else {
                            throw new RuntimeException("not implemented");
                        }
                    }
                }
            }
            if (obj instanceof TypedElement) {
                TypedElement typedElement = (TypedElement)obj;
                if (typedElement.getType() != null) {
                	String qualifiedName = null;
                	String typeName = typedElement.getType().getName();
                	if (typeName != null && (typeName.equals(Integer.class.getSimpleName()) ||
                			typeName.equals(core.primitivetypes.String.class.getSimpleName()) ||
                			typeName.equals(core.primitivetypes.String.class.getSimpleName()) ||
                			typeName.equals(core.primitivetypes.String.class.getSimpleName()))) {
                     	qualifiedName = "core.primitivetypes." + typedElement.getType().getName();
                    } else {
                    	qualifiedName = typedElement.getType().getQualifiedName();
                    }
                    if (types.get(qualifiedName) != null) {
                    	//elementsToRemove.add(typedElement.getType());
                    	typedElement.setType(types.get(qualifiedName));
                    }
                }
            } else if (obj instanceof Operation) {
            	// this is the exact same core as in the branch above
                Operation typedElement = (Operation)obj;
                if (typedElement.getType() != null) {
                	String qualifiedName = null;
                	if (typedElement.getType().getName().equals(Integer.class.getSimpleName()) ||
                			typedElement.getType().getName().equals(core.primitivetypes.String.class.getSimpleName()) ||
                			typedElement.getType().getName().equals(core.primitivetypes.String.class.getSimpleName()) ||
                			typedElement.getType().getName().equals(core.primitivetypes.String.class.getSimpleName())) {
                     	qualifiedName = "core.primitivetypes." + typedElement.getType().getName();
                    } else {
                    	qualifiedName = typedElement.getType().getQualifiedName();
                    }
                    if (types.get(qualifiedName) != null) {
                    	//elementsToRemove.add(typedElement.getType());
                    	typedElement.setType(types.get(qualifiedName));
                    }
                }
            }
        }

        // remove reread elements
        for (cmof.NamedElement element: new HashSet<cmof.NamedElement>(elementsToRemove)) {
            for (Object obj: ((cmof.reflection.Object)element).getComponents()) {
                elementsToRemove.remove(obj);
            }
        }
        for (Object obj: elementsToRemove) {
            ((cmof.reflection.Object)obj).delete();
        }


        // evaluate property constrains like subsets, redefines, etc.
        // evaluate more general classifier first
        Collection<Classifier> alreadyDone = new HashSet<Classifier>();
        XmiException exceptions = new XmiException("Errors during resolution of subsets and redefines:");
        boolean throwIt = false;
        for (Object c: extent.objectsOfType(null, false)) {
        	if (c instanceof Classifier) {
        		try {
        			evaluatePropertiesInClassifier((Classifier)c, alreadyDone);
        			if (c instanceof UmlClass) {
        				MofClassifierSemantics.changedClassClassifierForUmlClass((UmlClass)c);
        			}
        		} catch (XmiException ex) {
        			exceptions.add(ex);
        			throwIt = true;
        		}
        	}
        }
        if (throwIt) {
        	throw exceptions;
        }
    }

    private static void evaluatePropertiesInClassifier(Classifier c, Collection<Classifier> alreadyDone) throws XmiException {
		if (!alreadyDone.contains(c)) {
			for (Classifier superClassifier: c.getGeneral()) {
				evaluatePropertiesInClassifier(superClassifier, alreadyDone);
			}
    		for (Object e: c.getFeature()) {
    			if (e instanceof Property) {
                    Property property = (Property)e;
                    if (property.getDetails() != null) {
                        evaluatePropertyString(property.getDetails(), property);
                    }
                }
    		}

    		alreadyDone.add(c);
		}
    }

    private static void evaluatePropertyString(String spec, Property property) throws XmiException {
        String[] details;
        if (spec.contains(",")) {
            details = spec.split(",");
        } else {
            details = new String[] { spec };
        }
        for (String detail: details) {
            detail = detail.trim();
            if (detail.equals("union")) {
                property.setIsUnique(true);
            } else if (detail.equals("derived union")) {
                property.setIsDerivedUnion(true);
            } else if (detail.startsWith("subsets")) {
                property.getSubsettedProperty().addAll(derivePropertiesFromPropertyString(detail, property));
            } else if (detail.equals("ordered")) {
                property.setIsOrdered(true);
            } else if (detail.startsWith("redefines")) {
            	Collection<Property> redefinedProperties = derivePropertiesFromPropertyString(detail, property);
            	for (Property redefinedProperty: redefinedProperties) {
            		if (redefinedProperty.isOrdered()) {
            			property.setIsOrdered(true);
            		}
            		if (redefinedProperty.isDerived()) {
            			property.setIsDerived(true);
            		}
            		if (redefinedProperty.isDerivedUnion()) {
            			property.setIsDerivedUnion(true);
            		}
            		if (property.getOwner() instanceof Association && redefinedProperty.getOwner() instanceof cmof.UmlClass) {
            			throw new XmiException("It is not allowed the decrease navigability when redefining a feature: " + property);
            		}
            		long redefinedUpper = redefinedProperty.getUpper();
            		if (redefinedUpper == -1) {
            			redefinedUpper = Long.MAX_VALUE;
            		}
            		long redefineeUpper = property.getUpper();
            		if (redefineeUpper == -1) {
            			redefineeUpper = Long.MAX_VALUE;
            		}
            		if (redefinedUpper < redefineeUpper || property.getLower() < redefinedProperty.getLower()) {
            			throw new XmiException("It is not allowed to increase the multiplicity within a redefinition: " + property + ", "
            					+ redefinedProperty.getLower() + ".." + redefinedProperty.getUpper() + "(redefined), "
            					+ property.getLower() + ".." + property.getUpper() + "(redefinee)");
            		}
            	}
                property.getRedefinedProperty().addAll(derivePropertiesFromPropertyString(detail, property));
            } else {
                throw new XmiException("unknown property string: " + detail);
            }
        }
    }

    private static Collection<Property> derivePropertiesFromPropertyString(String propertyString, Property property) throws XmiException {
        String[] words;
        Collection<Property> result = new Vector<Property>();
        if (propertyString.contains(" ")) {
            words = propertyString.split(" ");
        } else {
            words = new String[] { propertyString };
        }
        if (words.length == 1) {
            throw new XmiException("badly formated property string: " + propertyString);
        } else {
            for (int i = 1; i < words.length; i++) {
                result.add(resolvePropertyName(words[i], property));
            }
        }
        return result;
    }

    private static Property resolvePropertyName(String name, Property property) throws XmiException {
        MofClassSemantics semantics = null;
        try {
	        if (property.getOwner() instanceof UmlClass) {
	            semantics = MofClassifierSemantics.createClassClassifierForUmlClass((UmlClass)property.getOwner());
	        } else if (property.getOwner() instanceof Association) {
	            semantics = MofClassifierSemantics.createClassClassifierForUmlClass((UmlClass)property.getOpposite().getType());
	        } else {
	            throw new XmiException("property string in invalid context: " + property.getOwner());
	        }
        } catch (AssertionException ex) {
    		if (ex.getCause() != null && ex.getCause() instanceof cmof.exception.MetaModelException) {
    			throw new XmiException("An unexpected MetaModelException occured during analyzing " +
    					((NamedElement)property.getOwner()).getQualifiedName(), ex.getCause());
    		}
    	}
        for (Property aProperty: semantics.getFinalProperties()) {
            if (name.equals(aProperty.getName()) && !aProperty.equals(property)) {
            	if (!property.getType().conformsTo(aProperty.getType())) {
            		throw new XmiException("property referenced in redefinition or subsetting has no conforming type, in "
            				+ property.getQualifiedName() + " and " + aProperty.getQualifiedName() + ".");
            	}
                return aProperty;
            }
        }
        // it still could be the name of an unnavigable association end
        for (Property aProperty: semantics.getNotNavigablePropertys(name)) {
            if (!aProperty.equals(property)) {
                return aProperty;
            }
        }
        throw new XmiException("property " + name + " in redefinition or subsetting of " +
                property.getQualifiedName() + " could not be resolved");
    }
}
