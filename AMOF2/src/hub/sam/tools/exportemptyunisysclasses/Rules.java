package hub.sam.tools.exportemptyunisysclasses;

import hub.sam.mof.mopatree.Mof2TreeNode;
import hub.sam.mopa.Name;
import hub.sam.mopa.Pattern;
import hub.sam.mopa.PatternClass;
import hub.sam.mopa.trees.TreeNode;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Vector;

public class Rules extends PatternClass {

    public static void writeUnisysXmi(cmof.reflection.Object element, String fileName) throws IOException {
        Collection<cmof.reflection.Object> elements = new Vector<cmof.reflection.Object>();
        elements.add(element);
        writeUnisysXmi(elements, fileName);
    }

    public static void writeUnisysXmi(Collection<cmof.reflection.Object> elements, String fileName) throws IOException {
        PrintWriter out = new PrintWriter(new File(fileName));

        out.println("<?xml version = '1.0' encoding = 'ISO-8859-1' ?>");
        out.println("<!-- <!DOCTYPE XMI SYSTEM 'UMLX13-11.dtd' > -->");
        out.println("<XMI xmi.version = '1.1' xmlns:UML='href://org.omg/UML/1.3' timestamp = 'Wed Jun 29 16:01:20 2005' >");
        out.println(" <XMI.header>");
        out.println("  <XMI.documentation>");
        out.println("  <XMI.exporter>Unisys.JCR.1</XMI.exporter>");
        out.println("  <XMI.exporterVersion>1.3.4</XMI.exporterVersion>");
        out.println("  </XMI.documentation>");
        out.println("  <XMI.metamodel xmi.name = 'UML' xmi.version = '1.3'/>");
        out.println(" </XMI.header>");
        out.println("<XMI.content>");
        out.println("<!-- ==================== test    [Model] ==================== -->");
        out.println("<UML:Model xmi.id = 'G.0' ");
        out.println("  name = 'test' visibility = 'public' isSpecification = 'false' ");
        out.println("  isRoot = 'false' isLeaf = 'false' isAbstract = 'false' >");
        out.println("  <UML:Namespace.ownedElement>");

        for (cmof.reflection.Object element: elements) {
            Rules.writeXml(out, Mof2TreeNode.createNode(element));
        }

        out.println("  </UML:Namespace.ownedElement>");
        out.println("</UML:Model>");
        out.println("</XMI.content>");
        out.println("</XMI>");
        out.close();
    }

    private static int unique = 0;

    public static void writeXml(PrintWriter out, TreeNode node) {
        Collection<TreeNode> nodes = new Vector<TreeNode>(1);
        nodes.add(node);
        try {
            new Rules(out).run(nodes, null, "", 0);
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    private final PrintWriter out;

    Rules(PrintWriter out) {
        super();
        this.out = out;
    }

    //pkg=cmof.Package
    @Pattern( order = 1, type = cmof.Package.class, variable = "pkg")
    public void packageRule(@Name("pkg") cmof.Package pkg) throws Throwable {
        out.println("<UML:Package xmi.id='" + unique++ + "' name = '" + pkg.getName() + "' visibility = 'public' isSpecification = 'false' isRoot = 'false' isLeaf = 'false' isAbstract = 'false'>");
        out.println("  <UML:Namespace.ownedElement>");
        dive();
        out.println("  </UML:Namespace.ownedElement>");
        out.println("</UML:Package>");
    }

    //cl=cmof.UmlClass
    @Pattern( order = 0, type = cmof.UmlClass.class, variable = "cl")
    public void classRule(@Name("cl") cmof.UmlClass cl) throws Throwable {
        out.println("<UML:Class xmi.id='" + cl.getQualifiedName() + "' name = '" + cl.getName() + "' visibility = 'public' isSpecification = 'false' isAbstract = '" + (cl.isAbstract() ? "true" : "false") +
                "' isActive = 'false' isRoot = '" + (cl.getSuperClass().size() == 0 ? "true" : "false") +
                "' isLeaf = 'false'/>");
        for (cmof.UmlClass superClass: cl.getSuperClass()) {
            out.println("<UML:Generalization xmi.id='" + unique++ + "' supertype = '" + superClass.getQualifiedName() +
                    "' subtype = '" + cl.getQualifiedName() + "' visibility = 'public'/>");
        }
    }
}
