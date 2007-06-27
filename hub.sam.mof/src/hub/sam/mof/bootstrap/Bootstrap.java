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

package hub.sam.mof.bootstrap;

import cmof.reflection.Extent;
import hub.sam.mof.codegeneration.Analyser;
import hub.sam.mof.codegeneration.CodeGenerationConfiguration;
import hub.sam.mof.codegeneration.PackageGenerator;
import hub.sam.mof.codegeneration.StreamFactory;
import hub.sam.mof.instancemodel.ClassInstance;
import hub.sam.mof.instancemodel.InstanceModel;
import hub.sam.mof.instancemodel.conversion.Converter;
import hub.sam.mof.xmi.Xmi2Reader;
import hub.sam.mof.xmi.XmiClassifier;

public class Bootstrap {
    public static Extent bootstrapMofExtent() throws Exception {
        InstanceModel<XmiClassifier,String,String> xmiModel = new InstanceModel<XmiClassifier,String,String>();
        Xmi2Reader xmiReader = new Xmi2Reader(xmiModel);
        System.out.println("parsing m3 xmi");
        xmiReader.read(new java.io.File("resources/models/bootstrap/bootstrap.xml"));
        InstanciatedXmiModel instantiatedXmiModel = new InstanciatedXmiModel(xmiModel);
        Instantiation conversion = new Instantiation(xmiModel, instantiatedXmiModel);
        Converter<XmiClassifier,String,String,
                ClassInstance<XmiClassifier,String,String>,ClassInstance<XmiClassifier,String,String>,
                ClassInstance<XmiClassifier,String,String>,ClassInstance<XmiClassifier,String,String>,java.lang.Object>
                converterForInstantiation = new Converter<XmiClassifier,String,String,
                        ClassInstance<XmiClassifier,String,String>,ClassInstance<XmiClassifier,String,String>,
                        ClassInstance<XmiClassifier,String,String>,ClassInstance<XmiClassifier,String,String>,java.lang.Object>(
                        conversion);
        System.out.println("instantiating xmi based model");
        converterForInstantiation.convert(xmiModel, instantiatedXmiModel);

        SelfClassification selfClassification = new SelfClassification();
        Converter<ClassInstance<XmiClassifier,String,String>,ClassInstance<XmiClassifier,String,String>,Object,
                ClassInstance<ClassInstance,ClassInstance,Object>,ClassInstance<ClassInstance,ClassInstance,Object>,
                ClassInstance<ClassInstance,ClassInstance,Object>,ClassInstance<ClassInstance,ClassInstance,Object>,java.lang.Object>
                converterForSelfClassification = new Converter<ClassInstance<XmiClassifier,String,String>,ClassInstance<XmiClassifier,String,String>,Object,
                        ClassInstance<ClassInstance,ClassInstance,Object>,ClassInstance<ClassInstance,ClassInstance,Object>,
                        ClassInstance<ClassInstance,ClassInstance,Object>,ClassInstance<ClassInstance,ClassInstance,Object>,java.lang.Object>(selfClassification);
        selfClassification.setConverter(converterForInstantiation,converterForSelfClassification);
        BootstrapModel bootstrapModel = new BootstrapModel();
        System.out.println("create self classification");
        converterForSelfClassification.convert(instantiatedXmiModel, bootstrapModel);

        bootstrapModel.setPropertyNames(selfClassification.getPropertyNames());
        bootstrapModel.setDependendProperties();
        System.out.println("create mof instance of mof");
        BootstrapExtent extent = new BootstrapExtent(bootstrapModel);
        return extent;
    }

    public static void main(String[] args) throws Exception {
        Extent mof = bootstrapMofExtent();
        System.out.println("analyse mof m3");
        new Analyser().analyse(mof);
        System.out.println("generate mof repository");
        StreamFactory streamFactory = new StreamFactory("resources/repository/generated-src/");
        CodeGenerationConfiguration.setActualConfig(
        		new CodeGenerationConfiguration(false, false, false, true));
        for (cmof.reflection.Object element: mof.objectsOfType(null, true)) {
            if (element instanceof cmof.Package) {
                if (((cmof.Package)element).getOwner() == null) {
                    new PackageGenerator(streamFactory).generate(new java.util.Vector<String>(), (cmof.Package)element);    
                }
            }
        }

        System.out.println("generate static mof model");
        hub.sam.mof.reflection.ExtentImpl.writeStaticModel("resources/repository/generated-src/cmof/" + BootstrapExtent.CMOF_EXTENT_NAME + ".java", "cmof", "CMOF", mof);
    }
}
