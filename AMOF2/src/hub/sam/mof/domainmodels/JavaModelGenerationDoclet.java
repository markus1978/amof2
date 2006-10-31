package hub.sam.mof.domainmodels;

import cmof.Element;
import cmof.MultiplicityElement;
import cmof.Operation;
import cmof.Package;
import cmof.Parameter;
import cmof.ParameterDirectionKind;
import cmof.PrimitiveType;
import cmof.Property;
import cmof.Tag;
import cmof.Type;
import cmof.TypedElement;
import cmof.UmlClass;
import cmof.cmofFactory;
import cmof.common.ReflectiveCollection;
import cmof.reflection.Extent;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.ConstructorDoc;
import com.sun.javadoc.DocErrorReporter;
import com.sun.javadoc.ExecutableMemberDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.PackageDoc;
import com.sun.javadoc.RootDoc;
import core.abstractions.visibilities.VisibilityKind;
import hub.sam.mof.Repository;
import hub.sam.mof.instancemodel.MetaModelException;
import hub.sam.mof.xmi.XmiException;
import hub.sam.util.AbstractFluxBox;
import org.jdom.JDOMException;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

public class JavaModelGenerationDoclet {
    public static final String PROXY_TAG = "hub.sam.mof.domainmodels.proxy";
    public static final String CLASS_PROXY_TAG = "hub.sam.mof.domainmodels.classProxy";
    public static final String CONSTRUCTOR_TAG = "hub.sam.mof.domainmodels.constructor";

    private final Repository repository;
    private final Extent workingExtent;
    private final Package m3Model;
    private final cmofFactory factory;
    private final AbstractFluxBox<ClassDoc, UmlClass, Object> fluxBox;

    private final PrimitiveType booleanType;
    private final PrimitiveType integerType;
    private final PrimitiveType stringType;
    private final PrimitiveType unlimitedNaturalType;
    private final PrimitiveType objectType;

    private final UmlClass object;

    private JavaModelGenerationDoclet() {
        super();
        repository = Repository.getLocalRepository();
        workingExtent = repository.createExtent("working extent");
        m3Model = (Package)repository.getExtent(Repository.CMOF_EXTENT_NAME).query("Package:cmof");
        factory = (cmofFactory)repository.createFactory(workingExtent, m3Model);

        fluxBox = new AbstractFluxBox<ClassDoc, UmlClass, Object>() {
            @Override
            protected UmlClass createValue(ClassDoc key, Object params) {
                if (key == null) {
                    throw new RuntimeException("assert");
                }
                if (key.qualifiedName().equals("domainmodels.lang.Object")) {
                    return object;
                }
                UmlClass result = factory.createUmlClass();
                result.setVisibility(VisibilityKind.PUBLIC);
                if (key.isIncluded()) {
                    result.setName(key.name());
                } else {
                    result.setName(key.qualifiedTypeName());
                    tag(PROXY_TAG, null, result);
                }
                return result;
            }
        };

        Package primitives = factory.createPackage();
        primitives.setVisibility(VisibilityKind.PUBLIC);
        primitives.setName("PrimitiveTypes");

        booleanType = factory.createPrimitiveType();
        booleanType.setVisibility(VisibilityKind.PUBLIC);
        booleanType.setName(core.primitivetypes.Boolean.class.getSimpleName());
        booleanType.setPackage(primitives);

        integerType = factory.createPrimitiveType();
        integerType.setVisibility(VisibilityKind.PUBLIC);
        integerType.setName(core.primitivetypes.Integer.class.getSimpleName());
        integerType.setPackage(primitives);

        stringType = factory.createPrimitiveType();
        stringType.setName(core.primitivetypes.String.class.getSimpleName());
        stringType.setPackage(primitives);
        stringType.setVisibility(VisibilityKind.PUBLIC);

        unlimitedNaturalType = factory.createPrimitiveType();
        unlimitedNaturalType.setName(core.primitivetypes.UnlimitedNatural.class.getSimpleName());
        unlimitedNaturalType.setPackage(primitives);
        unlimitedNaturalType.setVisibility(VisibilityKind.PUBLIC);

        objectType = factory.createPrimitiveType();
        objectType.setName(core.primitivetypes.Object.class.getSimpleName());
        objectType.setPackage(primitives);
        objectType.setVisibility(VisibilityKind.PUBLIC);

        object = factory.createUmlClass();
        object.setVisibility(VisibilityKind.PUBLIC);
        object.setName("domainmodels.lang.Object");
        tag(PROXY_TAG, "domainmodels.lang.Object", object);
    }

    @SuppressWarnings({"OverlyLongMethod"})
    private void forClass(ClassDoc javaClass, Package owningPackage) {
        UmlClass mofClass = fluxBox.getObject(javaClass, null);
        UmlClass mofClassClass = null;

        if (owningPackage != null) {
            mofClass.setPackage(owningPackage);
        }
        mofClass.setName(javaClass.name());

        if (javaClass.isAbstract() || javaClass.isInterface()) {
            mofClass.setIsAbstract(true);
        }

        boolean hasStaticMethod = false;
        Loop:
        for(MethodDoc javaMethod: javaClass.methods(true)) {
            if (javaMethod.isStatic()) {
                hasStaticMethod = true;
                break Loop;
            }
        }
        if (hasStaticMethod || !(javaClass.isAbstract() || javaClass.isInterface())) {
            mofClassClass = factory.createUmlClass();
            mofClassClass.setPackage(owningPackage);
            mofClassClass.setName(javaClass.name() + "Class");
            mofClassClass.setVisibility(VisibilityKind.PUBLIC);
            tag(CLASS_PROXY_TAG, javaClass.qualifiedTypeName(), mofClassClass);
        }

        ReflectiveCollection<? extends UmlClass> superClasses = mofClass.getSuperClass();
        if (javaClass.superclass() != null) {
            if (!javaClass.superclass().qualifiedName().equals(Object.class.getCanonicalName())) {
                superClasses.add(fluxBox.getObject(javaClass.superclass(), null));
            }
        }
        for(ClassDoc superInterface: javaClass.interfaces()) {
            if (!javaClass.superclass().qualifiedName().equals(Object.class.getCanonicalName())) {
                superClasses.add(fluxBox.getObject(superInterface, null));
            }
        }

        tag(PROXY_TAG, javaClass.qualifiedTypeName(), mofClass);

        Collection<String> properties = new HashSet<String>();
        for(MethodDoc javaMethod: javaClass.methods(true)) {
            String name = javaMethod.name();
            if ((name.startsWith("get") || name.startsWith("is")) && !javaMethod.isStatic()
                    && javaMethod.parameters().length == 0) {
                properties.add(propertyName(name));
            }
        }

        AbstractFluxBox<String, Property, Object> propertyForName = new AbstractFluxBox<String, Property, Object>() {
            @Override
            protected Property createValue(String key, Object params) {
                Property result = factory.createProperty();
                result.setVisibility(VisibilityKind.PUBLIC);
                result.setName(key);
                result.setIsReadOnly(true);
                return result;
            }
        };
        for(MethodDoc javaMethod: javaClass.methods(true)) {
            String name = javaMethod.name();
            String propertyName = propertyName(name);
            if ((name.startsWith("set") || name.startsWith("setIs")) && !javaMethod.isStatic() &&
                    properties.contains(name)) {
                Property mofProperty = propertyForName.getObject(propertyName, null);
                mofProperty.setIsReadOnly(false);
                mofProperty.setUmlClass(mofClass);
            } else if ((name.startsWith("get") || name.startsWith("is")) && !javaMethod.isStatic()) {
                Property property = propertyForName.getObject(propertyName(name), null);
                configureTypedElement(property, javaMethod.returnType());
                property.setUmlClass(mofClass);
            } else {
                forMethod(javaMethod, javaClass, mofClass, mofClassClass);
            }
        }
        if (!(javaClass.isAbstract() || javaClass.isInterface())) {
            for(ConstructorDoc javaMethod: javaClass.constructors(true)) {
                forMethod(javaMethod, javaClass, mofClass, mofClassClass);
            }
        }
    }

    private Type simpleType(com.sun.javadoc.Type javaType) {
        if ((javaType.isPrimitive() && javaType.simpleTypeName().equals("boolean")) ||
                javaType.qualifiedTypeName().equals("domainmodels.lang.Boolean")) {
            return booleanType;
        } else if ((javaType.isPrimitive() && javaType.simpleTypeName().equals("int")) ||
                javaType.qualifiedTypeName().equals("domainmodels.lang.Integer")) {
            return integerType;
        } else if ((javaType.isPrimitive() && javaType.simpleTypeName().equals("long")) ||
                javaType.qualifiedTypeName().equals("domainmodels.lang.Long")) {
            return unlimitedNaturalType;
        } else if (javaType.qualifiedTypeName().equals("domainmodels.lang.String")) {
            return stringType;
        } else if (javaType.qualifiedTypeName().equals("domainmodels.lang.Object")) {
            return objectType;
        } else {
            if (javaType.asClassDoc() == null) {
                throw new RuntimeException("Unexpected type " + javaType);
            } else {
                return fluxBox.getObject(javaType.asClassDoc(), null);
            }
        }
    }

    private void configureTypedElement(TypedElement mofTyped, com.sun.javadoc.Type javaType) {
        boolean isCollection = false;
        boolean isOrdered = false;
        if (javaType.qualifiedTypeName().equals("domainmodels.util.Collection")) {
            isCollection = true;
            mofTyped.setType(object);
        } else if (javaType.qualifiedTypeName().equals("domainmodels.util.List")) {
            isCollection = true;
            isOrdered = true;
            mofTyped.setType(object);
        } else if (javaType.dimension().length() > 0) {
            isCollection = true;
            isOrdered = true;
            mofTyped.setType(simpleType(javaType));
        } else {
            mofTyped.setType(simpleType(javaType));
        }

        if (mofTyped instanceof MultiplicityElement) {
            MultiplicityElement mofMul = (MultiplicityElement)mofTyped;
            if (isCollection) {
                mofMul.setUpper(-1);
                mofMul.setLower(0);
            } else {
                mofMul.setUpper(1);
                mofMul.setLower(0);
            }
            mofMul.setIsOrdered(isOrdered);
        }
    }

    private void forMethod(ExecutableMemberDoc javaMethod, ClassDoc javaClass, UmlClass owningClass, UmlClass classProxy) {
        Operation mofOperation = factory.createOperation();
        mofOperation.setVisibility(VisibilityKind.PUBLIC);
        mofOperation.setName(javaMethod.name());

        com.sun.javadoc.Type returnType = null;
        if (javaMethod instanceof MethodDoc) {
            returnType = ((MethodDoc)javaMethod).returnType();
        }
        if (returnType == null || javaMethod.isStatic()) {
            classProxy.getOwnedOperation().add(mofOperation);
            if (returnType == null) {
                tag(CONSTRUCTOR_TAG, null, mofOperation);
                forParameter(javaClass, null, mofOperation, ParameterDirectionKind.RETURN);
                mofOperation.setType(simpleType(javaClass));
            } else if (!returnType.simpleTypeName().equals("void")) {
                forParameter(returnType, null, mofOperation, ParameterDirectionKind.RETURN);
                mofOperation.setType(simpleType(returnType));
            }
        } else {
            owningClass.getOwnedOperation().add(mofOperation);
            if (!returnType.simpleTypeName().equals("void")) {
                forParameter(returnType, null, mofOperation, ParameterDirectionKind.RETURN);
                mofOperation.setType(simpleType(returnType));
            }
        }
        for(com.sun.javadoc.Parameter javaParameter: javaMethod.parameters()) {
            forParameter(javaParameter.type(), javaParameter.name(), mofOperation, ParameterDirectionKind.INOUT);
        }
    }

    private void forParameter(com.sun.javadoc.Type javaType, String name, Operation owningOperation,
                              ParameterDirectionKind direction) {
        Parameter mofParameter = factory.createParameter();
        mofParameter.setVisibility(VisibilityKind.PUBLIC);
        mofParameter.setName(name);
        mofParameter.setOperation(owningOperation);
        mofParameter.setDirection(direction);
        configureTypedElement(mofParameter, javaType);
    }

    private String propertyName(String methodName) {
        String result;
        int prefixLength;
        if (methodName.startsWith("get") || methodName.startsWith("set")) {
            prefixLength = 3;
        } else if (methodName.startsWith("is")) {
            prefixLength = 2;
        } else if (methodName.startsWith("setIs")) {
            prefixLength = 5;
        } else {
            return null;
        }
        result = "";
        if (methodName.length() > prefixLength) {
            result += methodName.substring(prefixLength, prefixLength + 1).toUpperCase();
        }
        if (methodName.length() > prefixLength + 1) {
            result += methodName.substring(prefixLength + 1, methodName.length());
        }
        return result;
    }

    private void tag(String name, String value, Element element) {
        Tag tag = factory.createTag();
        tag.setName(name);
        if (value != null) {
            tag.setValue(value);
        }
        element.getTag().add(tag);
    }

    private void forPackage(PackageDoc javaPackage, Package owningPackage, String modelName) {
        Package mofPackage = factory.createPackage();
        mofPackage.setVisibility(VisibilityKind.PUBLIC);
        if (owningPackage != null) {
            mofPackage.setNestingPackage(owningPackage);
        }
        if (owningPackage != null) {
            mofPackage.setName(javaPackage.name());
        } else {
            String javaPackageName;
            if (javaPackage.name().lastIndexOf('.') != -1) {
                javaPackageName = javaPackage.name().substring(javaPackage.name().lastIndexOf('.') + 1);
            } else {
                javaPackageName = javaPackage.name();
            }
            mofPackage.setName(modelName + javaPackageName.substring(0,1).toUpperCase() +
                    javaPackageName.substring(1, javaPackageName.length()));
        }
        for (ClassDoc javaType: javaPackage.allClasses()) {
            if (javaType.isException()) {
                System.out.println("found a exception " + javaType);
            } else if (javaType.isEnum()) {
                System.out.println("found a enum " + javaType);
            } else if (javaType.isInterface()) {
                forClass(javaType, mofPackage);
            } else if (javaType.isClass()) {
                forClass(javaType, mofPackage);
            } else {
                throw new RuntimeException("Unexpected doc element kind for doclet: " + javaType);
            }
        }
    }

    private void writeOutput(String fileName) throws IOException, MetaModelException, XmiException, JDOMException {
        repository.writeExtentToXmi(fileName, m3Model, workingExtent);
    }

    public static String readOption(String[][] options, DocErrorReporter reporter, String parameterName) {
        String fileName = null;
        for(String[] option: options) {
            if (option[0].equals(parameterName)) {
                fileName = option[1];
            }
        }
        if (fileName == null) {
            if (reporter != null) {
                reporter.printError("Usage: javadoc -modelfile <modelfile> -modelname <modelname> ...");
            }
        }
        return fileName;
    }

    public static boolean validOptions(String[][] options, DocErrorReporter reporter) {
        return (readOption(options, reporter, "-modelfile") != null) &&
                (readOption(options, reporter, "-modelname") != null);
    }

    public static boolean start(RootDoc root) {
        JavaModelGenerationDoclet doclet = new JavaModelGenerationDoclet();
        for(PackageDoc aPackage: root.specifiedPackages()) {
            doclet.forPackage(aPackage, null, readOption(root.options(), null, "-modelname"));
        }
        try {
            doclet.writeOutput(readOption(root.options(), null, "-modelfile"));
        } catch (IOException e) {
            System.err.println("IO Error: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected Error: " + e.getMessage());
            e.printStackTrace(System.err);
            return false;
        }
        return true;
    }

    public static int optionLength(String option) {
        if (option.equals("-modelfile")) {
            return 2;
        } else if (option.equals("-modelname")) {
            return 2;
        } else {
            return 0;
        }
    }
}
