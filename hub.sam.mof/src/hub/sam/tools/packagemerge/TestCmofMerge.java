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
package hub.sam.tools.packagemerge;

import hub.sam.mof.Repository;
import hub.sam.mof.instancemodel.MetaModelException;
import hub.sam.mof.xmi.XmiException;

import java.io.IOException;

import org.jdom.JDOMException;

import cmof.CMOF;
import cmof.NamedElement;
import cmof.Package;
import cmof.UmlClass;
import cmof.cmofFactory;
import cmof.exception.QueryParseException;
import cmof.reflection.Extent;
/**
 * @author kon
 *
 */
public class TestCmofMerge {

	/**
	 * 
	 */
	

	    protected static Repository repository;
	    protected static Extent metaModelExtent;
	    protected static Extent modelExtent;

	    
	
	public static void main(String[] args) throws QueryParseException, IOException, MetaModelException, XmiException, JDOMException {
		repository = Repository.getLocalRepository();
       repository.reset();
       modelExtent = repository.createExtent("testExtent");
       metaModelExtent = CMOF.createModel();
		ElementSelector selector = new ElementSelectorImpl((cmofFactory)repository.createFactory(modelExtent, (Package)metaModelExtent.query("Package:cmof")));
		PackageMerge merge = new PackageMerge(true);
		Package test = (Package)metaModelExtent.query("Package:cmof");
	
		cmofFactory fac = (cmofFactory)repository.createFactory(modelExtent, (Package)metaModelExtent.query("Package:cmof"));
		
		Package merged = fac.createPackage();
		Package merging = fac.createPackage();
		
		merged.setName("merged");
		merging.setName("merging");
		
		UmlClass a = fac.createUmlClass();
		UmlClass b = fac.createUmlClass();
		UmlClass aa = fac.createUmlClass();
		
		a.setName("A");
		b.setName("B");
		aa.setName("A");
		
		a.setPackage(merging);
		b.setPackage(merged);
		aa.setPackage(merged);
		
		merge.merge(merging, merged, selector);
		
		for (NamedElement element : merging.getMember()) {
			System.out.println(element.getName());
		}
		
		
		repository.writeExtentToXmi("mergeTest", (Package) metaModelExtent.query("Package:cmof"), modelExtent);
	}

}
