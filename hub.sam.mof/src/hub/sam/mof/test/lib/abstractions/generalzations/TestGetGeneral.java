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

package hub.sam.mof.test.lib.abstractions.generalzations;

import concretecore.concreteabstractions.concretegeneralizations.*;
import hub.sam.mof.test.lib.AbstractTestRepository;
import hub.sam.mof.util.SetImpl;
import cmof.Package;
import cmof.common.ReflectiveCollection;

public class TestGetGeneral extends AbstractTestRepository {

	public TestGetGeneral() {
		super ("test getGeneral()");
	}
	
	protected concretegeneralizationsFactory factory;
	
	public void testGetGeneral() {
	factory = (concretegeneralizationsFactory) repository.createFactory(modelExtent, (Package) metaModelExtent.query(ABSTRACTIONS_PACKAGE_NAME + "Package:concretegeneralizations"));
	
	Classifier class1 = factory.createClassifier();
	Classifier class2 = factory.createClassifier();
	Classifier class3 = factory.createClassifier();
	Classifier class4 = factory.createClassifier();
	Classifier class5 = factory.createClassifier();
	Generalization generalization1 = factory.createGeneralization();
	Generalization generalization1_clone = factory.createGeneralization();
	Generalization generalization2 = factory.createGeneralization();
	Generalization generalization3 = factory.createGeneralization();
	Generalization generalization4 = factory.createGeneralization();
	
	generalization1.setGeneral(class1);
	generalization1_clone.setGeneral(class1);
	generalization2.setGeneral(class2);
	generalization3.setGeneral(class3);
	generalization4.setGeneral(class4);
	
	class1.setName("class1");
	class1.setIsAbstract(true);
	class2.setName("class2");
	class3.setName("class3");
	class3.getGeneralization().add(generalization1);
	class4.setName("class4");
	class4.getGeneralization().add(generalization1_clone);
	class3.getGeneralization().add(generalization2);
	class5.setName("class5");
	class5.getGeneralization().add(generalization3);
	class5.getGeneralization().add(generalization4);

	
	ReflectiveCollection<? extends core.abstractions.umlsuper.Classifier> allParents = new SetImpl<core.abstractions.umlsuper.Classifier>(5);
	allParents.add(class3);
	allParents.add(class4);
	assertEquals(true, class5.getGeneral().containsAll(allParents));
	assertEquals(false, class5.getGeneral().contains(class1));
	}
	
}
