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

package hub.sam.mof.codegeneration.wrapper;

import cmof.UmlClass;

public class UmlClassWrapper extends AbstractWrapper {
	final cmof.UmlClass umlClass;
    public UmlClassWrapper(UmlClass umlClass) {
        this.umlClass = umlClass;
    }
    public String getName() {
        return getJavaIdentifier(umlClass);
    }
    public String getUmlName() {
        return umlClass.getName();
    }
    public String getImplName() {
        return getName() + "Impl";
    }
    public String getImplements() {
        return "implements " + getName();
    }
    public String getExtends() {
        String result = "extends " + cmof.reflection.Object.class.getCanonicalName();
        for (UmlClass superClass: umlClass.getSuperClass()) {
            result += ", ";
            result += getFullQualifiedJavaIdentifier(superClass);
        }
        return result;
    }
    @SuppressWarnings("synthetic-access")
	public String getAttributeDocString() {
        String result = "";
        if (umlClass.isAbstract()) {
            result += ", isAbstract";
        }
        if (umlClass.getSuperClass().size() > 0) {
            result += ", superClass = {" + docStringForElementList(umlClass.getSuperClass()) + "}";
        }
        return result;
    }
	public cmof.UmlClass getUmlClass() {
		return umlClass;
	}
    public String getOclModelElement() {
        return getFullQualifiedJavaIdentifier(umlClass) + "Value";
    }

}
