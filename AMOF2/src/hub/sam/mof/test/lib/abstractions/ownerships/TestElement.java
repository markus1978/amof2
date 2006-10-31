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

package hub.sam.mof.test.lib.abstractions.ownerships;

import cmof.Package;
import cmof.common.ReflectiveCollection;
import hub.sam.mof.test.lib.AbstractTestRepository;
import hub.sam.mof.util.SetImpl;
import concretecore.concreteabstractions.concreteownerships.*;

public class TestElement extends AbstractTestRepository {

	public TestElement() {
		super("test ownerships element");
	}
	
	private concreteownershipsFactory factory;
	
	public void testAll() {
		factory = (concreteownershipsFactory) repository.createFactory(modelExtent, (Package) metaModelExtent.query(ABSTRACTIONS_PACKAGE_NAME + "Package:concreteownerships"));
		
		Element e1 = factory.createElement();
		Element e2 = factory.createElement();
		Element e3 = factory.createElement();
		Element e4 = factory.createElement();
		
		e1.getOwnedElement().add(e2);
		e1.getOwnedElement().add(e3);
		e3.getOwnedElement().add(e4);
		
		assertEquals(true, e1.mustBeOwned());
		
		ReflectiveCollection< Element> c1 = new SetImpl<Element>();
		ReflectiveCollection< Element> c2 = new SetImpl<Element>();
		
		c2.add(e4);
		c1.add(e3);
		c1.add(e2);
		
		assertEquals(true, e3.allOwnedElements().containsAll(c2));
		assertEquals(true, e1.allOwnedElements().containsAll(c1));
		assertEquals(false, e3.allOwnedElements().containsAll(c1));
	}
}
