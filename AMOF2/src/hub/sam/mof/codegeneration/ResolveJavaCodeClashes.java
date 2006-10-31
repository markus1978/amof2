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

import cmof.Property;
import cmof.Tag;
import cmof.cmofFactory;
import hub.sam.mof.mopatree.Mof2TreeNode;
import hub.sam.mopa.Name;
import hub.sam.mopa.Pattern;
import hub.sam.mopa.PatternClass;
import hub.sam.mopa.PatternList;
import hub.sam.mopa.trees.TreeNode;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ResolveJavaCodeClashes extends PatternClass {

    private Map<Property,Property> renames = null;
    private cmofFactory factory = null;

    public void resolveJavaCodeClashes(Collection<cmof.reflection.Object> outermostContainer,
                                       cmofFactory factory) {
        this.factory = factory;
        renames = new HashMap<Property,Property>();
        resolve(Mof2TreeNode.createNodes(outermostContainer));
        for (Property r1: renames.keySet()) {
            unique++;
            renameAll(r1, renames.get(r1));
        }
        renames = null;
        this.factory = null;
    }

    private void rename(Property property) {
        System.out.println("Had to rename " + property.getQualifiedName() + " to " + property.getName() + "_renamed");
        Tag renameTag = factory.createTag();
        renameTag.setName("javax.jmi.SubstituteName");
        renameTag.setValue(property.getName() + "_renamed" + Integer.toString(unique));
        renameTag.getElement().add(property);
        hub.sam.mof.javamapping.JavaMapping.mapping.addSubstituteNameTag(renameTag);
    }

    private static int unique = 0;

    private void renameAll(Property property, Property upto) {
        if (!upto.isConsistentWith(property) && property.getName().equals(upto.getName())) {
            rename(property);
            for (Property redefinedProperty: property.getRedefinedProperty()) {
                renameAll(redefinedProperty, upto);
            }
        }
    }

    private int renameDepth(Property property, Property upto) {
        int result = 0;
        if (!upto.isConsistentWith(property) && property.getName().equals(upto.getName())) {
            result += 1;
            int max = 0;
            int hold;
            for (Property redefinedProperty: property.getRedefinedProperty()) {
                hold = renameDepth(redefinedProperty, upto);
                if (hold > max) {
                    max = hold;
                }
            }
            result += max;
        }
        return result;
    }

    private void resolve(Collection<TreeNode> nodes) {
        try {
            run(nodes, null, "", 0);
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    //p=Property(r1=redefinedProperty:Property, r2=redefinedProperty:Property) provided (r1 != r2 &&
    //        r1.getName().equals(p.getName()) && r2.getName().equals(p.getName()) &&
    //        !r1.isConsistentWith(r2) && !r2.isConsistentWith(r1)) -> {
    @PatternList(order = 1, value = {
        @Pattern( type = Property.class, variable = "p", children = {"r1", "r2"}),
        @Pattern( type = Property.class, variable = "r1", property = "redefinedProperty", name = "r1"),
        @Pattern( type = Property.class, variable = "r2", property = "redefinedProperty", name = "r2")})
    public void property(@Name("p") Property p, @Name("r1") Property r1, @Name("r2") Property r2) {
        provided(r1 != r2 &&
                r1.getName().equals(p.getName()) && r2.getName().equals(p.getName()) &&
                !r1.isConsistentWith(r2) && !r2.isConsistentWith(r1));
        //System.out.println("WARNING property merge with clashing types: ");
        //System.out.println("    " + r1.getQualifiedName() + " with " + r2.getQualifiedName() + " in " + p.getQualifiedName());
        if (renameDepth(r1,r2) > renameDepth(r2,r1)) {
            renames.put(r2, r1);
        } else {
            renames.put(r1, r2);
        }
        continueTopLevelPattern();
    }

    @Pattern(order = 0, type = java.lang.Object.class )
    public void defaultRule() throws Throwable {
        dive();
    }
}
