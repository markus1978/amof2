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

package hub.sam.mof.javamapping;

import cmof.Element;
import cmof.EnumerationLiteral;
import cmof.NamedElement;
import cmof.Operation;
import cmof.PrimitiveType;
import cmof.Property;
import cmof.Tag;
import cmof.Type;
import cmof.UmlClass;
import hub.sam.mof.codegeneration.GenerationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class JavaMapping {
    public static final java.lang.String SubstituteNameTagName = "javax.jmi.SubstituteName";
    public static final java.lang.String PackagePrefixTagName = "javax.jmi.PackagePrefix";

    public static final JavaMapping mapping = new JavaMapping();


    private JavaMapping() {
        super();
    }

    private final Map<Element, String> renames = new HashMap<Element, String>();

    public void addSubstituteNameTag(Tag tag) {
        if (tag.getName().equals(SubstituteNameTagName)) {
            for (Element el : tag.getElement()) {
                renames.put(el, tag.getValue());
            }
        }
    }

    // public just for bootstrapping, private for all others
    public String getJavaIdentifierForName(String name) {
        if (name.equals("Class")) {
            return "UmlClass";
        } else if (name.equals("class")) {
            return "umlClass";
        } else if (name.equals("super")) {
            return "umlsuper";
        } else {
            return name;
        }
    }

    public String getJavaNameForProperty(Property property) {
        return getJavaIdentifier(property);
    }

    private String getUpperName(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
    }

    public String getJavaMethodNameForOperation(Operation op) {
        return getJavaIdentifier(op);
    }

    public String getJavaSetMethodNameForProperty(Property property) {
        return "set" + getUpperName(getJavaNameForProperty(property));
    }

    // only for bootstrap
    public String getJavaGetMethodNameForProperty(String name, boolean isBoolean) {
        if (isBoolean) {
            return getJavaIdentifierForName(name);
        } else {
            return "get" + getUpperName(getJavaIdentifierForName(name));
        }
    }

    public String getJavaGetMethodNameForProperty(Property property) {
        Type type = (Type)property.getType();
        if (type instanceof PrimitiveType) {
            if (type.getName().equals(core.primitivetypes.Boolean.class.getSimpleName())) {
                return getJavaNameForProperty(property);
            }
        }
        return "get" + getUpperName(getJavaNameForProperty(property));
    }

    /**
     * Creates a StringBuffer with the full qualified Java name for a MOF NamedElement. It uses
     * {@link JavaMapping#getJavaIdentifier(cmof.NamedElement)} to create the names for all path items and looks
     * for the {@link JavaMapping.PackagePrefixTagName} tag in the topmost package.
     *
     * @param metaClass
     *        The NamedElement that the path is needed for.
     * @return  A StringBuffer containing the full qualified Java name.
     */
    private StringBuffer getClassNameForMetaClass(NamedElement metaClass) {
        List<NamedElement> qualifiedName = new Vector<NamedElement>(10);
        qualifiedName.add(metaClass);
        cmof.NamedElement owningPackage = metaClass.getNamespace();
        while (owningPackage != null) {
            qualifiedName.add(owningPackage);
            owningPackage = (cmof.Package)owningPackage.getOwner();
        }
        StringBuffer className = new StringBuffer(qualifiedName.size() * 15);
        boolean first = true;
        for(int i = qualifiedName.size() - 1; i >= 0; i--) {
            NamedElement namedElement = qualifiedName.get(i);
            if (first) {
                first = false;
                TagLoop: for(Tag tag: namedElement.getTag()) {
                    if (tag.getName().equals(PackagePrefixTagName)) {
                        className.append(tag.getValue());
                        className.append(".");
                        break TagLoop;
                    }
                }
            } else {
                className.append(".");
            }
            className.append(getJavaIdentifier(namedElement));
        }
        return className;
    }

    public String getImplClassNameForMetaClass(UmlClass metaClass) {
        return getClassNameForMetaClass(metaClass).append("Impl").toString();
    }

    public String getCustomClassNameForMetaClass(UmlClass metaClass) {
        return getClassNameForMetaClass(metaClass).append("Custom").toString();
    }

    /**
     * Returns the Java identifier for a MOF element. It takes all tags directly owned by the element,
     * build in rules, such as Class=UmlClass, and additional renamings scheduled by
     * {@link hub.sam.mof.codegeneration.ResolveJavaCodeClashes} into account.
     *
     * @param element The element for that the Java name is needed.
     * @return The Java identifier as String.
     */
    public String getJavaIdentifier(cmof.NamedElement element) {
        for (Tag tag : element.getTag()) {
            if (tag.getName().equals(SubstituteNameTagName)) {
                return tag.getValue();
            }
        }
        String name = element.getName();
        if (name == null) {
            String message = "A element of owner " + element.getOwner() + " has no name.";
            if (element instanceof Property) {
                message += " This element is a property with type " + ((Property)element).getType();
            }
            throw new GenerationException(message);
        }
        String substituteName = renames.get(element);
        if (substituteName == null) {
            return getJavaIdentifierForName(name);
        } else {
            return getJavaIdentifierForName(substituteName);
        }
    }

    public String getFullQualifiedJavaIdentifier(NamedElement element) {
        return getClassNameForMetaClass(element).toString();
    }

    public String getFullQualifiedImplFactoryNameForPackage(cmof.Package thePackage) {
        return getFullQualifiedJavaIdentifier(thePackage) + "." + getFactoryNameForPackage(thePackage) + "Impl";
    }

    public String getFullQualifiedFactoryNameForPackage(cmof.Package thePackage) {
        return getFullQualifiedJavaIdentifier(thePackage) + "." + getFactoryNameForPackage(thePackage);
    }

    public String getFactoryNameForPackage(cmof.Package thePackage) {
        return getJavaIdentifier(thePackage) + "Factory";
    }

    public String getJavaEnumConstantForLiteral(EnumerationLiteral literal) {
        return literal.getName().toUpperCase();
    }

    public String getJavaTypeNameForPrimitiveType(PrimitiveType type) {
        return type.getName();
    }
}
