package hub.sam.mof.ocl;

import java.util.HashMap;
import java.util.Map;

import org.oslo.ocl20.OclProcessor;
import org.oslo.ocl20.semantics.SemanticsVisitor;
import org.oslo.ocl20.semantics.bridge.Environment;
import org.oslo.ocl20.semantics.bridge.ModelElement;
import org.oslo.ocl20.semantics.bridge.Namespace;

import cmof.Package;
import cmof.PackageableElement;
import cmof.Type;

public class MofNamespaceImpl implements Namespace {

	protected final OclProcessor processor;
	private final cmof.Package pkg;

	public MofNamespaceImpl(Package pkg, OclProcessor proc) {
		this.processor = proc;
		this.pkg = pkg;
	}	

	Map<String, ModelElement> _elements = new HashMap<String, ModelElement>();
	
	public ModelElement lookupOwnedElement(String name) {
		ModelElement mel = _elements.get(name);
		if (mel == null) {
			Type result = null;
			loop: for (PackageableElement element: pkg.getPackagedElement()) {
				if (element instanceof Type) {
					if (name.equals(element.getName())) {
						result = (Type)element;
						break loop;
					}
				}
			}	
			if (result == null)
				return null;
			else {
				mel = this.processor.getBridgeFactory().buildClassifier(result);
				_elements.put(name, mel);
			}
		}
		return mel;
	}

	private Namespace namespace = null;
	
	public Namespace getNamespace() {
		if (namespace == null)
			namespace = new MofNamespaceImpl(pkg.getNestingPackage(), this.processor);
		return namespace;
	}
	public void setNamespace(Namespace n) {
		namespace = n;
	}

	public Environment getEnvironmentWithoutParents() {
		Environment env = this.processor.getBridgeFactory().buildEnvironment();
		env.addNamespace(this);
		env.setParent(null);
		return env;
	}

	public Environment getEnvironmentWithParents() {
		if (this.getNamespace() == null) {
			return null;
		} else {
			Environment result = getEnvironmentWithoutParents();
			result.setParent(this.getNamespace().getEnvironmentWithParents());
			return result;
		}
	}

	public String getName() {
		return pkg.getName();
	}

	public String getFullName(String sep) {
		String name = "";
		Package pkg = this.pkg.getNestingPackage();
		while (pkg != null) {
			if (!name.equals("")) name = sep+name;
			name = pkg.getName()+name;
			pkg = pkg.getNestingPackage();
		}
		if (!name.equals("")) name += sep;
		return name+pkg.getName();
	}
	
	public void setName(String name) {
		// emtpy
	}

	public Object accept(SemanticsVisitor v, Object obj) {
		return v.visit(this,obj);
	}

	@Override
	public Object clone() {
		return new MofNamespaceImpl(pkg, this.processor);
	}
	public Object getDelegate() {
		return pkg;
	}
	
	@Override
	public String toString() {
		return pkg.getQualifiedName().replaceAll("\\.", "::");
	}
}
