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

package hub.sam.mof.test;

import hub.sam.mof.test.bugs.MisreadAggregationOnMDImport;
import hub.sam.mof.test.bugs.ReflectiveSequenceExtension;
import hub.sam.mof.test.bugs.ReflectiveSequenceTest;
import hub.sam.mof.test.lib.abstractions.behavioralfeatures.TestIsDistinguishAbleFrom;
import hub.sam.mof.test.lib.abstractions.classifier.TestAllFeatures;
import hub.sam.mof.test.lib.abstractions.generalzations.TestConformsTo;
import hub.sam.mof.test.lib.abstractions.generalzations.TestGetGeneral;
import hub.sam.mof.test.lib.abstractions.generalzations.TestParents;
import hub.sam.mof.test.lib.abstractions.multiplicities.TestMultiplicity;
import hub.sam.mof.test.lib.abstractions.namespaces.TestNamedElement;
import hub.sam.mof.test.lib.abstractions.namespaces.TestNamespace;
import hub.sam.mof.test.lib.abstractions.ownerships.TestElement;
import hub.sam.mof.test.lib.abstractions.redefinitions.TestRedfinableElement;
import hub.sam.mof.test.lib.abstractions.umlsuper.TestClassifier;
import hub.sam.mof.test.qualifier.QualifierTest;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

@SuppressWarnings({"OverlyCoupledClass"})
public class Main extends TestCase {

    @SuppressWarnings({"OverlyCoupledMethod"})
    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(BootstrapM3Model.class);
        suite.addTestSuite(XmiM2.class);
        suite.addTestSuite(XmiMagicDraw.class);
        suite.addTestSuite(UpdateGraph.class);
        suite.addTestSuite(CustomCode.class);
        suite.addTestSuite(ClientServer.class);
        suite.addTestSuite(DomainModelsTest.class);
        suite.addTestSuite(Misc.class);
        suite.addTestSuite(OclTests.class);

        suite.addTestSuite(hub.sam.mof.merge.MergeTest.class);

        suite.addTestSuite(TestIsDistinguishAbleFrom.class);
        suite.addTestSuite(TestAllFeatures.class);
        suite.addTestSuite(TestGetGeneral.class);
        suite.addTestSuite(TestParents.class);
        suite.addTestSuite(TestConformsTo.class);
        suite.addTestSuite(TestMultiplicity.class);
        suite.addTestSuite(TestNamedElement.class);
        suite.addTestSuite(TestNamespace.class);
        suite.addTestSuite(TestElement.class);
        suite.addTestSuite(TestRedfinableElement.class);
        suite.addTestSuite(TestClassifier.class);

        // TODO: update tests
        //suite.addTestSuite(PropertyChangeNotification.class);
        //suite.addTestSuite(ReflectiveColSeqToJavaWrapper.class);        
        suite.addTestSuite(ReflectiveSequenceExtension.class);
        suite.addTestSuite(JavaTypeTest.class);
        suite.addTestSuite(QualifierTest.class);
        suite.addTestSuite(JOclTest.class);
        
        suite.addTestSuite(ReflectiveSequenceTest.class);
        suite.addTestSuite(MisreadAggregationOnMDImport.class);
        
        return suite;
    }
}
