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
import hub.sam.mof.instancemodel.ValueSpecification;
import hub.sam.mopa.Name;
import hub.sam.mopa.Pattern;
import hub.sam.mopa.PatternClass;
import hub.sam.mopa.PatternList;
import hub.sam.mopa.trees.TreeNode;

import java.util.Collection;
import java.util.HashSet;
import java.util.Vector;

public class MagicDrawXmi2ToMOF2 extends PatternClass implements XmiTransformator {

    private String getUnspecifiedValue(ClassInstance<XmiClassifier,String,String> from, String key, int index) {
        if (from == null || from.get(key) == null || from.get(key).getValues(null).size() <= index  ||
                from.get(key).getValues(null).get(index) == null ||
                from.get(key).getValues(null).get(index).asUnspecifiedValue() == null) {
            return null;
        }
        try {
            return (String)from.get(key).getValues(null).get(index).asUnspecifiedValue().getUnspecifiedData();
        } catch (NullPointerException e) {
            return null;
        }
    }

    private void removeAttribute(ClassInstance<XmiClassifier,String,String> from, String attr) {
        if (from.get(attr) != null) {
            if (from.get(attr).getValues(null).size() > 0) {
                for(ValueSpecification<XmiClassifier,String,String> value: from.get(attr).getValues(null)) {
                    if (value.asInstanceValue() != null) {
                        value.asInstanceValue().getInstance().setComposite(null);
                    }
                }
                from.get(attr).getValues(null).clear();
            }
        }
    }

    private final InstanceModel<XmiClassifier,String,String> model;
    private final Collection visistedNodes = new HashSet();

    public MagicDrawXmi2ToMOF2(InstanceModel<XmiClassifier,String,String> model) {
        super();
        this.model = model;
    }

    public Collection<ClassInstance<XmiClassifier,String,String> > getTopLevelElements() {
        // has to be implemented but is not needed
        return null;
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

    //Package.Class.Association.DataType.Enumeration.PrimitiveType.Operation
    @SuppressWarnings({"unchecked"})
    @Pattern( order = 100, atype = "Package.Class.Association.DataType.Enumeration.PrimitiveType.Operation")
    public void classifier() throws Throwable {
        if (!visistedNodes.contains(actualNode())) {
            visistedNodes.add(actualNode());
            dive();
        } else {
            breakPattern();
        }
    }

    //c=Comment
    @Pattern ( order = 99, atype = "Comment", variable = "c")
    public void classifier(@Name("c") ClassInstance<XmiClassifier,String,String> c) {
        c.setComposite(null);
        c.delete();
    }

    //p=Package(r=Realization)
    @PatternList( order = 98, value = {
        @Pattern( atype = "Package", variable = "p", children = "Realization"),
        @Pattern( atype = "Realization", variable = "r", name = "Realization")})
    public void realization(
            @Name("p")  ClassInstance<XmiClassifier,String,String> p,
            @Name("r") ClassInstance<XmiClassifier,String,String> r) {
        String realizingClassifier = getUnspecifiedValue(r, "client", 0);
        String realizedClassifier = getUnspecifiedValue(r, "supplier", 0);
        model.getInstance(realizingClassifier).addValue("metaClassifier", model.createInstanceValue(
                model.getInstance(realizedClassifier)), null);
        p.get("ownedMember").getValues(null).remove(model.createInstanceValue(r));
        r.setComposite(null);
        r.delete();
    }

    //c=Class(g=generalization:Generalization) -> {
    @PatternList( order = 97, value = {
        @Pattern( atype = "Class", variable = "c", children = "Generalization"),
        @Pattern( atype = "Generalization", property = "generalization", variable = "g", name = "Generalization")})
    public void generalization(
            @Name("c")  ClassInstance<XmiClassifier,String,String> c,
            @Name("g") ClassInstance<XmiClassifier,String,String> g) {
        String superClass = getUnspecifiedValue(g, "general",0);
        c.addValue("superClass", model.createInstanceValue(model.getInstance(superClass)), null);
        c.get("generalization").getValues(null).remove(model.createInstanceValue(g));
        g.setComposite(null);
        g.delete();
    }

    //p=Property.Parameter(d=defaultValue:LiteralString.LiteralInteger.LiteralBoolean)
    @PatternList( order = 96, value = {
        @Pattern( atype = "Property.Parameter", variable = "p", children = "value"),
        @Pattern( atype = "LiteralString.LiteralInteger.LiteralBoolean", property = "defaultValue", variable = "d", name = "value")})
    public void defaultValue(
            @Name("p")  ClassInstance<XmiClassifier,String,String> p,
            @Name("d") ClassInstance<XmiClassifier,String,String> d) {
        String defautValue = getUnspecifiedValue(d, "value", 0);
        if (defautValue == null) {
            defautValue = "0";
        }
        p.addValue("default", model.createPrimitiveValue(defautValue), null);
        p.get("defaultValue").getValues(null).remove(model.createInstanceValue(d));
        d.setComposite(null);
        d.delete();
    }

    //p=Property.Parameter(l=lowerValue:LiteralString.LiteralInteger) -> {
    @PatternList( order = 95, value = {
        @Pattern( atype = "Property.Parameter", variable = "p", children = "value"),
        @Pattern( atype = "LiteralString.LiteralInteger", property = "lowerValue", variable = "l", name = "value")})
    public void lowerValue(
            @Name("p")  ClassInstance<XmiClassifier,String,String> p,
            @Name("l") ClassInstance<XmiClassifier,String,String> l) {
        String lowerValue = getUnspecifiedValue(l, "value", 0);
        if (lowerValue == null) {
            lowerValue = "0";
        }
        p.addValue("lower", model.createPrimitiveValue(lowerValue), null);
        p.get("lowerValue").getValues(null).remove(model.createInstanceValue(l));
        l.setComposite(null);
        l.delete();
    }

    //p=Property.Parameter(u=upperValue:LiteralString.LiteralInteger.LiteralUnlimitedNatural)
    @PatternList( order = 94, value = {
        @Pattern( atype = "Property.Parameter", variable = "p", children = "value"),
        @Pattern( atype = "LiteralString.LiteralInteger.LiteralUnlimitedNatural", property = "upperValue", variable = "u", name = "value")})
    public void upperValue(
            @Name("p")  ClassInstance<XmiClassifier,String,String> p,
            @Name("u") ClassInstance<XmiClassifier,String,String> u) throws XmiException {
        String upperValue = getUnspecifiedValue(u, "value", 0);
        if (upperValue == null) {
            throw new XmiException("No valid literal value found.");
        }
        if (upperValue.equals("*")) {
            upperValue = "-1";
        }
        p.addValue("upper", model.createPrimitiveValue(upperValue), null);
        p.get("upperValue").getValues(null).remove(model.createInstanceValue(u));
        u.setComposite(null);
        u.delete();
    }

    //p=Property
    @Pattern( order = 93, atype = "Property", variable = "p")
    public void property(@Name("p") ClassInstance<XmiClassifier,String,String> p) {
        String aggregation = getUnspecifiedValue(p, "aggregation", 0);
        if (aggregation != null) {
            if (aggregation.equals("composite")) {
                p.addValue("isComposite", model.createPrimitiveValue("true"), null);
            }
            removeAttribute(p, "aggregation");
        } else {
            if (p.get("association") == null) {
                p.addValue("isComposite", model.createPrimitiveValue("true"), null);
            }
        }
    }
    
    //d=Dependency
    @Pattern( order = 92, atype = "Dependency", variable = "d")
    public void dependency(@Name("d") ClassInstance<XmiClassifier,String,String> d) {
        d.getComposite().get("ownedMember").getValues(null).remove(model.createInstanceValue(d));
        d.setComposite(null);
        d.delete();
    }
}
