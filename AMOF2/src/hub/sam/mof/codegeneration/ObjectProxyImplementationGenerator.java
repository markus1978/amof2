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

package hub.sam.mof.codegeneration;

import hub.sam.mof.codegeneration.wrapper.OperationWrapper;
import hub.sam.mof.codegeneration.wrapper.PropertyWrapper;
import hub.sam.mof.codegeneration.wrapper.UmlClassWrapper;
import hub.sam.mof.reflection.ExtentImpl;

public class ObjectProxyImplementationGenerator extends AbstractObjectProxyGenerator {

    private final String classNameExtension;
    public ObjectProxyImplementationGenerator(StreamFactory streamFactory, String classNameExtension) {
        super(streamFactory);
        this.classNameExtension = classNameExtension;
    }

    @Override
    protected final String getClassName(UmlClassWrapper umlClass) {
        return umlClass.getName() + classNameExtension;
    }

    @Override
    protected void addClassSignature(UmlClassWrapper umlClass) throws Throwable {
        add("public class " + getClassName(umlClass) + " extends " + hub.sam.mof.reflection.ObjectImpl.class.getName() + " $implements");
    }

    @Override
    protected void addGetterCode(PropertyWrapper property) throws Throwable {
        add("public $type $getterName($getterArgs) {");
        if (property.hasQualifier()) {
        add("    java.lang.Object value = get(\"$umlName\", qualifier);");
        } else {
        add("    java.lang.Object value = get(\"$umlName\");");
        }
        add("    if (value == null) {");
        if (property.isJavaPrimitive()) {
        add("       throw new RuntimeException(\"assert\");");
        } else {
        add("       return null;");
        }
        add("    } else {");
        if (property.isJavaList()) {
            if (property.isList()) {
        add("        return new " + hub.sam.mof.util.TypeWrapperListImpl.class.getCanonicalName() + "((" + cmof.common.ReflectiveSequence.class.getName() + ")value, this, \"$umlName\");");
            } else {
        add("        return new " + hub.sam.mof.util.TypeWrapperSetImpl.class.getCanonicalName() + "((" + cmof.common.ReflectiveCollection.class.getName() + ")value, this, \"$umlName\");");
            }
        } else {
        add("        return ($javaObjectType)value;");
        }
        add("    }");
        add("}");
        if (property.isJavaList() && !property.hasHigherMulitplicity()) {
            add("public $javaObjectType _$getterName($getterArgs) {");
            if (property.hasQualifier()) {
            add("    java.lang.Object value = get(\"$umlName\", qualifier);");
            } else {
            add("    java.lang.Object value = get(\"$umlName\");");
            }
            add("    if (value == null) {");
            if (property.isJavaPrimitive()) {
            add("       throw new RuntimeException(\"assert\");");
            } else {
            add("       return null;");
            }
            add("    } else {");
            add("        return ($javaObjectType)((" + ExtentImpl.ValueList.class.getCanonicalName() + ")value).get(0);");
            add("    }");
            add("}");
        }
    }

    @Override
    protected void addSetterCode(PropertyWrapper property) throws Throwable {
        add("public void $setterName($setterArgs) {");
        if (property.hasQualifier()) {
        add("    set(\"$umlName\", qualifier, value);");
        } else {
        add("    set(\"$umlName\", value);");
        }
        add("}");
    }

    @Override
    protected void addOperationCode(OperationWrapper operation) throws Throwable {
        add("public $type $name($parameters) $exceptions {");
        if (operation.hasReturn()) {
            add("    java.lang.Object value = invokeOperation(\"$unambigousName\", new java.lang.Object[] { $parameterNames });");
            add("    if (value == null) {");
            if (operation.isJavaPrimitive()) {
            add("       throw new RuntimeException(\"assert\");");
            } else {
            add("       return null;");
            }
            add("    } else {");
            if (operation.isCollection()) {
                if (operation.isList()) {
            add("        return new " + hub.sam.mof.util.TypeWrapperListImpl.class.getCanonicalName() + "((" + cmof.common.ReflectiveSequence.class.getName() + ")value);");
                } else {
            add("        return new " + hub.sam.mof.util.TypeWrapperSetImpl.class.getCanonicalName() + "((" + cmof.common.ReflectiveCollection.class.getName() + ")value);");
                }
            } else {
            add("        return ($javaObjectType)value;");
            }
            add("    }");
        } else {
            add("    invokeOperation(\"$unambigousName\", new java.lang.Object[] { $parameterNames });");
        }
        add("}");
    }

    @Override
    protected void addGeneralClassBodyCode(UmlClassWrapper umlClass) throws Throwable {
        add("public " + getClassName(umlClass) + "(" + hub.sam.mof.instancemodel.ClassInstance.class.getName() +
                " instance, " + hub.sam.mof.reflection.ExtentImpl.class.getName() + " extent) {");
        add("    super(instance, extent);");
        add("}");
        add("public " + getClassName(umlClass) + "(" +
                hub.sam.mof.reflection.Identifier.class.getName() + " id, " +
                hub.sam.mof.reflection.ExtentImpl.class.getCanonicalName() + " extent, " +
                hub.sam.mof.reflection.Identifier.class.getName() + " metaId, " +
                String.class.getCanonicalName() + " implementationClassName, " +
                String.class.getCanonicalName() + "[] delegateClassNames) {");
        add("    super(id, extent, metaId, implementationClassName, delegateClassNames);");
        add("}");
        //if (CodeGenerationConfiguration.getActualConfig().isGenerateOcl()) {
        //    add("public $oclModelElement ocl$name() {");
        //    add("    return  new $oclModelElement(this);");
        //    add("}");
        //}
    }

    @Override
    protected void addGeneralClassBodyCodeForParent(UmlClassWrapper umlClass) throws Throwable {
        //if (CodeGenerationConfiguration.getActualConfig().isGenerateOcl()) {
        //    add("public $oclModelElement ocl$name() {");
        //    add("    return  new $oclModelElement(this);");
        //    add("}");
        //}
    }

    @Override
    protected boolean generateOnlyForOwnedMember() { return false; }
}
