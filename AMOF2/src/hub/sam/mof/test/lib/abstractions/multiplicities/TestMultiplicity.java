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

package hub.sam.mof.test.lib.abstractions.multiplicities;

import cmof.Package;
import concretecore.concreteabstractions.concretemultiplicities.*;
import hub.sam.mof.test.lib.AbstractTestRepository;

public class TestMultiplicity extends AbstractTestRepository {

	public TestMultiplicity() {
		super("test mulitplicity");
	}
	
	protected concretemultiplicitiesFactory factory;
	
	
	public void testAll() {
		factory = (concretemultiplicitiesFactory) repository.createFactory(modelExtent, (Package) metaModelExtent.query(ABSTRACTIONS_PACKAGE_NAME + "Package:concretemultiplicities"));
		MultiplicityElement element1 = factory.createMultiplicityElement();
		MultiplicityElement element2 = factory.createMultiplicityElement();
		
		element1.setUpper(30);
		element1.setLower(1);
		
		element2.setUpper(28);
		element2.setLower(5);
		
		assertEquals(true,element1.includesCardinality(5));
		assertEquals(true,element1.includesCardinality(30));
		assertEquals(true,element1.includesCardinality(1));
		assertEquals(false,element1.includesCardinality(31));
		assertEquals(false,element1.includesCardinality(0));
		assertEquals(true, element1.includesMultiplicity(element1));
		assertEquals(true, element1.includesMultiplicity(element2));
		assertEquals(false, element2.includesMultiplicity(element1));
	}
	
	
}
