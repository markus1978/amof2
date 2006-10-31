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

import cmof.Element;
import cmof.PackageImport;
import cmof.Property;
import cmof.Tag;
import cmof.UmlClass;
import hub.sam.mof.instancemodel.ClassInstance;
import hub.sam.mof.instancemodel.MetaModelException;
import hub.sam.mof.instancemodel.StructureSlot;
import hub.sam.mof.instancemodel.ValueSpecificationImpl;
import hub.sam.mof.instancemodel.conversion.Conversion;
import hub.sam.mof.mofinstancemodel.MofClassInstance;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CMOFToXmi implements Conversion<UmlClass, Property, java.lang.Object, XmiClassifier, String, String, String, String> {

    private final Map<cmof.Package, String> packageNsPrefixes = new HashMap<cmof.Package, String>();
    private final String defaultNsPrefix;
    private final PrimitiveValueSerializeConfiguration valueSerializeConfiguration =
            new StandardPrimitiveValueSerializeConfiguration();

    public CMOFToXmi(cmof.Package metaModel, String defaulNsPrefix) {
        this.defaultNsPrefix = defaulNsPrefix;
        searchForNsPrefixes(metaModel);
    }

    private void searchForNsPrefixes(cmof.Package inPackage) {
    	for(Tag tag: inPackage.getTag()) {
    		if (new String("org.omg.xmi.nsPrefix").equals(tag.getName())) {
                packageNsPrefixes.put(inPackage, tag.getValue());
            }
    	}
        for (Element element: inPackage.getOwnedElement()) {
            if (element instanceof cmof.Package) {
                searchForNsPrefixes((cmof.Package)element);
            } else if (element instanceof PackageImport) {
                searchForNsPrefixes(((PackageImport)element).getImportedPackage());
            }
        }
    }

    private String getNsPrefixForPackage(cmof.Package forPackage) {
        String result = null;
        result = packageNsPrefixes.get(forPackage);
        loop: while (forPackage != null && result == null) {
            forPackage = forPackage.getNestingPackage();
            result = packageNsPrefixes.get(forPackage);
            break loop;
        }
        if (result == null) {
            return defaultNsPrefix;
        } else {
            return result;
        }
    }

    public Collection<String> getNsPrefixes() {
        return packageNsPrefixes.values();
    }

    public XmiClassifier getClassifier(UmlClass identifier) throws MetaModelException {
        return new XmiClassifier(identifier.getName(), getNsPrefixForPackage(identifier.getPackage()));
    }

    public String getProperty(Property property, XmiClassifier classifier) throws MetaModelException {
        return property.getName();
    }

    public String getPropertyType(String property) throws MetaModelException {
        return "ignore";
    }

    public String createFromString(String type, Object stringRepresentation) throws MetaModelException {
        if (stringRepresentation.getClass().isEnum()) {
    		// this is awkward. The java representation of a literal is toUpperCase, to reproduce the original
    		// literal this has to be reverted. Unfortunatly the datatype is not really known at this point and
    		// thus this creepy implementation is the only thing possible.
    		return stringRepresentation.toString().toLowerCase();
    	} else {
            if (valueSerializeConfiguration.needsSerialization(stringRepresentation)) {
                return valueSerializeConfiguration.serialize(type, stringRepresentation);
            } else {
                return stringRepresentation.toString();
            }
        }
    }

    public String asDataType(String type) {
        return type;
    }

    /**
     * Checks whether a value should be exported as value of the given property. Due to the subsetting of propertys,
     * it is possible that a single value is value for multiple properties. But it should only be exported once.
     * The method returns true if the given value is only stored in the given property and in subsetted propertys of
     * that property within the given instance (object).
     */
    public boolean doConvert(ValueSpecificationImpl<UmlClass, Property, Object> value, StructureSlot<UmlClass, Property, Object> slot,
            ClassInstance<UmlClass, Property, Object> instance) throws MetaModelException {
        return staticDoConvert(value, slot, instance);
    }

    public static boolean staticDoConvert(ValueSpecificationImpl<UmlClass, Property, Object> value, StructureSlot<UmlClass, Property, Object> slot,
            ClassInstance<UmlClass, Property, Object> instance) throws MetaModelException {

        java.util.Collection<Property> allSubsets = ((MofClassInstance)instance).getInstanceClassifier().getSupersettedProperties(slot.getProperty());
        for (Property subset: allSubsets) {
            StructureSlot<UmlClass,Property,Object> subsetValues = instance.get(subset);
            if (subsetValues != null) {
                if (subsetValues.getValuesAsList(null).contains(value)) {
                    return false;
                }
            }
        }
        if (slot.getProperty().isDerivedUnion()) {
        	if (slot.getProperty().getOpposite() == null) {
        		throw new MetaModelException("Inconsistent model: the value of an derived union cannot be derived: " + slot.getProperty().getQualifiedName());
        	} else {
        		// TODO check whether the value can be derived through the association
        		return false;
        	}
        } else {
        	return true;
        }
    }

    public String getProperty(Property property, XmiClassifier classifier, ValueSpecificationImpl<XmiClassifier, String, String> value) throws MetaModelException {
		return getProperty(property, classifier);
	}
}
