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
import cmof.UmlClass;
import cmof.reflection.Extent;
import hub.sam.mof.instancemodel.ClassInstance;
import hub.sam.mof.instancemodel.InstanceModel;
import hub.sam.mof.instancemodel.MetaModelException;
import hub.sam.mof.instancemodel.StructureSlot;
import hub.sam.mof.instancemodel.ValueSpecification;
import hub.sam.mof.instancemodel.ValueSpecificationImpl;
import hub.sam.mof.instancemodel.conversion.Converter;
import hub.sam.mof.reflection.ExtentImpl;
import hub.sam.mof.xmi.Xmi1Reader.XmiKind;
import org.jdom.DefaultJDOMFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Xmi2Writer {

    private final DefaultJDOMFactory jdom;
    private final Document document;
    private final org.jdom.Namespace xmi;
    private final org.jdom.Namespace xsi;
    private org.jdom.Element rootElement;
    private final Map<String, org.jdom.Namespace> nsForPrefix = new HashMap<String, org.jdom.Namespace>();
    
    private IIdProvider idProvider = null;

    private Xmi2Writer(String xsiNamespacePrefix) {
        super();
        this.jdom = new DefaultJDOMFactory();
        this.xmi = org.jdom.Namespace.getNamespace("xmi", "http://www.omg.org/XMI");

        rootElement = jdom.element("XMI", xmi);
        document = jdom.document(rootElement);

        rootElement.addNamespaceDeclaration(xmi);
        if (xsiNamespacePrefix.equals("xmi")) {
            this.xsi = xmi;
        } else {
            this.xsi = org.jdom.Namespace.getNamespace(xsiNamespacePrefix, "http://www.omg.org/XSI");
            rootElement.addNamespaceDeclaration(xsi);
        }
        rootElement.setAttribute("version", "2.1", xmi);
    }
    
    public void setIdProvider(IIdProvider provider) {
    	this.idProvider = provider;
    }

    private void write(InstanceModel<XmiClassifier,String,String> model, OutputStream out, Element additionalXmi) throws XmiException, java.io.IOException {
        for (ValueSpecification<XmiClassifier,String,String> instance: model.getOutermostComposites()) {
            org.jdom.Element element = jdom.element(instance.asInstanceValue().getInstance().getClassifier().getName());
            rootElement.addContent(element);

            String nsPrefix = instance.asInstanceValue().getInstance().getClassifier().getNamespacePrefix();
            if (nsForPrefix.get(nsPrefix) == null) {
                org.jdom.Namespace ns = org.jdom.Namespace.getNamespace(nsPrefix, "http://www.foo.org/" + nsPrefix);
                rootElement.addNamespaceDeclaration(ns);
                nsForPrefix.put(nsPrefix, ns);
            }
            element.setNamespace(nsForPrefix.get(nsPrefix));
            write(instance.asInstanceValue().getInstance(), element);
        }

        if (additionalXmi != null) {
        	Collection<Element> children = new Vector<Element>();
	        for (Object obj: additionalXmi.getChildren()) {
	        	children.add((Element)((Element)obj).clone());	       	        
	        }
	        for (Element additionalElement: children) {
	        	additionalElement.detach();
	        	rootElement.addContent(additionalElement);
	        }
        }
        new XMLOutputter(Format.getPrettyFormat()).output(document, out);
    }

    private void addValueToBuffer(StringBuffer values, String value) {
        if (values.length() == 0) {
            values.append(value);
        } else {
            values.append(" ");
            values.append(value);
        }
    }

    private void write(ClassInstance<XmiClassifier,String,String> instance, org.jdom.Element parent) throws XmiException {
        parent.setAttribute("id", getId(instance), xmi);
        for(StructureSlot<XmiClassifier,String,String> slot: instance.getSlots()) {
            StringBuffer values = new StringBuffer();
            for (ValueSpecificationImpl<XmiClassifier,String,String> value: slot.getValues(null)) {
                if (value.asInstanceValue() != null) {
                    ClassInstance<XmiClassifier,String,String> valueInstance = value.asInstanceValue().getInstance();
                    if (valueInstance.getComposite() != instance) {
                        addValueToBuffer(values, getId(valueInstance));
                    } else {
                        org.jdom.Element element = jdom.element(slot.getProperty());
                        parent.addContent(element);
                        write(valueInstance, element);
                        if (instance.getClassifier().getNamespacePrefix() != null && !instance.getClassifier().getNamespacePrefix().equals(valueInstance.getClassifier().getNamespacePrefix())) {
                            element.setAttribute("type", valueInstance.getClassifier().getNamespacePrefix() + ":" + valueInstance.getClassifier().getName(), xsi);
                        } else {
                            element.setAttribute("type", valueInstance.getClassifier().getName(), xsi);
                        }
                    }
                } else if (value.asUnspecifiedValue() != null) {
                    addValueToBuffer(values, value.asUnspecifiedValue().getUnspecifiedData().toString());
                } else if (value.asDataValue() != null) {
                    addValueToBuffer(values, value.asDataValue().getValue());
                } else {
                    throw new XmiException("assert");
                }
            }
            if (values.length() != 0) {
                parent.setAttribute(slot.getProperty(), values.toString());
            }
        }
    }
    
    private String getId(ClassInstance<XmiClassifier, String, String> instance) {
    	if (idProvider == null) {
    		return instance.getId();
    	} else {
    		return (String)idProvider.getId(instance.getId());
    	}
    }
    
    public static void writeMofXmi(java.io.File file, Extent extent, cmof.Package m2, XmiKind xmiKind) throws java.io.IOException, XmiException, MetaModelException {
    	writeMofXmi(new FileOutputStream(file), extent, m2, xmiKind, null);
    }
    
    public static void writeMofXmi(OutputStream out, Extent extent, cmof.Package m2, XmiKind xmiKind) throws java.io.IOException, XmiException, MetaModelException {
    	writeMofXmi(out, extent, m2, xmiKind, null);
    }

    /**
     * Writes XMI with a m1 model of the given m2 into a xmi file. The extent must be based on a MofInstanceModel.
     */
    public static void writeMofXmi(OutputStream out, Extent extent, cmof.Package m2, XmiKind xmiKind,
    		XmiImportExport importExport) throws java.io.IOException, XmiException, MetaModelException {
    	// check attriutes for is composite == false
    	for (Object o: extent.getObject()) {
    		if (o instanceof Property) {
    			Property p = (Property)o;
    			if (p.getAssociation() == null && p.getUmlClass() != null && (p.getType() instanceof DataType)) {    				
    				p.setIsComposite(true);
    			}
    		}
    	}

        InstanceModel<XmiClassifier,String,String> xmiModel = new InstanceModel<XmiClassifier,String,String>();
        CMOFToXmi conversion = new CMOFToXmi(m2, "unknownNs");

        Converter<UmlClass, Property, java.lang.Object, XmiClassifier, String, String, String, String> converter =
        		new Converter<UmlClass, Property, java.lang.Object, XmiClassifier,String,String, String, String>(conversion);
        converter.setIdMemorizer(importExport);
        converter.convert(((ExtentImpl)extent).getModel(), xmiModel);

        if (xmiKind == XmiKind.md) {
            XmiTransformator transformator = new MOF2ToMagicDrawXmi2(xmiModel);

            Collection<ClassInstance> outermostComposites = new Vector<ClassInstance>();
            for (ValueSpecification<XmiClassifier,String,String> instance: xmiModel.getOutermostComposites()) {
            	outermostComposites.add(instance.asInstanceValue().getInstance());
            }
            for (ClassInstance<XmiClassifier,String,String> instance: outermostComposites) {
                if (!instance.getClassifier().getName().equals("Model")) {
                    transformator.transform(hub.sam.mof.xmi.mopa.XmiMopaTreeNode.createNode(instance));
                }
            }
        }

        Xmi2Writer writer = new Xmi2Writer((xmiKind == XmiKind.md)?"xmi":"xsi");
        writer.setIdProvider(importExport);        
        writer.write(xmiModel, out, (importExport == null) ? null : importExport.getXMI());
    }
}
