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

package hub.sam.mof.test.lib.abstractions.redefinitions;

import concretecore.concreteabstractions.concreteredefinitions.RedefinableElement;
import concretecore.concreteabstractions.concreteredefinitions.concreteredefinitionsFactory;
import concretecore.concreteabstractions.concretesuper.concretesuperFactory;
import concretecore.concreteabstractions.concretesuper.Classifier;
import hub.sam.mof.test.lib.AbstractTestRepository;
import cmof.Package;

public class TestRedfinableElement extends AbstractTestRepository {
	
	public TestRedfinableElement() {
		super("test redfineable Element");
	}
	
	private concreteredefinitionsFactory factory;
	
	
	public void testAll() {
		factory = (concreteredefinitionsFactory) repository.createFactory(modelExtent, (Package) metaModelExtent.query(ABSTRACTIONS_PACKAGE_NAME + "Package:concreteredefinitions"));
		
		RedefinableElement e1 = factory.createRedefinableElement();
		RedefinableElement e2 = factory.createRedefinableElement();
		RedefinableElement e3 = factory.createRedefinableElement();
		
		concretesuperFactory cFactory = (concretesuperFactory) repository.createFactory(modelExtent, (Package) metaModelExtent.query(ABSTRACTIONS_PACKAGE_NAME + "Package:concretesuper"));
		
		Classifier c1 = cFactory.createClassifier();
		Classifier c2 = cFactory.createClassifier();
		Classifier c3 = cFactory.createClassifier();
		
		e1.getRedefinitionContext().add(c1);
		e2.getRedefinitionContext().add(c3);
		e3.getRedefinitionContext().add(c2);
		
		c1.getGeneral().add(c3);
		
		assertEquals(true, e1.isRedefinitionContextValid(e2));
		assertEquals(false, e1.isRedefinitionContextValid(e3));
	}

}
