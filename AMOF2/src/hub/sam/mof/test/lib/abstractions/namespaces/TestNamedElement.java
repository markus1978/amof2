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

package hub.sam.mof.test.lib.abstractions.namespaces;


import cmof.Package;
import hub.sam.mof.test.lib.AbstractTestRepository;
import hub.sam.mof.util.ListImpl;
import concretecore.concreteabstractions.concretenamespaces.*;

public class TestNamedElement extends AbstractTestRepository {

	public TestNamedElement() {
		super("test NamedElement");
	}
	
	private concretenamespacesFactory factory;
	
	public void testAll() {
		factory = (concretenamespacesFactory) repository.createFactory(modelExtent, (Package) metaModelExtent.query(ABSTRACTIONS_PACKAGE_NAME + "Package:concretenamespaces"));
		
		Namespace ns1 = factory.createNamespace();
		Namespace ns2 = factory.createNamespace();
		Namespace ns3 = factory.createNamespace();
		Namespace ns4 = factory.createNamespace();
		
		ns1.setName("ns1");
		ns2.setName("ns2");
		ns3.setName("ns3");
		
		ns1.getOwnedMember().add(ns2);
		ns2.getOwnedMember().add(ns3);
		ns2.getOwnedMember().add(ns4);
		
		
		NamedElement e1 = factory.createNamedElement();
		NamedElement e2 = factory.createNamedElement();
		
		e1.setName("e1");
		e2.setName("e2");
		
		
		ns3.getOwnedMember().add(e1);
		
		
		
		assertEquals("::", e1.separator());
		assertEquals("ns1::ns2::ns3::e1", e1.getQualifiedName());
		assertEquals("e2", e2.getQualifiedName());
		
		ListImpl<Namespace> nestedNamespaces = new ListImpl<Namespace>();
		
		nestedNamespaces.add(ns3);
		nestedNamespaces.add(ns2);
		nestedNamespaces.add(ns1);

		assertEquals(true, nestedNamespaces.equals(e1.allNamespaces()));
		assertEquals(false, e2.allNamespaces().equals(nestedNamespaces));
		
		ns2.getOwnedMember().add(e2);
		
		assertEquals(true,e1.isDistinguishableFrom(e2, ns3));
		assertEquals(true,e1.isDistinguishableFrom(e2, ns2));
		assertEquals(false,e1.isDistinguishableFrom(e1, ns3));
	}
}
