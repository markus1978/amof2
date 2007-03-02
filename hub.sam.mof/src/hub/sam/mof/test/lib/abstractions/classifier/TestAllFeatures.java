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

package hub.sam.mof.test.lib.abstractions.classifier;

import hub.sam.mof.test.lib.AbstractTestRepository;
import hub.sam.mof.util.SetImpl;
import cmof.Package;
import cmof.common.ReflectiveCollection;
import concretecore.concreteabstractions.concreteclassifiers.Classifier;
import concretecore.concreteabstractions.concreteclassifiers.Feature;
import concretecore.concreteabstractions.concreteclassifiers.concreteclassifiersFactory;

public class TestAllFeatures extends AbstractTestRepository {

	
	public TestAllFeatures() {
        super("classifier allFeatures() test");
    }
    
    protected concreteclassifiersFactory classifierFactory;
    
    
    public void testAllFeatures() {
        classifierFactory = (concreteclassifiersFactory) repository.createFactory(modelExtent, (Package) metaModelExtent.query(ABSTRACTIONS_PACKAGE_NAME + "Package:concreteclassifiers"));

        Classifier class1 = classifierFactory.createClassifier();
        Classifier class2 = classifierFactory.createClassifier();
        class1.setName("Class1");
        class2.setName("basic class2");
        ReflectiveCollection<Feature> features = new SetImpl<Feature>(10);

        for (int i=0; i<features.size(); ++i) {
        	Feature feature = (classifierFactory.createFeature());
        	feature.setName("feature" + i);
        	features.add(feature);
        }
        
        class1.getMember().addAll(features);
        assertEquals(true, features.containsAll(class1.allFeatures()) && class1.allFeatures().containsAll(features));
        Feature feature1 = classifierFactory.createFeature();
        feature1.setName("another feature");
        features.add(feature1);
        assertEquals(false, features.containsAll(class1.allFeatures()) && class1.allFeatures().containsAll(features));
    }
    
}
