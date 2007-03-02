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

import cmof.PrimitiveType;
import cmof.Type;
import cmof.DataType;
import cmof.Enumeration;
import hub.sam.mof.jocl.standardlib.OclString;
import hub.sam.mof.jocl.standardlib.OclInteger;
import hub.sam.mof.jocl.standardlib.OclBoolean;
import hub.sam.mof.jocl.standardlib.OclSequence;
import hub.sam.mof.jocl.standardlib.OclSet;

public abstract class TypedElementWrapper extends AbstractWrapper {
    public abstract String getName();

    public abstract Type getUmlType();

    public String getUpperName() {
        String name = getName();
        return name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
    }

    public final String getPlainJavaType() {
        String typeName = null;
        Type type = getUmlType();
        if (type == null) {
            return "void";
        }
        if (type instanceof PrimitiveType) {
            if (type.getName().equals(core.primitivetypes.String.class.getSimpleName())) {
                typeName = java.lang.String.class.getCanonicalName();
            } else if (type.getName().equals(core.primitivetypes.Object.class.getSimpleName())) {
                typeName = Object.class.getCanonicalName();
            } else if (type.getName().equals(core.primitivetypes.Integer.class.getSimpleName())) {
                typeName = "int";
            } else if (type.getName().equals(core.primitivetypes.Boolean.class.getSimpleName())) {
                typeName = "boolean";
            } else if (type.getName().equals(core.primitivetypes.UnlimitedNatural.class.getSimpleName())) {
                typeName = "long";
            } else {
                return javaMapping.getJavaTypeNameForPrimitiveType((PrimitiveType)type);
            }
        } else {
            typeName = getFullQualifiedJavaIdentifier(type);
        }
        return typeName;
    }

    public final String getJavaObjectType() {
        String typeName = null;
        Type type = getUmlType();
        if (type == null) {
            return "void";
        }
        if (type instanceof PrimitiveType) {
            if (type.getName().equals(core.primitivetypes.String.class.getSimpleName())) {
                typeName = java.lang.String.class.getCanonicalName();
            } else if (type.getName().equals(core.primitivetypes.Object.class.getSimpleName())) {
                typeName = java.lang.Object.class.getCanonicalName();
            } else if (type.getName().equals(core.primitivetypes.Integer.class.getSimpleName())) {
                typeName = Integer.class.getCanonicalName();
            } else if (type.getName().equals(core.primitivetypes.Boolean.class.getSimpleName())) {
                typeName = Boolean.class.getCanonicalName();
            } else if (type.getName().equals(core.primitivetypes.UnlimitedNatural.class.getSimpleName())) {
                typeName = Long.class.getCanonicalName();
            } else {
                return javaMapping.getJavaTypeNameForPrimitiveType((PrimitiveType)type);
            }
        } else {
            typeName = getFullQualifiedJavaIdentifier(type);
        }
        return typeName;
    }

    protected final String getPlainOclType() {
        String typeName = null;
        Type type = getUmlType();
        if (type instanceof PrimitiveType) {
            if (type.getName().equals(core.primitivetypes.String.class.getSimpleName())) {
                typeName = OclString.class.getCanonicalName();
            } else if (type.getName().equals(core.primitivetypes.Integer.class.getSimpleName())) {
                typeName = OclInteger.class.getCanonicalName();
            } else if (type.getName().equals(core.primitivetypes.Boolean.class.getSimpleName())) {
                typeName = OclBoolean.class.getCanonicalName();
            } else if (type.getName().equals(core.primitivetypes.UnlimitedNatural.class.getSimpleName())) {
                typeName = OclInteger.class.getCanonicalName();
            } else if (type.getName().equals(core.primitivetypes.Object.class.getSimpleName())) {
                // TODO
                typeName = OclString.class.getCanonicalName();
            } else {
                // TODO generic Javatypes
                typeName = OclString.class.getCanonicalName();
            }
        } else if (type instanceof Enumeration) {
            // TODO: throw new RuntimeException("ocl enumerations not implemented");
            typeName = OclString.class.getCanonicalName();
        } else {
            typeName = getFullQualifiedJavaIdentifier(type) + "Value";
        }
        return typeName;
    }

    protected abstract boolean isList();

    protected abstract boolean isCollection();

    public final String getType() {
        String typeName = getPlainJavaType();
        if (typeName.equals("void")) {
            return typeName;
        }
        if (!isCollection()) {
            return typeName;
        } else {
            if (!(getUmlType() instanceof DataType)) {
                typeName = "? extends " + getJavaObjectType();
            } else {
                typeName = getJavaObjectType();
            }
            if (isList()) {
                return cmof.common.ReflectiveSequence.class.getCanonicalName() + "<" + typeName + ">";
            } else {
                return cmof.common.ReflectiveCollection.class.getCanonicalName() + "<" + typeName + ">";
            }
        }
    }

    public final String getOclType() {
        if (!isCollection()) {
            return getPlainOclType();
        } else {
            String typeName;
            if (!(getUmlType() instanceof DataType)) {
                typeName = getPlainJavaType();
            } else {
                typeName = getJavaObjectType();
            }
            if (isList()) {
                return OclSequence.class.getCanonicalName() + "<" + getPlainOclType() + "," + typeName + ">";
            } else {
                return OclSet.class.getCanonicalName() + "<" + getPlainOclType() + "," + typeName + ">";
            }
        }
    }

    public final String getOclTypeWoTP() {
        if (!isCollection()) {
            return getPlainOclType();
        } else {
            String typeName;
            if (!(getUmlType() instanceof DataType)) {
                typeName = getJavaObjectType();
            } else {
                typeName = getJavaObjectType();
            }
            if (isList()) {
                return OclSequence.class.getCanonicalName();
            } else {
                return OclSet.class.getCanonicalName();
            }
        }
    }
}
