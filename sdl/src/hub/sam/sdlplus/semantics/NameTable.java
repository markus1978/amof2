package hub.sam.sdlplus.semantics;

import InfrastructureLibrary.Core.Abstractions.Elements.Element;
import InfrastructureLibrary.Core.Abstractions.Namespaces.NamedElement;
import InfrastructureLibrary.Core.Abstractions.Namespaces.Namespace;
import SDL.ConcreteSyntax.SdlIdentifier;
import SDL.ConcreteSyntax.SdlPathItem;
import SDL.ConcreteSyntax.SdlQualifier;
import SDL.ConcreteSyntax.SdlScopeUnitKind;
import SDL.SdlAgentKind;
import SDL.SdlAgentType;
import SDL.SdlPackage;
import SDL.SdlProcedure;
import SDL.SdlStateType;
import cmof.UmlClass;
import hub.sam.mopa.Name;
import hub.sam.mopa.Pattern;
import hub.sam.mopa.PatternClass;
import hub.sam.mopa.trees.TreeNode;

public class NameTable extends PatternClass {
    private Iterable<TreeNode> model;

    public NameTable(Iterable<TreeNode> model) {
        super();
        this.model = model;
    }

    /** uses resolveAIdentifierInContainer to resolve identifier in context, or
     * the surrounding namespaces of context. */
    public NamedElement resolveAIdentifier(SdlIdentifier identifier, Element context, UmlClass referenceTypeConstraint) {
        return resolveAIdentifierInContainer(identifier, getContainerOfElement((cmof.reflection.Object)context), referenceTypeConstraint);
    }

    /** resolves identifier in the given context (Container). Null is retured
     * if the identifier cannot be resolved at all, or when it can be resolved,
     * but resolves to a type not compatible with referenceTypeContstraint.
     */
    public NamedElement resolveAIdentifierInContainer(SdlIdentifier identifier, InfrastructureLibrary.Core.Abstractions.Namespaces.Namespace context, UmlClass referenceTypeConstraint) {
        SdlQualifier qualifier = identifier.getQualifier();
        NamedElement result;

        if (qualifier == null) {
            result = resolveEntityInContainer(identifier.getName(), context, referenceTypeConstraint);
        } else {
            context = resolveAQualifier(qualifier, model);
            if (context == null) {
                return null;
            }
            result = resolveEntityInContainer(identifier.getName(), context, referenceTypeConstraint);
        }

        return result;
    }

    private NamedElement resolveEntityWithInConainter(String name, Namespace context, UmlClass referenceTypeConstraint) {
        for (NamedElement namedElement: context.getMember()) {
            if (name.equals(namedElement.getName()) &&
                    ((cmof.reflection.Object)namedElement).getMetaClass().conformsTo(referenceTypeConstraint)) {
                return namedElement;
            } else if (namedElement instanceof Namespace) {
                NamedElement result = resolveEntityWithInConainter(name, (Namespace)namedElement, referenceTypeConstraint);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    private NamedElement resolveEntityInContainer(String name, Namespace context, UmlClass referenceTypeConstraint) {
        if (compareNames(name, context.getName()) &&
                ((cmof.reflection.Object)context).getMetaClass().conformsTo(referenceTypeConstraint)) {
            return context;
        }
        NamedElement result = resolveEntityWithInConainter(name, context, referenceTypeConstraint);
        if (result != null) {
            return result;
        }
        cmof.reflection.Object owner = ((cmof.reflection.Object)context).container();
        while(owner != null) {
            if (owner instanceof Namespace) {
                return resolveEntityInContainer(name, (Namespace)owner, referenceTypeConstraint);
            } else {
                owner = owner.container();
            }
        }
        return null;
    }

    private Namespace resolveAQualifier(SdlQualifier qualifier, Iterable<TreeNode> nodes) {
        try {
            return (Namespace)run(nodes, new Object[] {qualifier}, "resolveAQualifier", 0);
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

    //ns=Namespace provided (compareNames(ns.getName(), ((PathItem)qualifier.getPathItems().get(mopaDepth)).getName()))
    @Pattern(type=Namespace.class,variable="ns",set="resolveAQualifier")
    public void resolveAQualifier(SdlQualifier qualifier, @Name("ns")Namespace ns) throws Throwable {
        provided(compareNames(ns.getName(), (qualifier.getPathItem().get(actualDepth())).getName()));

        SdlPathItem currentItem = qualifier.getPathItem().get(actualDepth());
        if (currentItem.getKind() != null) {
            SdlScopeUnitKind kind = currentItem.getKind();
            if (kind.equals(SdlScopeUnitKind.PACKAGE)) {
                if (!(ns instanceof SdlPackage)) {
                    breakNode();
                }
            } else if (kind.equals(SdlScopeUnitKind.BLOCKTYPE)) {
                if (!(ns instanceof SdlAgentType)) {
                    if (!((SdlAgentType)ns).getKind().equals(SdlAgentKind.BLOCK)) {
                        breakNode();
                    }
                }
            } else if (kind.equals(SdlScopeUnitKind.PROCESSTYPE)) {
                if (!(ns instanceof SdlAgentType)) {
                    if (!((SdlAgentType)ns).getKind().equals(SdlAgentKind.PROCESS)) {
                        breakNode();
                    }
                }
            } else if (kind.equals(SdlScopeUnitKind.SYSTEMTYPE)) {
                if (!(ns instanceof SdlAgentType)) {
                    if (!((SdlAgentType)ns).getKind().equals(SdlAgentKind.SYSTEM)) {
                        breakNode();
                    }
                }
            } else if (kind.equals(SdlScopeUnitKind.STATETYPE)) {
                if (!(ns instanceof SdlStateType)) {
                    breakNode();
                }
            } else if (kind.equals(SdlScopeUnitKind.PROCEDURE)) {
                if (!(ns instanceof SdlProcedure)) {
                    breakNode();
                }
            } else {
                breakNode();
            }
        }
        if (actualDepth() < qualifier.getPathItem().size() - 1) {
            returnPattern(dive());
        } else {
            returnPattern(ns);
        }
    }

    /** Returns the Container that a given element is contained in. */
    public Namespace getContainerOfElement(cmof.reflection.Object element) {
        cmof.reflection.Object result = element.container();
        if (result == null) {
            return null;
        } else if (result instanceof Namespace) {
            return (Namespace)result;
        } else {
            return getContainerOfElement(result);
        }
    }

    /**
     * Helper function callback that compares two Strings. Can be overwriten to realize a NameTable for languages that
     * differ names other than Sting.equal does.
     */
    protected boolean compareNames(String one, String two) {
        if ((one == null) || (two == null)) {
            return false;
        }
        return one.equals(two);
    }
}
