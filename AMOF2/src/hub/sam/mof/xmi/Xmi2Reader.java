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

import cmof.DataType;
import cmof.Property;
import cmof.Type;
import cmof.UmlClass;
import cmof.reflection.Extent;
import hub.sam.mof.instancemodel.ClassInstance;
import hub.sam.mof.instancemodel.InstanceModel;
import hub.sam.mof.instancemodel.InstanceValue;
import hub.sam.mof.instancemodel.MetaModelException;
import hub.sam.mof.instancemodel.ReferenceValue;
import hub.sam.mof.instancemodel.StructureSlot;
import hub.sam.mof.instancemodel.ValueSpecification;
import hub.sam.mof.instancemodel.conversion.Converter;
import hub.sam.mof.reflection.ExtentImpl;
import hub.sam.mof.reflection.FactoryImpl;
import hub.sam.mof.xmi.Xmi1Reader.XmiKind;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Vector;

/**
 * This class contain functionality to read XMI version 2.x. Each instance works on the given XMI instance model.
 */
public class Xmi2Reader {
    private final InstanceModel<XmiClassifier, String, String> model;
    private Namespace xmiNamespace = null;
    private Namespace xsiNamespace = null;
    private static final String XMI_ROOT_ELEMENT = "XMI";
    private String actualNamespacePrefix = null;
    private String actualIdPrefix = null;

    public Xmi2Reader(InstanceModel<XmiClassifier, String, String> model) {
        super();
        this.model = model;
    }

    /**
     * When the reader read XMI Ids it will preattach this id prefix to all Ids. That way multiple XMI files can
     * be read into the same XMI extent. When you choose the prefix that it concurs with href ids, you can use
     * this feature to read XMI with cross file references.
     *
     * @param prefix
     *        This prefix plus a # will be pre attached to each XMI id.
     */
    public void setActualIdPrefix(String prefix) {
        actualIdPrefix = prefix + "#";
    }

    /**
     * Read the XMI id from an element and pre attaches the actual Id prefix to it.
     * @param fromElement
     *        The XML element that the XMI id should be read from.
     * @param attrName
     *        The name of the attribute that carries the id.
     */
    private String readXmiId(Element fromElement, String attrName) {
        if (actualIdPrefix != null) {
            String id = fromElement.getAttributeValue(attrName, xmiNamespace);
            if (id != null) {
                return actualIdPrefix + id;
            } else {
                return null;
            }
        } else {
            return fromElement.getAttributeValue(attrName, xmiNamespace);
        }
    }

    public void read(java.io.File file) throws JDOMException, java.io.IOException, XmiException, MetaModelException {
        read(new SAXBuilder().build(file));
    }

    public void read(InputStream in) throws JDOMException, IOException, XmiException, MetaModelException {
        read(new SAXBuilder().build(in));
    }

    private void read(Document document) throws XmiException, MetaModelException {
        Element xmi = document.getRootElement();
        if (!xmi.getName().equals(XMI_ROOT_ELEMENT)) {
            throw new XmiException("Unexpected root element \"" + xmi.getName() + "\"");
        }
        xmiNamespace = xmi.getNamespace();
        for (Object oNs : xmi.getAdditionalNamespaces()) {
            Namespace namespace = (Namespace) oNs;
            if (namespace.getPrefix().equals("xmi")) {
                xmiNamespace = namespace;
            } else if (namespace.getPrefix().equals("xsi")) {
                xsiNamespace = namespace;
            }
        }
        if (xsiNamespace == null) {
            xsiNamespace = xmiNamespace; // TODO
        }
        if (xmiNamespace == null) {
            xmiNamespace = Namespace.NO_NAMESPACE;
        }
        if (xsiNamespace == null) {
            xsiNamespace = Namespace.NO_NAMESPACE;
        }

        for (Object oChild : xmi.getChildren()) {
            if (oChild instanceof Element) {
                Element child = (Element) oChild;
                readInstance(child);
            }
        }
    }

    private boolean ignoreHRefs(Element element) {
        return element.getChild("Extension", xmiNamespace) != null;
    }

    private boolean ignoreHRefs(Element element, Object o) {
        return element.getChild("Extension", xmiNamespace) != null;
    }

    private boolean ignoreElement(Element element) {
        return element.getNamespace().equals(xmiNamespace) ||
            (element.getAttribute("href") != null && ignoreHRefs(element, null));
    }

    public void readInstance(Element child) throws XmiException, MetaModelException {
        String id = readXmiId(child, "id");
        actualNamespacePrefix = child.getNamespacePrefix();
        if (!ignoreElement(child)) {
            ClassInstance<XmiClassifier, String, String> instance = model.createInstance(id,
                    new XmiClassifier(child.getName(), actualNamespacePrefix));
            readContentForInstance(child, instance);
        }
    }

    @SuppressWarnings({"ReuseOfLocalVariable"})
    public void readContentForInstance(Element element, ClassInstance<XmiClassifier, String, String> instance)
            throws XmiException, MetaModelException {
        for (Object oAttr : element.getAttributes()) {
            Attribute attr = (Attribute) oAttr;
            String namespace = attr.getNamespacePrefix();
            if (namespace.equals("")) {
                String name = attr.getName();
                String value = attr.getValue();
                instance.addValue(name, model.createUnspecifiedValue(value, actualIdPrefix), null);
            }
        }
        for (Object oChild : element.getChildren()) {
            Element child = (Element) oChild;
            String name = child.getName();
            String propertyName = null;
            String namespace = child.getNamespacePrefix();
            if (namespace.equals("")) {
                namespace = null;
                //throw new XmiException("Unexpected namespace prefix \"" + child.getNamespacePrefix() +
                //        "\" for element \"" + name + "\"");
                //instance.getClassifier().setNamespacePrefix(namespace);
            }
            String id = readXmiId(child, "id");
            String type = child.getAttributeValue("type", xsiNamespace);
            String idref = readXmiId(child, "idref");
            String href = null;
            if (!ignoreHRefs(child)) {
                href = child.getAttributeValue("href");
            }
            if (child.getAttributes().size() + child.getChildren().size() == 0) {
                instance.addValue(child.getName(), model.createUnspecifiedValue(child.getText(), actualIdPrefix), null);
            } else if (idref == null && href == null) {
                XmiClassifier elementForChild;
                String nsPrefix = null;
                if (type == null) {
                    if (namespace != null) {
                        elementForChild = new XmiClassifier(name, namespace);
                    } else {
                        elementForChild = new XmiClassifier(instance.getClassifier(), name);
                        propertyName = name;
                    }
                } else {
                    propertyName = name;
                    if (type.contains(":")) {
                        String[] typeElements = type.split(":");
                        if (typeElements.length != 2) {
                            throw new XmiException("badly formatted type identifier: " + type);
                        }
                        nsPrefix = typeElements[0];
                        elementForChild = new XmiClassifier(typeElements[1], nsPrefix);
                    } else {
                        elementForChild = new XmiClassifier(type, actualNamespacePrefix);
                    }
                }

                if (!ignoreElement(child)) {
                    InstanceValue<XmiClassifier, String, String> instanceValue =
                            model.createInstanceValue(model.createInstance(id, elementForChild));

                    String oldNsPrefix = actualNamespacePrefix;
                    if (nsPrefix != null) {
                        actualNamespacePrefix = nsPrefix;
                    }

                    readContentForInstance(child, instanceValue.getInstance());
                    actualNamespacePrefix = oldNsPrefix;

                    if (propertyName != null) {
                        instance.addValue(propertyName, instanceValue, null);
                    }
                    instanceValue.getInstance().setComposite(instance);
                }
            } else {
                String referedId;
                if (idref != null) {
                    referedId = idref;
                } else {
                    referedId = href;
                }
                for (ReferenceValue<XmiClassifier, String, String> ref : model.createReferences(referedId)) {
                    instance.addValue(name, ref, null);
                }
            }
        }
    }

    private static void tranformMDXmi(ClassInstance<XmiClassifier, String, String> instance, Collection<ClassInstance<XmiClassifier, String, String>> toDelete, Collection<ClassInstance<XmiClassifier, String, String>> topLevel) {
        boolean hasHRef = false;
        for (StructureSlot<XmiClassifier, String, String> slot : instance.getSlots()) {
            if (slot.getProperty().equals("href")) {
                hasHRef = true;
            }
        }
        if (hasHRef) {
            toDelete.add(instance);
            instance.setComposite(null);
            return;
        }
        if (!instance.getClassifier().isDefinedByContext()) {
            if (instance.getClassifier().getNamespacePrefix().equals("uml")) {
                instance.getClassifier().setNamespacePrefix("cmof");
            }
            if (instance.getClassifier().getNamespacePrefix().equals("xmi")) {
                instance.setComposite(null);
                toDelete.add(instance);
            }
            if (instance.getClassifier().getName().equals("Model")) {
                for (ClassInstance<XmiClassifier, String, String> childInstance : new Vector<ClassInstance<XmiClassifier, String, String>>(
                        instance.getComponentsAsCollection())) {
                    topLevel.add(childInstance);
                    childInstance.setComposite(null);
                    tranformMDXmi(childInstance, toDelete, topLevel);
                }
                instance.setComposite(null);
                toDelete.add(instance);
            }
        }
        for (ClassInstance<XmiClassifier, String, String> childInstance : new Vector<ClassInstance<XmiClassifier, String, String>>(instance.getComponentsAsCollection()))
        {
            tranformMDXmi(childInstance, toDelete, topLevel);
        }
    }

    public static void readMofXmi(Map<String, InputStream> xmi, Extent extent, cmof.Package m2, XmiKind xmiKind)
            throws JDOMException, XmiException, MetaModelException, IOException {
        InstanceModel<XmiClassifier, String, String> xmiModel = new InstanceModel<XmiClassifier, String, String>();
        Xmi2Reader reader = new Xmi2Reader(xmiModel);
        if (xmi.size() > 1) {
            for (String key: xmi.keySet()) {
                reader.setActualIdPrefix(key);
                reader.read(xmi.get(key));
            }
        } else if (xmi.size() == 1) {
            reader.read(xmi.values().iterator().next());
        }

        if (xmiKind == XmiKind.unisys) {
            throw new XmiException("Wrong xmi reader.");
        } else if (xmiKind == XmiKind.ea) {
            throw new XmiException("Wrong xmi reader.");
        } else if (xmiKind == XmiKind.md) {
            Collection<ClassInstance<XmiClassifier, String, String>> toDelete = new HashSet<ClassInstance<XmiClassifier, String, String>>();
            Collection<ClassInstance<XmiClassifier, String, String>> topLevel = new HashSet<ClassInstance<XmiClassifier, String, String>>();
            for (ValueSpecification<XmiClassifier, String, String> topLevelInstance :
                    new Vector<ValueSpecification<XmiClassifier, String, String>>(xmiModel.getOutermostComposites())) {
                tranformMDXmi(topLevelInstance.asInstanceValue().getInstance(), toDelete, topLevel);
            }

            for (ValueSpecification<XmiClassifier, String, String> outermostComposite : xmiModel.getOutermostComposites()) {
                if (!topLevel.contains(outermostComposite.asInstanceValue().getInstance())) {
                    toDelete.add(outermostComposite.asInstanceValue().getInstance());
                }
            }
            for (ClassInstance<XmiClassifier, String, String> instance : toDelete) {
                instance.delete();
            }

            XmiTransformator transformator = new MagicDrawXmi2ToMOF2(xmiModel);

            for (ValueSpecification<XmiClassifier,String,String> instance:
                    new Vector<ValueSpecification<XmiClassifier, String, String>>(xmiModel.getOutermostComposites())) {
                transformator.transform(hub.sam.mof.xmi.mopa.XmiMopaTreeNode.createNode(instance.asInstanceValue().getInstance()));
            }
        }

        XmiToCMOF conversion = new XmiToCMOF(extent, m2);
        InstanceModel<UmlClass, Property, Object> mofModel = ((ExtentImpl) extent).getModel();
        new Converter<XmiClassifier, String, String, UmlClass, Property, Type, DataType, java.lang.Object>(conversion).
                convert(xmiModel, mofModel);
        FactoryImpl factory = FactoryImpl.createFactory(extent, m2);
        for (ClassInstance<UmlClass, Property, Object> instance : mofModel.getInstances()) {
            factory.create(instance);
        }

        // TODO Remove this when opposite of Properties is realy derived.
        if (xmiKind == XmiKind.md) {
            for(cmof.reflection.Object o: extent.getObject()) {
                if (o instanceof Property) {
                    Property p = (Property)o;
                    if (p.getAssociation() != null) {
                        if (p.getOpposite() == null) {
                            for (Property end: p.getAssociation().getMemberEnd()) {
                                if (!end.equals(p)) {
                                     p.setOpposite(end);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
