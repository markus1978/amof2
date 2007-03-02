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

import cmof.Element;
import cmof.Operation;
import cmof.Parameter;
import cmof.ParameterDirectionKind;
import cmof.Property;
import cmof.Type;
import cmof.UmlClass;
import cmof.common.ReflectiveSequence;
import hub.sam.mof.codegeneration.GenerationException;
import hub.sam.mof.jocl.standardlib.OclAny;

public class OperationWrapper extends TypedElementWrapper {
    private final Operation operation;
    private boolean hasNoType = false;
    private Type type = null;

    public OperationWrapper(Operation operation) {
        super();
        this.operation = operation;
    }

    @Override
    public String getName() {
        String name = javaMapping.getJavaIdentifier(operation);
        for (Element element : ((UmlClass)operation.getOwner()).getMember()) {
            if (element instanceof Property) {
                if (new PropertyWrapper((Property)element, false, false).getName().equals(name)) {
                    name += "Operation";
                }
            }
        }
        return name;
    }

    public boolean hasReturn() {
        return getUmlType() != null;
    }

    public String getUmlName() {
        return operation.getName();
    }

    public boolean isJavaPrimitive() {
        return ((operation.getUpper() == 1) && (
                getUmlType().getName().equals("Integer") ||
                        getUmlType().getName().equals("UnlimitedNatural") ||
                        getUmlType().getName().equals("Boolean"))
        );
    }

    @Override
    public boolean isList() {
        if (getReturnParameter() != null) {
            return getReturnParameter().isOrdered();
        } else {
            return operation.isOrdered();
        }
    }

    @Override
    public boolean isCollection() {
        Parameter returnParameter = getReturnParameter();
        return !((returnParameter != null && returnParameter.getUpper() == 1) ||
                (returnParameter == null && operation.getUpper() ==1));
    }

    private Parameter getReturnParameter() {
        for (core.basic.Parameter parameter : operation.getOwnedParameter()) {
            if (((cmof.Parameter)parameter).getDirection() == ParameterDirectionKind.RETURN) {
                return (cmof.Parameter)parameter;
            }
        }
        return null;
    }

    @Override
    public Type getUmlType() {
        if (hasNoType) {
            return null;
        }
        Type result = type;
        if (result == null) {
            result = operation.getType();
        }
        if (result == null) {
            Parameter returnParameter = getReturnParameter();
            if (returnParameter != null) {
                result = (cmof.Type)returnParameter.getType();
            }
        }
        if (result == null) {
            hasNoType = true;
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public String getParameters() throws GenerationException {
        StringBuffer result = new StringBuffer();
        boolean first = true;
        cmof.common.ReflectiveSequence<? extends Parameter> parameters = (ReflectiveSequence<? extends Parameter>)operation.getOwnedParameter();
        for (Parameter parameter : parameters) {
            if (parameter.getDirection() != ParameterDirectionKind.RETURN) {
                if (first) {
                    first = false;
                } else {
                    result.append(", ");
                }
                ParameterWrapper parameterWrapper = new ParameterWrapper(parameter);
                result.append(parameterWrapper.getType());
                result.append(" ");
                result.append(parameterWrapper.getName());
            }
        }
        return result.toString();
    }

    @SuppressWarnings("unchecked")
    public String getOclParameters() throws GenerationException {
        StringBuffer result = new StringBuffer();
        boolean first = true;
        cmof.common.ReflectiveSequence<? extends Parameter> parameters = (ReflectiveSequence<? extends Parameter>)operation.getOwnedParameter();
        for (Parameter parameter : parameters) {
            if (parameter.getDirection() != ParameterDirectionKind.RETURN) {
                if (first) {
                    first = false;
                } else {
                    result.append(", ");
                }
                ParameterWrapper parameterWrapper = new ParameterWrapper(parameter);
                result.append(parameterWrapper.getOclType());
                result.append(" ");
                result.append(parameterWrapper.getName());
            }
        }
        return result.toString();
    }

    @SuppressWarnings("unchecked")
    public String getParameterNames() throws GenerationException {
        StringBuffer result = new StringBuffer();
        boolean first = true;
        cmof.common.ReflectiveSequence<? extends Parameter> parameters = (ReflectiveSequence<? extends Parameter>)operation.getOwnedParameter();
        for (Parameter parameter : parameters) {
            if (parameter.getDirection() != ParameterDirectionKind.RETURN) {
                if (first) {
                    first = false;
                } else {
                    result.append(", ");
                }
                ParameterWrapper parameterWrapper = new ParameterWrapper(parameter);
                result.append(parameterWrapper.getName());
            }
        }
        return result.toString();
    }

    @SuppressWarnings("unchecked")
    public String getOclParameterArray() throws GenerationException {
        StringBuffer result = new StringBuffer();
        result.append("new ").append(OclAny.class.getCanonicalName()).append("[] {");
        boolean first = true;
        cmof.common.ReflectiveSequence<? extends Parameter> parameters = (ReflectiveSequence<? extends Parameter>)operation.getOwnedParameter();
        for (Parameter parameter : parameters) {
            if (parameter.getDirection() != ParameterDirectionKind.RETURN) {
                if (first) {
                    first = false;
                } else {
                    result.append(", ");
                }
                ParameterWrapper parameterWrapper = new ParameterWrapper(parameter);
                result.append(parameterWrapper.getName());
            }
        }
        result.append("}");
        return result.toString();
    }

    public String getUnambigousName() throws GenerationException {
        StringBuffer result = new StringBuffer();
        result.append(operation.getName());
        for (Parameter parameter : operation.getFormalParameter()) {
            if (parameter.getDirection() != ParameterDirectionKind.RETURN) {
                ParameterWrapper parameterWrapper = new ParameterWrapper(parameter);
                result.append("_");
                result.append(parameterWrapper.getUmlType().getQualifiedName());
            }
        }
        return result.toString();
    }

    public String getExceptions() {
        String result = "";
        boolean first = true;
        for (Type exception : operation.getRaisedException()) {
            if (first) {
                result += "throws ";
                first = false;
            } else {
                result += ", ";
            }
            result += getFullQualifiedJavaIdentifier(exception);
        }
        return result;
    }

    public String getAttributeDocString() {
        StringBuffer result = new StringBuffer();
        if (operation.isUnique() && operation.getUpper() > 1) {
            result.append(", isUnique");
        }
        if (operation.isOrdered() && operation.getUpper() > 1) {
            result.append(", isOrdered");
        }
        if (operation.isQuery()) {
            result.append(", isQuery");
        }
        return result.toString();
    }

    public String getMultiplicity() {
        return operation.getLower() + "," + ((operation.getUpper() == -1) ? "*" : Long.toString(operation.getUpper()));
    }

    public boolean isQuery() {
        return operation.isQuery();
    }
}
