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

package hub.sam.mof.codegeneration;

import cmof.PackageableElement;
import cmof.PrimitiveType;
import cmof.UmlClass;
import hub.sam.mof.codegeneration.wrapper.FactoryWrapper;
import hub.sam.mof.codegeneration.wrapper.UmlClassWrapper;
import hub.sam.mof.remote.LocalObjectImpl;
import static hub.sam.mof.javamapping.JavaMapping.mapping;

import java.util.List;
import java.util.Vector;

public class PackageGenerator extends AbstractGenerator {
    private final StreamFactory streamFactory;

    public PackageGenerator(StreamFactory streamFactory) {
        super(streamFactory);
        this.streamFactory = streamFactory;
    }

    public void generate(final List<String> packageName, cmof.Package thePackage) throws GenerationException {
        System.out.println("Generate code for package " + thePackage.getQualifiedName());
        try {
            List<String>myPackageName = new Vector<String>(packageName);
            String qualifiedPackageName = (thePackage.getOwner() == null) ? mapping.getFullQualifiedJavaIdentifier(thePackage) :
                    mapping.getJavaIdentifier(thePackage);
            for (String singleName: qualifiedPackageName.split("\\.")) {
                myPackageName.add(singleName);
            }

            FactoryWrapper wrapper = new FactoryWrapper(packageName, thePackage);
            init(myPackageName, wrapper.getJavaFactory());
            add("public interface $javaFactory extends " + cmof.reflection.Factory.class.getCanonicalName() + " {");
            print(wrapper);

            FactoryImplGenerator factoryImplGenerator = null;
            FactoryImplGenerator factoryClientImplGenerator = null;
            if (!CodeGenerationConfiguration.actualConfig.isInterfacesOnly()) {
            	factoryImplGenerator = new FactoryImplGenerator(streamFactory);
            	factoryImplGenerator.init(myPackageName, wrapper);

                if (CodeGenerationConfiguration.getActualConfig().isGenerateEJBRemote()) {
                    factoryClientImplGenerator = new FactoryClientImplGenerator(streamFactory);
                    factoryClientImplGenerator.init(myPackageName, wrapper);
                }
            }

            GenerationException exceptions = new GenerationException("Errors during code generation: ");
            for (PackageableElement ownedType: thePackage.getPackagedElement()) {
            	try {
	                if (ownedType instanceof UmlClass) {
	                    UmlClass umlClass = (UmlClass)ownedType;
	                    if (!umlClass.isAbstract()) {
	                        add("    public " + getFullQualifiedJavaIdentifier(umlClass) + " create" + getJavaIdentifier(umlClass) + "();");
	                        print(null);
	                        if (!CodeGenerationConfiguration.actualConfig.isInterfacesOnly()) {
	                        	factoryImplGenerator.addType(umlClass);
                                if (CodeGenerationConfiguration.getActualConfig().isGenerateEJBRemote()) {
                                    factoryClientImplGenerator.addType(umlClass);
                                }
                            }
	                    }
	                    new ObjectProxyInterfaceGenerator(streamFactory, CodeGenerationConfiguration.actualConfig.isInterfacesOnly()).generate(myPackageName, umlClass);
	                    if (!CodeGenerationConfiguration.actualConfig.isInterfacesOnly()) {
                            new ObjectProxyDelegatorGenerator(streamFactory).generate(myPackageName, umlClass);
                            if (CodeGenerationConfiguration.actualConfig.isGenerateOcl()) {
                                new OclObjectProxyGenerator(streamFactory).generate(myPackageName, umlClass);
                            }
                        }
	                    if (!umlClass.isAbstract() && !CodeGenerationConfiguration.actualConfig.isInterfacesOnly()) {
	                        new ObjectProxyImplementationGenerator(streamFactory, "Impl").generate(myPackageName, umlClass);
                            if (CodeGenerationConfiguration.getActualConfig().isGenerateEJBRemote()) {
                                new ClientObjectProxyImplementationGenerator(streamFactory, "ClientImpl").generate(myPackageName, umlClass);
                            }
                            if (CodeGenerationConfiguration.getActualConfig().isGenerateRemote()) {
                            	new ObjectLocalProxyImplementationGenerator(streamFactory, "LocalImpl").generate(myPackageName, umlClass);
                            }
                        }
	                } else if (ownedType instanceof cmof.Enumeration) {
	                    new EnumerationGenerator(streamFactory).generate(myPackageName, (cmof.Enumeration)ownedType);
	                } else if (ownedType instanceof PrimitiveType) {
                        if (ownedType.getName().equals(core.primitivetypes.Integer.class.getSimpleName()) ||
                                ownedType.getName().equals(core.primitivetypes.Boolean.class.getSimpleName()) ||
                                ownedType.getName().equals(core.primitivetypes.String.class.getSimpleName()) ||
                                ownedType.getName().equals(core.primitivetypes.Object.class.getSimpleName()) ||
                                ownedType.getName().equals(core.primitivetypes.UnlimitedNatural.class.getSimpleName())) {
                           new PrimitiveTypeGenerator(streamFactory).generate(myPackageName, (cmof.PrimitiveType)ownedType);
                        }
                    } else if (ownedType instanceof cmof.Association) {
	                    // TODO
	                } else if (ownedType instanceof cmof.Type) {
	                    throw new RuntimeException("Code generation for meta-type " +
                                ((cmof.reflection.Object)ownedType).getMetaClass() + " not implemented");
	                }
            	} catch (GenerationException ex) {
            		exceptions.add(ex);
            	}
            }
            for (cmof.Package nestedPackage: thePackage.getNestedPackage()) {
            	try {
            		new PackageGenerator(streamFactory).generate(myPackageName, nestedPackage);
            	} catch (GenerationException ex) {
            		exceptions.add(ex);
            	}
            }
            if (!CodeGenerationConfiguration.actualConfig.isInterfacesOnly()) {
             	factoryImplGenerator.end(wrapper);
                if (CodeGenerationConfiguration.actualConfig.isGenerateEJBRemote()) {
                    factoryClientImplGenerator.end(wrapper);
                }
            }
            add("}");
            print(wrapper);
            if (exceptions.getExceptions().size() > 0) {
            	throw exceptions;
            }
        } catch (GenerationException ex) {
        	throw ex;
        } catch (Throwable ex) {
            ex.printStackTrace(System.out);
            throw new GenerationException(ex);
        }
    }
}
