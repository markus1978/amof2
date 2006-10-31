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

import hub.sam.mof.instancemodel.ClassInstance;
import hub.sam.mof.instancemodel.InstanceModel;
import hub.sam.mof.instancemodel.InstanceValue;
import hub.sam.mopa.Name;
import hub.sam.mopa.Pattern;
import hub.sam.mopa.PatternClass;
import hub.sam.mopa.PatternList;
import hub.sam.mopa.trees.TreeNode;

import java.util.Collection;
import java.util.List;
import java.util.Vector;

@SuppressWarnings({"ClassWithTooManyMethods"})
public class XmiUnisysUML1ToMOF2 extends PatternClass implements XmiTransformator {

    private ClassInstance<XmiClassifier,String,String> getInstanceValue(ClassInstance<XmiClassifier,String,String> from, String key, int index) {
        return from.get(key).getValues(null).get(index).asInstanceValue().getInstance();
    }

    private String getUnspecifiedValue(ClassInstance<XmiClassifier,String,String> from, String key, int index) {
        try {
            return (String)from.get(key).getValues(null).get(index).asUnspecifiedValue().getUnspecifiedData();
        } catch (NullPointerException e) {
            return null;
        }
    }

    private void removeAttribute(ClassInstance<XmiClassifier,String,String> from, String attr) {
        if (from.get(attr) != null) {
            if (from.get(attr).getValues(null).size() > 0) {
                from.get(attr).getValues(null).remove(0);
            }
        }
    }

    private final InstanceModel<XmiClassifier,String,String> model;
    private List<ClassInstance<XmiClassifier,String,String> > tlElements = null;

    public XmiUnisysUML1ToMOF2(InstanceModel<XmiClassifier,String,String> model) {
        super();
        this.model = model;
        this.tlElements = new Vector<ClassInstance<XmiClassifier,String,String>>();
        for (ClassInstance<XmiClassifier, String, String> instance : model.getInstances()) {
            instance.getClassifier().setNamespacePrefix("cmof");
        }
    }

    public Collection<ClassInstance<XmiClassifier,String,String> > getTopLevelElements() {
        return tlElements;
    }

    public void transform(TreeNode node) throws XmiException {
        Collection<TreeNode> nodes = new Vector<TreeNode>(1);
        nodes.add(node);
        try {
            run(nodes, null, "", 0);
        } catch (XmiException e) {
            throw e;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    //m= Model(dt=DataType)
    @PatternList(order = 100, value = {
        @Pattern( atype = "Model", variable = "m", children = "DataType"),
        @Pattern( atype = "DataType", variable = "dt", name = "DataType")})
    public void removeDataTypesFromModel(
            @Name("m")  ClassInstance<XmiClassifier,String,String> m,
            @Name("dt") ClassInstance<XmiClassifier,String,String> dt) {
        m.get("ownedElement").getValues(null).remove(dt);
        tlElements.add(dt);
    }

    //Model
    @Pattern(order = 99, atype = "Model" )
    public void modelDive() throws XmiException, Throwable {
        dive();
        if (tlElements.size() == 0) {
            throw new XmiException("Xmi does not contain a meta-model");
        }
    }

    //p=Package
    @Pattern(order = 98, atype = "Package", variable = "p" )
    public void unecessaryPackageAttributes(@Name("p") ClassInstance<XmiClassifier,String,String> p) throws Throwable {
        removeAttribute(p, "visibility");
        removeAttribute(p, "isSpecification");
        removeAttribute(p, "namespace");
        removeAttribute(p, "isAbstract");
        removeAttribute(p, "isLeaf");
        removeAttribute(p, "isRoot");
        removeAttribute(p, "clientDependency");
        removeAttribute(p, "supplierDependency");

        dive();
    }

    //p=Package(s=Stereotype) provided (!getUnspecifiedValue(s,"name",0).equals("metamodel"))
    @PatternList(order = 97, value = {
        @Pattern( atype = "Package", variable = "p", children = "Stereotype"),
        @Pattern( atype = "Stereotype", variable = "s", name = "Stereotype")})
    public void notMetamodelStereoType(
            @Name("p") ClassInstance<XmiClassifier,String,String> p,
            @Name("s") ClassInstance<XmiClassifier,String,String> s) throws XmiException {
        provided(!getUnspecifiedValue(s, "name", 0).equals("metamodel"));

        p.get("ownedElement").getValues(null).remove(model.createInstanceValue(s));

        if (getUnspecifiedValue(s, "name", 0).equals("enumeration") ||
                getUnspecifiedValue(s, "name", 0).equals("Enumeration")) {
            String ids = getUnspecifiedValue(s, "extendedElement", 0);
            if (ids.contains(" ")) {
                for (String id : ids.split(" ")) {
                    model.getInstance(id).getClassifier().setName("Enumeration");
                    transform(hub.sam.mof.xmi.mopa.XmiMopaTreeNode.createNode(model.getInstance(id)));
                }
            } else {
                model.getInstance(ids).getClassifier().setName("Enumeration");
                transform(hub.sam.mof.xmi.mopa.XmiMopaTreeNode.createNode(model.getInstance(ids)));
            }
        }
    }

    //p=Package(s=Stereotype) provided (getUnspecifiedValue(s,"name",0).equals("metamodel"))
    @PatternList(order = 96, value = {
        @Pattern( atype = "Package", variable = "p", children = "Stereotype"),
        @Pattern( atype = "Stereotype", variable = "s", name = "Stereotype")})
    public void metamodelStereoType(
            @Name("p") ClassInstance<XmiClassifier,String,String> p,
            @Name("s") ClassInstance<XmiClassifier,String,String> s) throws XmiException {
        provided(getUnspecifiedValue(s, "name", 0).equals("metamodel"));

        p.get("ownedElement").getValues(null).remove(model.createInstanceValue(s));

        removeAttribute(p, "visibility");
        removeAttribute(p, "isSpecification");
        removeAttribute(p, "namespace");
        removeAttribute(p, "isAbstract");
        removeAttribute(p, "isLeaf");
        removeAttribute(p, "isRoot");
        removeAttribute(p, "clientDependency");
        removeAttribute(p, "supplierDependency");

        tlElements.add(p);
    }

    //p=Package(e=ownedElement:Association.Class.DataType.PrimitiveType.Enumeration)
    @PatternList(order = 95, value = {
        @Pattern ( atype = "Package", variable = "p", children = "ownedElement" ),
        @Pattern ( atype = "Association.Class.DataType.PrimitiveType.Enumeration", property = "ownedElement",
                variable = "e", name = "ownedElement")})
    public void ownedType(
            @Name("p") ClassInstance<XmiClassifier,String,String> p,
            @Name("e") ClassInstance<XmiClassifier,String,String> e) {
        p.get("ownedElement").getValues(null).remove(model.createInstanceValue(e));
        p.addValue("ownedType", model.createInstanceValue(e), null);
    }

    //p=Package(np=ownedElement:Package)
    @PatternList(order = 94, value = {
        @Pattern ( atype = "Package", variable = "p", children = "ownedElement" ),
        @Pattern ( atype = "Package", property = "ownedElement",
                variable = "np", name = "ownedElement")})
    public void nestedPackage(
            @Name("p") ClassInstance<XmiClassifier,String,String> p,
            @Name("np") ClassInstance<XmiClassifier,String,String> np) {
        p.get("ownedElement").getValues(null).remove(model.createInstanceValue(np));
        p.addValue("nestedPackage", model.createInstanceValue(np), null);
    }

    //p=Package(uw=Constraint.Dependency)
    @PatternList(order = 93, value = {
        @Pattern ( atype = "Package", variable = "p", children = "ownedElement" ),
        @Pattern ( atype = "Constraint.Dependency", property = "ownedElement",
                variable = "uw", name = "ownedElement")})
    public void unwantedOwnedElement(
            @Name("p") ClassInstance<XmiClassifier,String,String> p,
            @Name("uw") ClassInstance<XmiClassifier,String,String> uw) {
        p.get("ownedElement").getValues(null).remove(model.createInstanceValue(uw));
    }

    //a=Association (end1=connection:AssociationEnd, end2=connection:AssociationEnd) provided (end1 != end2)
    @PatternList(order = 92, value = {
        @Pattern( atype = "Association", variable = "a", children = { "end1", "end2" }),
        @Pattern( atype = "AssociationEnd", variable = "end1", name = "end1"),
        @Pattern( atype = "AssociationEnd", variable = "end2", name = "end2")})
    public void associationEnds(
            @Name("a") ClassInstance<XmiClassifier,String,String> a,
            @Name("end1") ClassInstance<XmiClassifier,String,String> end1,
            @Name("end2") ClassInstance<XmiClassifier,String,String> end2) throws Throwable {
        provided(end1 != end2);

        InstanceValue<XmiClassifier,String,String> end1Value = model.createInstanceValue(end1);
        InstanceValue<XmiClassifier,String,String> end2Value = model.createInstanceValue(end2);

        end1.addValue("opposite", end2Value, null);
        end2.addValue("opposite", end1Value, null);
        a.addValue("memberEnd", end1Value, null);
        a.addValue("memberEnd", end2Value, null);
        dive();
        a.get("connection").getValues(null).remove(end1Value);
        a.get("connection").getValues(null).remove(end2Value);

        removeAttribute(a, "isLeaf");
        removeAttribute(a, "isRoot");
        removeAttribute(a, "isAbstract");
        removeAttribute(a, "isSpecification");

        continueTopLevelPattern();
    }

    //property=AssociationEnd.Attribute(Multiplicity(range=MultiplicityRange))
    @PatternList(order = 91, value = {
        @Pattern( atype = "AssociationEnd.Attribute", variable = "property", children = "mul"),
        @Pattern( atype = "Multiplicity", name = "mul", children = "range"),
        @Pattern( atype = "MultiplicityRange", variable = "range", name = "range")})
    public void mulitplicity(
            @Name("property") ClassInstance<XmiClassifier,String,String> property,
            @Name("range") ClassInstance<XmiClassifier,String,String> range) {
        property.addValue("upper", range.get("upper").getValues(null).get(0), null);
        property.addValue("lower", range.get("lower").getValues(null).get(0), null);
        property.get("multiplicity").getValues(null).remove(0);
    }

    //property=Attribute(expr=initialValue:Expression)
    @PatternList(order = 90, value = {
        @Pattern( atype = "Attribute", variable = "property", children = "initial"),
        @Pattern( atype = "Expression", property = "initialValue", variable = "expr", name = "initial")})
    public void initialValue(
            @Name("property") ClassInstance<XmiClassifier,String,String> property,
            @Name("expr") ClassInstance<XmiClassifier,String,String> expr) {
        String body = getUnspecifiedValue(expr, "body", 0);
        if (!"".equals(body) && body != null) {
            property.addValue("default", model.createPrimitiveValue(body), null);
        }
        removeAttribute(property, "initialValue");
    }

    //property=AssociationEnd
    @SuppressWarnings({"unchecked"})
    @Pattern(order = 89, atype = "AssociationEnd", variable = "property")
    public void associationEnd(@Name("property") ClassInstance<XmiClassifier,String,String> property) {
        boolean isNavigable = false;
        if (property.get("isNavigable") != null) {
            isNavigable = Boolean.TRUE.toString().equals(getUnspecifiedValue(property,"isNavigable",0));
        }

        if (!isNavigable) {
              ClassInstance<XmiClassifier,String,String> association =
                      (ClassInstance<XmiClassifier,String,String>)actualNode().getParent().getElement();
              association.addValue("ownedEnd", model.createInstanceValue(property), null);
              property.changeComposite(association);
        } else {
            ClassInstance<XmiClassifier,String,String> theClass =
                    model.getInstance(getUnspecifiedValue(getInstanceValue(property, "opposite", 0), "type", 0));
            theClass.addValue("ownedAttribute", model.createInstanceValue(property), null);
            property.changeComposite(theClass);
        }

        if ("ordered".equals(getUnspecifiedValue(property, "ordering", 0))) {
            property.addValue("isOrdered", model.createPrimitiveValue(Boolean.TRUE.toString()), null);
        }

        if ("composite".equals(getUnspecifiedValue(property, "aggregation", 0))) {
            getInstanceValue(property, "opposite", 0).addValue("isComposite", model.createPrimitiveValue(Boolean.TRUE.toString()), null);
        }
    }

    //property=AssociationEnd.Attribute
    @Pattern(order = 88, atype = "AssociationEnd.Attribute", variable = "property")
    public void property(@Name("property") ClassInstance<XmiClassifier,String,String> property) {
        property.getClassifier().setName("Property");

        if ("unchangeable".equals(getUnspecifiedValue(property, "changeability", 0))) {
            property.addValue("isReadOnly", model.createPrimitiveValue(Boolean.TRUE.toString()), null);
        }

        String name = getUnspecifiedValue(property, "name", 0);
        if (name.startsWith("/")) {
            property.addValue("isDerived", model.createPrimitiveValue(Boolean.TRUE.toString()), null);
            removeAttribute(property, "name");
            property.addValue("name", model.createPrimitiveValue(name.substring(1, name.length())), null);
        }
    }

    //feature=Property.Operation
    @Pattern(order = 87, atype = "Property.Operation", variable = "feature")
    public void feature(@Name("feature") ClassInstance<XmiClassifier,String,String> feature) throws Throwable {
        removeAttribute(feature, "ordering");
        removeAttribute(feature, "aggregation");
        //removeAttribute(feature, "visibility");
        removeAttribute(feature, "targetScope");
        removeAttribute(feature, "ownerScope");
        removeAttribute(feature, "constraint");
        removeAttribute(feature, "changeability");
        removeAttribute(feature, "isSpecification");
        removeAttribute(feature, "isNavigable");
        removeAttribute(feature, "isQuery");
        removeAttribute(feature, "concurrency");
        removeAttribute(feature, "isAbstract");
        removeAttribute(feature, "specification");
        removeAttribute(feature, "isRoot");
        removeAttribute(feature, "isLeaf");
        dive();
    }

    //op=Operation(param=parameter:Parameter)
    @PatternList(order = 86, value = {
        @Pattern( atype = "Operation", variable = "op", children = "parameter"),
        @Pattern( atype = "Parameter", property = "parameter", variable = "param", name = "parameter")})
    public void parameter(
            @Name("op") ClassInstance<XmiClassifier,String,String> op,
            @Name("param") ClassInstance<XmiClassifier,String,String> param) {
        op.get("parameter").getValues(null).remove(model.createInstanceValue(param));
        if ("return".equals(getUnspecifiedValue(param, "kind", 0))) {
            op.addValue("returnResult", model.createInstanceValue(param), null);
            op.addValue("type", model.createInstanceValue(model.getInstance(getUnspecifiedValue(param, "type",0))), null);
        } else {
            op.addValue("formalParameter", model.createInstanceValue(param), null);
        }
        removeAttribute(param, "visibility");
        removeAttribute(param, "isSpecification");
        removeAttribute(param, "kind");
        removeAttribute(param, "defaultValue");
    }

    //constraint=Constraint(expr=BooleanExpression) -> {
    @PatternList(order = 85, value = {
        @Pattern( atype = "Constraint", variable = "constraint", children = "BooleanExpression"),
        @Pattern( atype = "BooleanExpression", variable = "expr", name = "BooleanExpression")})
    public void constraint(
            @Name("constraint") ClassInstance<XmiClassifier,String,String> constraint,
            @Name("expr") ClassInstance<XmiClassifier,String,String> expr) {
        String ids = getUnspecifiedValue(constraint,"constrainedElement",0);
        if (ids.contains(" ")) {
            for (String id: ids.split(" ")) {
                model.getInstance(id).addValue("details", expr.get("body").getValues(null).get(0), null);
            }
        } else {
            model.getInstance(ids).addValue("details", expr.get("body").getValues(null).get(0), null);
        }
    }

    //theClass=Class(generalisation=Generalization)
    @PatternList(order = 84, value = {
        @Pattern( atype = "Class", variable = "theClass", children = "Generalization"),
        @Pattern( atype = "Generalization", variable = "generalisation", name = "Generalization")})
    public void generalization(
            @Name("theClass") ClassInstance<XmiClassifier,String,String> theClass,
            @Name("generalisation") ClassInstance<XmiClassifier,String,String> generalisation) {
        String ids = getUnspecifiedValue(generalisation, "parent", 0);
        if (ids.contains(" ")) {
            for(String id: ids.split(" ")) {
                theClass.addValue("superClass", model.createInstanceValue(model.getInstance(id)), null);
            }
        } else {
            theClass.addValue("superClass", model.createInstanceValue(model.getInstance(ids)), null);
        }
        theClass.get("ownedElement").getValues(null).remove(model.createInstanceValue(generalisation));
    }

    //theDataType=DataType(generalisation=Generalization)
    @PatternList(order = 83, value = {
        @Pattern( atype = "DataType", variable = "theDataType", children = "Generalization"),
        @Pattern( atype = "Generalization", variable = "generalisation", name = "Generalization")})
    public void dataTypeGeneralization(
            @Name("theDataType") ClassInstance<XmiClassifier,String,String> theDataType,
            @Name("generalisation") ClassInstance<XmiClassifier,String,String> generalisation) {
        String ids = getUnspecifiedValue(generalisation, "parent", 0);
        if (ids.contains(" ")) {
            for(String id: ids.split(" ")) {
                theDataType.addValue("superClass", model.createInstanceValue(model.getInstance(id)), null);
            }
        } else {
            theDataType.addValue("general", model.createInstanceValue(model.getInstance(ids)), null);
        }
        theDataType.get("ownedElement").getValues(null).remove(model.createInstanceValue(generalisation));
    }

    //theClass=Class.DataType(attribute=Attribute)
    @PatternList(order = 82, value = {
        @Pattern( atype = "Class.DataType", variable = "theClass", children = "Attribute"),
        @Pattern( atype = "Attribute", variable = "attribute", name = "Attribute")})
    public void renameFeature(
            @Name("theClass") ClassInstance<XmiClassifier,String,String> theClass,
            @Name("attribute") ClassInstance<XmiClassifier,String,String> attribute) {
        theClass.get("feature").getValues(null).remove(model.createInstanceValue(attribute));
        theClass.addValue("ownedAttribute", model.createInstanceValue(attribute), null);
    }

    //enumeration=Enumeration(attr=Attribute.Property)
    @PatternList(order = 81, value = {
        @Pattern( atype = "Enumeration", variable = "enumeration", children = "Attribute.Property"),
        @Pattern( atype = "Attribute.Property", variable = "attr", name = "Attribute.Property")})
    public void enumLiteral(
            @Name("enumeration") ClassInstance<XmiClassifier,String,String> enumeration,
            @Name("attr") ClassInstance<XmiClassifier,String,String> attr) {
        enumeration.get("feature").getValues(null).remove(model.createInstanceValue(attr));
        ClassInstance<XmiClassifier,String,String> literal = model.createInstance(null,
                new XmiClassifier("EnumerationLiteral", "cmof"), enumeration);
        enumeration.addValue("ownedLiteral", model.createInstanceValue(literal), null);
        literal.addValue("name", model.createPrimitiveValue(getUnspecifiedValue(attr, "name", 0)), null);
    }


    //theClass=Class.DataType.Enumeration
    @Pattern(order = 80, atype = "Class.DataType.Enumeration", variable = "theClass")
    public void unwantedClassifierProperties(@Name("theClass") ClassInstance<XmiClassifier,String,String> theClass)
            throws Throwable {
        removeAttribute(theClass, "visibility");
        removeAttribute(theClass, "isSpecification");
        removeAttribute(theClass, "isActive");
        removeAttribute(theClass, "namespace");
        removeAttribute(theClass, "isLeaf");
        removeAttribute(theClass, "isRoot");
        removeAttribute(theClass, "specialization");
        removeAttribute(theClass, "generalization");
        removeAttribute(theClass, "clientDependency");
        removeAttribute(theClass, "supplierDependency");
        dive();
    }

    //theClass=Class(op=feature:Operation)
    @PatternList(order = 79, value = {
        @Pattern( atype = "Class", variable = "theClass", children = "Operation"),
        @Pattern( atype = "Operation", property = "feature", variable = "op", name = "Operation")})
    public void classOperations(
            @Name("theClass") ClassInstance<XmiClassifier,String,String> theClass,
            @Name("op") ClassInstance<XmiClassifier,String,String> op) {
        theClass.get("feature").getValues(null).remove(model.createInstanceValue(op));
        theClass.addValue("ownedOperation", model.createInstanceValue(op), null);

    }

    //dt=DataType
    @Pattern(order = 78, atype = "DataType", variable = "dt")
    public void primitiveTypes(@Name("dt") ClassInstance<XmiClassifier,String,String> dt)
            throws Throwable {
        String name = getUnspecifiedValue(dt, "name", 0);
        if ("String".equals(name) ||
                "Boolean".equals(name) ||
                "Integer".equals(name) ||
                "UnlimitedNatural".equals(name)) {
            dt.getClassifier().setName("PrimitiveType");
        }
    }
}
