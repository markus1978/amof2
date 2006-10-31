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

package hub.sam.mof.test.lib.abstractions.behavioralfeatures;

import hub.sam.mof.test.lib.AbstractTestRepository;
import cmof.Package;
import concretecore.concreteabstractions.concretebehavioralfeatures.BehavioralFeature;
import concretecore.concreteabstractions.concretebehavioralfeatures.concretebehavioralfeaturesFactory;
import concretecore.concreteabstractions.concretenamespaces.Namespace;
import concretecore.concreteabstractions.concretenamespaces.concretenamespacesFactory;
import concretecore.concreteabstractions.concretetypedelements.Type;
import concretecore.concreteabstractions.concretetypedelements.concretetypedelementsFactory;
import concretecore.concreteabstractions.concretebehavioralfeatures.Parameter;

public class TestIsDistinguishAbleFrom extends AbstractTestRepository {
	public TestIsDistinguishAbleFrom() {
        super("behavioralfeature isDistinguishAbleFrom() test");
    }
    
    protected concretebehavioralfeaturesFactory factory;
    
    
    public void testIsDistinguishAbleFrom() {
        factory = (concretebehavioralfeaturesFactory) repository.createFactory(modelExtent, (Package) metaModelExtent.query(ABSTRACTIONS_PACKAGE_NAME + "Package:concretebehavioralfeatures"));
    	
    	BehavioralFeature feature1 = factory.createBehavioralFeature();
    	BehavioralFeature feature2 = factory.createBehavioralFeature();
    	feature1.setName("feature1");
    	feature2.setName("feature1");
    	
    	Namespace ns = ((concretenamespacesFactory)repository.createFactory(modelExtent, (Package) metaModelExtent.query(ABSTRACTIONS_PACKAGE_NAME + "Package:concretenamespaces"))).createNamespace();
    	ns.setName("namespace");
    	
    	Parameter param1 = factory.createParameter();
    	Parameter param1_clone = factory.createParameter();
    	Parameter param2 = factory.createParameter();
    	
    	param1.setName("param1");
    	param1_clone.setName("param1");
    	
    	param2.setName("param2");
    	concretetypedelementsFactory typeFactory = ((concretetypedelementsFactory)repository.createFactory(modelExtent, (Package) metaModelExtent.query(ABSTRACTIONS_PACKAGE_NAME + "Package:concretetypedelements")));
    	
    	Type type = typeFactory.createType();
    	
    	type.setName("myType");
    	
    	param1.setType(type);
    	param1_clone.setType(type);

    	
    	Type newtype = typeFactory.createType();
    	newtype.setName("newMyType");
    	param2.setType(type);    	
    	
    	feature1.getParameter().add(param1);
    	feature2.getParameter().add(param1_clone);
    	ns.getMember().add(feature1);
    	ns.getMember().add(feature2);
    	
    	assertEquals(false, feature1.isDistinguishableFrom(feature1, ns));
    	assertEquals(false, feature1.isDistinguishableFrom(feature2, ns));
    	feature2.getParameter().add(param2);
    	assertEquals(true, feature1.isDistinguishableFrom(feature2, ns));
    	feature2.setName("feature2");
    	feature2.getParameter().remove(param2);
    	assertEquals(true, feature1.isDistinguishableFrom(feature2, ns));
    }

}
