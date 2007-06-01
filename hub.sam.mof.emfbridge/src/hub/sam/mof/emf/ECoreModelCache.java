package hub.sam.mof.emf;

import hub.sam.mof.reflection.ObjectImpl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;

import cmof.NamedElement;
import cmof.reflection.Extent;

/**
 * Manages a EMF version of the edited AMOF meta-model. All TEF editors are
 * working with EMF. The OCL editor can only work with context objects that
 * are based on EMF. This class uses the AMOF-EMF bridge to temporarily
 * create a ECore meta-model as a substitute for the actual AMOF meta-model.
 */
public class ECoreModelCache {		
	
	private final Resource resource;
	private final Map<cmof.UmlClass, EObject> cache = new HashMap<cmof.UmlClass, EObject>();		
	
	/**
	 * @param extent
	 *            The extent with the meta-model that has to be replaced by
	 *            a Ecore meta-model.
	 * @param emfDomain
	 *            The editing domain that the Ecore meta-model will be
	 *            stored in.
	 */
	public ECoreModelCache(Extent extent, EditingDomain emfDomain) {		
		try {
			ResourceSet resourceSet = emfDomain.getResourceSet();			
			URI fileURI = URI.createFileURI(File.createTempFile("masmodelcache", ".ecore").getAbsolutePath());				
			resource = resourceSet.createResource(fileURI);		
			EcoreGenerator generator = new EcoreGenerator(resource);
			
			List<cmof.Package> packages = new Vector<cmof.Package>();
			for (Object obj: extent.outermostComposites()) {
				if (obj instanceof cmof.Package) {
					packages.add((cmof.Package)obj);
				}					
			}
			generator.generateEcorModel(packages);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * @param arbitraryObject
	 *            An abitrary object of the extent with the meta-model that
	 *            has to be replaced by a Ecore meta-model. It is just a way
	 *            to identify an extent, when the extent itself is not
	 *            available.
	 * @param emfDomain
	 *            The editing domain that the Ecore meta-model will be
	 *            stored in.
	 */
	public ECoreModelCache(cmof.reflection.Object arbitraryObject, EditingDomain emfDomain) {
		this(((ObjectImpl)arbitraryObject).getExtent(), emfDomain);			
	}		
	
	public EObject getContext(cmof.UmlClass aClass) {
		EObject result = cache.get(aClass);
		if (result == null) {
			TreeIterator i = resource.getAllContents();
			while(i.hasNext()) {
				Object obj = i.next();
				if (obj instanceof EClass) {
					if (compare(aClass, (EClass)obj)) {
						result = (EClass)obj;
						cache.put(aClass, result);
					}
				}
			}
		}
		return result;
	}
	
	private boolean compare(NamedElement mof, ENamedElement emf) {
		try {
			if (mof == emf) {
				return true;
			} else if (mof == null || emf == null) {
				return false;
			}
			if (mof.getName().equals(emf.getName())) {
				return compare(mof.getNamespace(), (ENamedElement)emf.eContainer());
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
}
