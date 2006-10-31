package hub.sam.mof.as;

import hub.sam.util.Tree;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.oslo.ocl20.semantics.bridge.Environment;

import cmof.Feature;
import cmof.NamedElement;
import cmof.Namespace;
import cmof.RedefinableElement;
import cmof.cmofFactory;

public abstract class AsBehavior {
	
	protected String getOclExpressionForExpression(String expr) {
		return expr.substring(1, expr.length()-1);
	}
	
	protected NamedElement resolveQualifiedName(Iterable<String> qualifiedName, Iterable<? extends Namespace> topLevelNamespaces) 
			throws AsSemanticException {		
		NamedElement result = null;
		outerLoop: for(Namespace ns: topLevelNamespaces) {
			Iterator<String> i = qualifiedName.iterator();
			NamedElement ne = null;
			innerLoop: while(i.hasNext()) {
				String name = i.next();
				ne = resolveName(ns, name);
				if (ne == null) {
					// empty
				} else {
					if (ne instanceof Namespace) {
						ns = (Namespace)ne;
					} else {
						if (i.hasNext()) {
							throw new AsSemanticException("The name " + qualifiedName + " could not be resolved.");
						} else {
							break innerLoop;
						}
					}
				}
			}
			if (ne == null) {
				continue outerLoop;
			}
			if (result == null) {
				result = ne;
			} else {
				throw new AsSemanticException("Multiple matches for name " + qualifiedName + ": " + result.getQualifiedName() + " and " + ne.getQualifiedName() + ".");
			}
		}
		return result;
	}
	
	public abstract void staticSemantics(AsAnalysisEnvironment environment);
			
	private NamedElement resolveName(Namespace ns, String name) throws AsSemanticException {
		Collection<NamedElement> memberWithName = new Vector<NamedElement>();
		for(NamedElement member: ns.getMember()) {
			if (name.equals(member.getName())) {
				memberWithName.add(member);
			}
		}
		if (memberWithName.size() == 0) {
			return null;
		} else if (memberWithName.size() == 1) {
			return memberWithName.iterator().next();
		} else {
			Tree<RedefinableElement> redefinition = new Tree<RedefinableElement>();
			for (NamedElement member: memberWithName) {
				if (member instanceof RedefinableElement) {
					redefinition.putAll((RedefinableElement)member, ((RedefinableElement)member).getRedefinedElement());				
				} else {
					throw new AsSemanticException("The name " + name + " reference mulitple elements in namespace " + ns.getQualifiedName() + ".");
				}
			}
			memberWithName = new Vector<NamedElement>(); 
			for(RedefinableElement root: redefinition.getRoots()) {
				memberWithName.addAll(redefinition.getLeaves(root));
			}
			if (memberWithName.size() == 1) {
				return memberWithName.iterator().next();
			} else {
				throw new AsSemanticException("The name " + name + " reference mulitple final elements in namespace " + ns.getQualifiedName() + ".");
			}
		}
	}
	
	public abstract Object invoke(Object object, Object[] args, AsExecutionEnvironment environment);
}
