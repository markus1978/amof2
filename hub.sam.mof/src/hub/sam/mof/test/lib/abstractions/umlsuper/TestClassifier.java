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

package hub.sam.mof.test.lib.abstractions.umlsuper;

import concretecore.concreteabstractions.concretesuper.Classifier;
import concretecore.concreteabstractions.concretesuper.concretesuperFactory;
import concretecore.concreteabstractions.concretevisibilities.NamedElement;
import concretecore.concreteabstractions.concretevisibilities.concretevisibilitiesFactory;
import hub.sam.mof.mofinstancemodel.MofClassSemantics;
import hub.sam.mof.mofinstancemodel.MofClassifierSemantics;
import hub.sam.mof.test.lib.AbstractTestRepository;
import cmof.Package;
import cmof.Property;

public class TestClassifier extends AbstractTestRepository {
	
	public TestClassifier() {
		super("test uml super classifier");
	}
	
	private concretesuperFactory factory;
	
	
	public void testAll() {
		factory = (concretesuperFactory) repository.createFactory(modelExtent, (Package) metaModelExtent.query(ABSTRACTIONS_PACKAGE_NAME + "Package:concretesuper"));
		
		Classifier c1 = factory.createClassifier();
		Classifier c2 = factory.createClassifier();
		
		c1.setName("c1");
		c2.setName("c2");
		
		NamedElement e = ((concretevisibilitiesFactory) repository.createFactory(modelExtent, (Package) metaModelExtent.query(ABSTRACTIONS_PACKAGE_NAME + "Package:concretevisibilities"))).createNamedElement();
		
		Property member = MofClassifierSemantics.createClassClassifierForUmlClass(((cmof.reflection.Object)c1).getMetaClass()).getProperty("member");
		c1.getMember().add(e);
		c2.getGeneral().add(c1);
		
		assertEquals(true, c1.inheritableMembers(c2).contains(e));
		assertEquals(true, c2.maySpecializeType(c1));

	}

}
