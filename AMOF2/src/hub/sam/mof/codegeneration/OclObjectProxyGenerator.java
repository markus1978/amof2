package hub.sam.mof.codegeneration;

import hub.sam.mof.codegeneration.wrapper.OperationWrapper;
import hub.sam.mof.codegeneration.wrapper.PropertyWrapper;
import hub.sam.mof.codegeneration.wrapper.UmlClassWrapper;

public class OclObjectProxyGenerator extends
        ObjectProxyImplementationGenerator {

    public OclObjectProxyGenerator(StreamFactory streamFactory) {
        super(streamFactory, "Value");
    }

    @Override
    protected void addClassSignature(UmlClassWrapper umlClass) throws Throwable {
        add("public class " + getClassName(umlClass) + " extends " + hub.sam.mof.jocl.standardlib.OclModelElement.class.getName() + "<$name>");
    }

    @Override
    protected void addGeneralClassBodyCode(UmlClassWrapper umlClass) throws Throwable {
        add("public " + getClassName(umlClass) + " ($name self) {");
        add("    super(self);");
        add("}");
    }

    @Override
    protected void addGeneralClassBodyCodeForParent(UmlClassWrapper umlClass) throws Throwable {
        // empty
    }

    @Override
    protected void addGetterCode(PropertyWrapper property) throws Throwable {
        if (property.useInOcl()) {
            add("public $oclType $getterName($oclGetterArgs) {");
            if (property.hasQualifier()) {
                add("    return ($oclType)get(\"$umlName\", qualifier, $oclTypeWoTP.class);");
            } else {
                add("    return ($oclType)get(\"$umlName\", $oclTypeWoTP.class);");
            }
            add("}");
        }
    }

    @Override
    protected void addOperationCode(OperationWrapper operation) throws Throwable {
        if (operation.isQuery()) {
            add("public $oclType $name($oclParameters) {");
            add("    return ($oclType)invoke(\"$name\", $oclParameterArray, $oclTypeWoTP.class);");
            add("}");
        }
    }

    @Override
    protected void addSetterCode(PropertyWrapper property) throws Throwable {

    }
}
